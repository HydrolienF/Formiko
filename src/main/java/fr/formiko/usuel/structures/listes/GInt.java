package fr.formiko.usuel.structures.listes;

import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCreature;
import fr.formiko.usuel.erreur;
import fr.formiko.formiko.Fourmi;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary Integer List that can be used to compute score of an anthill.}
*@lastEditedVersion 2.23
*/
public class GInt extends Liste<Integer> implements Serializable{
  // private CInt début, fin;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create an empty int list.}
  *@lastEditedVersion 2.23
  */
  public GInt(){super();}
  /**
  *{@summary Create a score GInt from an anthill.}
  *@param fere the anthill that we want the score
  *@lastEditedVersion 2.23
  */
  public GInt(Fourmiliere fere){
    this();
    GCreature gc = fere.getGc();
    if(gc.length()==0){return;}
    if(gc.getReine()!=null){add(1);}else{add(0);}
    add(gc.getGcStade(0).length()-getFirst());
    for(int i=-1; i>-4;i--){
      add(gc.getGcStade(i).length());
    }
    add(fere.getNbrFourmisMorte());
  }
  // GET SET ----------------------------------------------------------------------
  // public CInt getHead(){ return début;}
  // public CInt getTail(){ return fin;}
  // FUNCTIONS -----------------------------------------------------------------
  // public int length(){
  //   if(début==null){ return 0;}
  //   return début.length();
  // }
  // public void add(int s){ // On ajoute a la fin par défaut.
  //   CInt c = new CInt(s);
  //   if (fin == null){ // si la liste est complètement vide.
  //     fin = c;
  //     début = c;
  //   } else {
  //     fin.setSuivant(c);
  //     c.setPrécédent(fin);
  //     fin = c;
  //   }
  // }
  // public void add(GInt gs){
  //   if(gs==null){ return;}
  //   if(this.getHead()==null){début = gs.getHead();return;}
  //   //on lie l'anciène fin au début de gs.
  //   this.fin.setSuivant(gs.getHead());
  //   this.fin.getSuivant().setPrécédent(fin);
  //   // on change la fin actuelle.
  //   this.fin = gs.getTail();
  // }
  /**
  *{@summary Return score of an anthill from this.}
  *We assume that this have been created with an anthill as parameter
  *@param fere the anthill that we want the score
  *@return the computed score
  *@lastEditedVersion 2.23
  */
  public int computeScore(Fourmiliere fere){
    if(isEmpty()){
      erreur.alerte("le GInt est null, impossible de calculer le score du joueur.");return -1;
    }else if(length()!=6){
      erreur.alerte("le GInt ne contient pas le bon nombre de CInt, impossible de calculer le score du joueur.");return -1;
    }else{
      int total=0;
      int multiscore [] = {50, 20, 9, 6, 3, -1};
      int lenms = multiscore.length;
      int k=0;
      for (int val : this) {
        if(k>=lenms){
          erreur.alerte("To much value for score");
          break;
        }
        total+=val*multiscore[k];
        k++;
      }
      if(fere.getJoueur()!=null && !fere.getJoueur().getIa() && fere.getGc()!=null && fere.getGc().toList().getFirst()!=null){
        total=(int)(total*((Fourmi) (fere.getGc().toList().getFirst())).getMultiplicateurDeDiff());
      }
      return total;
      // int x=0;
      // x=contenu*50 + suivant.getContent()*20 + suivant.getSuivant().getContent()*9 + suivant.getSuivant().getSuivant().getContent()*6 + suivant.getSuivant().getSuivant().getSuivant().getContent()*3 + suivant.getSuivant().getSuivant().getSuivant().getSuivant().getContent()*(-1);
      // try {
      //   if(!fere.getJoueur().getIa()){
      //     x=(int)(x*((Fourmi) (fere.getGc().getHead().getContent())).getMultiplicateurDeDiff());
      //   }
      // }catch (Exception e) {
      //   erreur.erreurGXVide("GCreature");
      // }
      // return x;
      // return début.computeScore(fere);
    }
  }
  // public String toString(){
  //   if(début==null){return "";}
  //   else{
  //     return début.toString()+"\n";
  //   }
  // }
  /**
  *{@summary Return an element of this.}
  *@param ca id of the element
  *@return the ca element of the GInt, or 0 if not found.
  *@lastEditedVersion 2.23
  */
  public int getCase(int ca){
    if(ca<0){return -1;}
    try {
      return get(ca);
    }catch (Exception e) {
      return 0;
    }
    // if(début==null){return 0;}
    // else{return début.getCase(ca);}
    /*
    if(ca==0){return contenu;}
    if(suivant==null){return 0;}
    else{return suivant.getCase(ca-1);}
    */
  }
}
