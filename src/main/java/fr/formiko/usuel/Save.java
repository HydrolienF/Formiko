package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
*Save all important information about game as what's the id of save.
*@author Hydrolien
*@version 1.14
*/
public class Save implements Serializable{
  private static final long serialVersionUID = 42l;
  private int idS;
  private static File f = new File("data/.save");

  // CONSTRUCTEUR ---------------------------------------------------------------

  // GET SET --------------------------------------------------------------------
  public void addSave(){idS++;}
  public int getIdS(){return idS;}
  // Fonctions propre -----------------------------------------------------------
  public static Save getSave(){
    Save r;
    if(f.exists()){
      try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getPath()+""));
        r = (Save) ois.readObject();
      }catch (Exception e) {
        r = new Save();
        r.idS=1;
      }
    }else{
      r = new Save();
      r.idS=1;
    }
    return r;
  }
  public void save(){
    try {
      if(!f.exists()){
        if(!f.createNewFile()){
          erreur.erreur("le fichier n'as pas été créer","Save.save");
        }
      }
    }catch (Exception e) {
      erreur.erreur("le fichier n'as pas été créer","Save.save");
    }
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f.getPath()+""));
      oos.writeObject(this);
    }catch (Exception e) {
      erreur.erreur("le fichier n'as pas été rempli","Save.save");
    }
  }
}
