package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.views.gui2d.readG;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.CMessage;
import fr.formiko.usuel.listes.GMessage;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.read;
import fr.formiko.usuel.tableau;

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
  // CONSTRUCTEUR -----------------------------------------------------------------
  //Principal
  public Joueur (Fourmiliere fere, String pseudo, boolean ia){
    id = i; i++; gm = new GMessage();
    this.fere = fere; this.pseudo = pseudo; this.ia = ia;
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
      try {
        pseudo = Main.getOp().getPseudo();
      }catch (Exception e) {}
    }
  }
  // GET SET -----------------------------------------------------------------------
  public int getId(){return id;}
  public static int getI(){return i;}
  public String getPseudo(){return pseudo;}
  public void setPseudo(String s){pseudo = s;}
  public Fourmiliere getFourmiliere(){return fere;}
  public Fourmiliere getFere(){ return getFourmiliere();}
  public void setFourmiliere(Fourmiliere nouvelleFourmiliere) {
    fere = nouvelleFourmiliere;
    nouvelleFourmiliere.setJoueur(this);
  }
  public boolean getIa(){ return ia;}
  public void setIa(boolean b){ia = b; }
  public void addMessage(Message m){ gm.add(m);}
  public GMessage getGm(){ return gm;}
  public boolean getCaseSombre(int x, int y){ try {return caseSombre[x][y];}catch (Exception e) {return false;}}
  public void setCaseSombre(int x, int y, boolean b){ caseSombre[x][y]=b;}
  public boolean isCaseSombre(CCase cc){return getCaseSombre(cc.getContenu().getPoint().getX(),cc.getContenu().getPoint().getY());}
  public boolean getCaseNuageuse(int x, int y){ try {return caseNuageuse[x][y];}catch (Exception e) {return false;}}
  public void setCaseNuageuse(int x, int y, boolean b){ caseNuageuse[x][y]=b;}
  public boolean isCaseNuageuse(CCase cc){return getCaseNuageuse(cc.getContenu().getPoint().getX(),cc.getContenu().getPoint().getY());}
  public void setPheromone(Pheromone ph){fere.getGc().setPheromone(ph);}
  public int getScore(){return fere.getScore();}
  public static void ini(){i=1;}
// Fonctions propre -----------------------------------------------------------
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
    if(!ia){
      Message m = new Message(pseudo+", "+g.get("débutTourJoueur"),id,6);
    }else{
      Message m = new Message(pseudo+" "+g.get("débutTourIa"),id,6);
    }
    if(Main.getGj().getJoueurHumain().length()>1 && !ia){
      Main.setPlayingAnt(null);
      if(Main.getRetournerAuMenu()){return;}
      Main.getView().popUpMessage(pseudo+" "+g.get("débutTourJoueur")+".");
    }
    fere.jouer();
  }
  public void afficheScore(){
    System.out.println(pseudo +" : "+getScore());
  }
  public void initialisationCaseNS(){
    int x = Main.getGc().getNbrX();
    int y = Main.getGc().getNbrY();
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
  public void actualiserCaseSN(){
    int x = Main.getGc().getNbrX();
    int y = Main.getGc().getNbrY();
    for (int i=0;i<x ;i++ ) {
      for (int j=0;j<y ;j++ ) {
        caseSombre[i][j]=true;
      }
    }
    fere.getGc().actualiserCaseSN();
    //affichecaseSN();
  }
  public void affichecaseSN(){
    System.out.println("Les tableaux s'affiche en inversé par rapport a la carte !");
    System.out.println("caseNuageuse");
    tableau.afficher(caseNuageuse);
    System.out.println("caseSombre");
    tableau.afficher(caseSombre);
  }
  public void prendreEnCompteLaDifficulté(){
    try {
      int x = (Main.getDifficulté()+3)*10;
      if(!ia){ x= 60-x;}
      this.fere.getGc().getReine().setNourriture(math.max(x,10));//10 minimum. (60 max en théorie.)
    }catch (Exception e) {erreur.alerte("Impossible de prendre en compte la difficulté");}
  }
  public String scoreToString(){
    return getPseudo()+" : "+getScore();
  }
  public void setAction0AndEndTurn(){
    fere.getGc().setAction0AndEndTurn();
  }
}
