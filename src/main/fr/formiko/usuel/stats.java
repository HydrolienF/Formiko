package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GGInt;
import fr.formiko.usuel.liste.CCInt;
import fr.formiko.usuel.liste.GInt;
import fr.formiko.usuel.liste.CInt;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.conversiondetype.str;

/**
*{@summary A tool class about statistic. <br>}
*@author Hydrolien
*@version 1.13
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
  // Fonctions propre -----------------------------------------------------------
  /**
  *Write the stats of javadoc comments in stats.txt.
  *@version 1.13
  */
  public static void statsJavadoc(String chemin){
    Main.débutCh();
    GString gs = fichier.listerLesFichiersDuRep(chemin);
    Main.finCh("listage des fichiers");Main.débutCh();
    //gs = la liste des fichiers.

    GGInt ggi = new GGInt();
    GGInt ggi2 = new GGInt();
    CString cs = null;
    if(gs.length()>0){cs = gs.getDébut();}
    GInt nbrDeLigne = new GInt();
    while(cs!=null){//pour chaque fichier on récupère le comtenu et on compte les Commentaire et les fonction longue et courte.
      if(str.contient(cs.getContenu(),".java",2)){ //on lit tt les .java
        GString contenuDuFichier = lireUnFichier.lireUnFichierGs(cs.getContenu());
        ggi.add(contenuDuFichier.compterFctEtComJavadoc());
        ggi2.add(contenuDuFichier.compterFctEnDetail());
        nbrDeLigne.add(contenuDuFichier.length());
      }
      cs = cs.getSuivant();
    }
    Main.finCh("récupération des data");Main.débutCh();

    //GGInt = la liste de toutes les données.
    sommeDesComG=0;sommeDesFctLG=0;
    sommeDesClassG=0;sommeDesFctLPuG=0;sommeDesFctLPoG=0;sommeDesFctLPrG=0;sommeDesFctCG=0;
    sommeNbrDeLigneG=0;
    //int sommeDesCom = ggi.sommeCase(2);
    //int sommeDesFctL = ggi.sommeCase(1);
    //String total = "total : ";
    //if(sommeDesFctL>0){total+=((sommeDesCom*100)/sommeDesFctL)+"% ("+sommeDesCom+"/"+sommeDesFctL+")";}
    GString gsr = new GString();
    gsr.add("comment %    cl-pu-po-pr-sh-ln   name of the file");
    //gsr.add(total);
    Main.finCh("calcul des valeur et du total");Main.débutCh();
    //ajouter tt les autres.
    CCInt cci = ggi.getDébut();
    CCInt cci2 = ggi2.getDébut();
    CInt ci = nbrDeLigne.getDébut();
    cs = gs.getDébut();
    while(cci!=null){
      String s = cs.getContenu().substring(20);
      gsr.add(toStatJd(cci)+toStatInfo(cci2.getContenu(),cci.getContenu())+numberOfLines(ci)+s);
      cci=cci.getSuivant();
      cci2=cci2.getSuivant();
      ci=ci.getSuivant();
      cs=cs.getSuivant();
    }
    GInt gi = new GInt(); gi.add(sommeDesFctCG); gi.add(sommeDesFctLG); gi.add(sommeDesComG);
    GInt gi2 = new GInt(); gi2.add(sommeDesClassG);gi2.add(sommeDesFctLPuG);gi2.add(sommeDesFctLPoG);gi2.add(sommeDesFctLPrG);
    String s="global";
    gsr.add(toStatJd(gi)+" "+toStatInfo(gi2,gi)+" "+sommeNbrDeLigneG+" "+s);
    Main.finCh("traitement du GString");Main.débutCh();
    ecrireUnFichier.ecrireUnFichier(gsr,"stats.txt");
    Main.finCh("sauvegarde finale");
  }

  public static String toStatJd(CCInt cci){return toStatJd(cci.getContenu());}
  /**
  *{@summary calculate the %age of commented fonction in a file.}
  *@version 1.13
  */
  public static String toStatJd(GInt gi){
    int sommeDesCom = gi.getCase(2);
    int sommeDesFctL = gi.getCase(1);
    sommeDesComG+=sommeDesCom;sommeDesFctLG+=sommeDesFctL;
    if(sommeDesFctL==0){return "null%";}
    String r = ((sommeDesCom*100)/sommeDesFctL)+"%";
    while(r.length()<5){r+=" ";}
    r=r+"("+sommeDesCom+"/"+sommeDesFctL+")";
    while(r.length()<5+8){r+=" ";}
    return r;
  }
  /**
  *{@summary calculate the %age of tested fonction in a file.}
  *@version 1.13
  */
  //TODO adapter au test (les tests sont dans un autre fichier).
  public static String toStatTst(GInt gi){
    int sommeDesCom = gi.getCase(2);
    int sommeDesFctL = gi.getCase(1);
    sommeDesComG+=sommeDesCom;sommeDesFctLG+=sommeDesFctL;
    if(sommeDesFctL==0){return "null%";}
    String r = ((sommeDesCom*100)/sommeDesFctL)+"%";
    while(r.length()<5){r+=" ";}
    r=r+"("+sommeDesCom+"/"+sommeDesFctL+")";
    while(r.length()<5+8){r+=" ";}
    return r;
  }
  /**
  *{@summary calculate the number of class, public, ø, protected, private longue fonction.}
  *@version 1.13
  */
  public static String toStatInfo(GInt gi,GInt gi2){
    int sommeDesClass = gi.getCase(0);
    int sommeDesFctLPu = gi.getCase(1);
    int sommeDesFctLPo = gi.getCase(2);
    int sommeDesFctLPr = gi.getCase(3);
    int sommeDesFctC = gi2.getCase(0);
    sommeDesClassG+=sommeDesClass;
    sommeDesFctLPuG+=sommeDesFctLPu;
    sommeDesFctLPoG+=sommeDesFctLPo;
    sommeDesFctLPrG+=sommeDesFctLPr;
    sommeDesFctCG+=sommeDesFctC;
    String r = sommeDesClass+" "; int k=3;
    while(r.length()<k){r+=" ";}k+=3;
    r+=sommeDesFctLPu+" ";
    while(r.length()<k){r+=" ";}k+=3;
    r+=sommeDesFctLPo+" ";
    while(r.length()<k){r+=" ";}k+=3;
    r+=sommeDesFctLPr+" ";
    while(r.length()<k){r+=" ";}k+=3;
    r+=sommeDesFctC+" ";
    //k++;
    while(r.length()<k){r+=" ";}
    return r;
  }
  /**
  *Count the number of ligne in a file an add it to the statistic info.
  */
  public static String numberOfLines(CInt ci){
    sommeNbrDeLigneG+=ci.getContenu();
    String r=ci.getContenu()+"";
    while(r.length()<5){r+=" ";}
    return r;
  }

}
