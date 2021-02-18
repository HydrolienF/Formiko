package fr.formiko.usuel;

import java.io.File;
/**
*{@summary Do thing about save.}<br>
*@author Hydrolien
*@version 1.33
*/
public class save {
  private static String REPSAVE = "data/sauvegarde/";
  /**
  *{@summary make a list with all save in a [] and return it.}<br>
  *return An array of every file aviable in REPSAVE sort in non-ascending order.<br>
  *@version 1.33
  */
  public static String [] listSave(){
    File f = new File(REPSAVE);
    String r [] = f.list();
    int lenr = r.length;
    /*for (int i=0;i<lenr ;i++ ) {
      try {
        r[i]=r[i].substring(0,r[i].length()-6);
      }catch (Exception e) {}
    }*/
    tableau.sort(r,false);
    return r;
  }
}
