package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCreature;
import java.io.Serializable;

public class GGInt implements Serializable{
  private CCInt début, fin;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GGInt(){}
  // GET SET -----------------------------------------------------------------------
  public CCInt getDébut(){ return début;}
  public CCInt getFin(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void ajouter(GInt contenu){ // On ajoute a la fin par défaut.
    CCInt c = new CCInt(contenu);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void add(GInt contenu){ ajouter(contenu);}
  public void afficheToi(){
    if(début==null){
      System.out.println(g.get("GGInt",1,"Le GGInt est vide"));
    }else{
      début.afficheTout();
    }
  }
  public String toString(){
    if(début==null){return "";}
    else{
      return début.toString();
    }
  }
}
