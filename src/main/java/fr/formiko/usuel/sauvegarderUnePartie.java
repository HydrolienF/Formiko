package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.formiko.ObjetAId;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 *{@summary Save a game.}<br>
 *@author Hydrolien
 *@lastEditedVersion 2.13
 */
public class sauvegarderUnePartie {
  private static final String REP = Main.getFolder().getFolderSaves();
  private static String fileName=".null"; //to create a hiden file if it fail.
  private static Save save;

  // GET SET ----------------------------------------------------------------------
  public static void setS(String s2){fileName=s2;}
  public static String getRep(){return REP;}
  public static String getNomDuFichierComplet(){return str.addALaFinSiNecessaire(REP+fileName,".save");}
  /**
  *{@summary Save getter that is able to load Save from the file if null.}
  *@lastEditedVersion 2.6
  */
  public static Save getSave(){
    if(save==null){setSave(Save.getSave());}
    return save;
  }
  public static void setSave(Save sa){save=sa;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary Save a Partie.}<br>
   *It use the java tools to save in byte code a Serializable Object.
   *@param p The Partie to save
   *@param fn The name of the output File (It will be place in REP/fileName.save)
   *@lastEditedVersion 2.22
   */
  public static void sauvegarder(Partie p, String fn){
    if(p==null){
      erreur.erreur("Can't save a null Partie.");
      return;
    }
    if(p.getPlayingJoueur()==null && p.getGj().length()>1){
      erreur.alerte("Unable to save while there is no current player");
      System.out.println("Unable to save while there is no current player");
      sendMessageSaveFail();
      return;
    }
    p.setLaunchingFromSave(true);
    long time = System.currentTimeMillis();
    fileName=fn;
    String s=getNomDuFichierComplet();
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(s))){
      oos.writeObject(p);
    }catch(StackOverflowError e){
      erreur.erreur("To many item relation to save: StackOverflowError");
      System.out.println("To many item relation to save: StackOverflowError");//@a
      sendMessageSaveFail();
      return;
    }catch (Exception e) {
      erreur.erreur("Unable to save current Partie because of "+e);
      System.out.println("Unable to save current Partie because of "+e);//@a
      sendMessageSaveFail();
      return;
    }
    getSave().addSave();
    getSave().save();// save saveId now to avoid issue if game is not normally close.
    if(debug.getPerformance()){
      erreur.info("Save done in "+(System.currentTimeMillis()-time)+" ms");
    }
    sendMessageSaveWork();
  }
  private static void sendMessageSaveFail(){
    new Message("messageSaveFail");
  }
  private static void sendMessageSaveWork(){
    new Message("messageSaveWork");
  }
  /**
   *{@summary Load a Partie.}<br>
   *It use the java tools to load in byte code a Serializable Object.
   *@param fn The name of the input File (File will be REP/fileName.save)
   *@lastEditedVersion 2.22
   */
  public static Partie charger(String fn){
    long time = System.currentTimeMillis();
    fileName=fn;
    Partie pa = null;
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getNomDuFichierComplet()))) {
      pa = (Partie) ois.readObject();
    }catch(StackOverflowError e){
      erreur.erreur("To many item to load: StackOverflowError");
    }catch (Exception e) {
      erreur.erreur("Unable to load Partie from "+fileName+" because of "+e);
    }
    if(debug.getPerformance()){
      erreur.info("Save load in "+(System.currentTimeMillis()-time)+" ms");
    }
    return pa;
  }
  /**
  *{@summary Delete a save.}
  *@param fn The name of the file to delete
  *@lastEditedVersion 1.1
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
  *@lastEditedVersion 2.16
  */
  public static String [] listSave(){
    File[] files = new File(REP).listFiles();
    Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
    int len = files.length;
    String r [] = new String[len];
    for (int i=0; i<len;i++ ) {
      String s=files[i].getName();
      int lenS = s.length();
      if(lenS>5){
        r[i]=s.substring(0,lenS-5);
      }else{
        r[i]="";
        erreur.alerte("A file name in save are to short to be concidered as a save: "+s);
      }
    }
    // File f = new File(REP);
    // String r [] = f.list();
    // int lenr = r.length;
    // tableau.sort(r,false);
    return r;
  }
}
