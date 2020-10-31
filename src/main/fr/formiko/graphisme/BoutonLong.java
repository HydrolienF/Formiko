package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Font;
import fr.formiko.formiko.Main;
import java.awt.Color;
import fr.formiko.formiko.Touches;

public class BoutonLong extends Bouton implements MouseListener {
  private static final long serialVersionUID = 221957878284545578L;
  private static int xBL; private static int yBL;
  //private static Color col = new Color(200,200,200,0);
  // CONSTRUCTEUR ---------------------------------------------------------------
  public BoutonLong(String str, Panneau p, int action){
    super(str,p,action);
    this.setPreferredSize(new Dimension(xBL,yBL));
    this.setFont(Main.getFont2());
    //this.setBackground(Color.BLUE); //couleur non visible.
    //this.setForeground(Color.RED); //couleur du texte et des contours
    addKeyListener(new Touches());
  }
  // GET SET --------------------------------------------------------------------
  public static int getXBL(){ return xBL;}
  public static void setXBL(int x){xBL=x;}
  public static int getYBL(){ return yBL;}
  public static void setYBL(int y){yBL=y;}
  // Fonctions propre -----------------------------------------------------------

}
