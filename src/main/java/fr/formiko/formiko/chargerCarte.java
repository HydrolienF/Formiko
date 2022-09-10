package fr.formiko.formiko;

import fr.formiko.formiko.CSquare;
import fr.formiko.formiko.Square;
import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Main;
import fr.formiko.usual.ReadFile;
import fr.formiko.usual.erreur;
import fr.formiko.usual.tableau;
import fr.formiko.usual.types.str;

/**
*{@summary To load a map as a GSquare from a .csv file.}
*@author Hydrolien
*@lastEditedVersion 1.46
*/
public class chargerCarte {
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Load a map as a GSquare from a .csv file.}
  *Only type of case are saved.
  *They are saved as int split by ",".
  *Lines are split by "\n".
  *@lastEditedVersion 1.46
  */
  public static GSquare chargerCarte(String fichier){
    String s [] = null;
    String fullName = Main.getFolder().getFolderStable()+Main.getFolder().getFolderMaps()+fichier+".csv";
    s = ReadFile.readFileArray(fullName);
    if(s==null || s.length==0){
      fullName = Main.getFolder().getFolderResourcesPacks()+Main.getFolder().getFolderMaps()+fichier+".csv";
      s = ReadFile.readFileArray(fullName);
    }
    if(s==null || s.length==0){
      fullName = Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderMaps()+fichier+".csv";
      s = ReadFile.readFileArray(fullName);
    }
    if (s == null || s.length==0){ erreur.erreur("Le chargement de la carte "+fichier+" a échoué.");return null;}
    int x = nbItemPerLine(s[0]);
    int y = nbLineNonEmpty(s);
    // erreur.info("Création d'un GSquare a partir d'un fichier. x="+x+" y="+y);
    GSquare gcr = new GSquare(x,y);
    gcr.setTypes(s);
    return gcr;
  }
  /**
  *{@summary return the number of lines in a Sring [] from a .csv file.}
  *@lastEditedVersion 1.32
  */
  private static int nbLineNonEmpty(String s[]){
    int xr=0;
    for (String ligne : s) {
      if(!ligne.equals("")){xr++;}
    }return xr;
  }
  /**
  *{@summary return the number of items per line in a String [] from a .csv file.}
  *@lastEditedVersion 1.32
  */
  private static int nbItemPerLine(String s){
    // c'est le nombre de ',' +1.
    return str.nbrDeX(s,',')+1;
  }
}
