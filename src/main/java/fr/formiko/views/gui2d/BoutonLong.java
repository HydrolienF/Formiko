package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;

public class BoutonLong extends FButton implements MouseListener {
  private static final long serialVersionUID = 221957878284545578L;
  private static int xBL; private static int yBL;
  //private static Color col = new Color(200,200,200,0);
  // CONSTRUCTORS --------------------------------------------------------------
  public BoutonLong(String str, Panneau p, int action){
    super(str,p,action);
    this.setPreferredSize(new Dimension(xBL,yBL));
    this.setFont(Main.getFont2());
    //this.setBackground(Color.BLUE); //couleur non visible.
    //this.setForeground(Color.RED); //couleur du texte et des contours
  }
  // GET SET -------------------------------------------------------------------
  public static int getXBL(){ return xBL;}
  public static void setXBL(int x){xBL=x;}
  public static int getYBL(){ return yBL;}
  public static void setYBL(int y){yBL=y;}
  // FUNCTIONS -----------------------------------------------------------------

}
