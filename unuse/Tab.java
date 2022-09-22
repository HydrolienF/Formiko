package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.util.Arrays;

public class Tab <T>{
  //private T valeur;
  private Object a[];
  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public T [] retirer (T t[], int i){
    int lent = t.length;
    int lentr = lent-1;
    if (i<0 || i>lent) {
      erreurPosition(i);
    }
    //pour créer un tableau dont on ne connait le type qu'a l'exectution :
    //T[] tr = (T[]) Array.newInstance(t,lentr);
    //@SuppressWarnings("uncheked")
    T[] tr = (T[]) a[lentr];
    //T tr [] = new T [lentr];
    for (int j=0;j<i; j++){ //copie des x 1a éléments.
      tr[j]=t[j];
    }
    for (int j=i+1; j<lentr+1; j++){ //copie des x dernier éléments.
      tr[j-1]=t[j];
    }
    return tr;
  }



  private static String f = "tableau";
  public static void erreurPosition(int i){
    erreur.erreur(g.get(f,1,"La position")+" " + i + " "+g.get(f,2,"n'existe pas dans le tableau")+".", "tableau.retir");
  }
  public static void erreurPositionCorrigé(int i){
    erreur.erreur(g.get(f,1,"La position")+" " + i + " "+g.get(f,2,"n'existe pas dans le tableau")+".", "tableau.retir",g.get(f,3,"On ajoute x en position finale."));
  }
  public static void erreurVide(){
    erreur.alerte(g.get(f,5,"Le tableau est vide !"),"tableau.");
  }
  public static void erreurElementManquant(String x){
    erreur.alerte("\""+x + "\" "+g.get(f,4,"n'est pas présent dans le tableau")+".", "tableau.retirX");
  }
}
