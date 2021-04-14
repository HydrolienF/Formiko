package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanneauMenu extends Panneau {
  private BoutonLong b[];
  private byte menu;
  private boolean lancer = false;
  private PanneauNouvellePartie pnp;
  private PanneauChoixPartie pcp;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauMenu(){}
  public void construire(){
    this.setLayout(null);
    setMenu(0);
  }
  // GET SET --------------------------------------------------------------------
  public byte getMenu(){return menu; }
  public boolean getLancer(){ return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public PanneauNouvellePartie getPnp(){return pnp;}
  public PanneauChoixPartie getPcp(){return pcp;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics gra){
    //this.setSize(Main.getPp().getWidth(),Main.getPp().getHeight());
    //debug.débogage("La taille du PanneauMenu est : x="+this.getWidth()+", y="+this.getHeight());
  }
  public void setMenu(byte x){
    menu=x;
    if(x==1){
      b[0].setActionB(4);b[1].setActionB(5);b[2].setActionB(6);
    }
  }
  public void setMenu(int x){ setMenu((byte)x);}
  public void actualiserText(){
    char c = 'P'; if(menu==1){c='N';}if(menu==2){c='M';}
    String s = " ("+g.get("bientôt")+")";
    b[0].setNom(g.get("menu"+c+".1"));
    b[1].setNom(g.get("menu"+c+".2"));
    //if(c=='P'){b[1].setNom(g.get("menu"+c+".2")+s);}
    b[2].setNom(g.get("menu"+c+".3"));
    if(c=='P'){b[2].setNom(g.get("menu"+c+".3")+s);}
  }
  public void construitPanneauMenu(int nbrDeBouton){
    debug.débogage("construitPanneauMenu");
    int xT = Main.getDimX(); int yT = Main.getDimY();
    this.setLayout(null);
    char c = 'P'; if(menu==1){c='N';}if(menu==2){c='M';}
    Double part = 4 + 1.5*nbrDeBouton;
    //3 part avant les boutons, 2 part après les boutons, 1 pour chaque bouton et 1/2 entre chaque bouton.
    int tailleBoutonX = xT/2;
    int tailleBoutonY = (int)(yT/part);
    BoutonLong.setXBL(tailleBoutonX);
    BoutonLong.setYBL(tailleBoutonY);
    int posX = xT/4;
    int posY = tailleBoutonY*3;

    b = new BoutonLong[nbrDeBouton];
    b[0] = new BoutonLong(g.get("menu"+c+"."+1),this,1);
    Dimension dim = b[0].getPreferredSize();
    for (int i=0;i<nbrDeBouton ;i++ ) {
      b[i] = new BoutonLong(g.get("menu"+c+"."+i+1),this,i+1);
      b[i].setBounds(posX,posY+(int)(i*tailleBoutonY*1.5),(int)dim.getWidth(),(int)dim.getHeight());
      add(b[i]);
    }
    actualiserText();
  }
  public void addPnp(){
    retirerBouton();
    pnp = new PanneauNouvellePartie();
    pnp.setBounds(0,0,this.getWidth(),this.getHeight());
    this.add(pnp);
    repaint();
  }
  public void retirerBouton(){
    int lenb = b.length;
    for (int i=0;i<lenb ;i++ ) {
      try {
        remove(b[i]);
      }catch (Exception e) {}
    }
  }
  public void addPcp(){
    retirerBouton();
    pcp = new PanneauChoixPartie();
    pcp.setBounds(0,0,this.getWidth(),this.getHeight());
    this.add(pcp);
    repaint();
  }
}
