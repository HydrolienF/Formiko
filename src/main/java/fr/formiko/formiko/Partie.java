package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;
import fr.formiko.views.gui2d.EtiquetteJoueur;
import fr.formiko.views.gui2d.GEtiquetteJoueur;

import java.awt.Image;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
*{@summary All informations about a Game in the Game Formiko.}<br>
*There is no word for "a game in the Game Formiko" in english so I use the french word Partie.<br>
*All map value are in Carte var.<br>
*@author Hydrolien
*@lastEditedVersion 1.39
*/
public class Partie implements Serializable{
  private static final long serialVersionUID = 1L;
  private static String script="";
  private GInsecte gi;
  private GJoueur gj;
  private static GEspece ge;
  private Carte mapo;
  private int nbrDeTour, tour;
  private int niveauDeLimitationDesinsectes = 4;
  private byte difficulté = 0; // 0 moyen. -1 facile, -2 très facile, 1 difficle, 2 très difficle, 3 ultra difficle.
  private LocalDateTime dateDeCréation;
  private boolean partieFinie;
  private int tableauDesEspecesAutorisée []; // les 0 et les 3 marche.
  private int nbrDeJoueurDansLaPartie;
  private boolean enCours;
  private int [] elément;
  private GEtiquetteJoueur gej;
  private double vitesseDeJeu=1;
  private boolean continerLeJeu;
  private boolean appartionInsecte;
  private boolean appartionGraine;
  private boolean dejaIni=false;
  private Fourmi playingAnt;
  private int antIdToPlay;
  private boolean launchingFromSave;

  // CONSTRUCTORS --------------------------------------------------------------
  // nombre de joueur, nombre d'ia, abondance des insectes, niveau de difficulté des ia, les especes autorisé, le nombre de tour.
  public Partie(int difficulté, int nbrDeTour, Carte mapo, double vitesseDeJeu){
    // script=""; //TODO we don't whant to update it every time.
    tour=0; partieFinie=false; enCours=false;
    dateDeCréation = LocalDateTime.now();
    this.mapo=mapo;
    if (nbrDeTour==-1){ nbrDeTour = 2147483647;}
    this.nbrDeTour=nbrDeTour;
    this.difficulté = str.iToBy(difficulté);
    this.vitesseDeJeu=vitesseDeJeu;
    tableauDesEspecesAutorisée = new int [1];//[2];
    tableauDesEspecesAutorisée[0]=0;
    //tableauDesEspecesAutorisée[1]=3;
    //a ce stade, il manque encore gi et gj. On les initialise null partie précaution.
    gj = new GJoueur();
    gi = new GInsecte();
    appartionInsecte=true;
    appartionGraine=true;
    antIdToPlay=-1;
    Fourmi.setUneSeuleAction(-1);//reini avaiable action if it have been edit by a script.
  }
  /**
  *{@summary Constructor with only minimal initialization.}<br>
  *@lastEditedVersion 1.2
  */
  public Partie(){
    this(0,0,new Carte(new GCase(1,1)),1.0);//initialisation "null"
    gj = new GJoueur();
    gi = new GInsecte();
  }
  // GET SET -------------------------------------------------------------------
  public static String getScript(){return script;}
  public static void setScript(String s){script=s;}
  public GInsecte getGi(){ return gi;}
  public void setGi(GInsecte g){gi=g;}
  public GJoueur getGj(){ return gj;}
  public boolean isMultiplayer(){if(getGj()!=null){return getGj().getJoueurHumain().length()>1;}return false;}
  public boolean isSolo(){if(getGj()!=null){return getGj().getJoueurHumain().length()==1;}return false;}
  public void setGj(GJoueur g){gj=g;}
  public GCase getGc(){return mapo.getGc();}
  public static GEspece getGe(){return ge;}
  public int getTour(){ return tour;}
  public void setTour(int x){ tour=x;}
  public void addTour(int x){tour+=x;}
  public void addTour(){addTour(1);}
  public int getNbrDeTour(){ return nbrDeTour;}
  public void setNbrDeTour(int x){ nbrDeTour=x;}
  public byte getDifficulté(){return difficulté;}
  public void setDifficulté(byte x){difficulté = x;}
  public LocalDateTime getDateDeCréation(){ return dateDeCréation;}
  public int [] getTableauDesEspecesAutorisée(){return tableauDesEspecesAutorisée;}
  public int getNbrDeJoueurDansLaPartie(){ return nbrDeJoueurDansLaPartie;}
  public Carte getCarte(){ return mapo;}
  public boolean getEnCours(){ return enCours;}
  public void setEnCours(boolean b){enCours=b;}
  public void setElément(int a, int b, int c){ elément=new int [3]; elément[0]=a;elément[1]=b;elément[2]=c;}
  public void setGej(GEtiquetteJoueur gej){this.gej=gej;}
  public double getVitesseDeJeu(){ return vitesseDeJeu;}
  public void setVitesseDeJeu(double v){vitesseDeJeu=v;}
  public boolean getPartieFinie(){return partieFinie;}
  public void setPartieFinie(boolean b){partieFinie=b;}
  public boolean getContinuerLeJeu(){ return continerLeJeu;}
  public void setContinuerLeJeu(boolean b){continerLeJeu=b;}
  public boolean getAppartionInsecte(){return appartionInsecte;}
  public void setAppartionInsecte(boolean b){appartionInsecte=b;}
  public boolean getAppartionGraine(){return appartionGraine;}
  public void setAppartionGraine(boolean b){appartionGraine=b;}
  public Fourmi getPlayingAnt(){return playingAnt;}
  public Joueur getPlayingJoueur(){try{return getPlayingAnt().getFere().getJoueur();}catch (Exception e) {return null;}}
  public boolean getCasesSombres(){if(getCarte()==null){ return false;} else {return getCarte().getCasesSombres();}}
  public boolean getCasesNuageuses(){if(getCarte()==null){ return false;} else {return getCarte().getCasesNuageuses();}}
  public int getAntIdToPlay(){return antIdToPlay;}
  public void setAntIdToPlay(int x){antIdToPlay=x;}
  public boolean getLaunchingFromSave(){return launchingFromSave;}
  public void setLaunchingFromSave(boolean launchingFromSave){this.launchingFromSave=launchingFromSave;}
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String r="";
    r+= g.get("continerLeJeu")+" : "+continerLeJeu;r+="\n";
    r+= g.get("appartionInsecte")+" : "+appartionInsecte;r+="\n";
    r+= g.get("appartionGraine")+" : "+appartionGraine;r+="\n";
    r+= g.get("vitesseDeJeu")+" : "+vitesseDeJeu;r+="\n";
    r+= g.get("enCours")+" : "+enCours;r+="\n";
    r+= g.get("nbrDeJoueurDansLaPartie")+" : "+nbrDeJoueurDansLaPartie;r+="\n";
    int id=-1;
    if(getPlayingJoueur()!=null){id=getPlayingJoueur().getId();}
    r+= g.get("currentPlayer")+" : "+id;r+="\n";
    r+= g.get("partieFinie")+" : "+partieFinie;r+="\n";
    r+= g.get("dateDeCréation")+" : "+dateDeCréation;r+="\n";
    r+= g.get("difficulté")+" : "+difficulté;r+="\n";
    r+= g.get("niveauDeLimitationDesinsectes")+" : "+niveauDeLimitationDesinsectes;r+="\n";
    r+= g.get("nbrDeTour")+" : "+nbrDeTour;r+="\n";
    r+= g.get("tour")+" : "+tour;r+="\n";
    r+= g.get("tableauDesEspecesAutorisée")+" : "+tableau.tableauToString(tableauDesEspecesAutorisée);r+="\n";
    r+= g.get("mapo")+" : "+mapo;r+="\n";
    r+= gj.toString();r+="\n";
    r+= gi.toString();r+="\n";
    r+= ge.toString();r+="\n";
    return r;
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Partie)){return false;}
    return toString().equals(((Partie)(o)).toString());
  }
  public void initialisationElément(int nbrDeJoueur, int nbrDIa, int nbrDeFourmi){
    if(!dejaIni){
      dejaIni=true;
      Main.startCh();
      Main.setMessageChargement(g.getM("initialisationDesJoueurs"));
      boolean b = false;
      nbrDeJoueurDansLaPartie=nbrDIa + nbrDeJoueur;
      if(gej!=null){nbrDeJoueurDansLaPartie=gej.length()-1;}//on a une case vide dans gej.
      if(nbrDeJoueurDansLaPartie>this.getCarte().length()){erreur.erreur("la carte est trop petite pour abriter "+nbrDeJoueur+" joueurs ; taille : "+getGc().length(),true);}
      if(gej==null){
        gj = new GJoueur();
        iniJoueur(nbrDeJoueur,nbrDeFourmi,mapo);
        iniIa(nbrDIa,nbrDeFourmi,mapo);
      }else{
        iniJoueurEtIa(mapo); gej=null;
      }
      //TODO #473 use fully anthillAlreadySet
      boolean anthillAlreadySet = true;
      if(anthillAlreadySet){
        for (Joueur j : gj) {
          for (Creature c : j.getFere().getGc()) {
            if(c instanceof Fourmi || ((Fourmi)(c)).estReine()){
              ((Fourmi)(c)).setCutWings(true);
            }
          }
        }
      }
      if(nbrDeJoueur==0){partieFinie=true;}//on ne déclanche pas de condition de victoire.
      if (nbrDeJoueurDansLaPartie==1){partieFinie=true;}//on ne déclanche pas les condition de victoire si il y a un seul joueur.
      debug.débogage("Création de 10 insectes programmé.");
      Main.setMessageChargement(g.getM("initialisationDesInsectes"));
      if(appartionInsecte){
        gi = new GInsecte(mapo.getNbrDInsecteAuDébut());
      }else{
        gi = new GInsecte();
      }
      Main.endCh("chargementElementsDeLaCarte");
      Main.setMessageChargement(g.getM("chargementDesGraphismes"));
    }
    // Main.getView().iniGuiData();
    setEnCours(true);
  }
  public void initialisationElément(int a, int b){initialisationElément(a,b,1);}
  public void initialisationElément(){ initialisationElément(elément[0],elément[1],elément[2]);}
  public void iniJoueur(int nbrDeJoueur, int nbrDeFourmi, Carte mapo){
    for (int i=0; i<nbrDeJoueur; i++){
      Joueur j = new Joueur(nbrDeFourmi,false,mapo);
      gj.add(j);
      if (mapo.getCasesNuageuses() || mapo.getCasesSombres()){
        j.initialisationCaseNS();
        j.updateCaseSN();
      }
    }
  }
  public void iniIa(int nbrDIa, int nbrDeFourmi, Carte mapo){
    for (int i=0; i<nbrDIa; i++){
      Joueur j = new Joueur(nbrDeFourmi,true,mapo);
      gj.add(j);
    }
  }
  public void iniJoueurEtIa(Carte mapo){
    gj = gej.getGJoueur(mapo);
  }
  public boolean launchGame(){
    if(!getLaunchingFromSave()){
      Main.getPartie().getGj().prendreEnCompteLaDifficulté();//setFood acording to difficutly.
      Main.stopScript();
      if(!Partie.getScript().equals("")){
        ThScript ths = new ThScript(Partie.getScript()+".formiko");
        Main.setScript(ths);
        Main.launchScript();
      }
      //lancement du jeux
      setContinuerLeJeu(true);
      if(Main.getGj().length()==1){setPartieFinie(true);}
      else{setPartieFinie(false);}
      try {
        Main.getMp().play();
      }catch (Exception e) {
        erreur.alerte("Music can't be played next");
      }
    }
    for(tour=1; tour<=nbrDeTour; tour++){
      new Message("\n"+g.get("tour")+" "+ tour +" :");
      //Main.repaint();
      //La joue toutes les ia et les joueurs
      Main.tour();
      testFinDePartie();
      if(Main.getRetournerAuMenu()){
        return true;
      }
    }
    erreur.info(g.get("dernierTourPassé"));
    finDePartie(1);
    return false;
  }
  public boolean jeu(){return launchGame();}
  public void testFinDePartie(){
    if(!getContinuerLeJeu()){
      finDePartie(0); //0=défaite
    }
    if(partieFinie){ return;}//on ne fait pas le test si on est déja après la fin et que le joueur veux continuer a jouer.
    if(getGj().plusQu1Joueur()){
      finDePartie(2);
    }
    //si aucun joueur humain n'as de fourmi :
    if(Main.getGj().filter(j -> j.getFere().getGc().length()!=0 && !j.isAI()).length()==0){
      finDePartie(0); //0=défaite
    }
    //afichage
  }
  public void finDePartie(){ finDePartie(-1);}
  public void finDePartie(int x){ finDePartie(x, true, -1);}
  public void finDePartie(int x, boolean withButton, int nextLevel){
    if (partieFinie) {return;}//on n'affiche pas plusieur fois les info de fin de partie.
    setPartieFinie(true);
    boolean canResumeGame=true;
    erreur.println(getTour()+"/"+getNbrDeTour());
    String victoire = g.get("victoireInconue");
    GJoueur gjSorted = getGj().getGjSorted();
    Joueur winner = null;
    String pseudo = "";
    try {
       winner = gjSorted.getFirst();
       pseudo = winner.getPseudo();
    }catch (NullPointerException e) {}
    if (x==2){
      victoire = g.get("élimination");
    } else if (x==1){
      // en théorie ici winner = joueur qui a le meilleur score.
      victoire =  g.get("temps");
      canResumeGame = false;
    }
    String mess = "";
    if(x!=0){
      //g.getM("le")+" "+g.get("joueur")+" "
      mess=pseudo+" "+g.get("victoirePar")+" " + victoire+" !";
      new Message(mess);
    }else{
      mess=g.getM("toutLesJoueurHumainsEliminés");
      canResumeGame = false;
      new Message(mess);
    }
    // gjSorted.afficheScore();
    Main.getView().endActionGame(withButton, nextLevel, mess, gjSorted, canResumeGame);
    setContinuerLeJeu(false);
    // Main.setRetournerAuMenu(true);//TODO ask & not force.
    if(withButton){
      while(!getContinuerLeJeu() && !Main.getRetournerAuMenu()){//on attend la validation que la partie continue.
        // try {
        //   wait();
        // }catch (Exception e) {}
        Temps.sleep();
      }
    }
  }
  // public void enregistrerLesScores(){gj.enregistrerLesScores();}
  //static
  /**
   * Load the GEspece.
   * @lastEditedVersion 1.33
   */
  public static void iniGe(){
    ge = new GEspece();
  }
  /**
   * Load the default Partie.
   * @lastEditedVersion 1.33
   */
  public static Partie getDefautlPartie(){
    Main.startCh();
    String nomCarte = "miniWorld";
    Carte mapo = new Carte(nomCarte);
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie partie = new Partie(1,100,mapo,1.0);
    partie.setElément(1,5,1);
    partie.setVitesseDeJeu(0.2);
    Main.endCh("chargementParamètrePartieParDéfaut");
    return partie;
  }
  /**
   * Load the default Partie.
   * @lastEditedVersion 1.14
   */
  public static Partie getPartieSave(String nom){
    Main.startCh();
    Partie partie = sauvegarderUnePartie.charger(nom);
    Main.endCh("chargementPartie");
    return partie;
  }
  public static void setPartieTutoInMain(){
    Main.setPartie(getPartieTuto());
    Main.getPartie().iniParametreCarteTuto();
    // Main.launchScript();
  }
  /**
  *{@summary create a new Partie to launch Tuto.}<br>
  *@lastEditedVersion 1.1.
  */
  private static Partie getPartieTuto(){
    Main.startCh();
    String nomCarte = "tuto";
    Carte mapo = new Carte(nomCarte);
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie partie = new Partie(1,100,mapo,1.0);
    partie.setElément(1,0,1);
    partie.setVitesseDeJeu(0.4);
    Main.endCh("chargementParamètrePartieTuto");
    partie.setAppartionInsecte(false);
    partie.setAppartionGraine(false);
    partie.initialisationElément();
    return partie;
  }
  /**
   * {@summary Initializes the tutorial parameters.}<br>
   * @lastEditedVersion 1.1
   */
  private void iniParametreCarteTuto(){
    Fourmiliere fere = getGj().getFirst().getFere();
    CCase ccIni = getGc().getCCase(0,1);
    fere.setCc(ccIni);
    fere.getGc().getFirst().setCCase(ccIni);
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(1,1),0,100,0);
    i.setGivenFood(200);
    i.setIsDead(false);
    i.setType(8);
    getGi().add(i);
    // ths.start();
  }
  /**
  *{@summary change the value of the playing ant.}<br>
  *We need to repaint the information about this playingAnt.<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.33
  */
  public boolean setPlayingAnt(Fourmi f){
    if (!Main.getView().getActionGameOn()) {return false;}
    playingAnt=f;
    try { //udpate playingJoueur if ant is not null.
      Joueur.setPlayingJoueur(f.getFere().getJoueur());
    }catch (NullPointerException e) {}
    return true;
  }
  /**
  *{@summary change the value of the playing ant with and id.}<br>
  *This action can only be run if action game is on.<br>
  *@return Return true if it work well. (Nothing goes wrong.)
  *@lastEditedVersion 1.33
  */
  private boolean setPlayingAnt(int id){
    try {
      return setPlayingAnt(triche.getFourmiParId(id+""));
    }catch (Exception e) {
      erreur.erreur("the ant "+id+" can't be used to play.");
      return false;
    }
  }
}
