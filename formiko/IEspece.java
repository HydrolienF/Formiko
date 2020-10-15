package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.tableau;

public class IEspece {
  private int ct[];
  protected final int id; protected static int cptid=0;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public IEspece(int ct []){
    this.id = cptid; cptid++;
    this.ct=ct;
  }
  // GET SET --------------------------------------------------------------------
  public int getCt(int i){
    i--;
    if(i<0 || i>=ct.length){erreur.erreur("Impossible de récupéré la valeur de la case "+i,"IEspece.getCt");return 0;}
    return ct[i];}
  public int [] getCt(){return ct;}
  public int getId(){ return id;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return "IEspece "+getId()+tableau.tableauToString(ct);
  }
  public void afficheToi(){
    System.out.println(this);
  }
}