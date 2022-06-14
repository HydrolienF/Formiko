package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.ReadFile;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

public class Espece implements Serializable{
  protected static int racioTourParJour = 24; // le fichier Espece.csv contient les temps en jours. (En plus de ce racio un développement lent sera 2 fois plus long (*2 par défaut.) Un dévelloppement rapide grace a une temperature idéale permettra au fourmi de passer 2 tour de vie d'un seul coup.)
  protected final int id;
  protected String name;
  protected int maxIndividus;
  protected boolean polycalic; protected boolean monogyne;
  protected boolean insectivore; protected boolean granivore; protected boolean fongivore; protected boolean herbivore; protected boolean miellativore;
  protected byte tTemperatureExt[] = new byte[4];
  protected byte tTemperatureInt[] = new byte[4];
  protected byte tHumidity[] = new byte[2];
  protected boolean tHabitat[] = new boolean[3];
  protected byte tHealthLost[];
  protected int tGivenFood[];
  protected int tSize[];
  protected String note;
  protected GIndividu giu;
  protected boolean vole;

  // CONSTRUCTORS --------------------------------------------------------------
  public Espece(int id, String name, int nbr,boolean polycalic, boolean monogyne, boolean insectivore, boolean granivore, boolean fongivore, boolean herbivore, boolean miellativore, byte tmin, byte tmini, byte tmaxi, byte tmax, byte tnidmin,byte tnidmini, byte tnidmaxi, byte tnidmax, byte humin, byte humax,boolean ha0, boolean ha1, boolean ha2, byte po[], int nf[], int ta[], boolean vole, String note){
    this.id = id;
    this.name = name;
    maxIndividus = nbr;
    this.polycalic = polycalic; this.monogyne = monogyne;
    this.insectivore = insectivore; this.granivore = granivore; this.fongivore = fongivore; this.herbivore = herbivore; this.miellativore =miellativore;
    tTemperatureExt[0] = tmin; tTemperatureExt[1] = tmini; tTemperatureExt[2] = tmaxi; tTemperatureExt[3] = tmax;
    tTemperatureInt[0] = tnidmin; tTemperatureInt[1] = tnidmini; tTemperatureInt[2] = tnidmaxi; tTemperatureInt[3] = tnidmax;
    tHumidity[0]=humax;tHumidity[1]=humin;
    tHabitat[0]=ha0;tHabitat[1]=ha1;tHabitat[2]=ha2;
    tHealthLost=po;
    tGivenFood=nf;
    tSize=ta;
    this.vole =vole;
    this.note = note;
    giu = new GIndividu();
  }
  // public Espece(int id, String name, int maxIndividus, boolean polycalic,
  //     boolean monogyne, boolean insectivore, boolean granivore, boolean fongivore,
  //     boolean herbivore, boolean miellativore, byte tTemperatureExt[],
  //     byte tTemperatureInt[], byte tHumidity[], boolean tHabitat[],
  //     byte tHealthLost[], int tGivenFood[], int tSize[], String note,
  //     GIndividu giu, boolean vole
  //     ){
  // }
  /**
	* Default Espece constructor
	*/
	public Espece(int id, String name, int maxIndividus, boolean polycalic,
      boolean monogyne, boolean insectivore, boolean granivore, boolean fongivore,
      boolean herbivore, boolean miellativore, byte tTemperatureExt[],
      byte tTemperatureInt[], byte tHumidity[], boolean tHabitat[], byte tHealthLost[],
      int tGivenFood[], int tSize[], String note, GIndividu giu, boolean vole) {
    this.id = id;
		this.name = name;
		this.maxIndividus = maxIndividus;
		this.polycalic = polycalic;
		this.monogyne = monogyne;
		this.insectivore = insectivore;
		this.granivore = granivore;
		this.fongivore = fongivore;
		this.herbivore = herbivore;
		this.miellativore = miellativore;
		this.tTemperatureExt = tTemperatureExt;
		this.tTemperatureInt = tTemperatureInt;
		this.tHumidity = tHumidity;
		this.tHabitat = tHabitat;
		this.tHealthLost = tHealthLost;
		this.tGivenFood = tGivenFood;
		this.tSize = tSize;
		this.note = note;
		this.giu = giu;
		this.vole = vole;
	}

  // public Espece(int id){
  //   this.id = id;
  // } //For jackson only
  // GET SET -------------------------------------------------------------------
  public int getId(){ return id;}
  public byte getHealthLost(byte stade){ // fluctue en fonction des tour et pas en fonction des individu.
    if(tHealthLost[stade+3]!=0){
      return (byte) allea.fluctuer(tHealthLost[stade+3],20);
    }else{
      return (byte) 0;
    }
  }
  public boolean getInsectivore(){ return insectivore;}
  public boolean getGranivore(){ return granivore;}
  public boolean getFongivore(){return fongivore;}
  public boolean getHerbivore(){return herbivore;}
  public boolean getMiellativore(){return miellativore;}
  public int getNbrDIndividuMax(){ return maxIndividus;}
  public GIndividu getGIndividu(){ return giu;}
  public void setGIndividu(GIndividu giu){ this.giu = giu;}
  public int [] getAviableType(){ return giu.getAviableType();}
  public Individu getIndividuByType(int typeF){ return giu.getIndividuByType(typeF);}
  public String getName(){
    if(name.equals("x")){return ""+getId();}
    return name;
  }
  public boolean getPolycalique(){return polycalic;}
  public void setPolycalique(boolean b){polycalic=b;}
  public boolean getHaveWings(){return vole;}//seule les imagos chez les insectes et spécifiquement les individu de type 0 ou 1 chez les fourmi vole.
  public int getGivenFood(byte stade){if(stade<-3 || stade > 0){erreur.erreur("givenFood demande un stade entre -3 et 0 hors le stade est de "+stade); return -1;}
    return allea.fluctuer(tGivenFood[stade+3]);}//-3 = case 0. 0 = case 3.
  public int getSize(byte stade){if(stade<-3 || stade > 0){erreur.erreur("getSize demande un stade entre -3 et 0 hors le stade est de "+stade); return -1;}
    return tSize[stade+3];}
  public int getSize(int stade){return getSize(str.iToBy(stade));}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String r = "";
    String finLigne = "\n";
    r+=descriptionF();r+="\n";
    r+="tTemperatureExt : ";
    r+=tableau.tableauToString(tTemperatureExt,",");r+=finLigne;
    r+="tTemperatureInt : ";
    r+=tableau.tableauToString(tTemperatureInt,",");r+=finLigne;
    r+="tHumidity : ";
    r+=tableau.tableauToString(tHumidity,",");r+=finLigne;
    r+="tHabitat : ";
    r+=tableau.tableauToString(tHabitat,",");r+=finLigne;
    r+="healthLost : ";
    r+=tableau.tableauToString(tHealthLost,",");r+="\n";
    r+="givenFood : ";
    r+=tableau.tableauToString(tGivenFood,",");r+="\n";
    r+=giu.toString();r+="\n";
    r+="Note : "+note;r+="\n";
    return r;
  }
  public String descriptionF(){
    // pour l'instant ne sont prise en compte que les fourmi ouvrière les plus courante dans les espèce a taille variable (2 ou 3 tailles).
    GString adj = new GString();
    if(polycalic){adj.add(g.get("polycalic"));}
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
    return name +"("+id+") est une espèce " + adj.toString()+".";
  }
}
