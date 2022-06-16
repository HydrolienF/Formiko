package fr.formiko.usual;

import fr.formiko.formiko.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
*Save all importants informations about game as what's the id of save.
*@author Hydrolien
*@lastEditedVersion 1.14
*/
public class Save implements Serializable {
  private static final long serialVersionUID = 42l;
  private int idS;
  private static File f = new File(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderBin()+".save");

  // CONSTRUCTORS --------------------------------------------------------------
  /** Empty constructor. */
  public Save(){}
  /**
  *{@summary Constructor with a specific file name.}
  *@param fileName the file name
  *@lastEditedVersion 2.6
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
  *@lastEditedVersion 2.6
  */
  public static Save getSave() {
    Save r;
    if(f.exists()) {
      ObjectInputStream ois=null;
      FileInputStream fis=null;
      try {
        fis = new FileInputStream(f.getPath()+"");
        ois = new ObjectInputStream(fis);
        r = (Save) ois.readObject();
      } catch (Exception e) {
        r = new Save();
        r.idS=1;
      } finally {
        // to be sur that everything is close & can be deleted we need to close ObjectStream & FileStream (if ObjectStream is null we need to close it separatly).
        if(ois!=null){
          try {
            ois.close();
          }catch (IOException e) {
            erreur.erreur("fail to close ois");
          }
        } else if(fis!=null){
          try {
            fis.close();
          }catch (IOException e) {
            erreur.erreur("fail to close fis");
          }
        }
      }
    }else{
      r = new Save();
      r.idS=1;
    }
    return r;
  }
  /**
  *{@summary save idSave as .save in bin/.}<br>
  *@lastEditedVersion 2.6
  */
  public void save() {
    try {
      if(!f.exists()){
        if(!f.createNewFile()){
          erreur.erreur("File can't be create");
        }
      }
    }catch (Exception e) {
      erreur.erreur("File can't be create");
    }
    ObjectOutputStream oos=null;
    FileOutputStream fos=null;
    try {
      fos = new FileOutputStream(f.getPath()+"");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.flush();
    }catch (Exception e) {
      erreur.erreur("File can't be write");
    } finally {
      // to be sur that everything is close & can be deleted we need to close ObjectStream & FileStream (if ObjectStream is null we need to close it separatly).
      if(oos!=null){
        try {
          oos.close();
        } catch (IOException e) {
          erreur.erreur("fail to close oos");
        }
      } else if(fos!=null) {
        try {
          fos.close();
        } catch (IOException e) {
          erreur.erreur("fail to close fos");
        }
      }
    }
  }
}
