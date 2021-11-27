package fr.formiko.views.gui2d;

import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.views.gui2d.FPanel;

public class readG {

  // FUNCTIONS -----------------------------------------------------------------
  public static String getString(String variable, String défaut, String message){
    FPanel.getView().getPb().addPChamp(défaut,message);
    boolean b;
    debug.débogage("lancement de la boucle d'attente de validation");
    do {
      b = FPanel.getView().getPb().getPChamp().getValidé();
      Temps.pause(10);
    } while (!b);
    String rep = FPanel.getView().getPb().getPChamp().getTexte();
    FPanel.getView().getPb().removePChamp();
    debug.débogage("Réponse reçu : "+rep);
    return rep;
  }
  public static boolean getOuiOuNon(String message){
    debug.débogage("demande d'une réponse sous la forme oui ou non");
    FPanel.getView().getPb().addPTB(message);
    boolean b = FPanelTBoolean.getChoixId();
    FPanel.getView().getPb().removePTB();
    return b;
  }
}
