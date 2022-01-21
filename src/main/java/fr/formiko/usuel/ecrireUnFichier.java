package fr.formiko.usuel;

import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ecrireUnFichier {

  public static boolean ecrireUnFichier(GString tableauDeLigne, String nomDuFichier) {
    nomDuFichier = str.sToDirectoryName(nomDuFichier);
    try {
      BufferedWriter ecriteurAvecBuffer = null;
      String ligne;
      File f = new File(nomDuFichier);
      f.createNewFile(); //it will be crate only if it haven't been yet.
      try {
        ecriteurAvecBuffer = new BufferedWriter(new FileWriter(nomDuFichier, StandardCharsets.UTF_8));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le fichier n'as pas pu être créer. Le problème peut venir d'un caractère incorecte");
        return false;
        //ecriteurAvecBuffer = new BufferedWriter(new FileWriter("sauvegarde/sauvegardeEnCasDErreur.txt"));
  	    //e.printStackTrace();
      }
      String contenu = tableauDeLigne.toStringLong();
      ecriteurAvecBuffer.write(contenu);
      ecriteurAvecBuffer.close();
    }catch (IOException e) {
      return false;
    }
    return true;
  }
  public static void ecrireUnFichier(GString tableauDeLigne){
    String pseudo = "X";
    LocalDateTime date = LocalDateTime.now();
    String dateFr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss"));
    String nomDuFichier = read.getString("nomDuFichier"," ");
    if (nomDuFichier.equals(" ")){
      nomDuFichier = pseudo + " "+ dateFr;
    }else{
      char charInterdit [] = {'/','\\',':','*','?','"','<','>','|'};
      if(opperationSurString.contientChar(nomDuFichier,charInterdit)){
        erreur.alerte("Les char : \" \\ / : * ? \" < > | \" sont interdit, vous ne pouvez pas les utiliser dans un nom de fichier ! (Il ont été retiré pour éviter les bogues)");
        nomDuFichier = opperationSurString.retirerChar(nomDuFichier,charInterdit);
      }
    }
    if (nomDuFichier.length()==0){ nomDuFichier = "sauvegardeEnCasDeNomDeFichierVide";}
    ecrireUnFichier(tableauDeLigne, "sauvegarde/" + nomDuFichier + ".txt");
  }
}
