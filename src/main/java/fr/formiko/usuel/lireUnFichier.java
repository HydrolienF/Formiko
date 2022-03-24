package fr.formiko.usuel;

import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.tableau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

public class lireUnFichier {

  public static String [] lireUnFichier(String nomDuFichier) {
    if(nomDuFichier==null){return null;}
    File f = new File(nomDuFichier);
    String tr [] = new String [0];
    if(!f.exists() || !f.isFile()){return tr;}
    try {
      BufferedReader lecteurAvecBuffer = null;
      String ligne;

      try {
        lecteurAvecBuffer = new BufferedReader(new FileReader(nomDuFichier, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le chargement du fichier "+ nomDuFichier+" a échoué.");
  	    //e.printStackTrace();
      }
      while ((ligne = lecteurAvecBuffer.readLine()) != null){
        tr = tableau.addX(tr, ligne); // La c'est couteux en opération.
      }
      lecteurAvecBuffer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    debug.débogage(tr.length + " "+g.get("lireUnFichier",1,"lignes on été trouvée dans le fichier"));
    return tr;
  }
  //sert a parcourir un fichier en ajoutant chaque ligne 1 a 1 dans une liste chainé GString.
  public static GString lireUnFichierGs(String nomDuFichier){
    GString gs= new GString();
    try {
      BufferedReader lecteurAvecBuffer = null;
      String ligne;

      try {
        lecteurAvecBuffer = new BufferedReader(new FileReader(nomDuFichier, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le chargement du fichier "+ nomDuFichier+" a échoué.");
        e.printStackTrace();
      }
      while ((ligne = lecteurAvecBuffer.readLine()) != null){
        gs.add(ligne);
      }
      lecteurAvecBuffer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    debug.débogage(gs.length() + " "+g.get("lireUnFichier",1,"lignes on été trouvée dans le fichier"));
    return gs;
  }
  public static GString lireUnFichierGs(File f){return lireUnFichierGs(f.getAbsolutePath());}
  public static GString lireUnFichierGsFromPath(Path path){return lireUnFichierGs(path.toString());}
  /**
  *{@summary Read a file &#38; return it as a single String.}<br>
  *@param f file to read
  *@lastEditedVersion 2.22
  */
  public static String readFile(File f){
    String s="";
    try {
      BufferedReader br = null;
      String line;
      try {
        br = new BufferedReader(new FileReader(f, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le chargement du fichier "+f+" a échoué.");
        e.printStackTrace();
      }
      while ((line = br.readLine()) != null){
        s+=line+'\n';
      }
      br.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    return s;
  }
  /***
  *{@summary Read a file &#38; return it as a single String.}<br>
  *@param fileName name of the file to read
  *@lastEditedVersion 2.22
  */
  public static String readFile(String fileName){return readFile(new File(fileName));}

}
