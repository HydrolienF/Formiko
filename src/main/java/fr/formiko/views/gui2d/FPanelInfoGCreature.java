package fr.formiko.views.gui2d;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetAId;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
*{@summary Panel that containt infos about a GCreature as a List of Panel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.18
*/
public class FPanelInfoGCreature extends FPanelInfo {
  // FUNCTIONS -----------------------------------------------------------------
  // @Override
  public static FPanelInfoGCreatureBuilder builder(){return new FPanelInfoGCreatureBuilder();}
  /**
  *{@summary Standard paintComponent.}<br>
  *@lastEditedVersion 2.18
  */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  /**
  *{@summary Standard Builder for this.}<br>
  *@author Hydrolien
  *@lastEditedVersion 2.18
  */
  // SUB-CLASS -----------------------------------------------------------------
  static class FPanelInfoGCreatureBuilder extends FPanelInfoBuilder {
    private Creature c;
    private static int boderFPanelObjetAId;
    /**
    *{@summary Function that change alpha.}<br>
    *@lastEditedVersion 2.18
    */
    private static Function<Integer, Integer> fctAlpha = (color) -> {
      int alpha = (color >> 24) & 0xff;
      alpha=alpha*2/3;
      int mc = (alpha << 24) | 0x00ffffff;
      return (color | 0xff000000) & mc;
    };
    // CONSTRUCTORS --------------------------------------------------------------

    // GET SET -------------------------------------------------------------------

    // FUNCTIONS -----------------------------------------------------------------
    //public
    /**
    *{@summary Standard Builder for this.}<br>
    *@lastEditedVersion 2.18
    */
    public FPanelInfoBuilder addCreaturesOnSameCase(Creature c){
      this.c=c;
      return this;
    }
    /**
    *{@summary Build including the tranfomation of Creature to Panel.}<br>
    *@lastEditedVersion 2.18
    */
    @Override
    public FPanelInfo build(){
      if(c!=null){
        //it add 7s for a Frame refrech from 20s (with 40 ants.) (7s where add only for drawing Carte)
        addGCreatureInfo();
      }else{
        erreur.alerte("FPanelInfoGCreature don't have a Creature and will be build as a FPanelInfo");
      }
      if(lp.isEmpty()){return null;}
      return super.build();
    }
    //private
    /**
    *{@summary Add all info from the Creatre.}<br>
    *Info are mostly the other Creature on the same Case.
    *@lastEditedVersion 2.18
    */
    private void addGCreatureInfo(){
      boderFPanelObjetAId=Main.getTailleElementGraphique(4);
      if(!(c instanceof Fourmi)){
        erreur.alerte("Can not build FPanelInfoGCreature without an ant");
        return;
      }
      Fourmi f = (Fourmi)c;
      GCreature gc = c.getCase().getSortedGc(f);
      gc.add(f);//TODO add head
      for (Creature ct : gc.toList()) {
        BufferedImage bi = Main.getData().getCreatureImage(ct);
        bi = image.resize(bi, yByElement-boderFPanelObjetAId*2);
        if(!(ct instanceof Fourmi) || (c instanceof Fourmi && !((Fourmi)(ct)).getFere().getJoueur().equals(f.getFere().getJoueur()))){
          image.editAllPixels(bi, fctAlpha);
        }
        lp.add(new FPanelObjetAId(ct, bi));
      }
      if(Main.getOp().getDrawSeeds() && (!Main.getOp().getDrawOnlyEatable() || Main.getPlayingJoueur().getEspece().getGranivore())){
        for (Graine s : c.getCase().getGg().toList()) {
          BufferedImage bi = Main.getData().getGraineImage(s);
          bi = image.resize(bi, yByElement-boderFPanelObjetAId*2);
          image.editAllPixels(bi, fctAlpha);
          lp.add(new FPanelObjetAId(s, bi));
        }
      }
    }
    // SUB-CLASS -----------------------------------------------------------------
    /**
    *{@summary Small panel that represent a creature.}<br>
    *@lastEditedVersion 2.18
    *@author Hydrolien
    */
    class FPanelObjetAId extends FPanel {
      private ObjetAId o;
      private BufferedImage bi;
      /**
      *{@summary Main constructor.}<br>
      *It add a MouseListener for description.
      *@lastEditedVersion 2.18
      */
      public FPanelObjetAId(ObjetAId o, BufferedImage bi){
        this.o=o;
        this.bi=bi;
        int size = math.max(bi.getWidth(), bi.getHeight())+boderFPanelObjetAId*2;
        setSize(size,size);
        addMouseListener(new MouseListenerEmpty(){
          /**
          *{@summary Update message description.}<br>
          *@lastEditedVersion 2.18
          */
          @Override
          public void mouseEntered(MouseEvent event) {
            getView().setMessageDesc(getDesc(), true);
          }
          /**
          *{@summary Remove message description.}<br>
          *@lastEditedVersion 2.18
          */
          @Override
          public void mouseExited(MouseEvent event) {
            getView().setMessageDesc("", true);
          }
          /**
          *{@summary Change playing ant.}<br>
          *@lastEditedVersion 2.18
          */
          @Override
          public void mouseReleased(MouseEvent event) {
            if(o instanceof Fourmi && ((Fourmi)o).getFere().getJoueur().equals(Main.getPlayingJoueur())){
              getView().getPb().setActionF(-2);
              getView().getPb().removePA();
              Main.getPartie().setAntIdToPlay(o.getId());
            }
          }
        });
        addMouseMotionListener(new DescMouseMotionListener());
      }
      /**
      *{@summary Return a description of this.}<br>
      *@lastEditedVersion 2.18
      */
      public String getDesc(){
        return o.toStringSmall();
      }
      /**
      *{@summary Paint this with a border, a centered image &#38; a small colored round.}<br>
      *@lastEditedVersion 2.18
      */
      public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bi, (getWidth()-bi.getWidth())/2, (getHeight()-bi.getHeight())/2, this);
        g.setColor(Color.BLACK);
        int border = Main.getTailleElementGraphique(2);
        if(!o.equals(Main.getPlayingAnt())){
          g.drawRoundRect(border/2, border/2, getWidth()-border, getHeight()-border, Main.getTailleElementGraphique(8), Main.getTailleElementGraphique(8));
        }
        ((Graphics2D)g).setStroke(new BasicStroke(border));
        if(o instanceof Creature){
          if(o instanceof Fourmi){
            Fourmi f = (Fourmi)o;
            if(f.getFere().getJoueur().equals(Main.getPlayingJoueur())){
              if(f.equals(Main.getPlayingAnt())){
                g.setColor(getData().getButtonColor(2));
                border = Main.getTailleElementGraphique(3);
                ((Graphics2D)g).setStroke(new BasicStroke(border));
                g.drawRoundRect(border/2, border/2, getWidth()-border, getHeight()-border, Main.getTailleElementGraphique(8), Main.getTailleElementGraphique(8));
              }
              if(f.getMaxAction()==0){
                g.setColor(getData().getButtonColor(6));
              }else if(f.getAction()==f.getMaxAction()){
                g.setColor(getData().getButtonColor(0));
              }else if (f.getAction()<1){
                g.setColor(getData().getButtonColor(3));
              }else{
                g.setColor(getData().getButtonColor(1));
              }
              g.fillOval(getHeight() - boderFPanelObjetAId*2, 0, boderFPanelObjetAId*2, boderFPanelObjetAId*2);
            }
          }
          Creature c = (Creature)o;
          if(c.isDead()){
            BufferedImage biD = getData().getImage("deadHead");
            biD=image.resize(biD, (yByElement-boderFPanelObjetAId*2)*2/3);
            image.editAllPixels(biD, fctAlpha);
            g.drawImage(biD, boderFPanelObjetAId, boderFPanelObjetAId, this);
          }
        }else if(o instanceof Graine){
          //Draw more info depending of o ? (open, openable)
        }
      }
    }
  }
}
