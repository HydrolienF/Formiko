package fr.formiko.views.gui2d;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Main;
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
      erreur.info(gc.length()+" voisins");
      for (Creature ct : gc.toList()) {
        BufferedImage bi = Main.getData().getCreatureImage(ct);
        bi = image.resize(bi, yByElement);
        if(ct instanceof Fourmi && c instanceof Fourmi && !((Fourmi)(ct)).getFere().getJoueur().equals(f.getFere().getJoueur())){
          // bi = bi.alpha/2 (draw in alpha.)
        }
        lp.add(new FPanelCreature(ct, bi));
      }
    }
  }
}
// SUB-CLASS -----------------------------------------------------------------
class FPanelCreature extends FPanel {
  private Creature c;
  private BufferedImage bi;
  public FPanelCreature(Creature c, BufferedImage bi){
    this.c=c;
    this.bi=bi;
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(bi, 0, 0, this);
    //TODO draw more info depending of c
  }
  //TODO add mouse listener for description of Creature.
}
