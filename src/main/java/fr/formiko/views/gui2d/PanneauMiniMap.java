package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public class PanneauMiniMap extends Panneau {
  private Bouton endTurn;
  public PanneauMiniMap() {
    super();
    endTurn = new Bouton("endTurnButton", Panneau.getView().getPj(), 200);
    add(endTurn);
    endTurn.setSize(Main.getTailleElementGraphique(50));
    endTurn.setLocation(0,0);
    setSize(Main.getTailleElementGraphiqueX(400), Main.getTailleElementGraphiqueY(260));
    setLocation(Panneau.getView().getWidth()-getWidth(), Panneau.getView().getHeight()-getHeight());
  }
}
