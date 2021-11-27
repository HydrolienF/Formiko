package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

/**
*{@summary Standard TextField for Formiko.}
*@author Hydrolien
*@version 1.x
*/
public class FTextField extends JTextField {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor with a string &#38; default font.}
  *@version 1.x
  */
  public FTextField(String s){
    super(s);
    // setFontText(); //no need because it have been set as default font for all graphics components
  }
  public FTextField(){ this("");}
  // GET SET -------------------------------------------------------------------
  //setText et getText
  public void setBounds(int a, int b, int c){setBounds(a,b,c,FLabel.getDimY());}
  public void setFontText(Font fon){ this.setFont(fon);}
  public void setFontText(){ setFontText(Main.getFont1());}
  public void setFondTransparent(){setBackground(new Color(0,0,0,0));}
  // FUNCTIONS -----------------------------------------------------------------

}
