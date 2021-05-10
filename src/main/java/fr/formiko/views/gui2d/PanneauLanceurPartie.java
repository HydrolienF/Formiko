package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public abstract class PanneauLanceurPartie extends Panneau{
  private BoutonLong lancerPartie;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauLanceurPartie(int ac){
    this.setLayout(null);
    lancerPartie = new BoutonLong(g.getM("lancerPartie"),getView().getPm(),ac);
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
