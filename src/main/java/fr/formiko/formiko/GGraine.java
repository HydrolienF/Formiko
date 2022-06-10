package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;
//extends Liste<Graine>
public class GGraine extends Liste<Graine> implements Serializable {
  // protected CGraine début;
  // CONSTRUCTORS --------------------------------------------------------------
  public GGraine(){
    super();
  }
  // GET SET -------------------------------------------------------------------
  // public CGraine getHead(){ return début;}
  // public void setDébut(CGraine x){début=x;}
  // public CGraine getTail(){
  //   if (début == null ){ return null;}
  //   CGraine fin = début;
  //   while (fin.getSuivant() != null){
  //     fin = fin.getSuivant();
  //   }
  //   return fin;
  // }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String r = g.get("GGraine")+" : ";
    if (getHead() == null){
      return r+"ø";
    }
    return r+toList().toString();
  }
  // public int length(){
  //   return toList().length();
  // }
  /**
  *{@summary return the seed that have the most givenFood & that is open}
  *lastEditedVersion 2.23
  */
  public Graine getGrainePlusDeGivenFoodSansDureté(){
    return toList()
        .filter(g -> g.getOuverte())
        .getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  public Graine getGrainePlusDeGivenFood(int duretéMax){
    return toList()
        .filter(g -> g.getOuverte() && g.getDureté()<duretéMax)
        .getMost((Graine g1, Graine g2) -> g2.getGivenFood() - g1.getGivenFood());
  }
  public Graine getGrainePlusDeGivenFood(Fourmi f){
    return getGrainePlusDeGivenFood(f.getDuretéMax());
  }
  public Graine getGraineOuverte(){
    for (Graine g : toList()) {
      if(g.getOuverte()){
        return g;
      }
    }
    return null;
  }
  // /**
  // *{@summary Add a seed to this GGraine.}
  // *@lastEditedVersion 1.40
  // */
  // public void add(Graine i){
  //   if (i != null){
  //     CGraine ci = new CGraine(i);
  //     ci.setSuivant(début);
  //     début = ci;
  //   }
  // }
  // public void addGraine(Graine i){add(i);}
  public void addGg(GGraine gg){
    for (Graine g : gg) {
      add(g);
    }
  }
  // public void retirerGraine(int i){
  //   if (this.début == null){ erreur.alerte("Impossible de remove i d'un groupe de Graine null"); return;}
  //   if(début.getContent().getId()==i){
  //     retirerGraine(début.getContent());
  //   }else{
  //     début.retirerGraine(i);
  //   }
  // }
  // public void retirerGraine(Graine i){
  //   debug.débogage("Suppression de : "+i.getId());
  //   if (i == null){
  //     erreur.alerte("Impossible de remove null d'un groupe de Graine");
  //   }
  //   if (début == null){
  //     erreur.alerte("Impossible de remove une Graine d'un groupe vide.");
  //   }else if (début.getContent().equals(i)){ // Si c'est le 1a
  //     début = début.getSuivant(); // On en retir 1.
  //     debug.débogage("début = début.getSuivant();");
  //   } else if(début.getSuivant() != null && début.getSuivant().getContent().equals(i)){ // Si c'est le 2a
  //     début.setSuivant(début.getSuivant().getSuivant());
  //   }else {
  //     début.retirerGraine(i);
  //   }
  // }public void remove(Graine i){ retirerGraine(i);}
  public GGraine copierGGraine(){
    GGraine gg = new GGraine();
    for (Graine g : toList()) {
      gg.add(g);
    }
    return gg;
  }
  public void tour(){
    for (Graine g : toList()) {
      g.tour();
    }
  }

  /**
  *{@summary Transform a GGraine in Liste&lt;Graine&gt;.}
  *@lastEditedVersion 1.38
  */
  public Liste<Graine> toList(){
    return this;
    // if (début==null){
    //   Liste<Graine> lc = new Liste<Graine>();
    //   return lc;
    // }
    // return début.toList();
  }
}
