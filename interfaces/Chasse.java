package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public interface Chasse {
  void chasse(Creature c);//ni public ni private indique que c'est dispo seulement dans le package fr.formiko.formiko.
  void chasser(Creature c, int directionSiPasDeProie);
}
