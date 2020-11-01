package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.Font;
import java.io.File;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;
import java.io.Serializable;
import fr.formiko.usuel.conversiondetype.str;

public class Options implements Serializable{
  private byte langue=0; // 0=eo; 1=fr; 2=en;
  private byte tailleBoutonZoom=0;
  private byte tailleBoutonAction=0;
  private byte tailleBoutonTX=0;
  private boolean mouvementRapide;
  private boolean déplacementInstantané;
  private boolean elementSurCarteOrientéAprèsDéplacement;
  private byte nbrMessageAfficher;// =10;
  private boolean dessinerGrille;//=false;
  private boolean forcerQuitter;// = false;
  private byte bordureBouton;//=5; // en pixel.
  private boolean dessinerIcone;
  private int taillePolice1;
  private int taillePolice2;
  private String police;
  private Font font1;// =new Font("Arial", Font.BOLD, 20);
  private Font font2;// =new Font("Arial", Font.BOLD, 60);
  private String pseudo;
  private boolean pleinEcran;
  private boolean chargementPendantLesMenu;
  private boolean garderLesGraphismesTourné;
  private boolean attendreAprèsLeChargementDeLaCarte;
  private boolean affLesEtapesDeRésolution;
  private boolean affLesPerformances;
  private boolean affG;
  private int dimLigne;
  private byte positionCase;
  private boolean bMusique;
  private boolean bSon;
  private byte volMusique;
  private byte volSon;
  private byte tailleRealiste;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Options(){}
  // GET SET --------------------------------------------------------------------
  public byte getLangue(){return langue;}
  public void setLangue(byte x){langue=x;} public void setLangue(int x){setLangue(str.iToBy(x));}
  public int getTailleBoutonZoom(){ return tailleBouton(tailleBoutonZoom);}
  public void setTailleBoutonZoom(byte x){ tailleBoutonZoom=x;}
  public int getTailleBoutonAction(){ return tailleBouton(tailleBoutonAction);}
  public void setTailleBoutonAction(byte x){ tailleBoutonAction=x;}
  public int getTailleBoutonTX(){ return tailleBouton(tailleBoutonTX);}
  public void setTailleBoutonTX(byte x){ tailleBoutonTX=x;}
  public boolean getMouvementRapide(){ return mouvementRapide;}
  public void setMouvementRapide(boolean b){ mouvementRapide = b;}
  public boolean getDéplacementInstantané(){return déplacementInstantané;}
  public void setDéplacementInstantané(boolean b){déplacementInstantané=b;}
  public boolean getElementSurCarteOrientéAprèsDéplacement(){ return elementSurCarteOrientéAprèsDéplacement;}
  public void setElementSurCarteOrientéAprèsDéplacement(boolean b){elementSurCarteOrientéAprèsDéplacement=b;}
  public byte getNbrMessageAfficher(){ return nbrMessageAfficher;}
  public void setNbrMessageAfficher(int x){ nbrMessageAfficher=str.iToBy(x);}
  public boolean getDessinerGrille(){ return dessinerGrille;}
  public void setDessinerGrille(boolean b){dessinerGrille=b; }
  public boolean getForcerQuitter(){ return forcerQuitter;}
  public void setForcerQuitter(boolean b){ forcerQuitter=b;}
  public byte getBordureBouton(){ return bordureBouton;}
  public void setBordureBouton(int x){bordureBouton=str.iToBy(x);}
  public boolean getDessinerIcone(){ return dessinerIcone;}
  public void setDessinerIcone(boolean b){dessinerIcone=b;}
  public Font getFont1(){ return font1;}
  public Font getFont1(Double d){ Font fTemp = new Font(getPolice(),Font.PLAIN,(int)(getTaillePolice1()*d)); return fTemp;}
  public void setFont1(Font f){ font1=f;}
  public Font getFont2(){ return font2;}
  public void setFont2(Font f){ font2=f;}
  public int getTaillePolice1(){ return taillePolice1;}
  public void setTaillePolice1(int x){ taillePolice1=x;}
  public int getTaillePolice2(){ return taillePolice2;}
  public void setTaillePolice2(int x){ taillePolice2=x;}
  public String getPolice(){ return police;}
  public void setPolice(String s){police=s;}
  public String getPseudo(){ return pseudo;}
  public void setPseudo(String s){pseudo=s;}
  public boolean getPleinEcran(){ return pleinEcran;}
  public void setPleinEcran(boolean b){ pleinEcran=b;}
  public boolean getChargementPendantLesMenu(){ return chargementPendantLesMenu;}
  public void setChargementPendantLesMenu(boolean b){chargementPendantLesMenu=b;}
  public boolean getGarderLesGraphismesTourné(){ return garderLesGraphismesTourné;}
  public void setGarderLesGraphismesTourné(boolean b){garderLesGraphismesTourné=b;}
  public boolean getAttendreAprèsLeChargementDeLaCarte(){ return attendreAprèsLeChargementDeLaCarte;}
  public void setAttendreAprèsLeChargementDeLaCarte(boolean b){attendreAprèsLeChargementDeLaCarte=b;}
  public boolean getAffLesEtapesDeRésolution(){return affLesEtapesDeRésolution;}
  public void setAffLesEtapesDeRésolution(boolean b){affLesEtapesDeRésolution=b;}
  public boolean getAffLesPerformances(){return affLesPerformances;}
  public void setAffLesPerformances(boolean b){affLesPerformances=b;}
  public boolean getAffG(){return affG;}
  public void setAffG(boolean b){affG=b;}
  public int getDimLigne(){ return dimLigne;}
  public void setDimLigne(int x){dimLigne=x;}
  public byte getPositionCase(){return positionCase;}
  public void setPositionCase(byte x){positionCase=x;}
  public boolean getBMusique(){return bMusique;}
  public void setBMusique(boolean b){bMusique=b;}
  public boolean getBSon(){return bSon;}
  public void setBSon(boolean b){bSon =b;}
  public byte getVolMusique(){return volMusique;}
  public void setVolMusique(byte x){volMusique=x;}
  public byte getVolSon(){return volSon;}
  public void setVolSon(byte x){volSon=x;}
  public byte getTailleRealiste(){return tailleRealiste;}
  public void setTailleRealiste(byte x){tailleRealiste=x;}public void setTailleRealiste(int x){tailleRealiste=str.iToBy(x);}
  // Fonctions propre -----------------------------------------------------------
  private int tailleBouton(byte x){
    if(x>2 && x%20==0){return x;}
    if(x==0){ return 80;}
    if(x==-1){ return 40;}
    if(x==1){ return 160;}
    if(x==-2){ return 20;}
    if(x==2){ return 320;}
    erreur.erreur("La taille des boutons spécifiée n'est pas correcte.","La taille moyenne a été choisie par défaut");
    return 80;
  }
  public void sauvegarder(){
    //on s'assure que le fichier n'existe plus pour éviter d'avoir a l'écraser plus tard.
    File f = new File("data/Options.txt");
    if (f.exists()){ // si le fichier d'options existe.
      f.delete();
    }
    //on remplie toute les infos qu'on veut sauvegarder dans un gs.
    GString gs = new GString();
    gs.add("version compatible:"+Main.getVersionActuelle());
    gs.add("langue:"+getLangue());
    gs.ajouter("taille bouton zoom:"+getTailleBoutonZoom());
    gs.ajouter("taille bouton action:"+getTailleBoutonAction());
    gs.ajouter("taille bouton tint:"+getTailleBoutonTX());
    gs.ajouter("mouvementRapide:"+getMouvementRapide());
    gs.ajouter("déplacementInstantané:"+getDéplacementInstantané());
    gs.ajouter("elementSurCarteOrientéAprèsDéplacement:"+getElementSurCarteOrientéAprèsDéplacement());
    gs.ajouter("nbrMessageAfficher:"+getNbrMessageAfficher());
    gs.ajouter("dessinerGrille:"+getDessinerGrille());
    gs.ajouter("forcerQuitter:"+getForcerQuitter());
    gs.ajouter("bordureBouton:"+getBordureBouton());
    gs.ajouter("dessinerIcone:"+getDessinerIcone());
    gs.ajouter("taillePolice1:"+getTaillePolice1());
    gs.ajouter("taillePolice2:"+getTaillePolice2());
    gs.ajouter("police:"+getPolice());
    gs.ajouter("pseudo:"+getPseudo());
    gs.ajouter("pleinEcran:"+getPleinEcran());
    gs.ajouter("chargement pendant les menu:"+getChargementPendantLesMenu());
    gs.ajouter("garder les graphsime tourné:"+getGarderLesGraphismesTourné());
    gs.ajouter("attendre après le chargement de la carte:"+getAttendreAprèsLeChargementDeLaCarte());
    gs.ajouter("afficher les étapes de résolution (débogage):"+getAffLesEtapesDeRésolution());
    gs.ajouter("afficher les performances(débogage):"+getAffLesPerformances());
    gs.ajouter("afficher les étape graphiques(débogage):"+getAffG());
    gs.ajouter("dimention des lignes de la carte:"+getDimLigne());
    gs.ajouter("positionCase:"+getPositionCase());
    gs.ajouter("musique:"+getBMusique());
    gs.ajouter("son:"+getBSon());
    gs.add("volMusique:"+getVolMusique());
    gs.add("volSon:"+getVolSon());
    gs.add("tailleRealiste:"+getTailleRealiste());
    //on rempli le fichier avec le GString.
    ecrireUnFichier.ecrireUnFichier(gs,"data/Options.txt");
  }
}
