package fr.formiko.usuel;

import fr.formiko.usuel.structures.listes.GString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
*{@summary A tool class to read a file.}<br>
*File can be read as a single Strings, as a List<String>, as a String[] or as a GString.<br>
*@lastEditedVersion 2.22
*@author Hydrolien
*/
public class ReadFile {

  /**
  *{@summary Read a file &#38; return it as a String.}<br>
  *@param file file to read
  *@lastEditedVersion 2.22
  */
  public static String readFile(File file){
    String s="";
    String line="";
    try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
      while ((line = br.readLine()) != null){
        s+=line+'\n';
      }
    }catch(FileNotFoundException e) {
      erreur.erreur("File "+file+" haven't been found.");
    }catch (IOException e) {
      erreur.erreur("File "+file+" can't be read.");
    }
    return s;
  }
  /***
  *{@summary Read a file &#38; return it as a String.}<br>
  *@param fileName name of the file to read
  *@lastEditedVersion 2.22
  */
  public static String readFile(String fileName){return readFile(new File(fileName));}
  /***
  *{@summary Read a file &#38; return it as a String.}<br>
  *@param path path to get the file
  *@lastEditedVersion 2.22
  */
  public static String readFile(Path path){return readFile(path.toString());}


  /**
  *{@summary Read a file &#38; return it as an array.}<br>
  *@param fileName name of the file to read
  *@lastEditedVersion 2.22
  */
  public static String [] readFileArray(String fileName) {
    return readFileList(new File(fileName)).toArray(new String[0]);
  }

  /**
  *{@summary Read a file &#38; return it as a List<String>.}<br>
  *@param file file to read
  *@lastEditedVersion 2.22
  */
  public static List<String> readFileList(File file){
    List<String> list = new ArrayList<String>();
    String line="";
    try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
      while ((line = br.readLine()) != null){
        list.add(line);
      }
    }catch(FileNotFoundException e) {
      erreur.erreur("File "+file+" haven't been found.");
    }catch (IOException e) {
      erreur.erreur("File "+file+" can't be read.");
    }
    return list;
  }
  /***
  *{@summary Read a file &#38; return it as a List<String>.}<br>
  *@param fileName name of the file to read
  *@lastEditedVersion 2.22
  */
  public static List<String> readFileList(String fileName){return readFileList(new File(fileName));}
  /***
  *{@summary Read a file &#38; return it as a List<String>.}<br>
  *@param path path to get the file
  *@lastEditedVersion 2.22
  */
  public static List<String> readFileList(Path path){return readFileList(path.toString());}


  /**
  *{@summary Read a file &#38; return it as a GString.}<br>
  *@param file file to read
  *@lastEditedVersion 2.22
  */
  public static GString readFileGs(File file){
    GString list = new GString();
    String line="";
    try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
      while ((line = br.readLine()) != null){
        list.add(line);
      }
    }catch(FileNotFoundException e) {
      erreur.erreur("File "+file+" haven't been found.");
    }catch (IOException e) {
      erreur.erreur("File "+file+" can't be read.");
    }
    return list;
  }
  /***
  *{@summary Read a file &#38; return it as a GString.}<br>
  *@param fileName name of the file to read
  *@lastEditedVersion 2.22
  */
  public static GString readFileGs(String fileName){return readFileGs(new File(fileName));}
  /***
  *{@summary Read a file &#38; return it as a GString.}<br>
  *@param path path to get the file
  *@lastEditedVersion 2.22
  */
  public static GString readFileGs(Path path){return readFileGs(path.toString());}
}
