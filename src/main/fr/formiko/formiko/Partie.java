package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.Message;
import fr.formiko.usuel.image.*;
import java.awt.Image;
import fr.formiko.graphisme.GEtiquetteJoueur;
import fr.formiko.graphisme.EtiquetteJoueur;
import fr.formiko.usuel.Temps;
import java.io.Serializable;
import fr.formiko.usuel.tableau;

public class Partie implements Serializable{
  private static final long serialVersionUID = 1L;
  private GInsecte gi;
  private GJoueur gj;
  private static GEspece ge;
  private Carte mapo;
  private int nbrDeTour, tour;
  private int niveauDeLimitationDesinsectes = 4;
  private byte difficulté = 0; // 0 moyen. -1 facile, -2 très facile, 1 difficle, 2 très difficle, 3 ultra difficle.
  //private byte difficulté = 3; // 1 facile, 2 moyen, 3 difficile.
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

  // CONSTRUCTEUR ---------------------------------------------------------------
  // nombre de joueur, nombre d'ia, abondance des insectes, niveau de difficulté des ia, les especes autorisé, le nombre de tour.
  public Partie(int difficulté, int nbrDeTour, Carte mapo, double vitesseDeJeu){
    tour=0; partieFinie=false; enCours=false;
    dateDeCréation = LocalDateTime.now();
    this.mapo=mapo;
    if (nbrDeTour==-1){ nbrDeTour = 2147483647;}
    this.nbrDeTour=nbrDeTour;
    this.difficulté = str.iToBy(difficulté);
    this.vitesseDeJeu=vitesseDeJeu;
    tableauDesEspecesAutorisée = new int [2];
    tableauDesEspecesAutorisée[0]=0;
    tableauDesEspecesAutorisée[1]=3;
    //a ce stade, il manque encore gi et gj.
    appartionInsecte=true;
    appartionGraine=true;
  }
  /**
  *{@summary Constructor with only minimal initialization.<br>}
  *@version 1.2
  */
  public Partie(){
    this(0,0,new Carte(new GCase(1,1)),1.0);//initialisation "null"
    gj = new GJoueur();
    gi = new GInsecte();
  }
  // GET SET --------------------------------------------------------------------
  public GInsecte getGi(){ return gi;}
  public void setGi(GInsecte g){gi=g;}
  public GJoueur getGj(){ return gj;}
  public void setGj(GJoueur g){gj=g;}
  public GCase getGc(){return mapo.getGc();}
  public static GEspece getGe(){return ge;}
  public int getTour(){ return tour;}
  public void setTour(int x){ tour=x;}
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
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String r="";
    r+= g.get("continerLeJeu")+" : "+continerLeJeu;r+="\n";
    r+= g.get("appartionInsecte")+" : "+appartionInsecte;r+="\n";
    r+= g.get("appartionGraine")+" : "+appartionGraine;r+="\n";
    r+= g.get("vitesseDeJeu")+" : "+vitesseDeJeu;r+="\n";
    r+= g.get("enCours")+" : "+enCours;r+="\n";
    r+= g.get("nbrDeJoueurDansLaPartie")+" : "+nbrDeJoueurDansLaPartie;r+="\n";
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
  public void initialisationElément(int nbrDeJoueur, int nbrDIa, int nbrDeFourmi){
    Main.débutCh();
    Main.setMessageChargement(g.getM("initialisationDesJoueurs"));
    boolean b = false;
    nbrDeJoueurDansLaPartie=nbrDIa + nbrDeJoueur;
    if(gej!=null){nbrDeJoueurDansLaPartie=gej.length()-1;}//on a une case vide dans gej.
    if(nbrDeJoueurDansLaPartie>this.getCarte().length()){erreur.erreur("la carte est trop petite pour abriter "+nbrDeJoueur+" joueurs ; taille : "+getGc().length(),"Fourmiliere.Fourmiliere",true);}
    if(gej==null){
      gj = new GJoueur();
      iniJoueur(nbrDeJoueur,nbrDeFourmi,mapo);
      iniIa(nbrDIa,nbrDeFourmi,mapo);
    }else{
      iniJoueurEtIa(mapo); gej=null;
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
    Main.finCh("chargementElementsDeLaCarte");
    Main.setMessageChargement(g.getM("chargementDesGraphismes"));
    Main.débutCh();
    initialiserGraphismePartie();
    Main.finCh("chargementGraphismes");
    setEnCours(true);
  }
  public void initialisationElément(int a, int b){initialisationElément(a,b,1);}
  public void initialisationElément(){ initialisationElément(elément[0],elément[1],elément[2]);}
  public void iniJoueur(int nbrDeJoueur, int nbrDeFourmi, Carte mapo){
    for (int i=0; i<nbrDeJoueur; i++){
      Joueur j = new Joueur(nbrDeFourmi,false,mapo);
      gj.ajouter(j);
      if (mapo.getCasesNuageuses() || mapo.getCasesSombres()){
        j.initialisationCaseNS();
        j.actualiserCaseSN();
      }
    }
  }
  public void iniIa(int nbrDIa, int nbrDeFourmi, Carte mapo){
    for (int i=0; i<nbrDIa; i++){
      Joueur j = new Joueur(nbrDeFourmi,true,mapo);
      gj.ajouter(j);
    }
  }
  public void iniJoueurEtIa(Carte mapo){
    gj = gej.getGJoueur(mapo);
  }
  public boolean jeu(){
    //lancement du jeux
    setContinuerLeJeu(true);
    if(Main.getGj().length()==1){setPartieFinie(true);}
    else{setPartieFinie(false);}
    for(tour=1; tour<=nbrDeTour; tour++){
      new Message("\n"+g.get("tour")+ tour +" :");
      Main.repaint();
      //La joue toutes les ia et les joueurs
      Main.tour();
      testFinDePartie();
      if(Main.getRetournerAuMenu()){return true;}
    }
    System.out.println(g.get("dernierTourPassé"));
    finDePartie(1);
    return false;
  }
  public void testFinDePartie(){
    if(partieFinie){ return;}//on ne fait pas le test si on est déja après la fin et que le joueur veux continuer a jouer.
    if(getGj().plusQu1Joueur()){
      finDePartie(2);
    }
    if(Main.getGj().getJoueurHumain().getPlusDeFourmi()){//si aucun joueur humain n'as de fourmi :
      finDePartie(0); //0=défaite
    }
    //afichage
  }
  public void finDePartie(){ finDePartie(-1);}
  public void finDePartie(int x){
    if (partieFinie) {return;}//on n'affiche pas plusieur fois les info de fin de partie.
    setPartieFinie(true);
    String victoire = g.get("victoireInconue");
    GJoueur gjOrdonné = getGj().getGjOrdonné();
    Joueur gagnant = gjOrdonné.getDébut().getContenu();
    if (x==2){
      victoire = g.get("élimination");
    } else if (x==1){
      // en théorie ici gagnant = joueur qui a le meilleur score.
      victoire =  g.get("temps");
    }
    String mess = "";
    if(x!=0){
      //g.getM("le")+" "+g.get("joueur")+" "
      mess=gagnant.getPseudo()+" "+g.get("victoirePar")+" " + victoire+" !";
      new Message(mess);
    }else{
      mess=g.getM("toutLesJoueurHumainsEliminés");
      new Message(mess);
    }
    gjOrdonné.afficheScore();
    Main.getPj().addPfp(mess,gjOrdonné);
    setContinuerLeJeu(false);
    while(!getContinuerLeJeu() && !Main.getRetournerAuMenu()){//on attend la validation que la partie continue.
      Temps.pause(10);
    }
  }
  public void initialiserGraphismePartie(){
    initialiserFX(gj.length());
    Main.initialiserElémentTournés(); //une partie des graphismes tourné est probablement déja faite.
  }

  public void initialiserFX(int nbrDeJoueur){
    //nouvelle méthode
    Pixel pi = new Pixel(255,105,0,255);
    for (int i=1;i<nbrDeJoueur+1 ;i++ ) {
      Pheromone ph;
      try {
        ph = gj.getJoueurParId(i).getFere().getGc().getDébut().getContenu().getPh();
      }catch (Exception e) {
        erreur.erreur("Les Pheromones de la fourmi n'ont pas put etre récupérer, il sont donc choisi aléatoirement.");
        ph = new Pheromone(127,127,127); // blanc sinon.
      }
      int a = ph.getRc(); int b=ph.getVc(); int c=ph.getBc();
      Img imgTemp = new Img("F.png");
      Pixel pi2 = new Pixel(a,b,c,255);
      imgTemp.changerPixel(pi,pi2);
      //imgTemp.ombrer(pi2); // met de l'ombre sur le pixel pi2. (en théorie)
      imgTemp.sauvegarde("temporaire/F"+i+".png");
    }
  }
  public void enregistrerLesScores(){
    gj.enregistrerLesScores();
  }
  public static void iniGe(){
    ge = new GEspece();
  }
}
