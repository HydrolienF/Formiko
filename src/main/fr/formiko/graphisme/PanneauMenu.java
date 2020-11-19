package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.g;
import fr.formiko.usuel.erreur; import fr.formiko.usuel.debug;
import fr.formiko.formiko.Main;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import fr.formiko.usuel.math.math;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.Carte;

public class PanneauMenu extends Panneau {
  private BoutonLong b[];
  private byte menu;
  private boolean lancer = false;
  private Partie pa;
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
  public void setMenu(byte x){ menu=x;} public void setMenu(int x){ setMenu((byte)x);}
  public boolean getLancer(){ return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public Partie getPartie(){ return pa;}
  public void setPartie(Partie p){pa=p;}
  public PanneauNouvellePartie getPnp(){return pnp;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics gra){
    //this.setSize(Main.getPp().getWidth(),Main.getPp().getHeight());
    //debug.débogage("La taille du PanneauMenu est : x="+this.getWidth()+", y="+this.getHeight());
  }
  public void actualiserText(){
    char c = 'P'; if(menu==1){c='N';}if(menu==2){c='M';}
    String s = " ("+g.get("bientôt")+")";
    b[0].setNom(g.get("menu"+c+".1"));
    b[1].setNom(g.get("menu"+c+".2"));
    if(c=='P'){b[1].setNom(g.get("menu"+c+".2")+s);}
    b[2].setNom(g.get("menu"+c+".3"));
    if(c=='P'){b[2].setNom(g.get("menu"+c+".3")+s);}
  }
  /**
  *Launch an action on the PanneauMenu
  *@version 1.14
  */
  public void doAction(int ac){//TODO passer dans une autre class Controleur ?
    if(ac==0){

    }else if(ac==1){
      setMenu(1);repaint(); b[0].setActionB(4);b[1].setActionB(5);b[2].setActionB(6);
      actualiserText();
    }else if(ac==2){
      addPcp();
    }else if(ac==3){
      erreur.erreurPasEncoreImplémenté("PanneauMenu");
    }else if(ac==4){
      debug.débogage("lancementNouvellePartie");
      //Main.lancementNouvellePartie();
      lancer=true;
    }else if(ac==5){
      addPnp();
    }else if(ac==6){
      Main.setTuto(true);
      lancer=true;
    }else if(ac==100){
      //Carte mapo = pnp.getCarte();
      pa=pnp.getPartie();
      lancer=true;
    }else if(ac==101){
      //Carte mapo = pnp.getCarte();
      pa=pcp.getPartie();
      lancer=true;
    }
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
