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
*@lastEditedVersion 1.46
*/
public class fichier {

  // CONSTRUCTORS ----------------------------------------------------------------

  // GET SET ----------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *make a liste of all .java file in the directory f.
  *@param f The directory were to search java file.
  *@lastEditedVersion 1.13
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
   *@lastEditedVersion 1.13
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
  *@lastEditedVersion 2.7
  */
  public static boolean download(String urlPath, String fileName, boolean withInfo){
    String reason=null;
    Exception ex=null;
    DownloadThread downloadThread=null;
    FileOutputStream fos=null;
    try {
      Main.getView().setButtonRetryVisible(false);
    }catch (NullPointerException e) {}
    try {
      URL url = new URL(urlPath);
      long fileToDowloadSize = getFileSize(url);
      ReadableByteChannel readChannel = Channels.newChannel(url.openStream());
      File fileOut = new File(fileName);
      fos = new FileOutputStream(fileOut);
      FileChannel writeChannel = fos.getChannel();
      if (withInfo) {
        String downloadName = "x";
        String t [] = fileName.split("/");
        downloadName = t[t.length-1];
        int downloadNameLen = downloadName.length();
        System.out.println(downloadName.substring(downloadNameLen-4,downloadNameLen));
        if(downloadNameLen>4 && ".zip".equals(downloadName.substring(downloadNameLen-4,downloadNameLen))){
          downloadName = downloadName.substring(0,downloadNameLen-4);
        }
        //launch Thread that update %age of download
        downloadThread = new DownloadThread(fileOut, fileToDowloadSize, downloadName);
        downloadThread.start();
      }
      // TODO #440 stop transferFrom if download take to long
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
        String err = "Download fail: "+reason;
        try {
          Main.getView().setDownloadingMessage(err);
          Main.getView().setButtonRetryVisible(true);
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
  *@lastEditedVersion 2.7
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
  *@lastEditedVersion 1.46
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
  *@lastEditedVersion 1.46
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
  *@lastEditedVersion 1.46
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
    *@lastEditedVersion 1.46
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
*this thread watch file size &#38; print it / fileSize.
*@lastEditedVersion 2.7
*@author Hydrolien
*/
class DownloadThread extends Thread {
  private File fileOut;
  private long fileToDowloadSize;
  private boolean running;
  private String downloadName;
  /**
  *{@summary Main constructor.}<br>
  *@param fileOut file that we are curently filling by the downloading file
  *@param fileToDowloadSize size that we should reach when download will end
  *@lastEditedVersion 2.7
  */
  public DownloadThread(File fileOut, long fileToDowloadSize, String downloadName){
    this.fileOut = fileOut;
    this.fileToDowloadSize = fileToDowloadSize;
    this.downloadName=downloadName;
    running=true;
  }

  public void stopRuning(){running=false;}
  /**
  *{@summary Main function that print every second %age of download done.}<br>
  *@lastEditedVersion 2.7
  */
  public void run(){
    long fileOutSize=0;
    long lastFileOutSize=0;
    long timeStart=System.currentTimeMillis();
    long timeFromLastBitDownload=timeStart;
    while (fileOutSize < fileToDowloadSize && running) {
      fileOutSize = fileOut.length();
      double progression = ((double)fileOutSize)/(double)fileToDowloadSize;
      int percent = (int)(100*progression);
      long curentTime = System.currentTimeMillis();
      long timeElapsed = curentTime-timeStart;
      long timeLeft = (long)((double)((timeElapsed/progression)-timeElapsed));
      String sTimeLeft = Temps.msToTime(timeLeft)+" left";
      String message = "Downloading "+downloadName+" - "+percent+"% - ";
      if(fileOutSize!=lastFileOutSize){//update watcher of working download
        timeFromLastBitDownload=curentTime;
      }
      if(timeFromLastBitDownload+10000<curentTime){
        message+=(((curentTime-timeFromLastBitDownload)/1000)+"s untill a new bit haven't been download");
        if(timeFromLastBitDownload+60000<curentTime){
          //TODO #440 stop download.
          erreur.erreur("STOP download");
        }
      }else{
        message+=sTimeLeft;
      }

      Main.getView().setDownloadingValue(percent);
      Main.getView().setDownloadingMessage(message);

      lastFileOutSize=fileOutSize;
      Temps.pause(50);
    }
    // erreur.info("download done");
  }
}
