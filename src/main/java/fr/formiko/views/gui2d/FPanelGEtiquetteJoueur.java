package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.g;

import java.awt.Graphics;

public class FPanelGEtiquetteJoueur extends FPanel {
  private GEtiquetteJoueur gej;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelGEtiquetteJoueur(GEtiquetteJoueur gej){
    this.gej=gej;
    this.setLayout(null);
    //int nbrDeJoueur = gej.length();
    int wi2 = Main.getF().getWidth()/2;
    int k=0;
    for (EtiquetteJoueur ej : gej ) {
      ej.setBounds(FLabel.getDimY(),k*FLabel.getDimY()*3,wi2*gej.length(),FLabel.getDimY()*3);
      add(ej);k++;
    }
  }
  // GET SET -------------------------------------------------------------------
  // public void getGej(){return gej;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(gej!=null && gej.getFirst()!=null){
      g.setColor(Main.getData().getButtonColor());
      int taille = gej.getFirst().getHeight();
      int size = gej.length();
      g.fillRect(0,0,getWidth(),taille*size);
    }
  }
  /**
  *{@summary Add all colorChooser to every open EtiquetteJoueur.}
  *@lastEditedVersion 2.15
  */
  public void addColorChooser(){
    for (EtiquetteJoueur ej : gej ) {
      if(ej.getOuvert()){
        ej.addColorChooser();
      }
    }
  }
}
