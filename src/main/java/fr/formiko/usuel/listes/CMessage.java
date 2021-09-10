package fr.formiko.usuel.listes;

//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Message;
import java.io.Serializable;

public class CMessage implements Serializable{
  private CMessage suivant, précédent;
  public Message contenu;
  // CONSTRUCTORS --------------------------------------------------------------
  public CMessage(Message m){contenu=m;}
  // GET SET -------------------------------------------------------------------
  public CMessage getSuivant(){return suivant;}
  public void setSuivant(CMessage cs){suivant = cs;}
  public CMessage getPrécédent(){return précédent;}
  public void setPrécédent(CMessage cs){précédent = cs;}
  public Message getContent(){ return contenu;}
  public void setContenu(Message x){contenu=x;}
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
  public GMessage filtreDoublon(){
    GMessage gsr = new GMessage();
    CMessage csTemp = this;
    while(csTemp != null){
      Message s = csTemp.getContent();
      if (!gsr.contient(s)){
        gsr.add(s);
      }
      csTemp = csTemp.getSuivant();
    }
    return gsr;
  }
  public boolean contient(Message s){
    CMessage csTemp = this;
    while(csTemp != null){
      if(csTemp.getContent().equals(s)){ return true;}
      csTemp = csTemp.getSuivant();
    }return false;
  }
  public GString gmToGs(int x){
    GString gs = new GString();
    CMessage csTemp = this;
    while(x>0 && csTemp != null){
      gs.add(csTemp.getContent().description());
      csTemp = csTemp.getPrécédent();
      x--;
      if(x==0){
        gs.addTail("...");
      }
    }
    return gs;
  }
}
