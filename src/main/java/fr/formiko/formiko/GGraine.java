package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;

import java.io.Serializable;

public class GGraine implements Serializable{
  protected CGraine début;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public void GGraine(){début = null;}
  // GET SET --------------------------------------------------------------------
  public CGraine gethead(){ return début;}
  public void setDébut(CGraine x){début=x;}
  public CGraine getTail(){
    if (début == null ){ return null;}
    CGraine fin = début;
    while (fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
    return fin;
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String r = g.get("GGraine")+" : ";
    if (début == null){
      return r+"ø";
    }
    return r+début.toString();
  }
  public int length(){
    if(début==null){
      return 0;
    }else {
      return début.length();
    }
  }
  public Graine getGrainePlusDeNourritureFournieSansDureté(){
    if (début == null){ erreur.erreur("Impossible de sélectionné la meilleur graine dans une liste vide."); return null;}
    CGraine ci = gethead();
    return ci.getGrainePlusDeNourritureFournieSansDureté(ci.getContent());
  }
  public Graine getGrainePlusDeNourritureFournie(Fourmi f){
    if (début == null){ erreur.erreur("Impossible de sélectionné la meilleur graine dans une liste vide."); return null;}
    byte duretéMax = f.getDuretéMax();
    CGraine ci = gethead();
    //if (ci.getSuivant() != null){
     return ci.getGrainePlusDeNourritureFournie(ci.getContent(),duretéMax);
    /*}
    if(ci.getContent().getDureté() <  f.getDuretéMax() && !ci.getContent().getOuverte()){ // si elle est fermé et cassable.
      return ci.getContent();
    }else{
      return null;
    }*/
  }
  public Graine getGraineOuverte(){
    if (début == null){ erreur.erreur("Impossible de sélectionné 1 graine ouverte dans une liste vide."); return null;}
    if (début.getContent().getOuverte()){ return début.getContent();}
    return début.getGraineOuverte();
  }
    // ici on choisirai la graine avec le plus de nourritureFournie parmi toutes les Graine que la fourmi peut ouvrir.
  //}

  public void afficheToi(){ System.out.println(this);}
  // public void addGraine(CCase cc){ addGraine(new Graine(cc));}
  /**
  *{@summary Add a seed to this GGraine.}
  *@version 1.40
  */
  public void add(Graine i){
    if (i != null){
      CGraine ci = new CGraine(i);
      ci.setSuivant(début);
      début = ci;
    }
  }
  public void addGraine(Graine i){add(i);}
  public void addGg(GGraine giTemp){
    if (this.début == null){
      this.début = giTemp.gethead();
    }else{
      this.getTail().setSuivant(giTemp.gethead()); // On raccroche les 2 bouts.
    }
  }
  public void retirerGraine(int i){
    if (this.début == null){ erreur.alerte("Impossible de retirer i d'un groupe de Graine null"); return;}
    if(début.getContent().getId()==i){
      retirerGraine(début.getContent());
    }else{
      début.retirerGraine(i);
    }
  }
  public void retirerGraine(Graine i){
    debug.débogage("Suppression de : "+i.getId());
    if (i == null){
      erreur.alerte("Impossible de retirer null d'un groupe de Graine");
    }
    if (début == null){
      erreur.alerte("Impossible de retirer une Graine d'un groupe vide.");
    }else if (début.getContent().equals(i)){ // Si c'est le 1a
      début = début.getSuivant(); // On en retir 1.
      debug.débogage("début = début.getSuivant();");
    } else if(début.getSuivant() != null && début.getSuivant().getContent().equals(i)){ // Si c'est le 2a
      début.setSuivant(début.getSuivant().getSuivant());
    }else {
      début.retirerGraine(i);
    }
  }public void retirer(Graine i){ retirerGraine(i);}
  public GGraine copierGGraine(){
    if(début==null){ return new GGraine();}
    return début.copierGGraine();
  }
  public void tour(){
    if(début!=null){ début.tour();}
  }

  /**
  *{@summary Transform a GGraine in Liste&lt;Graine&gt;.}
  *@version 1.38
  */
  public Liste<Graine> toList(){
    if (début==null){
      Liste<Graine> lc = new Liste<Graine>();
      return lc;
    }
    return début.toList();
  }
}
