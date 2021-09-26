package fr.formiko.usuel;

import fr.formiko.formiko.Main;
// import fr.formiko.usuel.read;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.types.str;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
*{@summary tools about Files.}
*@author Hydrolien Baeldung
*@version 1.46
*/
public class fichier {

  // CONSTRUCTORS ----------------------------------------------------------------

  // GET SET ----------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *make a liste of all .java file in the directory f.
  *@param f The directory were to search java file.
  *@version 1.13
  */
  public static GString listerLesFichiersDuRep(File f) {
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

  public static void affichageDesLecteurALaRacine (File f) {
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
  public static void copierUnFichier(String fileSourceName){
    String fileTargetName = read.getString("Nom du nouveau fichier","Copie de " + fileSourceName);
    copierUnFichier(fileSourceName, fileTargetName);
  }
  public static void copierUnFichier(String fileSourceName, String fileTargetName){
    try {
      Path source = Paths.get(fileSourceName);
      Path target = Paths.get(fileTargetName);
      Files.copy(source, target);
    }catch (Exception e) {
      erreur.erreur("fail to copy file from "+fileSourceName+" to "+fileTargetName);
    }
  }
  /**
  *{@summary Download a file from the web.}<br>
  *@param urlPath the url as a String
  *@param fileName the name of the file were to save data from the web
  *@param withInfo if true launch a thread to have info during download
  *@version 2.7
  */
  public static boolean download(String urlPath, String fileName, boolean withInfo){
    String reason=null;
    Exception ex=null;
    DownloadThread downloadThread=null;
    FileOutputStream fos=null;
    try {
      URL url = new URL(urlPath);
      long fileToDowloadSize = getFileSize(url);
      // if(withInfo){
      //   erreur.info("Downoading "+urlPath+" of size : "+fileToDowloadSize);
      // }
      ReadableByteChannel readChannel = Channels.newChannel(url.openStream());
      File fileOut = new File(fileName);
      fos = new FileOutputStream(fileOut);
      FileChannel writeChannel = fos.getChannel();
      if (withInfo) {
        //launch Thread that update %age of download
        //this thread watch file size & print it / fileSize.
        downloadThread = new DownloadThread(fileOut, fileToDowloadSize);
        downloadThread.start();
      }
      writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
      return true;
    } catch (MalformedURLException e) {
      reason = "URL is malformed";
    } catch (UnknownHostException e) {
      reason = "can't resolve host";
    } catch (FileNotFoundException e) {
      reason = "file can't be found on the web site";
      ex=e;
    } catch (Exception e) {
      reason = e.toString();
    } finally {
      if(fos!=null){
        try {
          fos.close();
        }catch (Exception e) {
          erreur.alerte("Can't close FileOutputStream");
        }
      }
      if(downloadThread!=null){
        downloadThread.stopRuning();
      }
      if(reason!=null){
        //"Fail to download "+fileName+" from "+urlPath+ " because "+
        String err = "Download fail: "+reason;
        try {
          Main.getView().setDownloadingMessage(err);
          boolean b=true;
          while(b){
            Temps.pause(1000);
          }
        }catch (Exception e) {
          erreur.erreur(err);
        }
      }
      // if(ex!=null){
      //   ex.printStackTrace();
      // }
    }
    return false;
  }
  public static boolean download(String urlPath, String fileName){return download(urlPath, fileName, false);}
  /**
  *{@summary return the size of the downloaded file.}
  *@version 2.7
  */
  private static long getFileSize(URL url) {
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("HEAD");
      return conn.getContentLengthLong();
    } catch (IOException e) {
      erreur.erreur("fail to get file size");
      return -1;
    } finally { //will be call even if there is return before.
      if (conn != null) {
        conn.disconnect();
      }
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

/**
*{@summary Print info about curent download.}<br>
*this thread watch file size & print it / fileSize.
*@version 2.7
*@author Hydrolien
*/
class DownloadThread extends Thread {
  private File fileOut;
  private long fileToDowloadSize;
  private boolean running;
  /**
  *{@summary Main constructor.}<br>
  *@param fileOut file that we are curently filling by the downoading file
  *@param fileToDowloadSize size that we should reach when download will end
  *@version 2.7
  */
  public DownloadThread(File fileOut, long fileToDowloadSize){
    this.fileOut = fileOut;
    this.fileToDowloadSize = fileToDowloadSize;
    running=true;
  }

  public void stopRuning(){running=false;}
  /**
  *{@summary Main function that print every second %age of download done.}<br>
  *@version 2.7
  */
  public void run(){
    long fileOutSize=0;
    long lastFileOutSize=0;
    while (fileOutSize < fileToDowloadSize && running) {
      fileOutSize = fileOut.length();
      int percent = (int)((100*fileOutSize)/fileToDowloadSize);
      // long speed = fileOutSize-lastFileOutSize;
      try {
        Main.getView().setDownloadingValue(percent);
      }catch (Exception e) {
        erreur.info(percent+"% dowload : "+fileOutSize+"/"+fileToDowloadSize);//+" "+speed+" B/s");
      }
      lastFileOutSize=fileOutSize;
      try {
        sleep(50);
      } catch (InterruptedException ie) {
        erreur.erreurPause(50);
      }
    }
    // erreur.info("download done");
  }
}
