package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.conversiondetype.ent;
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;

public class Espece {
  protected static int racioTourParJour = 24; // le fichier Espece.csv contient les temps en jours. (En plus de ce racio un développement lent sera 2 fois plus long (*2 par défaut.) Un dévelloppement rapide grace a une température idéale permettra au fourmi de passer 2 tour de vie d'un seul coup.)
  protected final int id; protected static int cptid;
  protected String nom;
  protected int nbrDIndividuMax;
  protected boolean polycalique; protected boolean monogyne;
  protected boolean insectivore; protected boolean granivore; protected boolean fongivore;
  protected byte tTempératureExt[] = new byte[4];
  protected byte tTempératureInt[] = new byte[4];
  protected byte tHumidité[] = new byte[2];
  protected boolean tHabitat[] = new boolean[3];
  protected byte tPropretéPerdu[] = new byte[4];
  protected String note;
  protected GIndividu giu;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Espece(int id, String nom, int nbr,boolean polycalique, boolean monogyne, boolean insectivore, boolean granivore, boolean fongivore, byte tmin, byte tmini, byte tmaxi, byte tmax, byte tnidmin,byte tnidmini, byte tnidmaxi, byte tnidmax, byte humin, byte humax,boolean ha0, boolean ha1, boolean ha2, byte po0, byte po1, byte po2, byte po3, String note){
    this.id = id;
    this.nom = nom;
    nbrDIndividuMax = nbr;
    this.polycalique = polycalique; this.monogyne = monogyne;
    this.insectivore = insectivore; this.granivore = granivore; this.fongivore = fongivore;
    tTempératureExt[0] = tmin; tTempératureExt[1] = tmini; tTempératureExt[2] = tmaxi; tTempératureExt[3] = tmax;
    tTempératureInt[0] = tnidmin; tTempératureInt[1] = tnidmini; tTempératureInt[2] = tnidmaxi; tTempératureInt[3] = tnidmax;
    tHumidité[0]=humax;tHumidité[1]=humin;
    tHabitat[0]=ha0;tHabitat[1]=ha1;tHabitat[2]=ha2;
    tPropretéPerdu[0]=po0; tPropretéPerdu[1]=po1; tPropretéPerdu[2]=po2; tPropretéPerdu[3]=po3;
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
  public int getNbrDIndividuMax(){ return nbrDIndividuMax;}
  public GIndividu getGIndividu(){ return giu;}
  public void setGIndividu(GIndividu giu){ this.giu = giu;}
  public int [] getTypeDIndividu(){ afficheToi(); return giu.getTypeDIndividu();}
  public Individu getIndividuParType(int x){ return giu.getIndividuParType(x);}
  public String getNom(){ return nom;}
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){
    System.out.println(descriptionF());
    System.out.print("tTempératureExt : ");
    tableau.afficher(tTempératureExt,",");
    System.out.print("tTempératureInt : ");
    tableau.afficher(tTempératureInt,",");
    System.out.print("tHumidité : ");
    tableau.afficher(tHumidité,",");
    System.out.print("tHabitat : ");
    tableau.afficher(tHabitat,",");
    System.out.print("propretéPerdu : ");
    tableau.afficher(tPropretéPerdu,",");
    giu.afficheToi();
    System.out.print("Note : ");System.out.println(note); System.out.println();
  }
  public String descriptionF(){
    // pour l'instant ne sont prise en compte que les fourmi ouvrière les plus courante dans les espèce a taille variable (2 ou 3 tailles).
    GString adj = new GString();
    if(polycalique){adj.ajouter("polycalique");}
    else{adj.ajouter("monocalique");}
    if(monogyne){ adj.ajouter("monogyne");}
    else{ adj.ajouter("polygyne");}
    if (insectivore && granivore && fongivore){ adj.ajouter("omnivore");}
    else{
      if(insectivore){ adj.ajouter("insectivore");}
      if(granivore){ adj.ajouter("granivore");}
      if(fongivore){ adj.ajouter("fongivore");}
    }
    return nom +"("+id+") est une espèce " + adj.concatèneCompacte();
  }
}