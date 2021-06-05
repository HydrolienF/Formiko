package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public abstract class PanneauLanceurPartie extends Panneau{
  private BoutonLong launchButton;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauLanceurPartie(int ac){
    this.setLayout(null);
    launchButton = new BoutonLong(g.getM("lancerPartie"),getView().getPm(),ac);
    add(launchButton);
    int wi = Main.getDimX();
    int he = Main.getDimY();
    int wi2 = wi/2;
    launchButton.setBounds(wi2/2,Main.getDimY()-launchButton.getYBL(),wi2,launchButton.getYBL());
  }
  // GET SET --------------------------------------------------------------------
  public BoutonLong getLaunchButton(){return launchButton;}
  // Fonctions propre -----------------------------------------------------------
  public abstract Partie getPartie();
}
