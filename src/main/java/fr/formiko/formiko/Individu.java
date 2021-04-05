package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

public class Individu implements Serializable{
  protected Espece e; // on la relie avec un id dans le tableau .csv
  protected byte type; // 0  Reine 1 = Male 2 = Minor 3 Medium 4 = Major 5 = soldate (etc).
  protected boolean sexué; // false = pas sexué
  protected boolean sexe; // false = femmelle; true = Male;
  protected byte nétoyage;
  protected byte actionMax; // dans certaine espèces les larves travaille en créant du fil de soie.
  protected byte taille;
  protected String couleur;
  protected byte poidMax; //(en gramme) si le poid est supérieur a 10% la fourmi peine et ses déplacements coute plus chere.
  protected byte tempsDeRepos; // en heure (ou en 24a de tour).
  protected int nourritureConso;

  protected int tAgeMax[] = new int[4]; // sauf l'age adulte qui vari.
  protected int tNourritureMax[]= new int[4]; // sauf la nourritureMax adulte.
  protected byte tCoutAction[] = new byte[6];
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Individu(int idEsp, byte ty, boolean se,boolean se2, byte né, byte ac, byte ta, String co, byte po, byte te, int tag0, int tag1, int tag2, int tag3, int nm0, int nm1, int nm2, int nm3,byte ca0,byte ca1,byte ca2,byte ca3,byte ca4,byte ca5,int nc){
    e = Main.getGEspece().getEspeceParId(idEsp);
    type = ty; sexué = se; sexe = se2; nétoyage = né; actionMax = ac; taille =ta; couleur = co; poidMax =po; tempsDeRepos = te;
    tAgeMax[0] = tag0; tAgeMax[1] = tag1; tAgeMax[2] = tag2; tAgeMax[3] = tag3;
    tNourritureMax[0] = nm0; tNourritureMax[1] = nm1; tNourritureMax[2] = nm2; tNourritureMax[3] = nm3;
    tCoutAction[0] = ca0; tCoutAction[1] = ca1; tCoutAction[2] = ca2; tCoutAction[3] = ca3; tCoutAction[4] = ca4; tCoutAction[5] = ca5;
    nourritureConso=nc;
    debug.débogage("Fin de la création d'un individu");
  }
  // GET SET --------------------------------------------------------------------
  public Espece getEspece(){ return e;}
  public byte getType(){ return type;}
  public byte getActionMax(){ return actionMax;}
  public byte getTaille(){ return taille;}

  public int getAgeMax(int i){ return tAgeMax[i]/2;} //to make it shorter
  public int getAgeMax(){ return getAgeMax(3);}
  public int getNourritureMax(int i){ return tNourritureMax[i];}
  public int getNourritureMax(){ return getNourritureMax(3);}

  public byte getCoutDéplacement(){ return tCoutAction[0];}
  public byte getCoutChasse(){ return tCoutAction[1];}
  public byte getCoutPondre(){ return tCoutAction[2];}
  public byte getCoutNétoyer(){ return tCoutAction[3];}
  public byte getCoutTrophallaxie(){ return tCoutAction[4];}
  public byte getNétoyage(){return nétoyage;}
  public String getStringType(){
    String ind = "";
    if (type== 0){ ind = g.get("reine");}
    else if (type== 1){ ind = g.get("male");}
    else if (type== 2){ ind = g.get("minor");}
    else if (type== 3){ ind = g.get("médias");}
    else if (type== 4){ ind = g.get("major");}
    else if (type== 5){ ind = g.get("soldate");}
    else{ ind = g.get("individu")+" "+type;}
    return ind;
  }
  public int getNourritureConso(int stade){
    if(stade==-3){ return 0;}
    return nourritureConso;
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    GString adj = new GString();//♂ ♀
    if(sexué){ String sex = "♀"; if(sexe){ sex = "♂";} adj.add(g.get("sexué")+" ("+sex+")");}
    else{adj.add(g.get("assexué"));}
    adj.add(couleur);
    String ind = getStringType();
    String s = "";
    s+=ind +" "+ adj.toString();s+="\n";
    s+=g.get("nétoyage")+" : "+nétoyage;s+="\n";
    s+=g.get("actionMax")+" : "+actionMax;s+="\n";
    s+=g.get("taille")+" : "+taille;s+="\n";
    s+=g.get("poidsSupportable")+" : "+poidMax;s+="\n";
    //System.out.println("Temps de repos néssésaire (par tour)")+" : "+tempsDeRepos);
    s+=g.get("tAgeMax")+" : ";
    s+=tableau.tableauToString(tAgeMax);s+="\n";
    s+=g.get("tNourritureMax")+" : ";
    s+=tableau.tableauToString(tNourritureMax);s+="\n";
    s+=g.get("tCoutAction")+" : ";
    s+=tableau.tableauToString(tCoutAction);s+="\n";
    return s;
  }
}
