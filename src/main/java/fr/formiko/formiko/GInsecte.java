package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

/**
*{@summary List of Insecte.}<br>
*@version 2.2
*@author Hydrolien
*/
public class GInsecte extends Liste<Insecte> implements Serializable{
  // CONSTRUCTEUR -----------------------------------------------------------------
  /**
  *{@summary Main constructor with 0 Insect.}<br>
  *@version 2.2
  */
  public GInsecte(){
    super();
  }
  /**
  *{@summary Secondary constructor with nbrOfInsect Insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@version 2.2
  */
  public GInsecte(int nbrOfInsect){
    this();
    add(nbrOfInsect);
  }
  // GET SET -----------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary return the Insecte that can give the most food.}<br>
  *@version 2.2
  */
  public Insecte getInsectePlusDeNourritureFournie(){
    if(getHead()==null){return null;}
    Insecte inr = getFirst();
    for (Insecte in : this) {
      if (in.getNourritureFournie() > inr.getNourritureFournie()){
        inr = in;
      }
    }
    return inr;
  }
  /**
  *{@summary add nbrOfInsect insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@version 2.2
  */
  public void add(int nbrOfInsect){
    for (int i=0; i<nbrOfInsect; i++) {
      add(new Insecte());
    }
  }public void add(){add(1);}
  /**
  *{@summary add nbrOfInsect death insect.}<br>
  *@param nbrOfInsect the number of insect to add.
  *@version 2.2
  */
  // public void addM(int nbrOfInsect){
  //   for (int i=0; i<nbrOfInsect; i++) {
  //     Insecte in = new Insecte();
  //     in.setEstMort(true);
  //     add(in);
  //   }
  // }
  /**
  *{@summary Play turn of insects.}<br>
  *@version 2.2
  */
  public void tourInsecte(){
    for (Insecte i : this) {
      i.tour();
    }
  }
  /**
  *{@summary Play pre-turn of insects.}<br>
  *@version 2.2
  */
  public void preTour(){
    for (Insecte i : this) {
      i.preTour();
    }
  }
  /**
  *{@summary retrun a new GInsecte with only alive insects.}<br>
  *@version 2.2
  */
  public GInsecte getGiVivant(){
    GInsecte gir = new GInsecte();
    for (Insecte in : this) {
      if(!in.getEstMort()){
        gir.add(in);
      }
    }
    return gir;
  }
  /**
  *{@summary retrun this as a GCreature.}<br>
  *@version 2.2
  */
  public GCreature toGCreature(){
    GCreature gcr = new GCreature();
    gcr.add(this);
    return gcr;
  }
}
