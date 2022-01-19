package fr.formiko.formiko;

import java.io.Serializable;
import java.awt.Color;

/**
*{@summary Grass blade.}<br>
* Bigger that moss blade.
*@author Hydrolien
*@version 2.16
*/
public class BladeGrass extends Blade implements Serializable {
  private static Color col = new Color(0,142,14);

  public BladeGrass(){super();}

  @Override
  public byte getLength(){return 20;}
  @Override
  public Color getColor(){return col;}
}
