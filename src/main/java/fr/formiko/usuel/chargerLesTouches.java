package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.liste.GString;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import fr.formiko.usuel.types.str;

public class chargerLesTouches {
  private static Map<String, Integer> map;
  // Fonctions propre -----------------------------------------------------------
  public static Map<String, Integer> chargerLesTouches(int versionActuelle){
    map = new HashMap<>();
    File f = new File("data/Key.txt");
    if (!f.exists()){ // si le fichier d'options n'existe pas.
      chargerLesTouchesDe0(versionActuelle);
    }
    String t [] = new String[0];
    t=lireUnFichier.lireUnFichier("data/Key.txt");
    if(decoderUnFichier.getIntDeLaLigne(t[0]) != versionActuelle){ erreur.erreur("Le fichier des touches n'est pas compatible avec la version "+versionActuelle,"chargerLesTouches.chargerLesTouches",true);return map;}
    int lent = t.length;
    for (int i=1; i<lent;i++) {
      ajouterObjetMap(t[i]);
    }
    return map;
  }

  public static void chargerLesTouchesDe0(int versionActuelle){
    GString gs = new GString();
    gs.ajouter("version compatible:"+versionActuelle);
    ecrireUnFichier.ecrireUnFichier(gs,"data/Options.txt");
  }

  private static void ajouterObjetMap(String s){
    int lens = s.length(); if(lens<3 || (s.charAt(0) == '/' && s.charAt(1) == '/')){ return;}
    String ts [] = s.split(":");
    if (ts.length<2){erreur.erreur("Il n'y a pas de ':' dans la String","chargerLesTouches.ajouterObjetMap");}
    else if (ts.length>2){erreur.erreur("Il y a trop de ':' dans la String","chargerLesTouches.ajouterObjetMap");}
    else if (ts[0].length()==0){ erreur.erreur("Il n'a pas suffisement de char pour former la clé.","chargerLesTouches.ajouterObjetMap");}
    else if(ts[1].length()==0){ erreur.alerte("Una action n'as pas de touche associée","chargerLesTouches.ajouterObjetMap");map.put(ts[0],-1);}
    else{ // le cas ou tout marche bien :
      Integer c;
      try {
        c = Integer.parseInt(ts[1]);
      }catch (Exception e) {
        c = (int) ts[1].charAt(0);
      }
      map.put(ts[0],c);
    }
  }

}
