package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;

public class FPanelActionInf extends FPanel{

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelActionInf(){
    setSize(Main.getDimX(),FPanel.getView().getPa().getHeight());
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    g.drawImage(getData().getBackgroundPAI(),0,0,this);
  }
}
