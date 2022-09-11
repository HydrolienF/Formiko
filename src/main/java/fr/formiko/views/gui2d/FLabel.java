package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
*{@summary Personalized JLabel.}<br>
*It use Formiko color &#38; font. It is uneditable.<br>
*@author Hydrolien
*@lastEditedVersion 2.6
*/
public class FLabel extends JLabel {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It use Formiko color &#38; font.
  *@param x the preferred width
  *@param y the preferred height
  *@lastEditedVersion 2.6
  */
  public FLabel(int x, int y){
    super();
    // setOpaque(false);
    setForeground(Color.BLACK);
    setBackground(Main.getData().getButtonColorWithoutAlpha());
    if(x!=-1 && y!=-1){
      Dimension dim = new Dimension(x,y);
      setPreferredSize(dim);
    }
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It set size to default : 500xgetDimY()
  *@lastEditedVersion 2.6
  */
  public FLabel(){
    this(500,getDimY());
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It dont set size, but set text.
  *@param s the text to use.
  *@lastEditedVersion 2.6
  */
  public FLabel(String s){
    this(-1,-1);
    setText(s);
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary set text or hide this.}<br>
  *It will hide this if text is "" or the description of an empty FButton.
  *@param s the text to use.
  *@lastEditedVersion 2.6
  */
  public void setTexte(String s){
    if(s.length() > 12 && s.substring(0,12).equals("bouton.desc.")){s="";}
    if (!s.equals("")){
      this.setOpaque(true);
      this.setVisible(true);
    }
    else{
      this.setOpaque(false);
      this.setVisible(true);
    }
    super.setText(s);
  }
  /** set empty text */
  public void setTexte(){ setTexte("");}
  @Override
  /***
  *{@summary set text or hide this.}<br>
  *It will hide this if text is "" or the description of an empty FButton.
  *@param s the text to use.
  *@lastEditedVersion 2.6
  */
  public void setText(String s){setTexte(s);}//on s'assure que setTexte est la seule méthode autorisé pour modifié du texte.
  // public void setFondColoré(Color col){this.setBackground(col);}
  //public void setFondColoré(){ setFondColoré(Main.getPiFond().piToColor());}
  public void setFondTransparent(){setBackground(new Color(0,0,0,0));}
  public void setFontText(Font fon){ setFont(fon);}
  // @Override
  // public void setFont(Font fon){super.setFont(fon);}
  public void setFontText(){ setFontText(Main.getFont1());}
  public void setBounds(int a, int b, int c){this.setBounds(a,b,c,getDimY());}
  public static int getDimY(){ return (int)(Main.getFop().getInt("fontSizeText")*1.2);}
  public void setCentered(){setHorizontalAlignment(SwingConstants.CENTER);}
  public void setSize(int w){setSize(w,getDimY());}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint function with a debug tool.}<br>
  *@lastEditedVersion 2.6
  */
  public void paintComponent(Graphics gTemp){
    super.paintComponent(gTemp);
    if(Main.getFop().getBoolean("paintHitBox")){
      Graphics2D g = (Graphics2D)gTemp;
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(getWidth()/100,getHeight()/100,1)));
      g.drawRect(0,0,getWidth(),getHeight());
    }
  }
  /**
  *{@summary Update the size of this depending of text.}
  *@lastEditedVersion 2.6
  */
  public void updateSize(){
    setSize((int)(Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(getText()))), (int)(getFont().getSize()*1.2));
  }
}
