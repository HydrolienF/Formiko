package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class CIndividu implements Serializable{
  private Individu contenu;
  protected CIndividu suivant;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CIndividu(Individu iu){
    contenu = iu;
  }
  // GET SET --------------------------------------------------------------------
  public Individu getContent(){return contenu;}
  public void setContenu(Individu c){contenu = c;}
  public CIndividu getSuivant(){return suivant;}
  public void setSuivant(CIndividu ce){ suivant =ce; }
  public Individu getIndividuParType(byte type){
    if (this.getContent().getType() == type){ return this.getContent();}
    if (this.getSuivant() == null){ return null;}
    return suivant.getIndividuParType(type);
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String s = contenu.toString();
    if(suivant==null){return s;}
    return s +"\n"+ suivant.toString();
  }
  public int length(){
    if (suivant == null){ return 1;}
    return 1+suivant.length();
  }

  public int [] getTypeDIndividu(){
    int lentr = this.length();
    int [] tr = new int [lentr];
    CIndividu ci = this;int k=0;
    while(ci != null && k<lentr){
      tr[k]=ci.getContent().getType();k++;
      ci = ci.getSuivant();
    }
    return tr;
  }

}
