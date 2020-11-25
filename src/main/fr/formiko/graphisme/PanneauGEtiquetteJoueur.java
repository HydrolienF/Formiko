package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.Graphics;
import fr.formiko.formiko.Main;

public class PanneauGEtiquetteJoueur extends Panneau{
  private GEtiquetteJoueur gej;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauGEtiquetteJoueur(GEtiquetteJoueur gej){
    super();
    this.gej=gej;
    this.setLayout(null);
    //int nbrDeJoueur = gej.length();
    CEtiquetteJoueur cej = gej.getDébut();
    int wi2 = Main.getF().getWidth()/2;
    int k=0;
    while(cej!=null){
      cej.getContenu().setBounds(0,k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      add(cej.getContenu());
      cej=cej.getSuivant();k++;
    }
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    /*int wi2 = Main.getF().getWidth()/2;
    CEtiquetteJoueur cej = gej.getDébut();
    int k=0;
    while(cej!=null){
      cej.getContenu().setBounds(0,k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      cej=cej.getSuivant();k++;
    }*/
  }
}
