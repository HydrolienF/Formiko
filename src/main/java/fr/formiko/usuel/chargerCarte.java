package fr.formiko.usuel;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.ReadFile;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

/**
*{@summary To load a map as a GCase from a .csv file.}
*@author Hydrolien
*@lastEditedVersion 1.46
*/
public class chargerCarte {
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Load a map as a GCase from a .csv file.}
  *Only type of case are saved.
  *They are saved as int split by ",".
  *Lines are split by "\n".
  *@lastEditedVersion 1.46
  */
  public static GCase chargerCarte(String fichier){
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
    debug.débogage("Création d'un GCase a partir d'un fichier. x="+x+" y="+y);
    GCase gcr = new GCase(x,y);
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
