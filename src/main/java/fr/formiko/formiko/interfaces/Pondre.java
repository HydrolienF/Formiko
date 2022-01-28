package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;
/**
 * {@summary lay.}<br>
 * Allow a Creature to lay<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public interface Pondre extends Serializable{
  void unePonte(Creature c);
  boolean canLay(Creature c);
}
