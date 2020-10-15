package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.liste.GGInt;
import fr.formiko.usuel.liste.GInt;

public class Fourmiliere {

  //Les données communes a chaque Fourmiliere :
  private static int idCpt=1; private final int id;
  private CCase cc;
  private Joueur joueur;
  private GCreature gc; private GGraine gg;
  private byte modeDéfaut;
  private GGInt ggi;
  private int nbrFourmisMorte;

  // CONSTRUCTEUR
  public Fourmiliere(CCase cc, Joueur j){
    id = idCpt; idCpt++;
    nbrFourmisMorte=0;
    modeDéfaut=3; ggi = new GGInt();
    if (cc==null){
      erreur.erreur("Impossible de créer une fourmilière sur une case null","Fourmiliere.Fourmiliere",true);
    }
    debug.débogage("Placement de la Fourmiliere dans la Case.");
    if (cc.getContenu().getFere() != null){
      erreur.alerte("Impossible de créer une fourmilière sur une case qui en contient déjà une !","Fourmiliere.Fourmiliere","Choix d'une autre case alléatoire.");
      do {
        cc = Main.getGc().getCCaseAlléa();
      } while (cc.getContenu().getFere() != null);
    }
    this.cc = cc; // la cc doit etre une case reliée a début de Main.getGc sinon on la vera pas.
    cc.getContenu().setFere(this);
    joueur = j;
    gc = new GCreature();
    gg = new GGraine();
  }
  public Fourmiliere(Joueur j, Carte mapo){
    this(mapo.getGc().getCCaseAlléa(),j);
  }

  public Fourmiliere(int taille, Joueur j, Carte mapo){
    this(j, mapo);
    int x = allea.getAlléaDansTableau(Main.getTableauDesEspecesAutorisée());
    gc = new GCreature(taille, this,Main.getGEspece().getEspeceParId(x),cc);
  }
  public Fourmiliere(int taille, Joueur j){ this(taille,j,Main.getCarte());}
  // GET SET -----------------------------------------------------------------------
  public int getId(){return id;}
  public Point getP(){return cc.getContenu().getP();}
  public Point getPoint(){return getP();}
  public CCase getCc(){return cc;}
  public CCase getCCase(){return getCc();}
  public void setCc(CCase x){
    getCCase().getContenu().setFere(null);
    cc = x;
    getCCase().getContenu().setFere(this);
  }
  public static int getI(){return idCpt;}
  public int getNbrDeFourmi(){return length();}
  public int getLen(){return length();}
  public int length(){return gc.length();}
  public void setJoueur(Joueur j){joueur = j;}
  public Joueur getJoueur(){return joueur;}
  public GCreature getGc(){return gc;}
  public Creature getReine(){ return gc.getReine();}// on part du principe qu'il n'y a qu'une reine.
  public GGraine getGg(){return gg;} public GGraine getGGraine(){ return getGg();}
  public void setGg(GGraine gg){ this.gg = gg;}
  public byte getModeDéfaut(){ return modeDéfaut;}
  public void setModeDéfaut(byte x){ modeDéfaut=x;}
  public void setModeDéfaut(int x){ setModeDéfaut((byte)x);}
  public void setLienFere(){ gc.setLienFere(this);}
  public Pheromone getPh(){
    if(getReine()!=null){return getReine().getPh();}
    else if(getGc().getDébut()!=null){return getGc().getDébut().getContenu().getPh();}
    else{return new Pheromone(0,0,0);}
  }
  public int getScore(){return ggi.getFin().getContenu().calculerScore(this);}
  public int getNbrFourmisMorte(){return nbrFourmisMorte;}
  public void setNbrFourmisMorte(int x){nbrFourmisMorte=x;}
  public void nbrFourmisMortePlus1(){setNbrFourmisMorte(getNbrFourmisMorte()+1);}
  public Espece getEspece(){return gc.getEspece();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    int leng = length();
    String s = (joueur.getIa()) ? "IA" : "Joueur";
    String sr = g.getM("laFourmilière")+" "+ id +" ("+s+") "+g.get("aPourCoordonnées")+" : "+ getP().toString() +" "+g.get("et")+" "+g.get("contient")+" "+leng+" "+g.get("fourmis")+".";
    sr+=gg.toString();
    return sr;
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public void afficheToiAvecFourmi(){
    afficheToi();
    if (gc != null && gc.getDébut() != null){
      gc.afficheToi();
    }
  }
  public void jouer(){
    if(gc.length()==0){return;}
    this.setModeDéfaut(3); //tant que tous le couvains n'aura pas été dorloté.
    do {
      gc.jouer();
      System.out.println(gc.aFiniDeJouer());
    } while (!joueur.getIa() && !gc.aFiniDeJouer());
    gc.finTour();
    ggi.ajouter(new GInt(this));
  }
  /*public void faireVarierLesAge(){
  }
  public void faireVarierLesPoint(){
  }*/
  public void déposer(Graine g){gg.ajouter(g); }
  public String enregistrerLesScores(){
    return ggi.toString();
  }
}