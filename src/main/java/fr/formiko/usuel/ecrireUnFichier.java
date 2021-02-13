package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.tableau;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import fr.formiko.usuel.types.str;

public class ecrireUnFichier {

  public static boolean ecrireUnFichier(GString tableauDeLigne, String nomDuFichier) {
    nomDuFichier = str.sToDirectoryName(nomDuFichier);
    try {
      BufferedWriter ecriteurAvecBuffer = null;
      String ligne;

      try {
        ecriteurAvecBuffer = new BufferedWriter(new FileWriter(nomDuFichier));
      } catch(FileNotFoundException e) {
        erreur.erreur("Le fichier n'as pas pu être créer. Le problème peut venir d'un caractère incorecte","ecrireUnFichier.ecrireUnFichier");
        return false;
        //ecriteurAvecBuffer = new BufferedWriter(new FileWriter("sauvegarde/sauvegardeEnCasDErreur.txt"));
  	    //e.printStackTrace();
      }
      String contenu = tableauDeLigne.concatène();
      ecriteurAvecBuffer.write(contenu);
      /*for (String s : tableauDeLigne) {
        ecriteurAvecBuffer.write(s+"\n");
      }*/
      ecriteurAvecBuffer.close();
    }catch (IOException e) {
      return false;
      //e.printStackTrace();
    }
    return true;
  }
  public static void ecrireUnFichier(GString tableauDeLigne){
    String pseudo = "X";
    LocalDateTime date = LocalDateTime.now();
    String dateFr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH;mm;ss"));
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
    /*File f = new File (nomDuFichier + ".txt");
    if (!nomDuFichier.equals("sauvegarde") && f.exists()){
      erreur.erreur("le nom de la sauvegarde éxiste déjà","ecrireUnFichier.ecrireUnFichier","Le nom du fichier sera sauvegardeX");
      nomDuFichier = "sauvegarde";
      f = new File ("sauvegarde/sauvegarde.txt");
    }
    int i=2;
    // test non éfficace pour éviter d'écraser un fichier.
    if (nomDuFichier.equals("sauvegarde") ){
      while (f.exists()){
        nomDuFichier = "sauvegarde/sauvegarde" + i;
        i++;
      }
    }*/
    ecrireUnFichier(tableauDeLigne, "sauvegarde/" + nomDuFichier + ".txt");
  }
}
