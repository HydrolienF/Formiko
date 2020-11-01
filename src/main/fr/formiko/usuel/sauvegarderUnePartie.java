package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.ObjetAId;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fr.formiko.usuel.conversiondetype.str;

/**
 *{@summary Save a game <br/>}
 *@author Hydrolien
 *@version 1.1
 */
public class sauvegarderUnePartie {
  private static ObjectOutputStream oos = null;
  private static ObjectInputStream ois = null;
  private static final String rep = "data/sauvegarde/";
  private static String s="null";

  // GET SET -----------------------------------------------------------------------
  public static void setS(String s2){s=s2;}
  public static String getRep(){return rep;}
  public static String getNomDuFichierComplet(){return str.ajouterALaFinSiNecessaire(rep+s,".save");}
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary Save a Partie <br/>}
   *It use the java tools to save in byte code a Serializable Object.
   *@param p The Partie to save
   *@param nomDuFichier The name of the output File (It will be place in rep/nomDuFichier.save)
   *@version 1.2
   */
  public static void sauvegarder(Partie p, String nomDuFichier){
    s=nomDuFichier;
    try {
      oos = new ObjectOutputStream(new FileOutputStream(getNomDuFichierComplet()));
      oos.writeObject(p);
      oos.flush();//ca permet de bien nétoyer le flu si j'ai bien compris
    }catch (Exception e) {
      erreur.erreur("Impossible de sauvegarder la partie pour une raison inconnue","sauvegarderUnePartie.sauvegarder");
    }
  }
  /**
   *{@summary Load a Partie <br/>}
   *It use the java tools to load in byte code a Serializable Object.
   *@param nomDuFichier The name of the input File (File will be rep/nomDuFichier.save)
   *@version 1.2
   */
  public static Partie charger(String nomDuFichier){
    s=nomDuFichier;
    Partie pa = null;
    try {
      ois = new ObjectInputStream(new FileInputStream(getNomDuFichierComplet()));
      pa = (Partie) ois.readObject();
    }catch (Exception e) {
      erreur.erreur("Impossible de charger la partie pour une raison inconnue","sauvegarderUnePartie.sauvegarder");
    }
    return pa;
  }
}
