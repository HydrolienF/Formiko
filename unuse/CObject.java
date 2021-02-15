package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exception.ElementListeIntrouvableException;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class CObject implements Serializable{
  protected CObject next, previous;
  protected Object content;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  * Default CObject constructor
  */
  public CObject(Object content, CObject next, CObject previous) {
    super();
    this.next = next;
    this.previous = previous;
    this.content = content;
  }
	/**
	* Default empty CObject constructor
	*/
	public CObject(Object content) {
		this(content,null,null);
	}
  // GET SET -------------------------------------------------------------------
	public CObject getNext() {return next;}
	public void setNext(CObject next) {this.next = next;}
	public CObject getPrevious() {return previous;}
	public void setPrevious(CObject previous) {this.previous = previous;}
	public Object getContent() {return content;}
	public void setContent(Object content) {this.content = content;}
  // FUNCTIONS -----------------------------------------------------------------
}
