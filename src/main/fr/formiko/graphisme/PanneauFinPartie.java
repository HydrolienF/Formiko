package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import fr.formiko.formiko.GJoueur;
import java.awt.Graphics;
import fr.formiko.formiko.Main;
import fr.formiko.graphisme.PanneauInfo;

public class PanneauFinPartie extends Panneau {
  private Desc message;
  private GJoueur gj;
  private PanneauInfo pi;
  private Bouton mp;
  private Bouton c;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauFinPartie(String s, GJoueur gj){
    super();
    addMessage(s);
    addPanneauInfo(gj);
    addBoutonMenuPrincipal();
    //si le type de victoire le permet :
      addBoutonContinuer();
    setVisible(true);
  }
  public PanneauFinPartie(){
    this.setLayout(null);
  }
  // GET SET --------------------------------------------------------------------
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    try {
      message.setBounds(0,0,this.getWidth(),Main.getTaillePolice2()*2);
      pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
      //mp.setBounds()
    }catch (Exception e) {
      erreur.erreur("certain élément graphique de PanneauFinPartie ne sont pas affichable.");
    }
  }
  public void addMessage(String s){
    message = new Desc(this.getWidth(),Main.getTaillePolice2()*2);
    //message = new Desc();
    //message.setFondColoré();
    //message.setPolice(Main.getFont1());
    message.setText(s);
    add(message);
    //message.setBounds(0,0,this.getWidth(),Main.getTaillePolice2()*2);
    //message.setBounds(0,0,this.getWidth());
  }
  public void addPanneauInfo(GJoueur gj){
    this.gj=gj;
    pi = new PanneauInfo(gj.scoreToGString());
    add(pi);
    //pi.setBounds(0,message.getHeight(),pi.getWidth(),pi.getHeight());
  }
  public void addBoutonMenuPrincipal(){
    mp= new Bouton(g.getM("quitterToMp"),Main.getPj(),112);
    add(mp);
  }
  public void addBoutonContinuer(){
    c = new Bouton(g.getM("continuerJeu"),Main.getPj(),113);
    add(c);
  }
}
