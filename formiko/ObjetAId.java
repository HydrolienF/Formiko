package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class ObjetAId {
  protected static int ic=1;
  protected final int id;

  // CONSTRUCTEUR ---------------------------------------------------------------
  //Principal
  public ObjetAId(){
    id = ic; ic++;
  }
  // GET SET --------------------------------------------------------------------
  public int getId(){return id;}
  public static int getIc(){return ic;}
  public static int getI(){return getIc();}
  // Fonctions propre -----------------------------------------------------------
  public boolean equals(ObjetAId o){
    if(id == o.id){ return true;}
    return false;
  }
}