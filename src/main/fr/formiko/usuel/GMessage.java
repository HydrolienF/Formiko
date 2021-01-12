package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import java.io.Serializable;

public class GMessage implements Serializable{
  private CMessage début, fin;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GMessage(){}
  // GET SET --------------------------------------------------------------------
  public CMessage getDébut(){ return début;}
  public CMessage getFin(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void ajouter(Message s){ // On ajoute a la fin par défaut.
    CMessage c = new CMessage(s);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void ajouter(GMessage gs){
    if(gs==null){ return;}
    if(this.getDébut()==null){début = gs.getDébut();return;}
    //on lie l'anciène fin au début de gs.
    this.fin.setSuivant(gs.getDébut());
    this.fin.getSuivant().setPrécédent(fin);
    // on change la fin actuelle.
    this.fin = gs.getFin();
  }
  public void add(Message s){ ajouter(s);}
  public void add(GMessage s){ ajouter(s);}
  public void afficheToi(){
    if(début==null){
      System.out.println(g.get("GMessage",1,"Le GMessage est vide"));
    }else{
      début.afficheTout();
    }
  }
  public boolean contient(Message s){
    if (début==null){ return false;}
    return début.contient(s);
  }
  public GString gmToGs(int x){
    if (début==null){ return new GString();}
    return fin.gmToGs(x);
  }
}
