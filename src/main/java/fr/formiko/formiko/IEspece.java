package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

/**
 *{@summary Save the pop probability of an Insecte.}<br>
 *The pop probability depend of the Case were the Insecte will pop.
 *@author Hydrolien
 *@lastEditedVersion 1.1
 */
public class IEspece implements Serializable{
  private int ct[];
  protected final int id; protected static int cptid;
  // CONSTRUCTORS --------------------------------------------------------------
  public IEspece(int ct []){
    this.id = cptid; cptid++;
    this.ct=ct;
  }
  // GET SET -------------------------------------------------------------------
  public int getCt(int i){
    i--;
    if(i<0 || i>=ct.length){erreur.erreur("Impossible de récupéré la valeur de la case "+i);return 0;}
    return ct[i];}
  public int [] getCt(){return ct;}
  public int getId(){ return id;}
  public static void ini(){cptid=0;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    return "IEspece "+getId()+tableau.tableauToString(ct);
  }
}
