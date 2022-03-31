package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary List of Individu.}<br>
*@lastEditedVersion 2.2
*@author Hydrolien
*/
public class GIndividu extends Liste<Individu> implements Serializable{
  // CONSTRUCTEUR --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------

  // Fonctions propre ----------------------------------------------------------
  /**
  *{@summary Return the individu corresponding to type.}<br>
  *There is only 1 (or 0) individu for every type.
  *@param type type of the searched Individu.
  *@lastEditedVersion 2.2
  */
  public Individu getIndividuByType(byte type){
    for (Individu in : this ) {
      if (in.getType() == type){ return in;}
    }
    return null;
  }public Individu getIndividuByType(int type){ return getIndividuByType((byte)type);}
  /**
  *{@summary Return all the aviable type in this GIndividu.}<br>
  *@return an array of int that contain all avaible type.
  *@lastEditedVersion 2.2
  */
  public int [] getAviableType(){
    int lentr = this.length();
    int [] tr = new int [lentr];
    int k=0;
    for (Individu in : this ) {
      tr[k]=in.getType();k++;
    }
    return tr;
  }
  //static ---------------------------------------------------------------------
  /**
  *{@summary Load the individu from a File.}<br>
  *File is .../stable/bin/Individu.csv
  *@lastEditedVersion 2.2
  */
  public static void loadIndividus(){
    String td [] = new String [0];
    try {
      td = lireUnFichier.readFileArray(Main.getFolder().getFolderStable()+Main.getFolder().getFolderBin()+"Individu.csv");
    }catch (Exception e) {
      erreur.erreur("Le fichier des Individu n'as pas pu être localisé. il devrais y avoir un dossier data et celui ci devrait contenir un fichier Individu.txt",true);
    } int lentd = td.length;
    if (lentd < 3){ erreur.erreur("Le fichier des Individu devrais contenir au moins 4 lignes dont 1 Individu",true);}
    String tDefault [] = decoderUnFichier.getTableauString(td[2],',');
    for (int i=3;i<lentd; i++) {
      Individu iu = createIndividu(td[i],tDefault); // on le cré.
      iu.getEspece().getGIndividu().add(iu); // on l'ajoute a son espèce.
    }
  }
  /**
  *{@summary Create an individu from a .csv file line.}<br>
  *tDefault is used to fill empty cell.<br>
  *@param csvFileLine a scv file line sorted for Individu constructor.
  *@param tDefault default value for the array.
  *@lastEditedVersion 2.2
  */
  private static Individu createIndividu(String csvFileLine, String [] tDefault){
    debug.débogage("Création d'un nouvel Individu");
    // test ici
    String t [] = decoderUnFichier.getTableauString(csvFileLine,',');
    tableau.boucherLesCasesVide(t,tDefault);
    int idEsp = str.sToI(t[0]);
    byte ty=str.sToBy(t[1]); boolean se=str.sToB(t[2]); boolean se2=str.sToB(t[3]); byte né=str.sToBy(t[4]);  byte ac=str.sToBy(t[5]); int ta=str.sToI(t[6]);
    String co=t[7]; byte po=str.sToBy(t[8]); byte te=str.sToBy(t[9]); int tag0=str.sToI(t[10]);  int tag1=str.sToI(t[11]);  int tag2=str.sToI(t[12]);  int tag3=str.sToI(t[13]);
    int nm0=str.sToI(t[14]);  int nm1=str.sToI(t[15]);  int nm2=str.sToI(t[16]);  int nm3=str.sToI(t[17]); byte ca0=str.sToBy(t[18]); byte ca1=str.sToBy(t[19]); byte ca2=str.sToBy(t[20]);
    byte ca3=str.sToBy(t[21]);byte ca4=str.sToBy(t[22]);byte ca5=str.sToBy(t[23]); int nc = str.sToI(t[24]);
    Individu iur = new Individu(idEsp, ty, se, se2, né, ac, ta, co, po, te, tag0, tag1, tag2, tag3, nm0, nm1, nm2, nm3, ca0,ca1,ca2,ca3,ca4,ca5,nc);
    return iur;
  }
}
