package fr.formiko.graphisme;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;

public class PanneauActionSup extends Panneau{
  public static Image fond;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauActionSup(){
    setSize(Main.getPa().getWidth(),Main.getPa().getHeight());
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    int len = Main.getPa().getNbrBouton();
    int dim = Main.getPa().getHeight();
    for (int i=0;i<len ;i++ ) {
      g.drawImage(fond, i*dim+Main.getPa().getBordureBouton(),0, this);
    }
  }
  public static void chargerFond(){
    fond = image.getImage("bordureBouton");
    fond = fond.getScaledInstance(Main.getPa().getHeight(), Main.getPa().getHeight(),Image.SCALE_SMOOTH);
  }
}
