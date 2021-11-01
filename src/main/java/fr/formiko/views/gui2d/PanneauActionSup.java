package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;

public class PanneauActionSup extends Panneau{

  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauActionSup(){
    setSize(Panneau.getView().getPa().getWidth(),Panneau.getView().getPa().getHeight());
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    int len = Panneau.getView().getPa().getNbrBouton();
    int dim = Panneau.getView().getPa().getHeight();
    for (int i=0;i<len ;i++ ) {
      g.drawImage(getData().getBackgroundPAS(), i*dim+Panneau.getView().getPa().getBorderButtonSize(),0, this);
    }
  }
}
