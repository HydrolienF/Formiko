package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class FTextPane extends JTextPane {
  public FTextPane(){
    super();
    setOpaque(true);
    setForeground(Color.BLACK);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    setEditable(false);
    // setLineWrap(true);
  }
  /**
  *{@summary paint function with a debug tool.}
  *@version 2.6
  */
  public void paintComponent(Graphics gTemp){
    super.paintComponent(gTemp);
    if(Main.getOp().getPaintHitBox()){
      Graphics2D g = (Graphics2D)gTemp;
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(getWidth()/100,getHeight()/100,1)));
      g.drawRect(0,0,getWidth(),getHeight());
    }
  }
}
