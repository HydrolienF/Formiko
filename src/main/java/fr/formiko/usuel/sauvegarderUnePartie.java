package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetAId;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *{@summary Save a game.}<br>
 *@author Hydrolien
 *@version 2.13
 */
public class sauvegarderUnePartie {
  private static ObjectOutputStream oos = null;
  private static ObjectInputStream ois = null;
  private static final String REP = Main.getFolder().getFolderSaves();
  private static String fileName=".null"; //to create a hiden file if it fail.
  private static Save save;

  // GET SET ----------------------------------------------------------------------
  public static void setS(String s2){fileName=s2;}
  public static String getRep(){return REP;}
  public static String getNomDuFichierComplet(){return str.addALaFinSiNecessaire(REP+fileName,".save");}
  /**
  *{@summary Save getter that is able to load Save from the file if null.}
  *@version 2.6
  */
  public static Save getSave(){
    if(save==null){setSave(Save.getSave());}
    return save;
  }
  public static void setSave(Save sa){save=sa;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary Save a Partie }<br>
   *It use the java tools to save in byte code a Serializable Object.
   *@param p The Partie to save
   *@param fileName The name of the output File (It will be place in REP/fileName.save)
   *@version 2.13
   */
  public static void sauvegarder(Partie p, String fn){
    if(p==null){
      erreur.erreur("Can't save a null Partie.");
      return;
    }
    long time = System.currentTimeMillis();
    fileName=fn;
    String s=getNomDuFichierComplet();
    try {
      oos = new ObjectOutputStream(new FileOutputStream(s));
      oos.writeObject(p);
    }catch (Exception e) {
      erreur.erreur("Impossible de sauvegarder la partie pour une raison inconnue");
      return;
    }finally {
      if(oos!=null){
        try {
          oos.flush();
          oos.close();
        }catch (Exception e) {
          erreur.erreur("can't close OOS");
        }
      }
    }
    getSave().addSave();
    getSave().save();// save saveId now to avoid issue if game is not normally close.
    if(debug.getPerformance()){
      erreur.info("Save done in "+(System.currentTimeMillis()-time)+" ms");
    }
  }
  /**
   *{@summary Load a Partie }<br>
   *It use the java tools to load in byte code a Serializable Object.
   *@param fn The name of the input File (File will be REP/fileName.save)
   *@version 2.13
   */
  public static Partie charger(String fn){
    long time = System.currentTimeMillis();
    fileName=fn;
    Partie pa = null;
    try {
      ois = new ObjectInputStream(new FileInputStream(getNomDuFichierComplet()));
      pa = (Partie) ois.readObject();
    }catch (Exception e) {
      erreur.erreur("Impossible de charger la partie "+fileName+" pour une raison inconnue");
    }finally {
      if(ois!=null){
        try {
          ois.close();
        }catch (Exception e) {
          erreur.erreur("can't close OIS");
        }
      }
    }
    if(debug.getPerformance()){
      erreur.info("Save load in "+(System.currentTimeMillis()-time)+" ms");
    }
    return pa;
  }
  /**
  *{@summary Delete a save.}
  *@param fn The name of the file to delete
  *@version 1.1
  */
  public static boolean supprimer(String fn){
    fileName=fn;
    String s = getNomDuFichierComplet();
    File f = new File(s);
    return f.delete();
  }
  /**
  *{@summary make a list with all save in a [] and return it.}<br>
  *return An array of every file aviable in REPSAVE sort in non-ascending order.<br>
  *@version 1.33
  */
  public static String [] listSave(){
    File f = new File(REP);
    String r [] = f.list();
    int lenr = r.length;
    tableau.sort(r,false);
    return r;
  }
}
