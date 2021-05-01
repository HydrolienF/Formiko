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

/**
*{@summary menu JPanel use to launch game.}<br>
*It contain all sub menu Panel as PanneauNouvellePartie or PanneauChoixPartie.<br>
*@author Hydrolien
*@version 1.44
*/
public class PanneauMenu extends Panneau {
  private BoutonLong b[]=null;
  private byte menu;
  private boolean lancer = false;
  private PanneauNouvellePartie pnp;
  private PanneauChoixPartie pcp;
  private Bouton returnButton;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauMenu(){}
  /**
  *{@summary Create the Panel empty.}<br>
  *@version 1.44
  */
  public void build(){
    // this.setLayout(null);
    // setBounds(0,0,Main.getDimX(),Main.getDimY());
    // returnButton=null;
  }
  // GET SET --------------------------------------------------------------------
  public byte getMenu(){return menu; }
  public boolean getLancer(){ return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public PanneauNouvellePartie getPnp(){return pnp;}
  public PanneauChoixPartie getPcp(){return pcp;}
  // Fonctions propre -----------------------------------------------------------

  /**
  *{@summary Update action of the menu buttons.}<br>
  *@version 1.44
  */
  private void setMenu(byte x){
    menu=x;
    if(x==0){
      b[0].setActionB(1);b[1].setActionB(2);b[2].setActionB(3);
      setReturnButtonAction(-1);
    }else if(x==1){
      b[0].setActionB(4);b[1].setActionB(5);b[2].setActionB(6);
      setReturnButtonAction(0);
    }
  }
  /***
  *{@summary Update action of the menu buttons.}<br>
  *@version 1.44
  */
  public void setMenu(int x){ setMenu((byte)x);}
  /**
  *{@summary Update text value of the menu buttons.}<br>
  *@version 1.44
  */
  public void actualiserText(){
    char c = 'P'; if(menu==1){c='N';}if(menu==2){c='M';}
    System.out.println("actualiserText with "+c+" from "+Thread.currentThread().getName());//@a
    String s = " ("+g.get("bientôt")+")";
    b[0].setNom(g.get("menu"+c+".1"));
    b[1].setNom(g.get("menu"+c+".2"));
    b[2].setNom(g.get("menu"+c+".3"));
    if(c=='P'){b[2].setNom(g.get("menu"+c+".3")+s);}
  }
  /**
  *{@summary Create the Panel with buttons.}<br>
  *If it have already been build it will only update text.<br>
  *If buttons have been remove it will add them back.<br>
  *@param nbrOfButtons the number of buttons.
  *@version 1.44
  */
  public void buildPanneauMenu(int nbrOfButtons, int menu){
    if(b==null || b[0]==null){
      debug.débogage("construitPanneauMenu");
      System.out.println("construitPanneauMenu "+menu);//@a
      this.setLayout(null);
      setBounds(0,0,Main.getDimX(),Main.getDimY());
      createButton(nbrOfButtons);
    }
    if(b[0].getParent()==null){
      for (int i=0;i<nbrOfButtons ;i++ ) {
        add(b[i]);
      }
    }
    setMenu(menu);
    actualiserText();
  }
  /**
  *{@summary Add PanneauNouvellePartie.}<br>
  *It remove all button or other panel if needed.<br>
  *@version 1.44
  */
  public void addPnp(){
    retirerBouton();
    removeP();
    setReturnButtonAction(1);
    pnp = new PanneauNouvellePartie();
    pnp.setBounds(0,0,this.getWidth(),this.getHeight());
    this.add(pnp);
    repaint();
  }
  /**
  *{@summary Add PanneauChoixPartie.}<br>
  *It remove all button or other panel if needed.<br>
  *@version 1.44
  */
  public void addPcp(){
    retirerBouton();
    removeP();
    setReturnButtonAction(0);
    pcp = new PanneauChoixPartie();
    pcp.setBounds(0,0,this.getWidth(),this.getHeight());
    this.add(pcp);
    repaint();
  }
  /**
  *{@summary Remove PanneauNouvellePartie & set it to null.}<br>
  *@version 1.44
  */
  public void removePnp(){
    remove(pnp);
    pnp=null;
  }
  /**
  *{@summary Remove PanneauChoixPartie & set it to null.}<br>
  *@version 1.44
  */
  public void removePcp(){
    remove(pcp);
    pcp=null;
  }
  /**
  *{@summary Remove PanneauNouvellePartie & PanneauChoixPartie.}<br>
  *@version 1.44
  */
  public void removeP(){
    try {
      removePnp();
    }catch (Exception e) {}
    try {
      removePcp();
    }catch (Exception e) {}
  }

  //private
  /**
  *{@summary Remove all button.}<br>
  *@version 1.44
  */
  private void retirerBouton(){
    int lenb = b.length;
    for (int i=0;i<lenb ;i++ ) {
      remove(b[i]);
    }
  }
  /**
  *{@summary Update returnButton with a new action.}<br>
  *It may change text of the button.
  *@version 1.44
  */
  private void setReturnButtonAction(int ac){
    if(returnButton==null){
      createReturnButton();
      add(returnButton);
    }
    returnButton.setActionB(ac);
    String oldName = returnButton.getNom();
    String newName = "";
    if(ac==-1){
      newName = g.get("menuP.4");
    }else{
      newName = g.get("menuN.4");
    }
    if(!newName.equals(oldName)){
      returnButton.setNom(newName);
      // returnButton.repaint();
    }
  }
  /**
  *{@summary Create the returnButton.}<br>
  *@version 1.44
  */
  private void createReturnButton(){
    returnButton = new Bouton("",Main.getPm(),-1);
    returnButton.setBounds(Main.getTailleElementGraphiqueX(10),Main.getDimY()-Main.getTailleElementGraphiqueY(10)-Main.getTailleElementGraphiqueY(50),Main.getTailleElementGraphiqueX(200),Main.getTailleElementGraphiqueY(50));
  }
  /**
  *{@summary Create the main buttons of the panel.}<br>
  *@param nbrOfButtons the number of buttons.
  *@version 1.44
  */
  private void createButton(int nbrOfButtons){
    int xT = Main.getDimX(); int yT = Main.getDimY();
    // this.setLayout(null);
    char c = 'P'; if(menu==1){c='N';}if(menu==2){c='M';}
    Double part = 4 + 1.5*nbrOfButtons;
    //3 part avant les boutons, 2 part après les boutons, 1 pour chaque bouton et 1/2 entre chaque bouton.
    int tailleBoutonX = xT/2;
    int tailleBoutonY = (int)(yT/part);
    BoutonLong.setXBL(tailleBoutonX);
    BoutonLong.setYBL(tailleBoutonY);
    int posX = xT/4;
    int posY = tailleBoutonY*3;
    b = new BoutonLong[nbrOfButtons];
    b[0] = new BoutonLong(g.get("menu"+c+"."+1),this,1);
    Dimension dim = b[0].getPreferredSize();
    for (int i=0;i<nbrOfButtons ;i++ ) {
      b[i] = new BoutonLong(g.get("menu"+c+"."+i+1),this,i+1);
      b[i].setBounds(posX,posY+(int)(i*tailleBoutonY*1.5),(int)dim.getWidth(),(int)dim.getHeight());
    }
  }
}
