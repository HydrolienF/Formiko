package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class CInsecte implements Serializable{
  protected CInsecte suivant;
  protected Insecte contenu;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CInsecte(Insecte c){
    contenu = c;
  }
  public CInsecte(){
    this(new Insecte());
  }
  // GET SET --------------------------------------------------------------------
  public CInsecte getSuivant(){return suivant;}
  public void setSuivant(CInsecte ci){suivant = ci;}
  public Insecte getContenu(){return contenu;}
  public Insecte getInsecte(){return getContenu();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if(suivant==null){return contenu.toString();}
    return contenu.toString() +"\n"+ suivant.toString();
  }
  public int length(){
    if(suivant == null){
      return 1;
    }else{
      return 1+suivant.length();
    }
  }
  public Insecte getInsecteParId(int id){
    if (contenu.getId() == id){
      return contenu;
    }
    if (suivant == null){
      erreur.alerte("L'insecte " + id + " n'as pas été trouvé dans la liste d'Insecte encore vivant.");
      return null;
    }
    return suivant.getInsecteParId(id);
  }
  public void tourInsecte(){
    contenu.tour();
    if (suivant != null){
      suivant.tourInsecte();
    }else {
      debug.débogage("tous les insectes ont joué");
    }
  }
  public void preTour(){
    contenu.preTour();
    if (suivant != null){
      suivant.tourInsecte();
    }else {
      debug.débogage("preTour insectes fini");
    }
  }
  public Insecte getInsecteSurLaCase(Point pTest){
    if(contenu.getCCase().getContenu().getPoint().equals(pTest)){
      return contenu;
    } else {
      return null;
    }
  }
  public Insecte getInsectePlusDeNourritureFournie(Insecte i){
    // on actualise l'insecte si néssésaire. (si un autre insecte peut founir plus de nourriture)
    if (this.getInsecte().getNourritureFournie() > i.getNourritureFournie()){
      i = this.getInsecte();
    }
    // Soit on s'arrête :
    if (this.getSuivant() ==  null){ return i;}
    // Soit on continue
    return this.getSuivant().getInsectePlusDeNourritureFournie(i);
  }
  public Insecte getInsectePlusDeNourritureFournie(){
    if (this.getSuivant()== null){ return this.getInsecte();}
    return this.getSuivant().getInsectePlusDeNourritureFournie(this.getInsecte());
  }
  public GInsecte getGiVivant(){
    GInsecte gir = new GInsecte();
    CInsecte ci = this;
    while(ci!=null){
      if (!ci.getContenu().getEstMort()){
        gir.add(ci.getContenu());
      }
      ci=ci.getSuivant();
    }
    return gir;
  }
  public void retirerInsecte(Insecte i){
    if (suivant == null){
      erreur.alerte("L'insecte "+ i.getId() +" n'as pas été trouvé et n'as donc pas pu être retirer");
    }else {
      if(suivant.getContenu().equals(i)){
        debug.débogage("Suppression d'1 Insecte");
        suivant = suivant.getSuivant(); // on saute un maillons.
      }else {
        suivant.retirerInsecte(i);
      }
    }
  }
  public GCreature toGCreature(){
    GCreature gc = new GCreature();
    CInsecte ci = this;
    while(ci!=null){
      gc.add(ci.getContenu());
      ci=ci.getSuivant();
    }
    return gc;
  }
}
