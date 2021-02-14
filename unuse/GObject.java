package fr.formiko.usuel.listes;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exception.*;
import fr.formiko.usuel.g;

import java.io.Serializable;
import java.util.Iterator;

public class GObject implements Serializable, Iterator{
  protected CObject start;
  protected CObject end;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GObject(){}
  // GET SET -----------------------------------------------------------------------
	public CObject getStart() {return start;}
	public void setStart(CObject start) {this.start = start;}
	public CObject getEnd() {return end;}
	public void setEnd(CObject end) {this.end = end;}
  // Fonctions propre -----------------------------------------------------------
  //iterator
}
