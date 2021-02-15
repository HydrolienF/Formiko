package fr.formiko.usuel.listes;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.usuel.erreur;

import java.io.Serializable;
import javax.swing.JComboBox;

public class CInt implements Serializable{
  private CInt suivant, précédent;
  private int contenu;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public CInt(int contenu){
    this.contenu = contenu;
  }
  // GET SET -----------------------------------------------------------------------
  public CInt getSuivant(){return suivant;}
  public void setSuivant(CInt cs){suivant = cs;}
  public CInt getPrécédent(){return précédent;}
  public void setPrécédent(CInt cs){précédent = cs;}
  public int getContenu(){ return contenu;}
  public void setContenu(int x){contenu=x;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(suivant==null){ return 1;}
    return 1+suivant.length();
  }
  public void afficheTout(){
    afficheToi();
    if(suivant != null){ System.out.print(", ");suivant.afficheTout();}
  }
  public void afficheToi(){
    System.out.print(contenu);
  }
  public int calculerScore(Fourmiliere fere){
    int x=0;
    x=contenu*50 + suivant.getContenu()*20 + suivant.getSuivant().getContenu()*9 + suivant.getSuivant().getSuivant().getContenu()*6 + suivant.getSuivant().getSuivant().getSuivant().getContenu()*3 + suivant.getSuivant().getSuivant().getSuivant().getSuivant().getContenu()*(-1);
    try {
      if(!fere.getJoueur().getIa()){
        x=(int)(x*((Fourmi) (fere.getGc().getDébut().getContenu())).getMultiplicateurDeDiff());
      }
    }catch (Exception e) {
      erreur.erreurGXVide("GCreature");
    }
    return x;
  }
  public String toString(){
    if(suivant==null){return contenu+"";}
    else{return contenu+","+suivant.toString();}
  }
  public int getCase(int ca){
    if(ca==0){return contenu;}
    if(suivant==null){return 0;}
    else{return suivant.getCase(ca-1);}
  }
}
