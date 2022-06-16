package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.View;

/**
*{@summary Personalized text area.}<br>
*It use Formiko color &#38; font. It is uneditable.<br>
*@author Hydrolien
*@lastEditedVersion 2.6
*/
public class FTextArea extends JTextArea {
  // private int maxLineWidth;
  // private int maxWidth;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *height is defined by counting lines.
  *@param s the text of the FTextArea
  *@param width the width used for the FTextArea
  *@lastEditedVersion 2.6
  */
  public FTextArea(String s, double width){
    super(s);
    // maxWidth=(int)width;
    setOpaque(true);
    setForeground(Color.BLACK);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    setEditable(false);
    setLineWrap(true);
    setWrapStyleWord(true);
    setSize((int)width,1);
    updateSize();
  }
  // GET SET -------------------------------------------------------------------
  public static int getDimY(){ return (int)(Main.getOp().getFontSizeText()*1.4);}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint function with a debug tool.}<br>
  *@lastEditedVersion 2.6
  */
  public void paintComponent(Graphics gTemp){
    super.paintComponent(gTemp);
    if(Main.getOp().getPaintHitBox()){
      Graphics2D g = (Graphics2D)gTemp;
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(getWidth()/100,getHeight()/100,1)));
      g.drawRect(0, 0, getWidth(), getHeight());
    }
  }
  /**
  *{@summary Update size of this depending of text.}<br>
  *@param width the width used to count how much line we need.
  *@lastEditedVersion 2.6
  */
  public void updateSize(){
    int lineCpt = countLines(getWidth());
    // setSize((int)(Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(getText()))), (int)(getFont().getSize()*1.2));
    // String text = "";
    // for (String s : getText().split("\n")) {
    //   if(s.length()>text.length()){
    //     text=s;
    //   }
    // }
    // setSize(new Dimension((int)(Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(text))+1), getDimY()*lineCpt));
    // int w;
    // if(maxLineWidth==0){w=maxWidth;}
    // else{w=math.min(maxWidth,maxLineWidth);}
    int w=getWidth();
    setSize(new Dimension(w, getDimY()*lineCpt));
  }
  /**
  *{@summary Count line function.}<br>
  *cf https://stackoverflow.com/questions/22328337/how-can-i-count-lines-in-jtextarea
  *@param width the width used to count how much line we need
  *@lastEditedVersion 2.6
  */
  private int countLines(double width) {
    javax.swing.text.PlainDocument doc = (javax.swing.text.PlainDocument) this.getDocument();
    double counting = 0;
    int tLen = this.getLineCount(); //number of /n on the text.
    // maxLineWidth=0;
    for (int i = 0; i < tLen; i++) { //for every bloc split by /n
      try {
        int start = this.getLineStartOffset(i);
        int length = this.getLineEndOffset(i) - start; // calculating the length of the line
        // if the width of the line in greater than the max width, the division would return the number of lines
        // double fullLineWidth = Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(doc.getText(start, length)));
        // double lineWidth = fullLineWidth / width;
        counting += Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(doc.getText(start, length)) / width);
        // if(maxLineWidth<fullLineWidth){maxLineWidth=(int)fullLineWidth+1;}
      } catch (javax.swing.text.BadLocationException ex) {
        System.err.println("ERROR\n" + ex.getMessage());
      }
    }
    // erreur.println(maxLineWidth);
    return (int)counting;
  }
}
