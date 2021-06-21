package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class CIEspece implements Serializable{
  protected IEspece contenu;
  protected CIEspece suivant;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CIEspece(IEspece ie){
    contenu=ie;
  }
  // GET SET --------------------------------------------------------------------
  public IEspece getContent(){return contenu;}
  public void setContenu(IEspece c){contenu = c;}
  public CIEspece getSuivant(){return suivant;}
  public void setSuivant(CIEspece ce){ suivant =ce; }
  public IEspece getIEspeceParId(int id){
    if (this.getContent().getId() == id){ return contenu;}
    if (this.getSuivant() != null){
      return suivant.getIEspeceParId(id);
    }else{
      return null;
    }
  }
  public int getTotal(int x){
    if(suivant==null){return contenu.getCt(x);}
    return contenu.getCt(x)+suivant.getTotal(x);
  }
  public int getIEspeceParAllea(int a, int x){
    a=a-contenu.getCt(x);//on dessend un peu a.
    if(a<0){return contenu.getId();}//si on est passer en dessous de 0 c'est que le nombre alléatoire correpondait.
    if(suivant==null){erreur.erreur("Sortie du GIEspece non désiré.","l'insecte choisi sera le dernier");return contenu.getId();}
    return suivant.getIEspeceParAllea(a,x);//sinon on passe au suivant.
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if(suivant != null){
      return contenu+"\n"+suivant.toString();
    }return contenu+"\n";
  }
  public void afficheToi(){
    contenu.afficheToi();
    if(suivant != null){
      suivant.afficheToi();
    }
  }
}
