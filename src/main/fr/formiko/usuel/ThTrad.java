package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.util.Map;
import java.util.HashMap;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.liste.GString;
import java.io.*;

public class ThTrad extends Thread{
  int langue;
  public ThTrad(int x){
    langue=x;
    run();
  }
  @Override
  public void run(){
    ajouterTradAuto();
  }
  public void ajouterTradAuto(){
    String sLangue=chargerLesTraductions.getLangue(langue);
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
      erreur.erreur("erreur de traduction","chargerLesTraductions.tradAuto");
    }
    return s2+"[auto]";
  }
}
