package fr.formiko.formiko;

import java.io.Serializable;
import java.awt.Color;

/**
*{@summary Grass blade.}<br>
* Bigger that moss blade.
*@author Hydrolien
*@lastEditedVersion 2.16
*/
public class BladeGrass extends Blade implements Serializable {
  private static Color col = new Color(0,142,14);

  public BladeGrass(){super(20);}

  @Override
  public Color getColor(){return col;}
}
