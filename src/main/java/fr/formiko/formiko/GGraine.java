package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.List;

import java.io.Serializable;

public class GGraine implements Serializable{
  protected CGraine début;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public void GGraine(){début = null;}
  // GET SET --------------------------------------------------------------------
  public CGraine getDébut(){ return début;}
  public void setDébut(CGraine x){début=x;}
  public CGraine getFin(){
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
    CGraine ci = getDébut();
    return ci.getGrainePlusDeNourritureFournieSansDureté(ci.getContenu());
  }
  public Graine getGrainePlusDeNourritureFournie(Fourmi f){
    if (début == null){ erreur.erreur("Impossible de sélectionné la meilleur graine dans une liste vide."); return null;}
    byte duretéMax = f.getDuretéMax();
    CGraine ci = getDébut();
    //if (ci.getSuivant() != null){
     return ci.getGrainePlusDeNourritureFournie(ci.getContenu(),duretéMax);
    /*}
    if(ci.getContenu().getDureté() <  f.getDuretéMax() && !ci.getContenu().getOuverte()){ // si elle est fermé et cassable.
      return ci.getContenu();
    }else{
      return null;
    }*/
  }
  public Graine getGraineOuverte(){
    if (début == null){ erreur.erreur("Impossible de sélectionné 1 graine ouverte dans une liste vide."); return null;}
    if (début.getContenu().getOuverte()){ return début.getContenu();}
    return début.getGraineOuverte();
  }
    // ici on choisirai la graine avec le plus de nourritureFournie parmi toutes les Graine que la fourmi peut ouvrir.
  //}

  public void afficheToi(){ System.out.println(this);}
  // public void ajouterGraine(CCase cc){ ajouterGraine(new Graine(cc));}
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
  }public void ajouter(Graine i){add(i);}
  public void ajouterGraine(Graine i){add(i);}
  public void ajouterGg(GGraine giTemp){
    if (this.début == null){
      this.début = giTemp.getDébut();
    }else{
      this.getFin().setSuivant(giTemp.getDébut()); // On raccroche les 2 bouts.
    }
  }
  public void retirerGraine(int i){
    if (this.début == null){ erreur.alerte("Impossible de retirer i d'un groupe de Graine null"); return;}
    if(début.getContenu().getId()==i){
      retirerGraine(début.getContenu());
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
    }else if (début.getContenu().equals(i)){ // Si c'est le 1a
      début = début.getSuivant(); // On en retir 1.
      debug.débogage("début = début.getSuivant();");
    } else if(début.getSuivant() != null && début.getSuivant().getContenu().equals(i)){ // Si c'est le 2a
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
  *{@summary Transform a GGraine in List&lt;Graine&gt;.}
  *@version 1.38
  */
  public List<Graine> toList(){
    if (début==null){
      List<Graine> lc = new List<Graine>();
      return lc;
    }
    return début.toList();
  }
}
