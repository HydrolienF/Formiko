package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;

public class BoutonLong extends FButton implements MouseListener {
  private static final long serialVersionUID = 221957878284545578L;
  private static int xBL; private static int yBL;
  //private static Color col = new Color(200,200,200,0);
  // CONSTRUCTORS --------------------------------------------------------------
  public BoutonLong(String nameTemp, FPanel p, int action){
    super(nameTemp,p,action);
    setPreferredSize(new Dimension(xBL,yBL));
    setFont(Main.getOp().getFontTitle(nameTemp));
    //this.setBackground(Color.BLUE); //couleur non visible.
    //this.setForeground(Color.RED); //couleur du texte et des contours
  }
  // GET SET -------------------------------------------------------------------
  public static int getXBL(){ return xBL;}
  public static void setXBL(int x){xBL=x;}
  public static int getYBL(){ return yBL;}
  public static void setYBL(int y){yBL=y;}
  /**
  *{@summary set nom &#38; update font if it can't print all the char.}
  *@lastEditedVersion 2.11
  */
  @Override
  public void setNom(String s){
    super.setNom(s);
    setFont(Main.getOp().getFontTitle(s));
  }
  // FUNCTIONS -----------------------------------------------------------------
  // private static String stripAccentIfNeed(String nameTemp){
  //   if(Main.getOp().getFontTitlePersonalised()){
  //     return str.stripAccents(nameTemp);
  //   }
  //   return nameTemp;
  // }
}
