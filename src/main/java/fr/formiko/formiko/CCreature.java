package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exceptions.ListItemNotFoundException;
import fr.formiko.usuel.exceptions.EmptyListException;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;
import java.util.Iterator;

public class CCreature implements Serializable{
  protected CCreature suivant, précédente;
  protected Creature contenu;
  // CONSTRUCTORS --------------------------------------------------------------
  public CCreature(Creature c, CCreature suivant, CCreature précédente){
    contenu = c;
    this.suivant = suivant;
    this.précédente = précédente;
  }
  public CCreature(Creature c){
    this(c, null, null);
  }
  // GET SET -------------------------------------------------------------------
  public CCreature getSuivant(){return suivant;}
  public void setSuivant(CCreature cc){suivant = cc;}
  public CCreature getPrécédent(){return précédente;}
  public void setPrécédent(CCreature cc){précédente = cc;}
  public Creature getContent(){return contenu;}
  public Creature getCreature(){return getContent();}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if (suivant == null){
      return contenu.toString()+"";
    }else{
      return contenu.toString()+ "\n"+suivant.gcToString();
    }
  }public String gcToString(){return toString();}
  public int length(){
    if (suivant == null){
      return 1;
    }else{
      return 1 + suivant.length();
    }
  }

  public Creature getCouvainSale(){
    if (suivant == null){ return null;}
    Fourmi f = (Fourmi) suivant.getContent();
    if (f.getHealth() < 75){
      return f;
    }else{
      return suivant.getCouvainSale();
    }
  }
  public void getCouvainsSale(){
    if (suivant == null){ return;}
    Fourmi f = (Fourmi) suivant.getContent();
    if (f.getHealth() > 75){
      suivant = suivant.getSuivant();
      this.getCouvainsSale();
    }else{
      suivant.getCouvainsSale();
    }
  }
  // public Fourmi getFourmiParFere(Fourmiliere fere){
  //   if(getContent().estFourmi()){
  //     if(((Fourmi)(getContent())).getFere().equals(fere)){return (Fourmi)contenu;}
  //   }
  //   if (this.getSuivant()==null){ return null;}
  //   return suivant.getFourmiParFere(fere);
  // }

  public int [] gcToTInt(){
    int lentr =this.length();
    int tr[]=new int [lentr];int k=0;
    CCreature cc = this;
    while(k<lentr && cc!= null){
      tr[k]=cc.getContent().getId();k++;
      cc=cc.getSuivant();
    }
    return tr;
  }
  public GCreature copier(){
    CCreature cc = this;
    GCreature gcr = new GCreature();
    while(cc!=null){
      gcr.addFin(cc.getContent());//on ajoute seulement le contenu a chaque fois.
      cc=cc.getSuivant();
    }
    return gcr;
  }
  public void remove(Creature c) {
    if (suivant == null){ throw new ListItemNotFoundException("Creature",c.getId());}
    if (suivant.getContent().equals(c)){
      suivant = suivant.getSuivant(); return;
    }
    suivant.remove(c);
  }
  public void afficheToi(){
    int x = 0;
    if(suivant!=null){ x++;}
    if(précédente!=null){ x++;}
    System.out.print(g.get("celluleA")+" "+ x+" "+g.get("lien")+" : ");
    System.out.println(contenu);
  }
  public void afficheToiRéduit(){
    int x = 0;
    if(suivant!=null){ x++;}
    if(précédente!=null){ x++;}
    System.out.print(contenu.getId());
  }
  public void afficheTout(){
    afficheToi();
    if (suivant!=null){
      try {
        suivant.afficheTout();
      }catch (Exception e) {
        erreur.erreur("impossible de remove cet éléments pour cause de stack OverFlow",true);
      }
    }
  }
  public void afficheToutRéduit(){
    afficheToiRéduit();
    if (suivant!=null){
      suivant.afficheToutRéduit();
    }else{
      System.out.println();
    }
  }

  public int [] toTId(){
    int tr []= new int[this.length()];
    CCreature cc = this;
    int k=0;
    while(cc!= null){
      tr[k]=cc.getContent().getId();k++;
      cc = cc.getSuivant();
    }
    return tr;
  }
  /**
  *{@summary Transform a GCreature in Liste&lt;Creature&gt;.}
  *@lastEditedVersion 1.38
  */
  public Liste<Creature> toList(){
    CCreature cc = this;
    Liste<Creature> lc = new Liste<Creature>();
    while(cc!= null){
      lc.add(cc.getContent());
      cc = cc.getSuivant();
    }
    return lc;
  }
}
