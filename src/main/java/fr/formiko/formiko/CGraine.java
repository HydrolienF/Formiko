package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

public class CGraine implements Serializable{
  // protected CGraine suivant;
  // protected Graine contenu;
  // // CONSTRUCTORS --------------------------------------------------------------
  // public CGraine(Graine i){contenu=i;}
  // // GET SET -------------------------------------------------------------------
  // public CGraine getSuivant(){return suivant;}
  // public void setSuivant(CGraine ci){suivant = ci;}
  // public Graine getContent(){return contenu;}
  // // FUNCTIONS -----------------------------------------------------------------
  // // public String toString(){
  // //   if (suivant == null){
  // //     return ", G"+contenu.getId();
  // //   }else{
  // //   return ", G"+contenu.getId() + suivant.toString();}
  // // }
  // // public Graine getGrainePlusDeGivenFoodSansDureté(Graine gMax){ // si la graine est cassable et que sont contenue est meilleur.
  // //   if(gMax.getGivenFood() < this.getContent().getGivenFood() && !this.getContent().getOuverte()){
  // //     gMax = this.getContent();
  // //   }
  // //   if(suivant == null){
  // //     if (!gMax.getOuverte()){ return gMax;}
  // //     return null;
  // //   }
  // //   return suivant.getGrainePlusDeGivenFoodSansDureté(gMax);
  // // }
  // // public Graine getGrainePlusDeGivenFood(Graine gMax,byte duretéMax){
  // //   // si la graine est cassable et que sont contenu est meilleur.
  // //   if(contenu.getDureté() < duretéMax && gMax.getGivenFood() < this.getContent().getGivenFood() && !this.getContent().getOuverte()){
  // //     gMax = this.getContent();
  // //     debug.débogage("changement de gMax pour :");
  // //   }
  // //   if(suivant == null){
  // //     if (!gMax.getOuverte() && gMax.getDureté() < duretéMax){ return gMax;}
  // //     return null;
  // //   }
  // //   return suivant.getGrainePlusDeGivenFood(gMax, duretéMax);
  // // }
  //
  //
  // // public Graine getGraineParId(int id){
  // //   if (contenu.getId() == id){
  // //     return contenu;
  // //   }
  // //   if (suivant == null){
  // //     erreur.alerte("La Graine " + id + " n'as pas été trouvé dans la liste de Graine encore vivant.");
  // //     return null;
  // //   }
  // //   return suivant.getGraineParId(id);
  // // }
  // public void retirerGraine(int i){
  //   if (suivant == null){
  //     erreur.alerte("La Graine "+ i +" n'as pas été trouvé et n'as donc pas pu être remove");
  //   }else {
  //     debug.débogage("Test dans CGraine");
  //     if(suivant.getContent().getId()==i){
  //       suivant = suivant.getSuivant(); // on saute un maillons.
  //     }else {
  //       debug.débogage("Graine non trouvée "+i+" != "+suivant.getContent().getId());
  //       suivant.retirerGraine(i);
  //     }
  //   }
  // }
  // public void retirerGraine(Graine i){
  //   debug.débogage("on a fait début.retirerGraine(i);");
  //   if (suivant == null){
  //     erreur.alerte("La Graine "+ i.getId() +" n'as pas été trouvé et n'as donc pas pu être remove");
  //   }else {
  //     if(suivant.getContent().equals(i)){
  //       suivant = suivant.getSuivant(); // on saute un maillons.
  //     }else {
  //       suivant.retirerGraine(i);
  //     }
  //   }
  // }
  // public GGraine copierGGraine(){
  //   GGraine ggr = new GGraine();
  //   CGraine temp = this;
  //   while(temp != null){
  //     ggr.add(temp.getContent());
  //     temp = temp.getSuivant();
  //   }
  //   return ggr;
  // }
  // /**
  // *{@summary Transform a GGraine in Liste&lt;Graine&gt;.}
  // *@lastEditedVersion 1.38
  // */
  // public Liste<Graine> toList(){
  //   CGraine cc = this;
  //   Liste<Graine> lc = new Liste<Graine>();
  //   while(cc!= null){
  //     lc.add(cc.getContent());
  //     cc = cc.getSuivant();
  //   }
  //   return lc;
  // }
}
