package fr.formiko.usuel;

import fr.formiko.usuel.ReadFile;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ThTrad extends Thread{
  int langue;
  public ThTrad(int x){
    langue=x;
    run();
  }
  @Override
  public void run(){
    addTradAuto();
  }
  public void addTradAuto(){
    String sLangue=chargerLesTraductions.getLanguage(langue);
    System.out.println("Ajout de la langue : "+sLangue);
    GString gs = new GString();
    String t[] = chargerLesTraductions.getTableauDesTrad(langue);
    for (String s : t) {
      if(chargerLesTraductions.estLigneDeTrad(s) && !chargerLesTraductions.fini(s)){//si la ligne termine par :, c'est qu'il manque la trad, on trad donc a partir du français.
        String sTrad = tradAuto(g.get(s.substring(0,s.length()-1)),sLangue);
        if(sTrad.equals("null[auto]")){return;}//si la langue de trad n'est pas reconnu.
        if(sTrad.charAt(0)==' '){sTrad=sTrad.substring(1);}
        s=s+sTrad;
      }
      gs.add(s);
    }
    ecrireUnFichier.ecrireUnFichier(gs,chargerLesTraductions.getRep()+sLangue+".txt");
  }
  private String tradAuto(String s, String sLangue){
    // set up the command and parameter
    String s2 = "";
    try {
      String pythonScriptPath = "trad/trad.py";
      String[] cmd = new String[4];
      cmd[0] = "python3";
      cmd[1] = pythonScriptPath;
      cmd[2] = sLangue;
      cmd[3] = s;

      // create runtime to execute external command
      Runtime rt = Runtime.getRuntime();
      Process pr = rt.exec(cmd);

      // retrieve output from python script
      BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      s2 = bfr.readLine();
    }catch (Exception e) {
      erreur.erreur("translation fail");
    }
    return s2+"[auto]";
  }
}
