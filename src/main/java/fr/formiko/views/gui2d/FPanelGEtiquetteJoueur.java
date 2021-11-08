package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public class FPanelGEtiquetteJoueur extends FPanel{
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
    // CEtiquetteJoueur cej = gej.getHead();
    // while(cej!=null){
    //   cej.getContent().setBounds(FLabel.getDimY(),k*FLabel.getDimY()*3,wi2*gej.length(),FLabel.getDimY()*3);
    //   add(cej.getContent());
    //   cej=cej.getSuivant();k++;
    // }
  }
  // GET SET -------------------------------------------------------------------
  // public void getGej(){return gej;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(gej!=null && gej.getHead()!=null && gej.getFirst()!=null){
      g.setColor(Main.getData().getButtonColor());
      int taille = gej.getFirst().getHeight();
      int size = gej.length();
      g.fillRect(0,0,getWidth(),taille*size);
    }
    /*int wi2 = Main.getF().getWidth()/2;
    CEtiquetteJoueur cej = gej.getHead();
    int k=0;
    while(cej!=null){
      cej.getContent().setBounds(0,k*FLabel.getDimY()*3,wi2*gej.length(),FLabel.getDimY()*3);
      cej=cej.getSuivant();k++;
    }*/
  }
}
