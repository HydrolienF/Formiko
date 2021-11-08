package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.types.str;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

/**
*{@summary A partie launcher Panel that create a new partie.}<br>
*@author Hydrolien
*@version 1.x
*/
public class FPanelNouvellePartie extends FPanelLanceurPartie {
  private FLabel jl; private FLabel jl2;
  private GEtiquetteJoueur gej;
  private FPanelGEtiquetteJoueur pgej;
  private JFormattedTextField jtf;
  private FLabel jtfDesc;
  private FComboBox<String> choixCarte;
  private FLabel choixCarteDesc;
  private JCheckBox casesNuageuses;
  private JCheckBox casesSombres;
  private int taille;
  private GString gsClé;
  private EtiquetteChoix eDif;
  private EtiquetteChoix eVitesseDeJeu;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelNouvellePartie(){
    super(100);
    Main.startCh();
    this.setLayout(null);
    //setOpaque(false);
    //les entêtes.
    jl = new FLabel(); jl2 = new FLabel();
    jl.setTexte(g.getM("paramètreCarte"));
    jl2.setTexte(g.getM("paramètreJeu"));
    jl.setFondTransparent();jl2.setFondTransparent();
    jl.setCentered();jl2.setCentered();
    jtfDesc = new FLabel();
    jtfDesc.setTexte(g.getM("nbrDeTour")+" : ");
    jtfDesc.setFondTransparent();
    choixCarteDesc = new FLabel();
    choixCarteDesc.setTexte(g.getM("choixCarte")+" : ");
    choixCarteDesc.setFondTransparent();
    this.add(jl);this.add(jl2);this.add(jtfDesc);this.add(choixCarteDesc);
    //a gauche :
    //nom de la carte parmi ceux proposer.
    GString nomDesCartes = mapList();//on charge les clé dans gsClé en meme temps.
    choixCarte = nomDesCartes.getComboBox();
    choixCarte.setFont(Main.getFont1(0.9));
    choixCarte.setSelectedItem(g.get("miniWorld")); // 2 = miniWorld pour l'instant
    add(choixCarte);
    //nomCarte = nomDesCartes.getHead().getContent();
    GString gs = new GString();
    for (int i=-2; i<4; i++){ // tout les niveaux de difficulté.
      gs.add(g.getM("diff"+i));
    }
    eDif = new EtiquetteChoix(2,"choixDif",gs);
    eDif.setCFond(null);
    add(eDif);
    gs = new GString();
    for (int i=-3; i<5; i++){ // tout les niveaux de vitesse.
      gs.add(g.getM("vitesseDeJeu"+i));
    }
    eVitesseDeJeu = new EtiquetteChoix(3,"choixVitesseDeJeu",gs);
    eVitesseDeJeu.setCFond(null);
    add(eVitesseDeJeu);
    //a droite :
    gej = new GEtiquetteJoueur(3);

    pgej = new FPanelGEtiquetteJoueur(gej);
    add(pgej);
    jtf = new JFormattedTextField(NumberFormat.getIntegerInstance());
    jtf.setText("100");
    jtf.setFont(Main.getFont1(0.9));
    add(jtf);
    casesNuageuses = new JCheckBox();
    casesSombres = new JCheckBox();
    // casesNuageuses.setFont(Main.getFont1());
    // casesSombres.setFont(Main.getFont1());
    casesNuageuses.setForeground(Color.BLACK);
    casesSombres.setForeground(Color.BLACK);
    casesNuageuses.setText(g.getM("casesNuageuses"));
    casesSombres.setText(g.getM("casesSombres"));
    casesNuageuses.setBackground(new Color(0,0,0,0));
    casesSombres.setBackground(new Color(0,0,0,0));
    casesNuageuses.setSelected(true);
    casesSombres.setSelected(true);
    add(casesNuageuses);add(casesSombres);
    Main.endCh("chargementFPanelNouvellePartie");
  }
  public void rafraichirPgej(){
    remove(pgej);
    pgej = new FPanelGEtiquetteJoueur(gej);
    add(pgej);
    getView().paint();
  }
  // GET SET -------------------------------------------------------------------
  public GEtiquetteJoueur getGej(){ return gej;}
  public int getTaille(){ return taille;}
  public FPanelGEtiquetteJoueur getPGej(){ return pgej;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    int wi = Main.getDimX();
    // int he = Main.getDimY();
    int wi2 = wi/2;
    //gauche
    jl.setSize((int)(wi2*0.9));
    int k=1; taille = jl.getHeight()*2;
    jtfDesc.setBounds(0,taille*k,wi2/2,FLabel.getDimY());
    jtf.setBounds(wi2/2,taille*k,wi2/3,FLabel.getDimY());k++;
    choixCarteDesc.setBounds(0,taille*k,wi2/2,FLabel.getDimY());
    choixCarte.setBounds(wi2/2,taille*k,wi2/3,FLabel.getDimY());k++;
    eDif.setBounds(0,taille*k,1000,FLabel.getDimY());k++;
    eVitesseDeJeu.setBounds(0,taille*k,1000,FLabel.getDimY());k++;
    casesNuageuses.setBounds(0,taille*k,wi2/2,FLabel.getDimY());
    casesSombres.setBounds(wi2/2,taille*k,wi2/3,FLabel.getDimY());k++;
    g.setColor(Main.getData().getButtonColor());
    g.fillRect(0,0,wi/2,taille*k);
    g.fillRect(wi/2,0,wi,taille);
    //droite
    int yDep=0;
    jl2.setBounds(wi2,yDep,(int)(wi2*0.9)); yDep=yDep+jl2.getHeight()*2;
    int tailleMaxY = this.getHeight() - yDep;
    pgej.setBounds(wi2,yDep,wi2,math.max(FLabel.getDimY()*3*gej.length(),tailleMaxY));
  }
  public Partie getPartie(){
    String nomTraduitDeLaCarte = choixCarte.getSelectedItem()+"";
    String nomDeLaCarte = gsClé.getKey(nomTraduitDeLaCarte);
    Carte mapo = new Carte(nomDeLaCarte);
    mapo.setCasesNuageuses(casesNuageuses.isSelected());
    mapo.setCasesSombres(casesSombres.isSelected());
    int dif = calculeDif();
    Double vitesseDeJeu = calculeVitesseDeJeu();
    // On transmet la difficulté, le nombre de tour, la carte et la vitesse de jeu.
    Partie pa = new Partie(dif,str.sToI(jtf.getText()),mapo,vitesseDeJeu);
    pa.setElément(1,1,1); // ne sert pas en pratique.
    pa.setGej(gej); // permet de passer toutes les informations des joueurs et ia a la partie.
    return pa;
  }
  /**
  *{@summary Do a list of the maps.}<br>
  *@return a list of all aviable map in all possible path.
  *@version 1.46
  */
  public GString mapList(){
    GString gsr = new GString();
    gsClé = new GString();
    addMapList(Main.getFolder().getFolderResourcesPacks()+Main.getFolder().getFolderMaps(), gsr);
    addMapList(Main.getFolder().getFolderTemporary()+Main.getFolder().getFolderMaps(), gsr);
    addMapList(Main.getFolder().getFolderStable()+Main.getFolder().getFolderMaps(), gsr);
    return gsr;
  }
  /**
  *{@summary add all .csv file from path folder.}<br>
  *Path with &#126; are not accepted because they are probably some lock file create by LibreOffice.
  *@version 1.46
  */
  //*@return a list of all aviable map in this path.
  private void addMapList(String path, GString gsr){
    File folder = new File(path);
    File[] files = folder.listFiles();
    for (File f : files ) {
      String fileName = f.getName();
      int lens = f.getName().length();
      if(str.nbrDeX(fileName,'~')==0 && fileName.substring(lens-4).equals(".csv")){
        fileName = fileName.substring(0,lens-4);
        gsr.add(g.get(fileName));
        gsClé.add(fileName);
      }
    }
  }

  public double calculeVitesseDeJeu(){
    // l'objetif est de récupéré la vitesse de jeu dans une liste déroulante sous la forme d'1 String et d'aboutir a un double qu'on puisse utiliser pour ralentir ou accélérer le jeu.
    String vitesse = eVitesseDeJeu.getSelectedItem(); // on récupère la String
    int x=0; boolean b=false;
    int i=-3;
    while(i<5 && !b){
      if (vitesse.equals(g.getM("vitesseDeJeu"+i))){ // on  la compare avec toutes les valeurs qu'elle peut avoir dans la langue du jeu.
        x=i+3;b=true;//si on trouve la String b = true
      }i++;
    }//si b n'est pas true, on a une erreur.
    if (!b){ erreur.erreur("La vitesse de jeu n'as pas pue être reconnue au lancement de la partie","vitesse déffinie a 1 (normale)");}
    Double tv [] = {0.25,0.5,0.75,1.0,1.5,2.0,2.75,4.0}; //du plus rapide au plus lent
    Double vitesseDeJeu;
    try { // on cherche dans le tableau la case numéro x.
      vitesseDeJeu = tv[x];
    }catch (Exception e) { // au cas ou cette case n'existe pas on prévoi un message d'erreur.
      erreur.erreur("La difficulté n'est pas correcte","difficulté fixé a la valeur min ou max.");
      if(x<1){vitesseDeJeu = tv[0];}
      else{vitesseDeJeu = tv[tv.length-1];}
    }//on return la vitesse de jeu.
    return vitesseDeJeu;
  }
  public int calculeDif(){
    String s = eDif.getSelectedItem();
    int dif=0; boolean b=false;
    for (int i=-2; i<4; i++){ // tout les niveau de difficulté.
      if (s.equals(g.getM("diff"+i))){
        dif = i;b=true;break;
      }
    }
    if (!b){ erreur.erreur("La difficulté n'as pas pue être reconnue au lancement de la partie","difficulté déffinie a 0 (normale)");}
    return dif;
  }
}
