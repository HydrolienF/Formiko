package fr.formiko.usuel;

import fr.formiko.formiko.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
*Save all importants informations about game as what's the id of save.
*@author Hydrolien
*@version 1.14
*/
public class Save implements Serializable{
  private static final long serialVersionUID = 42l;
  private int idS;
  private static File f = new File(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderBin()+".save");

  // CONSTRUCTORS --------------------------------------------------------------
  /** Empty constructor. */
  public Save(){}
  /**
  *{@summary Constructor with a specific file name.}
  *@param fileName the file name
  *@version 2.6
  */
  public Save(String fileName){
    f=new File(fileName);
  }

  // GET SET -------------------------------------------------------------------
  public void addSave(){idS++;}
  public int getIdS(){return idS;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary get save informations.}<br>
  *@version 1.14
  */
  public static Save getSave(){
    Save r;
    if(f.exists()){
      try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getPath()+""));
        r = (Save) ois.readObject();
        ois.close();
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
  /**
  *{@summary save idSave as .save in bin/.}<br>
  *@version 1.14
  */
  public void save(){
    try {
      if(!f.exists()){
        if(!f.createNewFile()){
          erreur.erreur("le fichier n'as pas été créer");
        }
      }
    }catch (Exception e) {
      erreur.erreur("le fichier n'as pas été créer");
    }
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f.getPath()+""));
      oos.writeObject(this);
      oos.flush();
      oos.close();
    }catch (Exception e) {
      erreur.erreur("le fichier n'as pas été rempli");
    }
  }
}
