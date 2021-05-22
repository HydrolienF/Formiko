package fr.formiko.usuel;

import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.read;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
*{@summary tools about Files.}
*@author Hydrolien Baeldung
*@version 1.46
*/
public class fichier{

  // CONSTRUCTEUR -----------------------------------------------------------------

  // GET SET -----------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  /**
  *make a liste of all .java file in the directory f.
  *@param f The directory were to search java file.
  *@version 1.13
  */
  public static GString listerLesFichiersDuRep(File f){
    GString gs = new GString();
    //parcourir les dossiers puis les sous dossiers etc jusqu'a ce que tout les fichiers soit traité,
    //cad sous la forme rep+sousdossier1+sousdossier2+nomDu.java
    if (f.isDirectory()){
      File allF [] = f.listFiles();
      if (allF != null && allF.length>0) {
          for (File file : allF) {
            try {
              gs.add(listerLesFichiersDuRep(file));
            }catch (Exception e) {}
          }
      }
    }else if(f.isFile()){
      gs.add(f.getPath());
    }
    return gs;
  }public static GString listerLesFichiersDuRep(String rep){return listerLesFichiersDuRep(new File(rep));}


  /**
   *{@summary Delete a directory and all his content.}<br>
   *If it's a folder it will call deleteDirectory on all sub file/folder and then destroy itself.
   *If it's a file it will destroy itself.
   *@version 1.13
   */
  public static boolean deleteDirectory(File directoryToBeDeleted) {
    if(directoryToBeDeleted==null){return false;}
    File allF [] = directoryToBeDeleted.listFiles();
    if (allF != null) {
        for (File file : allF) {
            deleteDirectory(file);
        }
    }
    return directoryToBeDeleted.delete();
  }public static boolean deleteDirectory(String s){try {return deleteDirectory(new File(str.sToDirectoryName(s)));}catch (Exception e){return false;}}

  public static void affichageDesLecteurALaRacine (File f){
    System.out.println("Affichage des lecteurs à la racine du PC : ");
    for(File file : f.listRoots()){
      System.out.println(file.getAbsolutePath());
      try {
        int i = 1;
        //On parcourt la liste des fichiers et répertoires
        for(File nom : file.listFiles()){
          //S'il s'agit d'un dossier, on ajoute un "/"
          System.out.print("\t\t" + ((nom.isDirectory()) ? nom.getName()+"/" : nom.getName()));

          if((i%4) == 0){
            System.out.print("\n");
          }
          i++;
        }
        System.out.println("\n");
      } catch (NullPointerException e) {} //can be throw if there is no file.
    }
  }
  public static void fichierCiblePeuAvoirCeNom(String nom) {
    File f = new File(nom);
    //if (f.exists()) { throw new FichierDejaPresentException (nom);}
  }
  public static void copierUnFichier(String nomDuFichierACopier){
    String nomDuFichierCible = read.getString("Nom du nouveau fichier","Copie de " + nomDuFichierACopier);
    copierUnFichier(nomDuFichierACopier, nomDuFichierCible);
  }
  public static void copierUnFichier(String nomDuFichierACopier, String nomDuFichierCible){
    FileInputStream fis = null;
    FileOutputStream fos = null;

    try {
       fis = new FileInputStream(new File(nomDuFichierACopier));
       fos = new FileOutputStream(new File(nomDuFichierCible));
       byte[] buf = new byte[8];

       // On crée une variable de type int pour y affecter le résultat de
       // la lecture (Vaut -1 quand c'est fini)
       int n = 0;

       // Tant que l'affectation dans la variable est possible, on boucle
       // Lorsque la lecture du fichier est terminée l'affectation n'est
       // plus possible ! On sort donc de la boucle
       while ((n = fis.read(buf)) >= 0) {
          // On écrit dans notre 2a fichier avec l'objet adéquat
          fos.write(buf);
          if (debug.getAffLesEtapesDeRésolution()){
          // On affiche ce qu'a lu notre boucle au format byte et au format char
          for (byte bit : buf) {
            System.out.print("\t" + bit + "(" + (char) bit + ")");
          }
          System.out.println("");
          }
          //Nous réinitialisons le buffer à vide
          //au cas où les derniers byte lus ne soient pas un multiple de 8
          //Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier
          buf = new byte[8];
       }
       debug.débogage("Copie terminée !");

    } catch (FileNotFoundException e) {
       // Cette exception est levée si l'objet FileInputStream ne trouve aucun fichier
       e.printStackTrace();
    } catch (IOException e) {
       // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
       e.printStackTrace();
    } finally {
       // On ferme nos flux de données dans un bloc finally pour s'assurer
       // que ces instructions seront exécutées dans tous les cas même si
       // une exception est levée !
       try {
          if (fis != null)
             fis.close();
       } catch (IOException e) {
          e.printStackTrace();
       }

       try {
          if (fos != null)
             fos.close();
       } catch (IOException e) {
          e.printStackTrace();
       }
    }
  }
  /**
   *{@summary download a file from the web.}<br>
   *@param url the url as a String.
   *@param fileName the name of the file were to save data from the web.
   *@version 1.46
   */
  public static void download(String url, String fileName){
    try {
      ReadableByteChannel readChannel = Channels.newChannel(new URL(url).openStream());
      FileOutputStream fileOS = new FileOutputStream(fileName);
      FileChannel writeChannel = fileOS.getChannel();
      writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }catch (Exception e) {
      erreur.erreur("Fail to download "+fileName+" from "+url);
      System.out.println(e);//@a
      e.printStackTrace();//@a
      System.out.println(e.getCause());
      System.out.println("--------------------");
    }
  }
  /**
  *{@summary a class to zip file.}<br>
  *cf https://www.baeldung.com/java-compress-and-uncompress
  *@version 1.46
  */
  public static void zip(String sourceFolder, String outputFile){
    try {
      outputFile = str.addALaFinSiNecessaire(outputFile,".zip");
      FileOutputStream fos = new FileOutputStream(outputFile);
      ZipOutputStream zipOut = new ZipOutputStream(fos);
      File fileToZip = new File(sourceFolder);
      zipFile(fileToZip, fileToZip.getName(), zipOut, outputFile);
      zipOut.close();
      fos.close();
    }catch (Exception e) {
      erreur.erreur("Fail to zip file "+sourceFolder+" into "+outputFile);
    }
  }
  /**
  *{@summary a class to do main part of ziping a file.}<br>
  *cf https://www.baeldung.com/java-compress-and-uncompress
  *@version 1.46
  */
  private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut, String outputFile) {
    try {
      if (fileToZip.isHidden()) {
          return;
      }
      if (fileToZip.isDirectory()) {
          fileName = str.addALaFinSiNecessaire(fileName,"/");
          zipOut.putNextEntry(new ZipEntry(fileName));
          zipOut.closeEntry();
          File[] children = fileToZip.listFiles();
          for (File childFile : children) {
              zipFile(childFile, fileName + childFile.getName(), zipOut, outputFile);
          }
          return;
      }
      FileInputStream fis = new FileInputStream(fileToZip);
      ZipEntry zipEntry = new ZipEntry(fileName);
      zipOut.putNextEntry(zipEntry);
      byte[] bytes = new byte[1024];
      int length;
      while ((length = fis.read(bytes)) >= 0) {
          zipOut.write(bytes, 0, length);
      }
      fis.close();
    }catch (Exception e) {
      erreur.erreur("Fail to zip file during ziping of "+fileToZip.getName()+" into "+outputFile);
    }
  }
  /**
  *{@summary a class to unzip file.}<br>
  *cf https://www.baeldung.com/java-compress-and-uncompress
  *@param fileName the name of the .zip file.
  *@param folderName the name of the folder were to save data from the .zip.
  *@version 1.46
  */
  public static void unzip(String fileName, final String folderName){
    fileName = str.addALaFinSiNecessaire(fileName,".zip");
    final File destDir = new File(folderName);
    final byte[] buffer = new byte[1024];
    try {
      final ZipInputStream zis = new ZipInputStream(new FileInputStream(fileName));
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
          final File newFile = newFile(destDir, zipEntry);
          if (zipEntry.isDirectory()) {
              if (!newFile.isDirectory() && !newFile.mkdirs()) {
                  throw new IOException("Failed to create directory " + newFile);
              }
          } else {
              File parent = newFile.getParentFile();
              if (!parent.isDirectory() && !parent.mkdirs()) {
                  throw new IOException("Failed to create directory " + parent);
              }

              final FileOutputStream fos = new FileOutputStream(newFile);
              int len;
              while ((len = zis.read(buffer)) > 0) {
                  fos.write(buffer, 0, len);
              }
              fos.close();
          }
          zipEntry = zis.getNextEntry();
      }
      zis.closeEntry();
      zis.close();
    }catch (Exception e) {
      erreur.erreur("Fail to unzip "+fileName+" in "+folderName);
    }
  }
    /**
    *{@summary a safe way to create a File from a zip file to avoid Zip Slip.}<br>
    *@param destinationDir File that we whant to create in the zipEntry folder.
    *@param zipEntry the ZipEntry.
    *@version 1.46
    */
  public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
      File destFile = new File(destinationDir, zipEntry.getName());
      String destDirPath = destinationDir.getCanonicalPath();
      String destFilePath = destFile.getCanonicalPath();
      if (!destFilePath.startsWith(destDirPath + File.separator)) {
          throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
      }
      return destFile;
  }
}
