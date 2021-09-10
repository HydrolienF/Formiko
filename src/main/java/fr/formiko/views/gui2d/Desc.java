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
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Desc extends JLabel {
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It use Formiko color &#38; font.
  *@param x the preferred width
  *@param y the preferred heigth
  *@version 2.6
  */
  public Desc(int x, int y){
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
  *@version 2.6
  */
  public Desc(){ this(500,getDimY());}
  /**
  *{@summary Secondary constructor.}<br>
  *It dont set size, but set text.
  *@param s the text to use.
  *@version 2.6
  */
  public Desc(String s){
    this(-1,-1);
    setText("s");
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary set text or hide this.}<br>
  *It will hide this if text is "" or the description of an empty FButton.
  *@param s the text to use.
  *@version 2.6
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
  *@version 2.6
  */
  public void setText(String s){setTexte(s);}//on s'assure que setTexte est la seule méthode autorisé pour modifié du texte.
  // public void setFondColoré(Color col){this.setBackground(col);}
  //public void setFondColoré(){ setFondColoré(Main.getPiFond().piToColor());}
  public void setFondTransparent(){setBackground(new Color(0,0,0,0));}
  public void setPolice(Font fon){ setFont(fon);}
  @Override
  public void setFont(Font fon){super.setFont(fon);}
  public void setPolice(){ setPolice(Main.getFont1());}
  public void setBounds(int a, int b, int c){this.setBounds(a,b,c,getDimY());}
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.2);}
  public void setCentered(){setHorizontalAlignment(SwingConstants.CENTER);}
  public void setSize(int w){setSize(w,getDimY());}
  // FUNCTIONS -----------------------------------------------------------------
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
  /**
  *{@summary Update the size of this depending of text.}
  *@version 2.6
  */
  public void updateSize(){
    // getView().pack();
    //TODO #10 calculate the real size of this.
    // javax.swing.text.PlainDocument doc = (javax.swing.text.PlainDocument) this.getDocument();
    // System.out.println(Math.ceil(this.getFontMetrics(this.getFont()).stringWidth(doc.getText(start, length)));
    setSize((int)(getText().length()*Main.getTaillePolice1()*0.6),Desc.getDimY());
  }
}
