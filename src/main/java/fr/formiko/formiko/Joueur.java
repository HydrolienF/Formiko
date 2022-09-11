package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.read;
import fr.formiko.usual.tableau;

import java.io.Serializable;

public class Joueur implements Serializable{
  private static int i; // le i augmente a chaque fois qu'on créer un joueur.
  private final int id;
  private Fourmiliere fere;
  private String pseudo;
  private boolean ia;
  private static String list [] = {"Sulfure", "Eole", "Hélios", "Léa", "Poumpoum", "Barabadur", "Diode", "Oxygène", "Toto", "Javet","327a","Chli-pou-ni","Bel-o-kiu-kiuni","Ha-yekte-douni","Sinjoro pistolo-terpomo","fraŭlino Lupo","Lignolaboristo","Eraro7","Hidrologo","Mago Vulpo","Markso","Luciano","gnomo","koboldo"};
  private GMessage gm;
  private boolean caseNuageuse [][];
  private boolean caseSombre [][];
  private boolean isTurnEnded;
  private static Joueur playingJoueur;
  private boolean haveAbandon;

  // CONSTRUCTORS ----------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  */
  public Joueur (Fourmiliere fere, String pseudo, boolean ia){
    id = i; i++; gm = new GMessage();
    this.fere = fere; this.pseudo = pseudo; this.ia = ia;
    isTurnEnded=false;
    haveAbandon=false;
  }
  //Auxiliaire
  public Joueur (int nbrDeFourmi, boolean ia, String pseudo, Carte mapo){
    this(null,pseudo,ia);
    this.fere = new Fourmiliere (nbrDeFourmi,this, mapo);
  }
  public Joueur (int nbrDeFourmi, boolean ia, Carte mapo){
    this(nbrDeFourmi,ia,"",mapo); // On crée un joueur au pseudo vide puis on le rempli si c'est une ia.
    if (ia){
      pseudo = get1Pseudo();
    }else{
      pseudo = Main.getFop().getString("pseudo");
    }
  }
  // GET SET ----------------------------------------------------------------------
  public int getId(){return id;}
  public static int getI(){return i;}
  public String getPseudo(){return pseudo;}
  public void setPseudo(String s){pseudo = s;}
  public Fourmiliere getFourmiliere(){return fere;}
  public Fourmiliere getFere(){ return getFourmiliere();}
  /**
  *{@summary Update Anthill &#38; Player of the Anthill.}<br>
  */
  public void setFourmiliere(Fourmiliere nouvelleFourmiliere) {
    fere = nouvelleFourmiliere;
    nouvelleFourmiliere.setJoueur(this);
  }
  public boolean getIa(){return isAI();}
  public boolean isIa(){return isAI();}
  public boolean isAI(){return ia;}
  public void setIa(boolean b){ia = b; }
  public void addMessage(Message m){ gm.addHead(m);}
  public GMessage getGm(){ return gm;}
  public boolean getSquareSombre(int x, int y){ try {return caseSombre[x][y];}catch (Exception e) {return false;}}
  public void setSquareSombre(int x, int y, boolean b){ caseSombre[x][y]=b;}
  public boolean isSquareSombre(CSquare cc){return getSquareSombre(cc.getContent().getPoint().getX(),cc.getContent().getPoint().getY());}
  public boolean getSquareNuageuse(int x, int y){ try {return caseNuageuse[x][y];}catch (Exception e) {return false;}}
  public void setSquareNuageuse(int x, int y, boolean b){ caseNuageuse[x][y]=b;}
  public boolean isSquareNuageuse(CSquare cc){return getSquareNuageuse(cc.getContent().getPoint().getX(),cc.getContent().getPoint().getY());}
  /**
  *{@summary Set Pheromone for all the Ants of the player.}
  *@lastEditedVersion 2.1
  */
  public void setPheromone(Pheromone ph){
    for (Creature c : fere.getGc()) {
      c.setPheromone(ph);
    }
  }
  public int getScore(){return fere.getScore();}
  public static void ini(){i=1;}
  public boolean getIsTurnEnded(){return isTurnEnded;}
  /**
  *{@summary Setter that notify playing ant in case it was waiting for an action to be choose.}
  *@lastEditedVersion 2.28
  */
  public void setIsTurnEnded(boolean b){
    isTurnEnded=b;
    if(Main.getPlayingAnt()!=null){
      synchronized (Main.getPlayingAnt()) {
        Main.getPlayingAnt().notifyAll();
      }
    }
  }
  public static Joueur getPlayingJoueur(){
    if(playingJoueur==null && Main.getPartie().getPlayingAnt()!=null){
      return Main.getPartie().getPlayingAnt().getFere().getJoueur();
    }
    return playingJoueur;
  }
  public static void setPlayingJoueur(Joueur j){playingJoueur=j;}
  public Espece getEspece(){return getFere().getEspece();}
  public boolean haveLost(){
    if(getFere()==null || getFere().getGc()==null){return false;}//something haven't been initialise
    return (haveAbandon || getFere().getGc().isEmpty());
  }
  public void abandon(){haveAbandon=true;}
// FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String s = (ia) ? g.get("laIA") : g.get("laJoueurHumain");
    s = pseudo + " est " + s + " " + id;
    return s + "\n" + fere.toString();
    //GMessage, casesSombres et casesNuageuses ne sont pas afficher pour cause de "pas tres utile"
  }
  public static String get1Pseudo(){
    String sr;
    if (list.length>0){
      int x = allea.getAlléa(list.length);
      sr = list[x];
      list = tableau.retirerX(list, list[x]); // Comme ca on aura pas de doublon.
    }else {
      sr = "X";
    }
    return sr;
  }
  public void changerDePeudo(){
    setPseudo(read.getString("pseudo",pseudo + i,"Rentrez votre nouveau pseudo : "));
  }
  public void jouer(){
    setPlayingJoueur(this);
    if(!ia){
      Message m = new Message(pseudo+", "+g.get("débutTourJoueur"),id,6);
    }else{
      Message m = new Message(pseudo+" "+g.get("débutTourIa"),id,6);
    }
    if(Main.getGj().filter(j -> !j.haveLost() && !j.isAI()).length()>1 && !ia){
      Main.setPlayingAnt(null);
      setPlayingJoueur(null);
      if(Main.getRetournerAuMenu()){return;}
      // if(Main.getView().getActionGameOn()){
      if(!haveAbandon){
        Main.getView().popUpMessage(pseudo+" "+g.get("débutTourJoueur")+".");
      }
      // }
    }
    if(haveAbandon){
      System.out.println("end turn because have lost");
      setIsTurnEnded(true);
    }
    fere.jouer();
    setPlayingJoueur(null);
  }
  public void afficheScore(){
    erreur.println(pseudo +" : "+getScore());
  }
  public void initialisationSquareNS(){
    int x = Main.getGc().getWidth();
    int y = Main.getGc().getHeight();
    caseNuageuse = new boolean[x][y];
    caseSombre = new boolean[x][y];
    for (int i=0;i<x ;i++ ) {
      for (int j=0;j<y ;j++ ) {
        caseNuageuse[i][j]=true;
      }
    }
    for (int i=0;i<x ;i++ ) {
      for (int j=0;j<y ;j++ ) {
        caseSombre[i][j]=true;
      }
    }
  }
  public void updateSquareSN(){
    int x = Main.getGc().getWidth();
    int y = Main.getGc().getHeight();
    for (int i=0;i<x ;i++ ) {
      for (int j=0;j<y ;j++ ) {
        caseSombre[i][j]=true;
      }
    }
    fere.getGc().updateSquareSN();
    //affichecaseSN();
  }
  public void affichecaseSN(){
    erreur.println("Les tableaux s'affiche en inversé par rapport a la carte !");
    erreur.println("caseNuageuse");
    tableau.afficher(caseNuageuse);
    erreur.println("caseSombre");
    tableau.afficher(caseSombre);
  }
  public void prendreEnCompteLaDifficulté(){
    try {
      int x = (Main.getDifficulté()+3)*10;
      if(!ia){ x= 60-x;}
      x+=20;
      this.fere.getGc().getReine().setFood(math.max(x,20));//20 minimum. (60 max en théorie.)
    }catch (Exception e) {erreur.alerte("Impossible de prendre en compte la difficulté");}
  }
  public String scoreToString(){
    return getPseudo()+" : "+getScore();
  }
  public void setAction0AndEndTurn(){
    fere.getGc().setAction0AndEndTurn();
  }
}
