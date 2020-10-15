package fr.formiko.usuel.liste;
import javax.swing.JComboBox;
import fr.formiko.usuel.erreur;

public class CCInt {
  private CCInt suivant, précédent;
  private GInt contenu;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public CCInt(GInt contenu){
    this.contenu = contenu;
  }
  // GET SET -----------------------------------------------------------------------
  public CCInt getSuivant(){return suivant;}
  public void setSuivant(CCInt cs){suivant = cs;}
  public CCInt getPrécédent(){return précédent;}
  public void setPrécédent(CCInt cs){précédent = cs;}
  public GInt getContenu(){ return contenu;}
  public void setContenu(GInt x){contenu=x;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(suivant==null){ return 1;}
    return 1+suivant.length();
  }
  public void afficheTout(){
    afficheToi();
    if(suivant != null){ suivant.afficheTout();}
  }
  public void afficheToi(){
    contenu.afficheToi();
  }
  public String toString(){
    String s = contenu.toString();
    if(suivant != null){ return s+suivant.toString();}
    else{
      return s;
    }
  }
}