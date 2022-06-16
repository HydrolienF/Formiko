package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.tableau;

import java.io.Serializable;

/**
*{@summary List of Insecte.}<br>
*@lastEditedVersion 2.2
*@author Hydrolien
*/
public class GInsecte extends Liste<Insecte> implements Serializable{
  // CONSTRUCTORS ----------------------------------------------------------------
  /**
  *{@summary Main constructor with 0 Insect.}<br>
  *@lastEditedVersion 2.2
  */
  public GInsecte(){
    super();
  }
  /**
  *{@summary Secondary constructor with nbrOfInsect Insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@lastEditedVersion 2.2
  */
  public GInsecte(int nbrOfInsect){
    this();
    add(nbrOfInsect);
  }
  // GET SET ----------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary return the Insecte that can give the most food.}<br>
  *@lastEditedVersion 2.2
  */
  public Insecte getInsectePlusDeGivenFood(){
    if(getHead()==null){return null;}
    Insecte inr = getFirst();
    for (Insecte in : this) {
      if (in.getGivenFood() > inr.getGivenFood()){
        inr = in;
      }
    }
    return inr;
  }
  /**
  *{@summary add nbrOfInsect insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@lastEditedVersion 2.2
  */
  public void add(int nbrOfInsect){
    for (int i=0; i<nbrOfInsect; i++) {
      add(new Insecte());
    }
  }public void add(){add(1);}
  /***
  *{@summary add nbrOfInsect death insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@lastEditedVersion 2.2
  */
  // public void addM(int nbrOfInsect){
  //   for (int i=0; i<nbrOfInsect; i++) {
  //     Insecte in = new Insecte();
  //     in.setIsDead(true);
  //     add(in);
  //   }
  // }
  /**
  *{@summary Play turn of insects.}<br>
  *@lastEditedVersion 2.2
  */
  public void tourInsecte(){
    for (Insecte i : this) {
      i.tour();
    }
  }
  /**
  *{@summary Play pre-turn of insects.}<br>
  *@lastEditedVersion 2.2
  */
  public void preTurn(){
    for (Insecte i : this) {
      i.preTurn();
    }
  }
  /**
  *{@summary retrun a new GInsecte with only alive insects.}<br>
  *@lastEditedVersion 2.2
  */
  public GInsecte getGiVivant(){
    GInsecte gir = new GInsecte();
    for (Insecte in : this) {
      if(!in.getIsDead()){
        gir.add(in);
      }
    }
    return gir;
  }
  /**
  *{@summary retrun this as a GCreature.}<br>
  *@lastEditedVersion 2.2
  */
  public GCreature toGCreature(){
    GCreature gcr = new GCreature();
    // gcr.add(this);
    for (Insecte in : this ) {
      gcr.add(in);
    }
    return gcr;
  }
}
