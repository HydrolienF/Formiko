package fr.formiko.usuel;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.graphisme.PanneauChargement;

public class chargerCarte {
  // Fonctions propre -----------------------------------------------------------
  public static GCase chargerCarte(String fichier){
    if(fichier.equals("désert")){PanneauChargement.setA(1);}
    fichier = "data/carte/"+fichier+".csv";
    String s [] = null;
    try {
      s = lireUnFichier.lireUnFichier(fichier);
    }catch (Exception e) {
      erreur.erreur("Le chargement de la carte "+fichier+" a échoué car le fichier n'as pas été reconu.");return null;
    }
    if (s == null){ erreur.erreur("Le chargement de la carte "+fichier+" a échoué.");return null;}
    int x = nbrDElementParLigne(s[0]);
    int y = ligneNonvide(s);
    debug.débogage("Création d'un GCase a partir d'un fichier. x="+x+" y="+y);
    GCase gcr = new GCase(x,y);
    gcr.setTypes(s);
    return gcr;
  }
  public static int ligneNonvide(String s[]){
    int xr=0;
    for (String ligne : s) {
      if(!ligne.equals("")){xr++;}
    }return xr;
  }
  public static int nbrDElementParLigne(String s){
    // c'est le nombre de ',' +1.
    return str.nbrDeX(s,',')+1;
  }
}