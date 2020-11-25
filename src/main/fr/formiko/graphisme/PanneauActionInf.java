package fr.formiko.graphisme;
//import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.image.image;
import java.awt.Image;
import java.awt.Graphics;

public class PanneauActionInf extends Panneau{
  private static Image fond;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauActionInf(){
    setSize(Main.getDimX(),Main.getPa().getHeight());
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //int len = Main.getPa().getNbrBouton();
    //int dim = Main.getPa().getHeight();
    g.drawImage(fond,0,0,this);
  }
  public static void chargerFond(){
    fond = image.getImage("basDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getPa().getHeight(),Image.SCALE_SMOOTH);
  }
}
