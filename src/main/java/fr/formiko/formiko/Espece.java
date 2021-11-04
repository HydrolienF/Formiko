package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class Espece implements Serializable{
  protected static int racioTourParJour = 24; // le fichier Espece.csv contient les temps en jours. (En plus de ce racio un développement lent sera 2 fois plus long (*2 par défaut.) Un dévelloppement rapide grace a une température idéale permettra au fourmi de passer 2 tour de vie d'un seul coup.)
  protected final int id; protected static int cptid;
  protected String nom;
  protected int nbrDIndividuMax;
  protected boolean polycalique; protected boolean monogyne;
  protected boolean insectivore; protected boolean granivore; protected boolean fongivore; protected boolean herbivore; protected boolean miellativore;
  protected byte tTempératureExt[] = new byte[4];
  protected byte tTempératureInt[] = new byte[4];
  protected byte tHumidité[] = new byte[2];
  protected boolean tHabitat[] = new boolean[3];
  protected byte tHealthPerdu[];
  protected int tGivenFood[];
  protected int tTaille[];
  protected String note;
  protected GIndividu giu;
  protected boolean vole;

  // CONSTRUCTORS --------------------------------------------------------------
  public Espece(int id, String nom, int nbr,boolean polycalique, boolean monogyne, boolean insectivore, boolean granivore, boolean fongivore, boolean herbivore, boolean miellativore, byte tmin, byte tmini, byte tmaxi, byte tmax, byte tnidmin,byte tnidmini, byte tnidmaxi, byte tnidmax, byte humin, byte humax,boolean ha0, boolean ha1, boolean ha2, byte po[], int nf[], int ta[], boolean vole, String note){
    this.id = id;
    this.nom = nom;
    nbrDIndividuMax = nbr;
    this.polycalique = polycalique; this.monogyne = monogyne;
    this.insectivore = insectivore; this.granivore = granivore; this.fongivore = fongivore; this.herbivore = herbivore; this.miellativore =miellativore;
    tTempératureExt[0] = tmin; tTempératureExt[1] = tmini; tTempératureExt[2] = tmaxi; tTempératureExt[3] = tmax;
    tTempératureInt[0] = tnidmin; tTempératureInt[1] = tnidmini; tTempératureInt[2] = tnidmaxi; tTempératureInt[3] = tnidmax;
    tHumidité[0]=humax;tHumidité[1]=humin;
    tHabitat[0]=ha0;tHabitat[1]=ha1;tHabitat[2]=ha2;
    tHealthPerdu=po;
    tGivenFood=nf;
    tTaille=ta;
    this.vole =vole;
    this.note = note;
    giu = new GIndividu();
  }
  // GET SET -------------------------------------------------------------------
  public int getId(){ return id;}
  public byte getHealthPerdu(byte stade){ // fluctue en fonction des tour et pas en fonction des individu.
    if(tHealthPerdu[stade+3]!=0){
      return (byte) allea.fluctuer(tHealthPerdu[stade+3],20);
    }else{
      return (byte) 0;
    }
  }
  public boolean getInsectivore(){ return insectivore;}
  public boolean getGranivore(){ return granivore;}
  public boolean getFongivore(){return fongivore;}
  public boolean getHerbivore(){return herbivore;}
  public boolean getMiellativore(){return miellativore;}
  public int getNbrDIndividuMax(){ return nbrDIndividuMax;}
  public GIndividu getGIndividu(){ return giu;}
  public void setGIndividu(GIndividu giu){ this.giu = giu;}
  public int [] getAviableType(){ return giu.getAviableType();}
  public Individu getIndividuByType(int typeF){ return giu.getIndividuByType(typeF);}
  public String getNom(){
    if(nom.equals("x")){return ""+getId();}
    return nom;
  }
  public boolean getPolycalique(){return polycalique;}
  public void setPolycalique(boolean b){polycalique=b;}
  public boolean getHaveWings(){return vole;}//seule les imagos chez les insectes et spécifiquement les individu de type 0 ou 1 chez les fourmi vole.
  public int getGivenFood(byte stade){if(stade<-3 || stade > 0){erreur.erreur("givenFood demande un stade entre -3 et 0 hors le stade est de "+stade); return -1;}
    return allea.fluctuer(tGivenFood[stade+3]);}//-3 = case 0. 0 = case 3.
  public int getTaille(byte stade){if(stade<-3 || stade > 0){erreur.erreur("getTaille demande un stade entre -3 et 0 hors le stade est de "+stade); return -1;}
    return tTaille[stade+3];}
  public int getTaille(int stade){return getTaille(str.iToBy(stade));}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String r = "";
    String finLigne = "\n";
    r+=descriptionF();r+="\n";
    r+="tTempératureExt : ";
    r+=tableau.tableauToString(tTempératureExt,",");r+=finLigne;
    r+="tTempératureInt : ";
    r+=tableau.tableauToString(tTempératureInt,",");r+=finLigne;
    r+="tHumidité : ";
    r+=tableau.tableauToString(tHumidité,",");r+=finLigne;
    r+="tHabitat : ";
    r+=tableau.tableauToString(tHabitat,",");r+=finLigne;
    r+="healthPerdu : ";
    r+=tableau.tableauToString(tHealthPerdu,",");r+="\n";
    r+="givenFood : ";
    r+=tableau.tableauToString(tGivenFood,",");r+="\n";
    r+=giu.toString();r+="\n";
    r+="Note : "+note;r+="\n";
    return r;
  }
  public String descriptionF(){
    // pour l'instant ne sont prise en compte que les fourmi ouvrière les plus courante dans les espèce a taille variable (2 ou 3 tailles).
    GString adj = new GString();
    if(polycalique){adj.add(g.get("polycalique"));}
    else{adj.add(g.get("monocalique"));}
    if(monogyne){ adj.add(g.get("monogyne"));}
    else{ adj.add(g.get("polygyne"));}
    if (insectivore && granivore && fongivore && herbivore && miellativore){ adj.add(g.get("omnivore"));}
    else{
      if(insectivore){ adj.add(g.get("insectivore"));}
      if(granivore){ adj.add(g.get("granivore"));}
      if(fongivore){ adj.add(g.get("fongivore"));}
      if(herbivore){ adj.add(g.get("herbivore"));}
      if(miellativore){ adj.add(g.get("miellativore"));}
    }
    return nom +"("+id+") est une espèce " + adj.toString()+".";
  }
}
