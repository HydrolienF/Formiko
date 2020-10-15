package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class Personalite {
  // santé devrais peut-être être la aussi (ou dans Creature).
  private byte rapidité; // sert pour la chasse et permet de perdre moins de point d'actions lors des déplacements.
  private byte force; // sert pour la chasse et pour les combats.
  private byte armure; // permet au fourmi de résister au attaque. Une armure suffisante permet de ne pas perdre de vie dutout.
  private byte agressivité; // définie si une fourmi provoque ou fuit le combat

  private byte reconnaissanceAllié; // les créatures reconnu aurons plus de chance de recevoir une trophalaxie ou une aide au combat.
  private byte reconnaissanceNeutre;// les créatures neutre ne seront pas attaquer et pas aider. (les neutres sont tolléré partout sauf dans la Fourmiliere.)
  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------

}