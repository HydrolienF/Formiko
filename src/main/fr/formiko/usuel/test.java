package fr.formiko.usuel.test;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.File;
import fr.formiko.usuel.conversiondetype.str;
//import org.junit.internal.TextListener;
//import org.junit.runner.JUnitCore;
import java.io.*;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;

/**
 *{@summary test class that can launch all tests. <br/>}
 *@author Hydrolien
 *@version 1.4
 */
public class test {
  public static String REPTEST = "build/test/";
  //private static Class<?> testClass = new Class<?>();
  private static GString gs;
  private static int idCpt=0;

  // GET SET --------------------------------------------------------------------
  public static int getId(){return idCpt++;}
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary Test all test file in REPTEST directory and all his content.<br/>}
   *@version 1.4
   */
  public static boolean testAll(){
    gs = new GString();
    /*File f = new File("test.txt");
    if(f.exists()){f.delete();}
    try {
      f.createNewFile();
    }catch (Exception e) {
      return false;
    }*/
    testDirectory(new File(REPTEST));
    saveGs();
    return true;
  }
  /**
   *{@summary Test all test file in a directory and all his content.<br/>}
   *@version 1.4
   */
  public static boolean testDirectory(File dir) {
    File allF [] = dir.listFiles();
    //on demande a tout les sous répertoires de valider.
    if (allF != null) {
      debug.débogage("Lancement de testDirectory sur "+allF.length+" sous docier(s) de "+dir);
      for (File file : allF) {
        testDirectory(file);
      }
    }
    //on traite le fichier.
    return executeTest(dir);
  }
  /**
   *{@summary Launch a test.<br/>}
   *@version 1.4
   */
  public static boolean executeTest(File f){
    if(f.isFile()){
      debug.débogage("Lancement de executeTest sur "+f);
      String s = f.toString();
      s = s.substring(11,s.length());//on retire "build/test/"
      s = s.replace('/','.');
      //String t [] = s.split("/");
      //s = t[t.length-1];//la fin sans les dossiers.
      if(str.contient(s,".class",2)){
        s = s.substring(0,s.length()-6);
        try {
          //Peu déclancher une erreur même si c'est dans un try catch...
          //Class c = Class.forName(s);
          debug.débogage("run");
          //testClass.add(c);
          /*if (run(s)) {
            return true;
          }*/
          gs.add(s);
        }catch (Exception e) {
          debug.débogage("quelque chose d'anormale est arrivé lors du test de "+s);
        }
      }
    }
    return false;
  }
  /**
   *{@summary Launch a test with junit package.<br/>}
   *@version 1.4
   */
  /*public static boolean run(Class<?> testClass){
    JUnitCore runner = new JUnitCore();
    runner.addListener(new TextListener(System.out));
    runner.run(testClass);
    return true;
  }*/
  /**
   *{@summary Launch a test in linux terminal.<br/>}
   *@version 1.4
   */
  public static boolean run(String s){
    // set up the command and parameter
    String s2 = "";
    String cmd []= new String[5];
    try {
      //cmd[0] = "javaj";
      //cmd[0] = "java -cp ~/Formiko/junit-4.13.1.jar:.:build/main junit.textui.TestRunner";
      //cmd[1] = s;
      /*cmd[0]="java";
      cmd[1]="-cp";
      cmd[2]="~/Formiko/junit-4.13.1.jar:.:build/main";
      cmd[3]="junit.textui.TestRunner";
      cmd[4]=s;*/
      cmd[0]="echo";
      cmd[1]=s;
      cmd[2]=">>";
      cmd[3]="test.txt";
      // create runtime to execute external command
      System.out.println("1");//@a
      Runtime rt = Runtime.getRuntime();
      System.out.println("2");//@a
      Process pr = rt.exec(cmd);
      System.out.println("3");//@a
      // retrieve output from command
      BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      s2 = bfr.readLine();
    }catch (Exception e) {
      erreur.erreur("erreur de test","test.run");
      return false;
    }
    //System.out.println(s2);
    return true;
  }
  /**
   *{@summary Save all file to test in testJunit.txt.<br/>}
   *@version 1.5
   */
  private static void saveGs(){
    ecrireUnFichier.ecrireUnFichier(gs,"testJunit.txt");
  }
}
