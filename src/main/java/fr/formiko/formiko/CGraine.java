package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;

import java.io.Serializable;

public class CGraine implements Serializable{
  protected CGraine suivant;
  protected Graine contenu;
  // CONSTRUCTORS --------------------------------------------------------------
  public CGraine(Graine i){contenu=i;}
  // GET SET -------------------------------------------------------------------
  public CGraine getSuivant(){return suivant;}
  public void setSuivant(CGraine ci){suivant = ci;}
  public Graine getContent(){return contenu;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if (suivant == null){
      return ", G"+contenu.getId();
    }else{
    return ", G"+contenu.getId() + suivant.toString();}
  }
  public int length(){
    if(suivant == null){
      return 1;
    }else{
      return 1+suivant.length();
    }
  }
  public Graine getGrainePlusDeNourritureFournieSansDureté(Graine gMax){ // si la graine est cassable et que sont contenue est meilleur.
    if(gMax.getNourritureFournie() < this.getContent().getNourritureFournie() && !this.getContent().getOuverte()){
      gMax = this.getContent();
    }
    if(suivant == null){
      if (!gMax.getOuverte()){ return gMax;}
      return null;
    }
    return suivant.getGrainePlusDeNourritureFournieSansDureté(gMax);
  }
  public Graine getGrainePlusDeNourritureFournie(Graine gMax,byte duretéMax){
    // si la graine est cassable et que sont contenu est meilleur.
    if(contenu.getDureté() < duretéMax && gMax.getNourritureFournie() < this.getContent().getNourritureFournie() && !this.getContent().getOuverte()){
      gMax = this.getContent();
      debug.débogage("changement de gMax pour :");
    }
    if(suivant == null){
      if (!gMax.getOuverte() && gMax.getDureté() < duretéMax){ return gMax;}
      return null;
    }
    return suivant.getGrainePlusDeNourritureFournie(gMax, duretéMax);
  }
  public Graine getGraineOuverte(){
    if(this.getSuivant()==null){ return null;}
    if(this.getSuivant().getContent().getOuverte()){ return suivant.getContent();}
    return suivant.getGraineOuverte();
  }


  public Graine getGraineParId(int id){
    if (contenu.getId() == id){
      return contenu;
    }
    if (suivant == null){
      erreur.alerte("La Graine " + id + " n'as pas été trouvé dans la liste de Graine encore vivant.");
      return null;
    }
    return suivant.getGraineParId(id);
  }
  public void retirerGraine(int i){
    if (suivant == null){
      erreur.alerte("La Graine "+ i +" n'as pas été trouvé et n'as donc pas pu être remove");
    }else {
      debug.débogage("Test dans CGraine");
      if(suivant.getContent().getId()==i){
        suivant = suivant.getSuivant(); // on saute un maillons.
      }else {
        debug.débogage("Graine non trouvée "+i+" != "+suivant.getContent().getId());
        suivant.retirerGraine(i);
      }
    }
  }
  public void retirerGraine(Graine i){
    debug.débogage("on a fait début.retirerGraine(i);");
    if (suivant == null){
      erreur.alerte("La Graine "+ i.getId() +" n'as pas été trouvé et n'as donc pas pu être remove");
    }else {
      if(suivant.getContent().equals(i)){
        suivant = suivant.getSuivant(); // on saute un maillons.
      }else {
        suivant.retirerGraine(i);
      }
    }
  }
  public GGraine copierGGraine(){
    GGraine ggr = new GGraine();
    CGraine temp = this;
    while(temp != null){
      ggr.addGraine(temp.getContent());
      temp = temp.getSuivant();
    }
    return ggr;
  }
  public void tour(){
    CGraine temp = this;
    while(temp != null){
      temp.getContent().tour();
      temp = temp.getSuivant();
    }
  }
  /**
  *{@summary Transform a GGraine in Liste&lt;Graine&gt;.}
  *@version 1.38
  */
  public Liste<Graine> toList(){
    CGraine cc = this;
    Liste<Graine> lc = new Liste<Graine>();
    while(cc!= null){
      lc.add(cc.getContent());
      cc = cc.getSuivant();
    }
    return lc;
  }
}
