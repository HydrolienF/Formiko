package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

public class GGraine extends Liste<Graine> implements Serializable {
  // CONSTRUCTORS --------------------------------------------------------------
  public GGraine(){
    super();
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String r = g.get("GGraine")+" : ";
    if (getHead() == null){
      return r+"ø";
    }
    return r+super.toString();
  }
  /**
  *{@summary Return the seed that have the most givenFood.}
  *lastEditedVersion 2.23
  */
  public Graine getBetterSeed(){
    return getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  /**
  *{@summary return the seed that have the most givenFood}
  *lastEditedVersion 2.23
  */
  public Graine getGrainePlusDeGivenFood(int duretéMax){
    return filter(g -> g.getOuverte() && g.getDureté()<duretéMax)
        .getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  public Graine getGrainePlusDeGivenFood(Fourmi f){
    return getGrainePlusDeGivenFood(f.getDuretéMax());
  }
  public Graine getGraineOuverte(){
    for (Graine g : this) {
      if(g.getOuverte()){
        return g;
      }
    }
    return null;
  }
  public void addGg(GGraine gg){
    for (Graine g : gg) {
      add(g);
    }
  }
  public GGraine copierGGraine(){
    GGraine gg = new GGraine();
    for (Graine g : this) {
      gg.add(g);
    }
    return gg;
  }
  public void tour(){
    for (Graine g : this) {
      g.tour();
    }
  }
}
