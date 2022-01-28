package fr.formiko.formiko;

import java.io.Serializable;
import java.awt.Color;

/**
*{@summary Vegetal blade on sand.}<br>
*@author Hydrolien
*@lastEditedVersion 2.16
*/
public class BladeSand extends Blade implements Serializable {
  private static Color col = new Color(182,140,0);

  public BladeSand(){super(25);}

  @Override
  public Color getColor(){return col;}
}
