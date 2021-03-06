package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.util.HashMap;

public class chargerLesTouches {
  private static HashMap<String, Integer> map;
  // Fonctions propre -----------------------------------------------------------
  public static HashMap<String, Integer> chargerLesTouches(String versionActuelle){
    map = new HashMap<>();
    File f = new File(Main.getFolder().getFolderMain()+"Keys.txt");
    if (!f.exists()){ // si le fichier d'options n'existe pas.
      chargerLesTouchesDe0(versionActuelle);
    }
    String t [] = new String[0];
    t=lireUnFichier.lireUnFichier(Main.getFolder().getFolderMain()+"Keys.txt");
    // if(!decoderUnFichier.getStringDeLaLigne(t[0]).equals(versionActuelle)){ erreur.erreur("Le fichier des touches n'est pas compatible avec la version "+versionActuelle,true);return map;}
    int lent = t.length;
    for (int i=1; i<lent;i++) {
      addObjetMap(t[i]);
    }
    return map;
  }

  public static void chargerLesTouchesDe0(String versionActuelle){
    //TODO #230
    GString gs = new GString();
    gs.add("version compatible:"+versionActuelle);
    ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderMain()+"Keys.txt");
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
