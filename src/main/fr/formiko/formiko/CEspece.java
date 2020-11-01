package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

public class CEspece implements Serializable{
  private Espece contenu;
  protected CEspece suivant;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CEspece(Espece e){
    contenu = e;
  }
  // GET SET --------------------------------------------------------------------
  public Espece getContenu(){return contenu;}
  public void setContenu(Espece c){contenu = c;}
  public CEspece getSuivant(){return suivant;}
  public void setSuivant(CEspece ce){ suivant =ce; }
  // Fonctions propre -----------------------------------------------------------
  public Espece getEspeceParId(int id){
    if (this.getContenu().getId() == id){ return contenu;}
    if (this.getSuivant() != null){
      return suivant.getEspeceParId(id);
    }else{
      return null;
    }
  }
  public String toString(){
    if(suivant==null){ return contenu.toString();}
    return contenu.toString() + "\n" + suivant.toString();
  }
}
