package fr.formiko.formiko;
import fr.formiko.graphisme.*;import fr.formiko.usuel.*;import fr.formiko.usuel.son.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.math;
import fr.formiko.usuel.image.*;
import fr.formiko.usuel.liste.*;
import fr.formiko.usuel.conversiondetype.str;
import java.time.LocalDateTime;
import java.util.Map;
import java.awt.Font;
// ArrayList<?> list; permet de déclarrer une liste de tout type. Sinon mettre l'objet ou Integer a la place. on peu aussi mettre Object pour spécifier que ce sera une liste d'objet.
// diff fichier1 fichier2 permet de comparer de façon très complète, les différences entre des fichiers. On peu comparer tout le contenu de formiko avec /diff -r Formiko14 Formiko15


public class Main {
  private static int versionActuelle = 100;
  private static Fenetre f;
  private static Options op;
  private static Chrono ch;
  private static long lon;
  private static long lonTotal;
  private static long tempsDeDébutDeJeu;
  private static Partie pa;
  private static byte niveauDeDétailDeLAffichage=3;
  private static GEspece ge;
  private static Map<String, String> map; // map.get(clé) permet d'obtenir le texte associé.
  private static Pixel pi;
  private static Map<String, Integer> key;
  private static boolean ecouteClavier;
  private static int avancementChargement;
  private static boolean affGraine=true;//tant que les espece granivore ne sont pas pleinement opérationelle.
  private static Temps tem;
  private static ThTriche trich; //écoute de commande triche dans le terminal.
  private static ThGraphisme tg;//actualise la fenetre tt avec 20 seconde de pause entre chaque actualisation.
  private static boolean retournerAuMenu;
  private static Os os;
  private static boolean tuto=false;
  private static ThScript ths;
  private static ThMusique thm;
  private static boolean premierePartie=false;

  public static void main (String [] args){
    //trad.copieTrads();quitter();
    debug.setAffLesEtapesDeRésolution(false);
    debug.setAffLesPerformances(false);
    debug.setAffG(false);
    //menuPrincipal();
    ch = new Chrono();
    while(args[0].substring(0,1).eqals("-")){//si il y a des options a "-"
      if(args[0].equals("-d")){
        debug.setAffLesEtapesDeRésolution(true);
      }else{
        erreur.alerte("une options dans la ligne de commande n'as pas été reconnue");
      }
      args = tableau.retirer(args, 0);
    }
    if(args.length>0){
      if(args[0].equals("trad")){
        debug.setAffLesPerformances(true);
        débutCh();
        chargerLesTraductions.iniTLangue();
        chargerLesTraductions.créerLesFichiers();
        finCh("créerLesFichiers");débutCh();
        map = chargerLesTraductions.chargerLesTraductions(1);//chargement des langues.
        finCh("chargerLesTraductions");débutCh();
        trad.copieTrads();
        finCh("copieTrads");débutCh();
        chargerLesTraductions.affPourcentageTraduit();
        finCh("affPourcentageTraduit");débutCh();
        chargerLesTraductions.ajouterTradAuto();
        finCh("ajouterTradAuto");débutCh();
        chargerLesTraductions.affPourcentageTraduit();
        finCh("affPourcentageTraduit");
      }else if(args[0].equals("son")){
        System.out.println(Musique.getMusiqueAlleatoire());
      }else if(args[0].equals("op")){
        chargerLesTraductions.iniTLangue();
        op = chargerLesOptions.chargerLesOptions();
        op.sauvegarder();
      }else if(args[0].equals("supprimer")){
        //diff.nbrDeLigneDiff("usuel/GString.java","../Formiko108/usuel/GString.java");
        if(args.length == 4){
          String s = args[1];
          //c'est pas nésséssaire sur le terminal linux mais au cas ou
          if(s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'){
            s = s.substring(1,s.length()-1);
          }
          String f = args[2];
          byte x = str.sToBy(args[3]);
          modificationDeFichier.retirerLesLignesR(s,f,x);//3== ca doit etre une ligne complète.
        }else{
          erreur.alerte("arguments de supprimer incorecte");
        }
      }else{
        erreur.erreur("Votre options a "+(args.length+1)+" agruments n'as pas été reconnue");
      }
    }else{
      main2();
    }
  }
  public static void main2(){
    //premierePartie=true;
    if(premierePartie){tuto=true;}
    avancementChargement=-1;
    initialisation();
    avancementChargement=0;
    ecouteClavier=true;

    débutCh();
    Carte mapo = new Carte(new GCase(1,1));
    pa = new Partie(0,0,mapo,1.0); //nouvelle partie vide.
    finCh("chargementPartieEtCarteBlanche");débutCh();
    f = new Fenetre(); //f.requestFocus(); doit permettre a la fenetre d'écouter les touches.
    finCh("chargementFenetre");débutCh();
    ini.initialiserToutLesPaneauxVide();
    finCh("chargementPanneauVide");débutCh();
    if(getChargementPendantLesMenu()){chargementDesGraphismesAutonomes();}
    else{ini.initialiserPanneauJeuEtDépendance();ini.initialiserAutreELémentTournés();}
    finCh("chargementDesGraphismesAutonomes");
    attenteDeLancementDePartie();
    if(retournerAuMenu){
      retourAuMenu();
    }
  }
  public static void attenteDeLancementDePartie(){
    //menu
    débutCh();
    getPm().construitPanneauMenu(3);
    finCh("chargementPanneauMenu");
    //attente
    debug.débogage("attente de lancement de la partie");
    Main.repaint();
    boolean b=false;
    while(!b && !premierePartie){Temps.pause(10);b=getPm().getLancer();}
    pa = getPm().getPartie();
    debug.débogage("lancementNouvellePartie");
    lancementNouvellePartie(pa);
  }
  public static void lancementNouvellePartie(Partie p){ //Nouveau système de lancement de partie :
    débutCh();
    getPp().removePm();//on retire le menu
    getPj().addPch();//on met le panneau de chargement au 1a plan.
    finCh("chargementPanneauChargementEtSuppressionMenu");//débutCh();
    if(premierePartie){tuto=true;}
    if(tuto){pa=getPartieTuto();}
    else if(p==null){pa=getPartieParDéfaut();}
    if(Main.getDimY()!=1080 || getPartie().getGc().getNbrY()!=9){
      getPj().dézoomer((byte)2);//on met la carte a la taille la plus grande possible pour qu'on voit tout.
    }
    //finCh("chargementDézoom");
    pa.setEnCours(true); //lance l'affichage de la Partie.
    //débutCh();
    pa.initialisationElément(); // pour l'instant ce bout de code ne marche pas ayeur qu'ici.
    débutCh();
    Main.getPb().addPz();
    finCh("ajoutPanneauZoom");débutCh();
    getPc().chargerImages(); //on a besoin du bon zoom pour effectuer cette action.
    finCh("chargementImagesDelaCarte");
    String s = g.get("chargementFini");
    if (debug.getAffLesPerformances()==true){s=s +" "+ "("+Temps.msToS(lonTotal)+")";}
    setMessageChargement(s);
    getPch().addBt();
    //affichageDeLaPageDeChargement
    boolean b=!op.getAttendreAprèsLeChargementDeLaCarte();
    while(!b){Temps.pause(10);b=getPch().getLancer();}
    getPj().removePch();
    getPs().construire();
    getGj().prendreEnCompteLaDifficulté();
    if(premierePartie){tuto=true;}
    if(tuto){iniParamètreCarteTuto();}
    else{//si ce n'est pas le tuto on change la musique.
      //thm.stop();
      thm.stopThm();
      thm = new ThMusique();
      thm.start();
    }
    pa.jeu(); //lance le jeux.
  }
  public static Partie getPartieParDéfaut(){
    débutCh();
    String nomCarte = "miniMonde";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie par = new Partie(1,100,mapo,1.0);
    par.setElément(1,5,1);
    par.setVitesseDeJeu(0.2);
    finCh("chargementParamètrePartieParDéfaut");
    return par;
  }
  /*public static Partie getPartieParDéfaut(){
   débutCh();
    String nomCarte = "miniMonde";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    Partie par = new Partie(1,500,mapo,1.0);
    par.setElément(0,1,1);
    par.setDifficulté((byte)5);
    par.setVitesseDeJeu(0.2);
    finCh("chargementParamètrePartieParDéfaut");
    return par;
  }*/
  public static Partie getPartieTuto(){
    débutCh();
    String nomCarte = "tuto";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    mapo.setCasesSombres(false);mapo.setCasesNuageuses(false);
    Partie par = new Partie(1,5,mapo,1.0);
    par.setElément(1,0,1);
    par.setVitesseDeJeu(0.2);
    finCh("chargementParamètrePartieTuto");
    par.setAppartionInsecte(false);
    par.setAppartionGraine(false);
    return par;
  }
  public static void iniParamètreCarteTuto(){
    Fourmiliere fere = Main.getGj().getDébut().getContenu().getFere();
    CCase ccIni = Main.getPartie().getGc().getCCase(0,1);
    fere.setCc(ccIni);
    fere.getGc().getDébut().getContenu().setCCase(ccIni);
    Insecte i = new Insecte(Main.getPartie().getGc().getCCase(1,1),200,0,100,0);
    i.setEstMort(false);
    i.setType(8);
    getGi().ajouterInsecte(i);
    ths = new ThScript("data/tuto.formiko");
    ths.start();
  }
  public static void retourAuMenu(){
    Carte mapo = new Carte(new GCase(1,1));
    pa = new Partie(0,0,mapo,1.0); //nouvelle partie vide.
    debug.débogage("lancement du retour au menu");
    Main.getPp().removePj();
    //Main.getPp().removePm();
    //repaint();
    ini.initialiserToutLesPaneauxVide();
    ini.initialiserPanneauJeuEtDépendance();
    getPm().construitPanneauMenu(3);
    repaint();
    debug.débogage("fin du retour au menu");
    attenteDeLancementDePartie();
  }

// GET SET ----------------------------------------------------------
  public static byte getNiveauDeDétailDeLAffichage(){return niveauDeDétailDeLAffichage;}
  public static int getVersionActuelle(){return versionActuelle;}
  public static Espece getEspece(){return getEspeceParId(0);}
  public static Espece getEspeceParId(int id){ return ge.getEspeceParId(id);}
  public static GEspece getGEspece(){ return ge;}
  public static Joueur getJoueurParId(int id){ return Main.getGj().getJoueurParId(id);}
  public static Fourmiliere getFourmiliereParId(int id){ return getJoueurParId(id).getFere();}
  public static Fenetre getF(){ return f;}
  public static Options getOp(){return op;}
  public static Chrono getCh(){ return ch;}
  public static String getMap(String clé){ return map.get(clé);}
  public static int getKey(String clé){ int r = key.get(clé);if(r==-1){return -1;}return r; }
  public static Partie getPartie(){ return pa;}
  public static Pixel getPiFond(){ return pi;}
  public static boolean getEcouteClavier(){ return ecouteClavier;}
  public static void setEcouteClavier(Boolean b){ecouteClavier=b;}
  public static int getAvancementChargement(){ return avancementChargement;}
  public static void setAvancementChargement(int x){avancementChargement=x;}
  public static boolean getAffGraine(){return affGraine;}
  public static Temps getTemps(){ return tem;}
  public static boolean getRetournerAuMenu(){return retournerAuMenu;}
  public static void setRetournerAuMenu(boolean b){retournerAuMenu=b;}
  public static Os getOs(){return os;}
  public static void setTuto(boolean b){tuto=b;}
  public static boolean getPremierePartie(){return premierePartie;}
  public static void setPremierePartie(boolean b){premierePartie=b;}
  //racourci
  public static boolean estWindows(){return os.getId()==1;}
  public static String get(String clé){ return g.get(clé);}
  public static Script getScript(){return ths.getScript();}
  //musique
  public static ThMusique getThm(){return thm;}
  public static Musique getMusique(){return getThm().getM();}
  public static void setMusique(Musique m){getThm().setM(m);}
  public static void setMusiqueSuivante(){getThm().setM();}
  //graphique
  public static PanneauPrincipal getPp(){ return f.getPp();}
  public static synchronized void repaint(){f.repaint();}
  public static synchronized void repaintParciel(Case c){getPc().repaintParciel(c);}
  public static PanneauJeu getPj(){ return getPp().getPj();}
  public static PanneauMenu getPm(){ return getPp().getPm();}
  public static PanneauNouvellePartie getPnp(){ return getPm().getPnp();}
  public static PanneauBouton getPb(){ return getPj().getPb();}
  public static PanneauCarte getPc(){ return getPj().getPc();}
  public static PanneauInfo getPi(){ return getPb().getPi();}
  public static PanneauZoom getPz(){ return getPb().getPz();}
  public static PanneauAction getPa(){ return getPb().getPa();}public static PanneauAction getPac(){return getPa();}
  public static Fourmi getFActuelle(){ return getPj().getFActuelle();}
  public static PanneauChargement getPch(){ return getPj().getPch();}
  public static PanneauSup getPs(){ return getPj().getPs();}
  public static PanneauEchap getPe(){ return getPj().getPe();}
  public static PanneauDialogue getPd(){ return getPj().getPd();}
  public static PanneauDialogueInf getPdi(){ return getPj().getPdi();}
  public static int getDimX(){ try {return getPp().getWidth();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getDimY(){ try {return getPp().getHeight();}catch (Exception e){erreur.erreur("Impossible de récupérer les dim de Pp");return 1;}}
  public static int getWidth(){return getDimX();}
  public static int getHeight(){return getDimY();}
  public static int getTailleElementGraphique(int x){ return math.min(getTailleElementGraphiqueY(x),getTailleElementGraphiqueX(x));}
  public static int getTailleElementGraphiqueX(int x){ return (x*getDimX())/1920;}
  public static int getTailleElementGraphiqueY(int x){ return (x*getDimY())/1080;}
  public static double getRacioEspaceLibre(){return 900.0/1080.0;}
  public static int getDimXCarte(){System.out.println(getRacioEspaceLibre());return (int)(1920.0*getRacioEspaceLibre());}
  //options
  public static byte getLangue(){ return op.getLangue();}
  public static void setLangue(int x){ op.setLangue(x);}
  public static int getTailleBoutonZoom(){return op.getTailleBoutonZoom();}
  public static int getTailleBoutonAction(){return op.getTailleBoutonAction();}
  public static int getTailleBoutonTX(){return op.getTailleBoutonTX();}
  public static boolean getMouvementRapide(){ return op.getMouvementRapide();}
  public static void setMouvementRapide(boolean b){ op.setMouvementRapide(b);}
  public static int getNbrMessageAfficher(){ return op.getNbrMessageAfficher();}
  public static boolean getDessinerGrille(){ return op.getDessinerGrille();}
  public static boolean getElementSurCarteOrientéAprèsDéplacement(){ return op.getElementSurCarteOrientéAprèsDéplacement();}
  public static boolean getForcerQuitter(){ return op.getForcerQuitter();}
  public static byte getBordureBouton(){ return op.getBordureBouton();}
  public static boolean getDessinerIcone(){return op.getDessinerIcone();}
  public static Font getFont1(){ return op.getFont1();}
  public static Font getFont1(double d){ return op.getFont1(d);}
  public static void setFont1(Font f){ op.setFont1(f);}
  public static Font getFont2(){ return op.getFont2();}
  public static void setFont2(Font f){ op.setFont2(f);}
  public static int getTaillePolice1(){ return op.getTaillePolice1();}
  public static int getTaillePolice2(){ return op.getTaillePolice2();}
  public static boolean getPleinEcran(){ return op.getPleinEcran();}
  public static boolean getChargementPendantLesMenu(){ return op.getChargementPendantLesMenu();}
  public static boolean getGarderLesGraphismesTourné(){ return op.getGarderLesGraphismesTourné();}
  public static int getDimLigne(){return op.getDimLigne();}
  public static int getPositionCase(){return op.getPositionCase();}
  //partie
  public static GInsecte getGi(){return pa.getGi();}
  public static GJoueur getListeJoueur(){return pa.getGj();}
  public static GJoueur getGj(){return pa.getGj();}
  public static GCase getGc(){return pa.getGc();}
  public static void setNbrDeTour(int x){pa.setNbrDeTour(x);}
  public static void setTour(int x){pa.setTour(x);}
  public static int getNbrDeTour(){return pa.getNbrDeTour();}
  public static int getTour(){return pa.getTour();}
  public static byte getDifficulté(){return pa.getDifficulté();}
  public static void setDifficulté(byte x){pa.setDifficulté(x);}
  public static LocalDateTime getDateDeCréation(){return pa.getDateDeCréation();}
  public static int [] getTableauDesEspecesAutorisée(){ return pa.getTableauDesEspecesAutorisée();}
  public static int getNbrDeJoueur(){ return pa.getNbrDeJoueurDansLaPartie();}
  public static Carte getCarte(){ return pa.getCarte();}
  public static double getVitesseDeJeu(){return pa.getVitesseDeJeu();}
  //ini
  public static void initialiserElémentTournés(){ ini.initialiserElémentTournés();}
  public static void initialiserAutreELémentTournés(){ ini.initialiserAutreELémentTournés();}
// Fonctions propre -------------------------------------------------
  public static void initialisation(){
    tempsDeDébutDeJeu=System.currentTimeMillis();
    os = new Os();
    setMessageChargement("vérificationsDeLArborécence");débutCh();
    if(!arbo.arborécenceIntacte()){arbo.réparationArboréscence();}
    finCh("vérificationsDeLArborécence");
    setMessageChargement("chargementDesOptions");débutCh();
    chargerLesTraductions.iniTLangue();
    op = chargerLesOptions.chargerLesOptions();
    if(!debug.getAffLesEtapesDeRésolution()){//si elle n'ont pas été activé par "-d"
      debug.setAffLesEtapesDeRésolution(op.getAffLesEtapesDeRésolution());
    }
    debug.setAffLesPerformances(op.getAffLesPerformances());
    debug.setAffG(op.getAffG());
    finCh("chargementDesOptions");
    setMessageChargement("chargementDesTouches");débutCh();
    key = chargerLesTouches.chargerLesTouches();
    finCh("chargementDesTouches");
    setMessageChargement("chargementDesLangues");débutCh();
    iniLangue();
    finCh("chargementDesLangues");
    trich = new ThTriche();//écoute des codes triche.
    trich.start();
    débutCh();
    tem = new Temps();
    finCh("chargementDesDonnéesTemporelles");
    setMessageChargement("chargementDesEspeceDeFourmi");débutCh();
    ge = new GEspece(); // chargement des Especes.
    GIndividu.chargerLesIndividus(); // chargement de leur individu.
    finCh("chargementDesIndividuDeFourmi");débutCh();
    Insecte.setGie(); // chargement des Insectes.
    finCh("chargementDesEspeceDInsecte");débutCh();
    thm = new ThMusique("menu");
    thm.start();
    finCh("chargementDeLaMusique");
    //System.out.println("Os reconnu : "+os);
  }
  public static void setMessageChargement(String s){
    //s c'est un truc du genre "chargementDesLangues"
    String s2 = g.getM(s)+"..."; //g.getM() permet d'aller chercher la traduction dans la table de hachage Map<String, String> map.
    try {
      getPch().setTexte(s2); //envoie a la fenetre le message d'avancement du chargement.
    }catch (Exception e) { // si quelque chose ce passe mal on envoie un message a la console.
      //erreur.alerte("Un message de chargement n'est pas arrivé a destination.");
    }
  }
  public static void gcToImage(){
    débutCh();
    Carte mapo = new Carte(chargerCarte.chargerCarte("miniMonde"));
    GCase gc = mapo.getGc();
    finCh("chargementCarteEtGc");débutCh();
    Img img = gc.getImg();
    finCh("récupérationDeLImage");débutCh();
    img.sauvegarder("carte.png");
    finCh("sauvegardeLeLImage");
    //debug.setAffLesEtapesDeRésolution(false);
  }
  public static void débutCh(){débutCh(ch);}
  public static void finCh(String s){finCh(s,ch);}
  public static void débutCh(Chrono chTemp){ //début du Chrono.
    if(!debug.getAffLesPerformances()){ return;}
    chTemp.start();
  }
  public static void finCh(String s,Chrono chTemp){ // fin du Chrono.
    if(!debug.getAffLesPerformances()){ return;}
    String s2 = g.getM(s);
    if (s2.length()!=0){ s=s2;}
    chTemp.stop();lon = chTemp.getDureeMs(); lonTotal=lonTotal+lon;
    String s3 = ""; if(!chTemp.equals(ch)){s3 = " ("+g.get("actionSecondaire")+" "+ch.getId()+")";}
    debug.performances("temps pour "+ s + " : "+lon+" ms"+s3); //affichage du chrono.
  }
  public static void menuM(){
    // a ajouter : un lien vers une FormikoPédia avec des explications sur les actions disponible des fourmis par exemple.
    String t2 [] = {get("menuM.1"),get("menuM.2"),get("menuM.3"),get("quitterJeu")};
    byte choix2 = menu(t2);
    if (choix2==1){
      // Effectuer une sauvegarde
      creerUneSauvegarde.creerUneSauvegarde();
    } else if (choix2==2){
      menuTriche();
    } else if (choix2==4){
      if (read.getOuiOuNon(get("validerQuitter"))){
        quitter();
      }else{
        menuM();
      }
    } // 3 = ne rien faire
  }
  public static void quitter(){
    try {
      débutCh();
      pa.enregistrerLesScores();
      finCh("enregistementDesScores");
      débutCh();
      if(getGarderLesGraphismesTourné()){image.clearPartielTemporaire();}
      else{image.clearTemporaire();}
      finCh("vidageDesFichiersImages");
      String s = "toutes les opérations longues ";
      debug.performances("temps pour "+ s + " : "+lonTotal+" ms");
      long tempsDeFinDeJeu=System.currentTimeMillis();
      long tempsJeuEcoulé = tempsDeFinDeJeu-tempsDeDébutDeJeu;
      System.out.println(g.getM("tempsJeuEcoulé")+" : "+ch.timeToHMS((tempsJeuEcoulé)/1000)+".");
      //System.out.println("\ud83d\ude00");//System.out.println("😀");
      tem.addTempsEnJeux(tempsJeuEcoulé);tem.actualiserDate2();tem.sauvegarder();
      System.out.println(g.getM("messageQuitter"));
      System.exit(0);
    }catch (Exception e) {
      System.exit(1); //une erreur a la fermeture.
    }
  }
  public static void menuTriche(){
    String commande = read.readString();
    triche.commande(commande);

  }
  public static byte menu(String t []){return menu.menu(t,get("choix"));}
  public static void afficheTout(){
    if (niveauDeDétailDeLAffichage>1){
      getGj().afficheToi();
      System.out.println();
    }
  }

  public static void tour(){
    //écouteTriche();
    //System.out.print("\033[H\033[2J");
    getGc().tourCases(); //actualisation des ressources sur les cases.
    getGj().jouer();
    getGi().tourInsecte();
    if(Main.getPartie().getAppartionInsecte()){
      int nbrDInsecteRestant = math.max( getGc().getNbrDeCase()/5 -  getGi().getGiVivant().length(),0);
      int x2 = math.min( getGc().getNbrDeCase()/20, nbrDInsecteRestant);
      new Message("Ajout de "+x2+" insectes");
      getGi().ajouterInsecte((x2*9)/10); //les insectes vivants n'apparaissent pas sur des cases déja occupé.
      getGi().ajouterInsecte(x2/10);
    }
    //}
  }
  public static void chargementDesGraphismesAutonomes(){
    Th thTemp = new Th(1);
    thTemp.start();
    Th thTemp2 = new Th(2);
    thTemp2.start();
  }
  public static void iniLangue(){
    map = chargerLesTraductions.chargerLesTraductions(getLangue());//chargement des langues.
  }
}
