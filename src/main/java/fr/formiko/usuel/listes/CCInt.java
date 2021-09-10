package fr.formiko.usuel.listes;

//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

public class CCInt implements Serializable{
  private CCInt suivant, précédent;
  private GInt contenu;
  // CONSTRUCTORS ----------------------------------------------------------------
  public CCInt(GInt contenu){
    this.contenu = contenu;
  }
  // GET SET ----------------------------------------------------------------------
  public CCInt getSuivant(){return suivant;}
  public void setSuivant(CCInt cs){suivant = cs;}
  public CCInt getPrécédent(){return précédent;}
  public void setPrécédent(CCInt cs){précédent = cs;}
  public GInt getContent(){ return contenu;}
  public void setContenu(GInt x){contenu=x;}
  // FUNCTIONS -----------------------------------------------------------------
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
  public int sommeCase(int ca){
    if(suivant==null){ return contenu.getCase(ca);}
    return contenu.getCase(ca)+suivant.getContent().getCase(ca);
  }
}
