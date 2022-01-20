package fr.formiko.formiko;

import java.io.Serializable;
import java.awt.Color;

/**
*{@summary small moss blade.}<br>
*@author Hydrolien
*@version 2.16
*/
public class BladeMoss extends Blade implements Serializable {
  private static Color col = new Color(11,93,16); //moss

  public BladeMoss(){super(10);}

  @Override
  public Color getColor(){return col;}
}
