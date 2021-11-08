package fr.formiko.views.gui2d;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

public class FPanelInfoText extends FPanel {
  private int nbrDeDesc;
  private FLabel descs [];
  private int xPi; private int yPi;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelInfoText(GString gs, int xD, boolean withAlpha, Font font){
    debug.débogage("création d'un panneauInfo avec "+gs.length()+" éléments.");
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    nbrDeDesc=gs.length();
    int yD = FLabel.getDimY();
    if(font!=null){
      yD = (int)(font.getSize()*1.2);
    }
    xPi=xD; yPi=(yD+1)*nbrDeDesc;
    this.setSize(xPi,yPi);
    gbc.gridx = 0; int k=0;
    descs = new FLabel[gs.length()];
    for (String s : gs ) {
      gbc.gridy = k;k++;
      FLabel desc = new FLabel(xD,yD);
      if(withAlpha){
        desc.setBackground(Main.getData().getButtonColor());
      }
      if(font!=null){
        desc.setFont(font);
      }
      desc.setTexte(s);
      this.add(desc,gbc);
      descs[k-1]=desc;
    }
  }
  public FPanelInfoText(GString gs, int xD, boolean withAlpha){this(gs,xD,withAlpha,null);}
  public FPanelInfoText(GString gs){
    this(gs, getView().getPz().getbuttonSize()*5,false);
  }
  public FPanelInfoText(Fourmi f,int xD){
    this(f.descriptionGString(),xD,false);
  }
  public FPanelInfoText(Fourmi f){
    this(f.descriptionGString());
  }
  public FPanelInfoText(){}
  // GET SET -------- ------------------------------------------------------------
  public int length(){ return nbrDeDesc;}
  public int getXPi(){ return xPi;}
  public int getYPi(){ return yPi;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    // debug.débogage("actualisation du FPanelInfoText avec pour taille : "+this.getWidth()+" "+this.getHeight());
    super.paintComponent(g);
  }
  public String toString(){
    String r = super.toString()+" number of desc "+nbrDeDesc+" ";
    if(descs!=null){
      for (FLabel d : descs ) {
        try {
          r+=" "+d.getText();
        }catch (Exception e) {
          erreur.alerte("can't read desc text.");
        }
      }
    }
    return r;
  }
}