package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Point;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.exceptions.NotNullLocationException;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.structures.listes.GGInt;
import fr.formiko.usual.structures.listes.GInt;

import java.io.Serializable;

public class Fourmiliere implements Serializable{
  //Les données communes a chaque Fourmiliere :
  /** Counter of id */
  private static int idCpt;
  /** Unique id of the Fourmiliere. */
  private final int id;
  /** Place on the map */
  private CCase ccase;
  /** Player that own this */
  private Joueur joueur;
  /** Liste of the Creature own by the Fourmiliere */
  private GCreature gc;
  /** Liste of the Graine own by the Fourmiliere */
  private GGraine gg;
  /** Liste of the Scores turn by turn of the Fourmiliere */
  private GGInt ggi;
  /** Number of died ant. */
  private int nbrFourmisMorte;
  private boolean waitingForEndTurn;

  // CONSTRUCTEUR
  public Fourmiliere(CCase ccase, Joueur j){
    id = idCpt; idCpt++;
    nbrFourmisMorte=0;
    //modeDéfaut=3;
    ggi = new GGInt();
    if (ccase==null){
      erreur.erreur("Impossible de créer une fourmilière sur une case null",true);
    }
    debug.débogage("Placement de la Fourmiliere dans la Case.");
    if (ccase!=null && ccase.getContent().getFere() != null){
      int k=0;
      do {
        ccase = Main.getGc().getCCaseAlléa();
        k++;
        if(k==100){erreur.alerte("Impossible de créer une fourmilière sur une case qui en contient déjà une ! Déjà 100 tentative de placement","Choix d'une autre case alléatoire.");}
      } while (ccase.getContent().getFere() != null);
    }
    this.ccase = ccase;
    if(ccase!=null) {ccase.getContent().setFere(this);}
    joueur = j;
    gc = new GCreature();
    gg = new GGraine();
  }
  public Fourmiliere(Joueur j, Carte mapo){
    this(mapo.getGc().getCCaseAlléa(),j);
  }

  public Fourmiliere(int taille, Joueur j, Carte mapo){
    this(j, mapo);
    int x = allea.getAlléaDansTableau(Main.getAvaibleSpecies());
    if(!j.getIa()){x=0;}// TOREMOVE #74 les joueurs ne joue que des Lasius Niger
    gc = new GCreature(taille, this,Main.getGEspece().getEspeceById(x),getCCase());
  }
  public Fourmiliere(int taille, Joueur j){ this(taille,j,Main.getCarte());}
  public Fourmiliere() {this(((CCase)(null)),null);} //Only for test
  // GET SET ----------------------------------------------------------------------
  public int getId(){return id;}
  public Point getP(){return getCCase().getContent().getP();}
  public Point getPoint(){return getP();}
  public CCase getCc(){return ccase;}
  public CCase getCCase(){return getCc();}
  /**
  *{@summary Move the anthill from a case to an other.}<br>
  *It will not add a Fourmiliere to a case that already have 1 but throw an Exception.
  *It will try to remove from old CCase and add to new CCase.<br>
  *@lastEditedVersion 1.41
  */
  public void setCc(CCase newCCase){
    if(newCCase!=null && newCCase.getContent()!=null){
      if(equals(newCCase.getContent().getFere())){return;}
      if(newCCase.getContent().getFere()!=null){throw new NotNullLocationException();}
    }
    if (getCCase()!=null) {
      getCCase().getContent().setFere(null);
    }
    ccase = newCCase;
    if (newCCase!=null){
      newCCase.getContent().setFere(this);
    }
  }public void setCCase(CCase ccase){setCc(ccase);}
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
  public void setLienFere(){ gc.setLienFere(this);}
  public Pheromone getPh(){
    if(getReine()!=null){return getReine().getPh();}
    else if(getGc()!=null){return getGc().getFirst().getPh();}
    else{return new Pheromone(0,0,0);}
  }
  public int getScore(){
    if(ggi.getLast()==null){ggi.add(toGInt());}
    return computeScore(ggi.getLast());
  }
  public int getNbrFourmisMorte(){return nbrFourmisMorte;}
  public void setNbrFourmisMorte(int x){nbrFourmisMorte=x;}
  public void nbrFourmisMortePlus1(){setNbrFourmisMorte(getNbrFourmisMorte()+1);}
  public Espece getEspece(){return gc.getEspece();}
  public boolean getWaitingForEndTurn(){return waitingForEndTurn;}
  public void setWaitingForEndTurn(boolean b){waitingForEndTurn=b;}
  public static void ini(){idCpt=1;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return a description of the Fourmiliere.}<br>
  *@param b If true we also return all the descriptions of the ants of the Fourmiliere.
  *@lastEditedVersion 1.31
  */
  public String toString(boolean b){
    int leng = length();
    String s = (joueur.getIa()) ? "IA" : "Joueur";
    String sr = g.getM("la")+" "+g.get("fourmilière")+" "+ id +" ("+s+") "+g.get("aPourCoordonnées")+" : "+ getP().toString() +" "+g.get("et")+" "+g.get("contient")+" "+leng+" "+g.get("fourmis")+"."+"\n";
    if(b){sr+=gc.toString();}
    sr+=gg.toString();
    return sr;
  }
  /**
  *{@summary Return a description of the Fourmiliere.}<br>
  *@lastEditedVersion 1.31
  */
  public String toString(){
    return toString(true);
  }
  /**
  *{@summary Return a short description of the Fourmiliere.}<br>
  *@lastEditedVersion 2.18
  */
  public String toStringShort(){
    int leng = length();
    String s = (joueur.getIa()) ? "IA" : "Joueur";
    String sr = g.getM("fourmilière")+" "+ id +" ("+s+") : "+ getP().toString() +" "+g.get("et")+" "+g.get("contient")+" "+leng+" "+g.get("fourmis");
    if(gg.length()>0){
      sr+=" "+g.get("et")+" "+gg.length()+" "+g.get("graine");
    }
    sr+=".";
    return sr;
  }
  /**
  *{@summary Let all ant play.}<br>
  *Before that ants play they all need to have a pre-turn update (this.preTurn()).<br>
  *Ants do not necessarily play in order so we way for haveDoneAllActionAviable() to end turn.<br>
  *At the end of the Fourmiliere turn we add a line to there stats (How many ant are alive and what stade).
  *@lastEditedVersion 2.22
  */
  public void jouer(){
    if(gc.length()==0){return;}
    //this.setModeDéfaut(3); //tant que tous le broods n'aura pas été dorloté.
    do {
      gc.jouer();
    } while (!gc.haveDoneAllActionAviable() && !getJoueur().getIsTurnEnded() && !Main.getRetournerAuMenu());
    Main.getView().setPlayingAnt(null);
    setWaitingForEndTurn(true);
    if(Main.getPlayingJoueur()!=null){
      Main.getView().waitForEndTurn();
    }
    for (Creature c : gc) {
      c.endTurn();
    }
    setWaitingForEndTurn(false);
    ggi.add(toGInt()); //stats of this turn
  }
  /**
  *{@summary Before that ants play they all have a pre-turn update (gc.preTurn()).}<br>
  *@lastEditedVersion 2.22
  */
  public void preTurn(){
    gc.preTurn();
  }
  /*public void faireVarierLesAge(){
  }
  public void faireVarierLesPoint(){
  }*/
  public void déposer(Graine g){gg.add(g); }
  /**
  *{@summary Save stats/score in the GGInt.}<br>
  *@lastEditedVersion 1.31
  */
  public String enregistrerLesScores(){
    return ggi.toString();
  }
  /**
  *{@summary Create a score GInt from an anthill.}
  *@lastEditedVersion 2.25
  */
  public GInt toGInt(){
    GInt gi = new GInt();
    GCreature gc = getGc();
    if(gc.length()==0){return gi;}
    if(gc.getReine()!=null){gi.add(1);}else{gi.add(0);}
    gi.add(gc.getGcStade(0).length()-gi.getFirst());
    for(int i=-1; i>-4;i--){
      gi.add(gc.getGcStade(i).length());
    }
    gi.add(getNbrFourmisMorte());
    return gi;
  }
  /**
  *{@summary Return score of an anthill from GGInt.}
  *We assume that all GInt have been created with an anthill as parameter
  *@return the computed score
  *@lastEditedVersion 2.25
  */
  public int computeScore(GInt gi){
    // return ggi.getLast().computeScore(this);
    if(gi.isEmpty()){
      erreur.alerte("le GInt est null, impossible de calculer le score du joueur.");return -1;
    }else if(gi.length()!=6){
      erreur.alerte("le GInt ne contient pas le bon nombre de CInt, impossible de calculer le score du joueur.");return -1;
    }else{
      int total=0;
      int multiscore [] = {50, 20, 9, 6, 3, -1};
      int lenms = multiscore.length;
      int k=0;
      for (int val : gi) {
        if(k>=lenms){
          erreur.alerte("To much value for score");
          break;
        }
        total+=val*multiscore[k];
        k++;
      }
      if(this.getJoueur()!=null && !this.getJoueur().getIa() && this.getGc()!=null && this.getGc().getFirst()!=null){
        total=(int)(total*((Fourmi) (this.getGc().getFirst())).getMultiplicateurDeDiff());
      }
      return total;
    }
  }
}
