package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

public class FPanelZoom extends FPanel {
  private FButton bPlus; private FButton bMoins; private FButton bc;//bouton de zoom.
  // private FButton bd1; private FButton bd2;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelZoom(){
    int tailleBouton=Main.getbuttonSizeZoom();
    setSize(tailleBouton*3,tailleBouton);
  }
  public void build(){
    int tailleBouton=Main.getbuttonSizeZoom();
    this.setLayout(new GridBagLayout());
    Image tIB []; tailleBouton=Main.getbuttonSizeZoom();
    //if(Main.getPiFond()==null){tIB = chargerTIB();}
    //else{tIB = chargerTIB2(Main.getPiFond());}
    tIB = Main.getData().chargerTIBZoom();
    Dimension dim = new Dimension(tailleBouton, tailleBouton);
    bMoins = new FButton("-",(FPanel)this,0,tIB[0]);
    bPlus = new FButton("+",(FPanel)this,2,tIB[2]);
    bc = new FButton("centrer",(FPanel)this,4,tIB[4]);
    // bd1 = new FButton("centrer sur la fourmi",(FPanel)this,6,tIB[7]);
    // bd2 = new FButton("dézoomer 2",(FPanel)this,8,tIB[8]);
    bPlus.setPreferredSize(dim); bMoins.setPreferredSize(dim);
    bc.setPreferredSize(dim);
    // bd1.setPreferredSize(dim); bd2.setPreferredSize(dim);
    //L'objet servant à positionner les composants
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.gridx = 0;
    add(bc,gbc);
    gbc.gridx = 1;
    add(bPlus,gbc);
    gbc.gridx = 2;
    add(bMoins,gbc);
    // gbc.gridy = 1;
    // gbc.gridx = 1;
    // add(bd1,gbc);
    // gbc.gridx = 2;
    // add(bd2,gbc);
    // Color col = new Color(0,0,0,0);
    //TODO border color should be black.
    bPlus.setColor(-1);
    bMoins.setColor(-1);
    bc.setColor(-1);
    // bd1.setColor(-1);
    // bd2.setColor(-1);
    bPlus.setBordure(false);
    bMoins.setBordure(false);
    bc.setBordure(false);
    // bd1.setBordure(false);
    // bd2.setBordure(false);
    setEnabled(true);
  }
  // GET SET -------------------------------------------------------------------
  public int getbuttonSize(){ return Main.getbuttonSizeZoom();}
  //public void setbuttonSize(int x){ tailleBouton=x;}
  public void setEnabled(boolean boo){
    bPlus.setEnabled(boo);
    bMoins.setEnabled(boo);
    bc.setEnabled(boo);
    // bd1.setEnabled(boo);bd2.setEnabled(boo);
    super.setEnabled(boo);
  }
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(Main.getPartie()==null || !Main.getPartie().getEnCours()){return;}
    int tailleBouton=Main.getbuttonSizeZoom();
    setSize(tailleBouton*3,tailleBouton);
  }
  public void doAction(byte ac){
    FPanel.getView().getPj().doAction(ac);
  }public void doAction(int ac){ doAction((byte)ac);}
}
