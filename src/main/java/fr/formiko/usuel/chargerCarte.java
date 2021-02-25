package fr.formiko.usuel;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

public class chargerCarte {
  // Fonctions propre -----------------------------------------------------------
  public static GCase chargerCarte(String fichier){
    fichier = Main.getFolder().getFolderStable()+Main.getFolder().getFolderMaps()+fichier+".csv";
    String s [] = null;
    try {
      s = lireUnFichier.lireUnFichier(fichier);
    }catch (Exception e) {
      erreur.erreur("Le chargement de la carte "+fichier+" a échoué car le fichier n'as pas été reconu.");return null;
    }
    if (s == null){ erreur.erreur("Le chargement de la carte "+fichier+" a échoué.");return null;}
    int x = nbItemPerLine(s[0]);
    int y = nbLineNonEmpty(s);
    debug.débogage("Création d'un GCase a partir d'un fichier. x="+x+" y="+y);
    GCase gcr = new GCase(x,y);
    gcr.setTypes(s);
    return gcr;
  }
  /**
  *{@summary return the number of lines in a Sring [] from a .csv file.}
  *@version 1.32
  */
  public static int nbLineNonEmpty(String s[]){
    int xr=0;
    for (String ligne : s) {
      if(!ligne.equals("")){xr++;}
    }return xr;
  }
  /**
  *{@summary return the number of items per line in a Sring [] from a .csv file.}
  *@version 1.32
  */
  public static int nbItemPerLine(String s){
    // c'est le nombre de ',' +1.
    return str.nbrDeX(s,',')+1;
  }
}
