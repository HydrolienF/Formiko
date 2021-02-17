package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;

public class PanneauActionInf extends Panneau{
  private static Image fond;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauActionInf(){
    setSize(Main.getDimX(),Main.getPa().getHeight());
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    //int len = Main.getPa().getNbrBouton();
    //int dim = Main.getPa().getHeight();
    g.drawImage(fond,0,0,this);
  }
  public static void chargerFond(){
    fond = image.getImage("basDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getPa().getHeight(),Image.SCALE_SMOOTH);
  }
}
