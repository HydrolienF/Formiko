package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

public class PanneauAction extends Panneau {
  private int tailleBouton;
  private int nbrDeBouton;
  private static final int nbrDeBoutonMax=10;
  private int tBoutonActif[];
  private static int bordure=10;
  private static int tailBouton=160;
  private FButton tB [];
  private FButton tAutoB [] = new FButton[2];
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauAction(int t[]){
    super();
    tailleBouton=Main.getTailleElementGraphique(tailBouton);
    tBoutonActif=t;
    nbrDeBouton=math.min(t.length,nbrDeBoutonMax-1);
    int espaceRéservéAuBouton = Main.getTailleElementGraphique(bordure*2+tailBouton);
    setSize((nbrDeBouton*espaceRéservéAuBouton+Main.getTailleElementGraphique(2*bordure)),espaceRéservéAuBouton);
    //setBackground(new Color(0,0,0,0));// du transparent en arrière plan.
  }
  public PanneauAction(){}
  public void build(){
    if(nbrDeBouton > nbrDeBoutonMax){ erreur.erreur("impossible d'afficher tant de boutons");}
    Dimension dim = new Dimension(tailleBouton, tailleBouton);
    setLayout(new GridBagLayout());
    tB = new FButton [nbrDeBouton]; // pour l'instant le bouton 10 n'as pas d'images.
    Main.getData().chargerTIPanneauAction();//ne ce lance que si néssésaire.
    for (int i=0;i<nbrDeBouton ;i++ ) { // seul les bouton mentionné dans t sont créé.
      tB[i] = new FButton(g.get("bouton.nom."+(20+tBoutonActif[i])),(Panneau)this,20+tBoutonActif[i],Main.getData().getTImage()[tBoutonActif[i]]);
      tB[i].setBordure(false);
      tB[i].setWithBackground(true);
      if(tBoutonActif[i]==7){
        tAutoB[0]=tB[i];
      }else if(tBoutonActif[i]==8){
        tAutoB[1]=tB[i];
      }
    }
    for (FButton b :tB){b.setPreferredSize(dim);}
    GridBagConstraints gbc = new GridBagConstraints();
    int espaceLibre = Main.getTailleElementGraphique(bordure);
    gbc.insets = new Insets(espaceLibre, espaceLibre, espaceLibre, espaceLibre);
    gbc.gridy = 0;
    for (int i=0;i<nbrDeBouton ;i++ ) {
      gbc.gridx = i;
      add(tB[i],gbc);
      //TODO use FBorder
      // tB[i].getFBorder().setColor(Main.getData().getButonBorderColor());
    }
  }
  // GET SET --------------------------------------------------------------------
  public int getTailleBouton(){ return tailleBouton;}
  public void setTailleBouton(int x){ tailleBouton=x;}
  public int getNbrBouton(){ return nbrDeBouton;}
  public boolean getEstBoutonActif(int x){return tableau.estDansT(tBoutonActif,x);}
  public int getBordureBouton(){ return Main.getTailleElementGraphique(bordure);}
  public void setEnabled(boolean boo){
    for (FButton b : tB ) {
      b.setEnabled(boo);
    }
    super.setEnabled(boo);
  }
  // Fonctions propre -----------------------------------------------------------

  public void paintComponent(Graphics g){
    if(!Main.getPartie().getEnCours()){return;}
    // debug.g("PanneauAction",this.getWidth(),this.getHeight());
    try {
      paintAutoButton();
    }catch (Exception e) {}
  }
  /**
  *{@summary color in yellow auto mode buttons if they are enabled.}
  *@version 2.5
  */
  public void paintAutoButton(){
    if(Main.getPlayingAnt().getMode()==0){
      tAutoB[0].setCFond(Color.YELLOW);
    }else{
      tAutoB[0].setCFond(Main.getData().getButtonColor());
      if(Main.getPlayingAnt().getMode()==3){
        tAutoB[1].setCFond(Color.YELLOW);
      }else{
        tAutoB[1].setCFond(Main.getData().getButtonColor());
      }
    }
  }
  public static int [] getTIntDef(){
    int tr[] = new int [nbrDeBoutonMax];
    for (int i=0;i<nbrDeBoutonMax ;i++ ) {
      tr[i]=i;
    }return tr;
  }
}
