package fr.formiko.usuel;

import fr.formiko.usuel.Chrono;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.structures.listes.GGInt;
import fr.formiko.usuel.structures.listes.GInt;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.types.str;

import java.util.Iterator;

/**
*{@summary A tool class about statistic.}<br>
*@author Hydrolien
*@lastEditedVersion 1.13
*/
public class stats {
  public static int sommeDesComG;
  public static int sommeDesFctLG;
  public static int sommeDesClassG;
  public static int sommeDesFctLPuG;
  public static int sommeDesFctLPoG;
  public static int sommeDesFctLPrG;
  public static int sommeDesFctCG;
  public static int sommeNbrDeLigneG;
  private static boolean onlyLastLine=false;
  private static char spliter=' ';

  public static void setSpliter(char c){spliter=c;}
  public static void setOnlyLastLine(boolean b){onlyLastLine=b;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Get all code stats as a GString.}<br>
  *@lastEditedVersion 2.23
  */
  public static GString getStats(String filePath, boolean raccourcir){
    Chrono.debutCh();
    GString gs = fichier.listerLesFichiersDuRep(filePath);
    erreur.info(gs.length()+" files to parse in "+filePath);
    if(gs.length()==0){return new GString();}
    Chrono.endCh("listage des fichiers");Chrono.debutCh();
    //gs = la liste des fichiers.

    GGInt ggi = new GGInt();
    GGInt ggi2 = new GGInt();
    GInt nbrDeLigne = new GInt();
    for (String s : gs ) { //pour chaque fichier on récupère le comtenu et on compte les Commentaire et les fonction longue et courte.
      if(str.contient(s,".java",2)){ //on lit tt les .java
        GString contenuDuFichier = ReadFile.readFileGs(s);
        ggi.add(contenuDuFichier.compterFctEtComJavadoc());
        ggi2.add(contenuDuFichier.compterFctEnDetail());
        nbrDeLigne.add(contenuDuFichier.length());
      }
    }
    Chrono.endCh("récupération des data");Chrono.debutCh();

    //GGInt = la liste de toutes les données.
    sommeDesComG=0;sommeDesFctLG=0;
    sommeDesClassG=0;sommeDesFctLPuG=0;sommeDesFctLPoG=0;sommeDesFctLPrG=0;sommeDesFctCG=0;
    sommeNbrDeLigneG=0;
    //int sommeDesCom = ggi.sommeCase(2);
    //int sommeDesFctL = ggi.sommeCase(1);
    //String total = "total : ";
    //if(sommeDesFctL>0){total+=((sommeDesCom*100)/sommeDesFctL)+"% ("+sommeDesCom+"/"+sommeDesFctL+")";}
    GString gsr = new GString();
    if(!onlyLastLine){
      gsr.add("comment %    cl-pu-po-pr-sh-ln   name of the file");
    }
    //gsr.add(total);
    Chrono.endCh("calcul des valeur et du total");Chrono.debutCh();
    //add tt les autres.
    Iterator<GInt> iggi = ggi.iterator();
    Iterator<GInt> iggi2 = ggi2.iterator();
    GInt ccic=iggi.next();
    GInt cci2c=iggi2.next();
    Iterator<Integer> iNbOfLine = nbrDeLigne.iterator();
    int ci=iNbOfLine.next();
    // CInt ci = nbrDeLigne.getHead();
    for (String s : gs ) {
      if(ccic==null){break;}
      if(raccourcir){
        s = s.substring(25);
      }
      String sTemp = toStatJd(ccic)+toStatInfo(cci2c,ccic)+numberOfLines(ci)+s;
      if(!onlyLastLine){
        gsr.add(sTemp);
      }
      // cci=cci.getSuivant();
      // cci2=cci2.getSuivant();
      try{
        ccic=iggi.next();
        cci2c=iggi2.next();
        ci=iNbOfLine.next();
      }catch (Exception e) {
        // erreur.alerte("igg don't have next");
      }
    }
    GInt gi = new GInt(); gi.add(sommeDesFctCG); gi.add(sommeDesFctLG); gi.add(sommeDesComG);
    GInt gi2 = new GInt(); gi2.add(sommeDesClassG);gi2.add(sommeDesFctLPuG);gi2.add(sommeDesFctLPoG);gi2.add(sommeDesFctLPrG);
    String s="global";
    gsr.add(""+toStatJd(gi,false)+spliter+toStatInfo(gi2,gi,false)+spliter+sommeNbrDeLigneG+spliter+s);
    Chrono.endCh("traitement du GString");
    return gsr;
  }
  /**
  *{@summary Write the stats of javadoc comments in stats.txt.}
  *@lastEditedVersion 2.20
  */
  public static void statsJavadoc(String filePath, boolean raccourcir){
    Chrono.debutCh();
    ecrireUnFichier.ecrireUnFichier(getStats(filePath, raccourcir),"stats.txt");
    Chrono.endCh("sauvegarde finale");
  }
  // public static void statsJavadoc(String filePath, boolean raccourcir){statsJavadoc(filePath,raccourcir,false);}
  public static void statsJavadoc(String filePath){statsJavadoc(filePath,false);}
  // public static String toStatJd(CCInt cci){return toStatJd(cci.getContent());}
  /**
  *{@summary calculate the %age of commented fonction in a file.}
  *@lastEditedVersion 1.13
  */
  public static String toStatJd(GInt gi, boolean addToGlobal){
    int sommeDesCom = gi.getCase(2);
    int sommeDesFctL = gi.getCase(1);
    if(addToGlobal){
      sommeDesComG+=sommeDesCom;sommeDesFctLG+=sommeDesFctL;
    }
    if(sommeDesFctL==0){return "null%";}
    String r = "";
    if(sommeDesCom<100){
      r = ((sommeDesCom*100)/sommeDesFctL)+"%";
    }else{
      r = ((sommeDesCom*100)/sommeDesFctL)+","+(((sommeDesCom*1000)/sommeDesFctL)%10)+"%";
    }
    r=completToK(r,5);
    r=r+"("+sommeDesCom+"/"+sommeDesFctL+")";
    if(spliter==' '){
      r=completToK(r,5+8);
    }
    return r;
  }public static String toStatJd(GInt gi){return toStatJd(gi,true);}
  /**
  *{@summary calculate the %age of tested fonction in a file.}
  *@lastEditedVersion 1.13
  */
  //TODO adapter au test (les tests sont dans un autre fichier).
  public static String toStatTst(GInt gi){
    int sommeDesCom = gi.getCase(2);
    int sommeDesFctL = gi.getCase(1);
    sommeDesComG+=sommeDesCom;sommeDesFctLG+=sommeDesFctL;
    if(sommeDesFctL==0){return "null%";}
    String r = ((sommeDesCom*100)/sommeDesFctL)+"%";
    r=completToK(r,5);
    r=r+"("+sommeDesCom+"/"+sommeDesFctL+")";
    if(spliter==' '){
      r=completToK(r,5+8);
    }
    return r;
  }
  /**
  *{@summary calculate the number of class, public, ø, protected, private longue fonction.}
  *@lastEditedVersion 1.13
  */
  public static String toStatInfo(GInt gi,GInt gi2, boolean addToGlobal){
    int sommeDesClass = gi.getCase(0);
    int sommeDesFctLPu = gi.getCase(1);
    int sommeDesFctLPo = gi.getCase(2);
    int sommeDesFctLPr = gi.getCase(3);
    int sommeDesFctC = gi2.getCase(0);
    if(addToGlobal){
      sommeDesClassG+=sommeDesClass;
      sommeDesFctLPuG+=sommeDesFctLPu;
      sommeDesFctLPoG+=sommeDesFctLPo;
      sommeDesFctLPrG+=sommeDesFctLPr;
      sommeDesFctCG+=sommeDesFctC;
    }
    String r = sommeDesClass+""; int k=3;
    r=completToK(r,k);
    k+=3;
    r+=sommeDesFctLPu;
    r=completToK(r,k);
    k+=3;
    r+=sommeDesFctLPo;
    r=completToK(r,k);
    k+=3;
    r+=sommeDesFctLPr;
    r=completToK(r,k);
    k+=3;
    r+=sommeDesFctC;
    //k++;
    if(spliter==' '){
      r=completToK(r,k);
    }
    return r;
  }public static String toStatInfo(GInt gi, GInt gi2){return toStatInfo(gi,gi2,true);}
  /**
  *Count the number of ligne in a file an add it to the statistic info.
  *@lastEditedVersion 1.13
  */
  public static String numberOfLines(int nbrOfLine){
    sommeNbrDeLigneG+=nbrOfLine;
    String r=nbrOfLine+"";
    r=completToK(r,5);
    return r;
  }
  /**
  *{@summary Add enoth space to have a String length of k, or add the spliter.}
  *We need to add several ' ' or 1 spliter.
  *@param toComplet String to complet
  *@param k max char
  *@lastEditedVersion 1.13
  */
  private static String completToK(String toComplet, int k){
    toComplet+=spliter; //at least 1.
    if(spliter==' '){ //more if it's the space spliter to form similar collums
      for (int i=toComplet.length(); i<k; i++) {
        toComplet+=spliter;
      }
    }
    return toComplet;
  }

}
