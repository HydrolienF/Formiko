package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary List of seeds.}
*lastEditedVersion 2.23
*@author Hydrolien
*/
public class GGraine extends Liste<Graine> implements Serializable {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.23
  */
  public GGraine(){
    super();
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return the seed that have the most givenFood.}
  *lastEditedVersion 2.23
  */
  public Graine getBetterSeed(){
    return getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  /**
  *{@summary Return the seed that have the most givenFood &38; with hardness &lb; hardnessMax.}
  *lastEditedVersion 2.23
  */
  public Graine getGrainePlusDeGivenFood(int hardnessMax){
    return filter(g -> g.getOuverte() && g.getHardness()<hardnessMax)
        .getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  /**
  *{@summary Return the seed that have the most givenFood &38; with hardness &lb; ant max hardness.}
  *lastEditedVersion 2.23
  */
  public Graine getGrainePlusDeGivenFood(Fourmi f){
    return getGrainePlusDeGivenFood(f.getHardnessMax());
  }
  /**
  *{@summary Return the first oppen seed.}
  *lastEditedVersion 2.23
  */
  public Graine getGraineOuverte(){
    for (Graine g : this) {
      if(g.getOuverte()){
        return g;
      }
    }
    return null;
  }

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a list with all this class functions.}
  *@lastEditedVersion 2.23
  */
  private static GGraine toGg(Liste<Graine> l){
    GGraine g = new GGraine();
    g.setHead(l.getHead());
    g.setTail(l.getTail());
    return g;
  }
  /**
  *{@summary make a copy.}
  *lastEditedVersion 2.23
  */
  public GGraine copierGGraine(){
    GGraine gg = new GGraine();
    for (Graine g : this) {
      gg.add(g);
    }
    return gg;
  }
  /**
  *{@summary Apply tour() over every seed.}
  *lastEditedVersion 2.23
  */
  public void tour(){
    for (Graine g : this) {
      g.tour();
    }
  }
}
