package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
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
  protected byte tPropretéPerdu[];
  protected int tNourritureFournie[];
  protected int tTaille[];
  protected String note;
  protected GIndividu giu;
  protected boolean vole;

  // CONSTRUCTEUR ---------------------------------------------------------------
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
    tPropretéPerdu=po;
    tNourritureFournie=nf;
    tTaille=ta;
    this.vole =vole;
    this.note = note;
    giu = new GIndividu();
  }
  // GET SET --------------------------------------------------------------------
  public int getId(){ return id;}
  public byte getPropretéPerdu(byte stade){ // fluctue en fonction des tour et pas en fonction des individu.
    if(tPropretéPerdu[stade+3]!=0){
      return (byte) allea.fluctuer(tPropretéPerdu[stade+3],20);
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
  public int [] getTypeDIndividu(){ return giu.getTypeDIndividu();}
  public Individu getIndividuParType(int x){ return giu.getIndividuParType(x);}
  public String getNom(){ return nom;}
  public boolean getPolycalique(){return polycalique;}
  public void setPolycalique(boolean b){polycalique=b;}
  public boolean getVole(){return vole;}//seule les imagos chez les insectes et spécifiquement les individu de type 0 ou 1 chez les fourmi vole.
  public int getNourritureFournie(byte stade){if(stade<-3 || stade > 0){erreur.erreur("nourritureFournie demande un stade entre -3 et 0 hors le stade est de "+stade,"Espece.getNourritureFournie"); return -1;}
    return allea.fluctuer(tNourritureFournie[stade+3]);}//-3 = case 0. 0 = case 3.
  public int getTaille(byte stade){if(stade<-3 || stade > 0){erreur.erreur("getTaille demande un stade entre -3 et 0 hors le stade est de "+stade,"Espece.getTaille"); return -1;}
    return tTaille[stade+3];}
  public int getTaille(int stade){return getTaille(str.iToBy(stade));}
  // Fonctions propre -----------------------------------------------------------
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
    r+="propretéPerdu : ";
    r+=tableau.tableauToString(tPropretéPerdu,",");r+="\n";
    r+="nourritureFournie : ";
    r+=tableau.tableauToString(tNourritureFournie,",");r+="\n";
    r+=giu.toString();r+="\n";
    r+="Note : "+note;r+="\n";
    return r;
  }
  public String descriptionF(){
    // pour l'instant ne sont prise en compte que les fourmi ouvrière les plus courante dans les espèce a taille variable (2 ou 3 tailles).
    GString adj = new GString();
    if(polycalique){adj.ajouter(g.get("polycalique"));}
    else{adj.ajouter(g.get("monocalique"));}
    if(monogyne){ adj.ajouter(g.get("monogyne"));}
    else{ adj.ajouter(g.get("polygyne"));}
    if (insectivore && granivore && fongivore && herbivore && miellativore){ adj.ajouter(g.get("omnivore"));}
    else{
      if(insectivore){ adj.ajouter(g.get("insectivore"));}
      if(granivore){ adj.ajouter(g.get("granivore"));}
      if(fongivore){ adj.ajouter(g.get("fongivore"));}
      if(herbivore){ adj.ajouter(g.get("herbivore"));}
      if(miellativore){ adj.ajouter(g.get("miellativore"));}
    }
    return nom +"("+id+") est une espèce " + adj.concatèneCompacte()+".";
  }
}
