package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.views.gui2d.Panneau;
import fr.formiko.views.gui2d.PanneauBouton;
import fr.formiko.views.gui2d.PanneauChamp;
import fr.formiko.views.gui2d.PanneauTBoolean;

public class readG {

  // FUNCTIONS -----------------------------------------------------------------
  public static String getString(String variable, String défaut, String message){
    Panneau.getView().getPb().addPChamp(défaut,message);
    boolean b;
    debug.débogage("lancement de la boucle d'attente de validation");
    do {
      b = Panneau.getView().getPb().getPChamp().getValidé();
      Temps.pause(10);
    } while (!b);
    String rep = Panneau.getView().getPb().getPChamp().getTexte();
    Panneau.getView().getPb().removePChamp();
    debug.débogage("Réponse reçu : "+rep);
    return rep;
  }
  public static boolean getOuiOuNon(String message){
    debug.débogage("demande d'une réponse sous la forme oui ou non");
    Panneau.getView().getPb().addPTB(message);
    boolean b = Panneau.getView().getPb().getPTB().getChoixId();
    Panneau.getView().getPb().removePTB();
    return b;
  }
}
