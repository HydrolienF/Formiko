package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Graphics;

public class Desc extends JLabel{
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Desc(int x, int y){
    setOpaque(false);
    //label.setForeground(Color.blue); couleur du text.
    Dimension dim = new Dimension(x,y);
    setFondColoré(new Color(55,255,0));
    setPreferredSize(dim);
    setPolice();
  }
  public Desc(){ this(500,getDimY());}
  /*public Desc(){
    setText("");
    setOpaque(false);
    setFondColoré(new Color(55,255,0,255));
    setPolice();
  }*/
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
  public void setFondColoré(Color col){this.setBackground(col);}
  //public void setFondColoré(){ setFondColoré(Main.getPiFond().piToColor());}
  public void setFondTransparent(){setFondColoré(new Color(0,0,0,0));}
  public void setPolice(Font fon){ setFont(fon);}
  @Override
  public void setFont(Font fon){super.setFont(fon);}
  public void setPolice(){ setPolice(Main.getFont1());}
  public void setBounds(int a, int b, int c){this.setBounds(a,b,c,getDimY());}
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.2);}
  // Fonctions propre -----------------------------------------------------------
  // @Override
  // public void paintComponent(Graphics g){
  //
  // }
}
