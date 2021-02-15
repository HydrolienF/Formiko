package fr.formiko.graphisme;

import fr.formiko.formiko.Main;
import fr.formiko.graphisme.PanneauBouton;
import fr.formiko.graphisme.PanneauChamp;
import fr.formiko.graphisme.PanneauTBoolean;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

public class readG {

  // Fonctions propre -----------------------------------------------------------
  public static String getString(String variable, String défaut, String message){
    Main.getPb().addPChamp(défaut,message);
    boolean b;
    debug.débogage("lancement de la boucle d'attente de validation");
    do {
      b = Main.getPb().getPChamp().getValidé();
      Temps.pause(10);
    } while (!b);
    String rep = Main.getPb().getPChamp().getTexte();
    Main.getPb().removePChamp();
    debug.débogage("Réponse reçu : "+rep);
    return rep;
  }
  public static boolean getOuiOuNon(String message){
    debug.débogage("demande d'une réponse sous la forme oui ou non");
    Main.getPb().addPTB(message);
    boolean b = Main.getPb().getPTB().getChoixId();
    Main.getPb().removePTB();
    return b;
  }
}
