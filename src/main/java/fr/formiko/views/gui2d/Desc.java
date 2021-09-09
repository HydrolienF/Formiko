package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Desc extends JLabel {
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Desc(int x, int y){
    super();
    setOpaque(false);
    setForeground(Color.BLACK);
    if(x!=-1 && y!=-1){
      Dimension dim = new Dimension(x,y);
      setPreferredSize(dim);
    }
    setBackground(Main.getData().getButtonColorWithoutAlpha());
  }
  public Desc(){ this(500,getDimY());}
  public Desc(String s){
    this(-1,-1);
    setText("s");
  }
  // GET SET --------------------------------------------------------------------
  public void setTexte(String s){
    if(s.length() > 12 && s.substring(0,12).equals("bouton.desc.")){s="";}
    if (s!=""){
      this.setOpaque(true);
    }
    else{this.setOpaque(false);}
    super.setText(s);
  }
  public void setTexte(){ setTexte("");}
  @Override
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
  // Fonctions propre -----------------------------------------------------------
  public void updateSize(){
    // getView().pack();
    setSize((int)(getText().length()*Main.getTaillePolice1()*0.6),Desc.getDimY());
  }
  // @Override
  // public void paintComponent(Graphics g){
  //
  // }
}
