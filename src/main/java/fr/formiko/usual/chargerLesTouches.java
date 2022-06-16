package fr.formiko.usual;

import fr.formiko.usual.debug;
import fr.formiko.usual.ecrireUnFichier;
import fr.formiko.usual.erreur;
import fr.formiko.usual.ReadFile;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.types.str;

import java.io.File;
import java.util.HashMap;

public class chargerLesTouches {
  private static HashMap<String, Integer> map;
  // FUNCTIONS -----------------------------------------------------------------
  public static HashMap<String, Integer> chargerLesTouches(String versionActuelle, String folderMain){
    map = new HashMap<>();
    File f = new File(folderMain+"Keys.txt");
    if (!f.exists()){ // si le fichier d'options n'existe pas.
      chargerLesTouchesDe0(versionActuelle, folderMain);
    }
    String t [] = new String[0];
    t=ReadFile.readFileArray(folderMain+"Keys.txt");
    // if(!decoderUnFichier.getStringDeLaLigne(t[0]).equals(versionActuelle)){ erreur.erreur("Le fichier des touches n'est pas compatible avec la version "+versionActuelle,true);return map;}
    int lent = t.length;
    for (int i=1; i<lent;i++) {
      addObjetMap(t[i]);
    }
    return map;
  }

  public static void chargerLesTouchesDe0(String versionActuelle, String folderMain){
    //TODO #230
    GString gs = new GString();
    gs.add("version compatible:"+versionActuelle);
    ecrireUnFichier.ecrireUnFichier(gs,folderMain+"Keys.txt");
  }

  private static void addObjetMap(String s){
    int lens = s.length(); if(lens<3 || (s.charAt(0) == '/' && s.charAt(1) == '/')){ return;}
    String ts [] = s.split(":");
    if (ts.length<2){erreur.erreur("Il n'y a pas de ':' dans la String");}
    else if (ts.length>2){erreur.erreur("Il y a trop de ':' dans la String");}
    else if (ts[0].length()==0){ erreur.erreur("Il n'a pas suffisement de char pour former la clé.");}
    else if(ts[1].length()==0){ erreur.alerte("Una action n'as pas de touche associée");map.put(ts[0],-1);}
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
