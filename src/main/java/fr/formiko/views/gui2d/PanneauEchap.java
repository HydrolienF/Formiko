package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
*{@summary Escap pannel }<br>
*Used to pause game.
*@author Hydrolien
*@version 1.41
*/
public class PanneauEchap extends Panneau{
  private Bouton tb[];
  private boolean visible;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauEchap(){
    setVisible(false);
  }
  /**
  *{@summary standard build methode.}
  *Disable all other panel #38; print this one with the 5 button.
  *@version 1.49
  */
  public void build(){
    if(estContruit()){return;}
    try {
      getView().getPz().setEnabled(false);
    }catch (Exception e) {}
    try {
      getView().getPc().setEnabled(false);
    }catch (Exception e) {}
    try{
      getView().getPa().setEnabled(false);
    }catch (Exception e) {}
    //setBackground(new Color(50,50,50,100));
    getView().getPs().setSize(0,0);
    setSize(Main.getDimX(),Main.getDimY());
    int lentb = 5;
    tb = new Bouton[lentb];
    for (int i=0;i<lentb ;i++ ) {
      String s ="";
      if(i==1){s=" ("+g.get("bientôt")+")";}//TODO s'assurer que ce n'est plus utile puis le retirer.
      tb[i]=new Bouton(g.getM("bouton.nom."+(-10-i))+s,getView().getPj(),-10-i);
      //tb[i].setBounds(0,Desc.getDimY()*i*2,Main.getDimX()/4,Desc.getDimY());
      tb[i].setCFond(new Color(55, 255, 0));
      add(tb[i]);
    }
    revalidate();
    getView().paint();
  }
  // GET SET --------------------------------------------------------------------
  public void setTb(Bouton tbTemp[]){tb=tbTemp;}
  public boolean getVisible(){return visible;}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary standard paint methode.}
  *@version 1.47
  */
  @Override
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    try {
      int lentb = tb.length;
      if(lentb==0){return;}
      int xCentré = (int)(Main.getDimX()*1.5/4);
      int tailleY =(int)(Bouton.getDimY()*lentb*1.5 - Bouton.getDimY()*0.5);
      int yCentré = ((Main.getDimY()-tailleY)/2);
      g2d.setColor(new Color(0,250,255));
      int bordure = Main.getTailleElementGraphique(10);
      g2d.fillRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
      g2d.setColor(new Color(20,20,255));
      g2d.setStroke(new BasicStroke(Main.getTailleElementGraphique(3)));
      g2d.drawRect(-bordure+xCentré,-bordure+yCentré,2*bordure+(Main.getDimX()/4),2*bordure+tailleY);
      for (int i=0;i<lentb ;i++ ) {
        tb[i].setBounds(xCentré,yCentré+(int)(Bouton.getDimY()*i*1.5),Main.getDimX()/4,Bouton.getDimY());
      }
    }catch (Exception e) {
      erreur.alerte("something when wrong when drawing component");
    }
  }
  /**
  *{@summary Set PanneauEchap visible.}<br>
  *It Override Component.setVisible() but also build panel if needed &#38; update PanneauSup size.<br>
  *@version 1.47
  */
  @Override
  public void setVisible(boolean b){
    if(b){build();getView().getPs().setSize(0,0);}
    else{
      try {
        getView().getPs().actualiserTaille();
      }catch (Exception e) {
        if(estContruit()){
          erreur.alerte("Can't update size of Ps.");
        }
      }
    }
    visible=b;
    super.setVisible(b);
  }
  /**
  *{@summary return true if only PanneauEchap button sould be enable.}
  *@version 1.41
  */
  public boolean estContruit(){
    return tb!=null;
  }
  /*
  public void miseALaMemeTaille(int lentb){
    int xMax = 0;
    for (int i=0;i<lentb ;i++ ) {
      if(tb[i].getWidth()>xMax){xMax = tb[i].getWidth();}
    }
    for (int i=0;i<lentb ;i++ ) {
      tb[i].setSize(xMax,getHeight());
    }
  }*/
}
