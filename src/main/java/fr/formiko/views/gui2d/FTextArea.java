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
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.View;

public class FTextArea extends JTextArea {
  public FTextArea(String s, double maxWidth){
    super(s);
    setOpaque(true);
    setForeground(Color.BLACK);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    setEditable(false);
    setLineWrap(true);
    setWrapStyleWord(true);
    int lineCpt = countLines(maxWidth);
    setSize(new Dimension((int)maxWidth, getDimY()*lineCpt));
  }
  private int countLines(double maxWidth) {
    javax.swing.text.PlainDocument doc = (javax.swing.text.PlainDocument) this.getDocument();
    double counting = 0;
    int tLen = this.getLineCount(); //number of /n on the text.
    for (int i = 0; i < tLen; i++) {
        try {
            int start = this.getLineStartOffset(i);
            int length = this.getLineEndOffset(i) - start; // calculating the length of the line
            // if the width of the line in greater than the max width, the division would return the number of lines
            counting += Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(doc.getText(start, length)) / maxWidth);
        } catch (javax.swing.text.BadLocationException ex) {
            System.err.println("ERROR\n" + ex.getMessage());
        }
    }
    return (int)counting;
  }
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.3);}
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
