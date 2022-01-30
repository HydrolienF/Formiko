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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
        addGCreatureInfo();
      }else{
        erreur.alerte("FPanelInfoGCreature don't have a Creature and will be build as a FPanelInfo");
      }
      if(lp.isEmpty()){return null;}
      return super.build();
    }
    //private
    private void addGCreatureInfo(){
      if(!(c instanceof Fourmi)){
        erreur.alerte("Can not build FPanelInfoGCreature without an ant");
        return;
      }
      Fourmi f = (Fourmi)c;
      GCreature gc = c.getCase().getSortedGc(f);
      Function<Integer, Integer> fctAlpha = (color) -> {
        int alpha = (color >> 24) & 0xff;
        alpha=alpha*2/3;
        int mc = (alpha << 24) | 0x00ffffff;
        return (color | 0xff000000) & mc;
      };
      for (Creature ct : gc.toList()) {
        BufferedImage bi = Main.getData().getCreatureImage(ct);
        bi = image.resize(bi, yByElement);
        if(!(ct instanceof Fourmi) || (c instanceof Fourmi && !((Fourmi)(ct)).getFere().getJoueur().equals(f.getFere().getJoueur()))){
          image.editAllPixels(bi, fctAlpha);
        }
        lp.add(new FPanelObjetAId(ct, bi));
      }
      if(Main.getOp().getDrawSeeds() && (!Main.getOp().getDrawOnlyEatable() || Main.getPlayingJoueur().getEspece().getGranivore())){
        for (Graine s : c.getCase().getGg().toList()) {
          BufferedImage bi = Main.getData().getGraineImage(s);
          bi = image.resize(bi, yByElement);
          image.editAllPixels(bi, fctAlpha);
          lp.add(new FPanelObjetAId(s, bi));
        }
      }
    }
    // SUB-CLASS -----------------------------------------------------------------
    class FPanelObjetAId extends FPanel {
      private ObjetAId o;
      private BufferedImage bi;
      public FPanelObjetAId(ObjetAId o, BufferedImage bi){
        this.o=o;
        this.bi=bi;
        setSize(bi.getWidth(), bi.getHeight());
      }
      public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bi, 0, 0, this);
        if(o instanceof Creature){
          //TODO draw more info depending of o
        }else if(o instanceof Graine){
          //TODO draw more info depending of o
        }
      }
      //TODO add mouse listener for description of Creature.
    }
  }
}
