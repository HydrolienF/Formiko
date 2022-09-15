package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.types.str;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;

/**
*{@summary Long button class used by menu.}
*@lastEditedVersion 2.30
*@author Hydrolien
*/
public class FButtonLong extends FButton implements MouseListener {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.30
  */
  public FButtonLong(String nameTemp, FPanel p, int action){
    super(nameTemp,p,action);
    setFont(Main.getFop().getFontTitle(nameTemp));
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary set nom &#38; update font if it can't print all the char.}
  *@lastEditedVersion 2.11
  */
  @Override
  public void setNom(String s){
    super.setNom(s);
    setFont(Main.getFop().getFontTitle(s));
  }
  // FUNCTIONS -----------------------------------------------------------------
}
