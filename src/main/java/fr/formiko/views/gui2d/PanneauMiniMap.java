package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanneauMiniMap extends Panneau {
  public PanneauMiniMap() {
    super();
    setSize(Main.getTailleElementGraphiqueX(400), Main.getTailleElementGraphiqueY(250));
    FBorder fBorder = new FBorder();
    fBorder.setColor(Main.getData().getButonBorderColor());
    setBorder(fBorder);
    System.out.println(this);

  }
}
