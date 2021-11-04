package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class EtiquetteChoix extends Panneau{
  private FComboBox<String> choixX;
  private FLabel choixXDesc;
  private int k;
  private Color cFond;
  // CONSTRUCTORS --------------------------------------------------------------
  public EtiquetteChoix(int x, String clé, GString gs){
    debug.débogage("Lancement de la création d'une EtiquetteChoix avec "+gs.length()+" éléments");
    this.setLayout(null);
    setOpaque(false);
    choixXDesc = new FLabel();
    choixXDesc.setTexte(g.getM(clé)+" : ");
    choixXDesc.setFondTransparent();

    choixX = gs.getComboBox(x);
    choixX.setFont(Main.getFont1(0.9));
    add(choixX);add(choixXDesc);
    int wi2 = Main.getTailleElementGraphiqueX(960);
    int taille = FLabel.getDimY();
    choixXDesc.setBounds(0,taille*k,wi2/2,FLabel.getDimY());
    choixX.setBounds(wi2/2,taille*k,wi2/3,FLabel.getDimY());
    cFond = Main.getData().getButtonColor();
  }
  public EtiquetteChoix(String clé, GString gs){ this(0,clé,gs);}
  // GET SET -------------------------------------------------------------------
  public String getSelectedItem(){ return choixX.getSelectedItem()+"";}
  public int getSelectedIndex(){ return choixX.getSelectedIndex();}
  public FComboBox getChoixX(){ return choixX;}
  public FLabel getChoixXDesc(){ return choixXDesc;}
  public void setCFond(Color c){cFond=c;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    //debug.débogage("Lancement de eDif.paintComponent");
    Graphics2D g2d = (Graphics2D)g;
    boolean cFondUseAlpha = true;
    if(cFond!=null && !isOpaque()){
      if(cFondUseAlpha){
        g2d.setColor(cFond);
      }else{
        g2d.setColor(new Color(cFond.getRed(),cFond.getGreen(),cFond.getBlue(),255));
      }
      g2d.fillRect(0,0,getWidth(),getHeight());
    }
    super.paintComponent(g);
  }
  public void setPos(int k){
    this.k = k;
  }
  public void setTaille(int x, int y){
    choixX.setSize(x/2,y);
    choixXDesc.setSize(x/2,y);
  }
  public void setFontText(Font font){
    choixX.setFont(font);
    choixXDesc.setFont(font);
  }
}
