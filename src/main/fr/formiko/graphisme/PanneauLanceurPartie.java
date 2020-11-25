package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.Graphics;
import fr.formiko.formiko.Partie;

public abstract class PanneauLanceurPartie extends Panneau{
  private BoutonLong lancerPartie;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauLanceurPartie(int ac){
    super();
    this.setLayout(null);
    lancerPartie = new BoutonLong(g.getM("lancerPartie"),Main.getPm(),ac);
    add(lancerPartie);
    int wi = Main.getDimX();
    int he = Main.getDimY();
    int wi2 = wi/2;
    lancerPartie.setBounds(wi2/2,Main.getDimY()-lancerPartie.getYBL(),wi2,lancerPartie.getYBL());
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public abstract Partie getPartie();
}
