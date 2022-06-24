package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.io.Serializable;

public class Personalite implements Serializable{
  // santé devrais peut-être être la aussi (ou dans Creature).
  private byte rapidité; // sert pour la chasse et permet de perdre moins de point d'actions lors des déplacements.
  private byte force; // sert pour la chasse et pour les combats.
  private byte armure; // permet au fourmi de résister au attaque. Une armure suffisante permet de ne pas perdre de vie dutout.
  private byte agressivité; // définie si une fourmi provoque ou fuit le combat

  private byte reconnaissanceAllié; // les créatures reconnu aurons plus de chance de recevoir une trophalaxie ou une aide au combat.
  private byte reconnaissanceNeutre;// les créatures neutre ne seront pas attaquer et pas aider. (les neutres sont tolléré partout sauf dans la Fourmiliere.)
  // CONSTRUCTORS --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------

}
