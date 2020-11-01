package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.Graphics;
import java.awt.Image;
import fr.formiko.usuel.image.image;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.liste.GString;

public class PanneauChargement extends Panneau {
  private Image img [];
  private Bouton b;
  private int tempsTotalDeChargement;
  private Desc message;
  private PanneauInfo conseil;
  private boolean lancer;
  private Bouton bt;
  private static int a=-1;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChargement(){
    this.setLayout(null);
    try {
      Image img2 [] = image.getImages("chargement",image.getNbrImages("chargement"));
      img = image.getScaledInstance(img2,Main.getDimX(), Main.getDimY(),Image.SCALE_SMOOTH);
    }catch (Exception e) {
      erreur.erreur("Impossible de charger l'image de la page de chargement","PanneauChargement.PanneauChargement");
      img = new Image[1];
      img[0]=image.getImage("null");
      img[0]=img[0].getScaledInstance(Main.getDimX(), Main.getDimY(),Image.SCALE_SMOOTH);
    }
    addMessage();
    addConseil();
    lancer=false;
    if(a==-1){
      setA(allea.getAlléa(img.length));
    }
  }
  // GET SET --------------------------------------------------------------------
  public void setTexte(String s){ message.setTexte(s);}
  public boolean getLancer(){return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public static void setA(int x){a=x;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {}
    debug.g("PanneauChargement",this.getWidth(),this.getHeight());
    g.drawImage(img[a],0,0,this);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    message.setBounds(xx,yy*4-Main.getTaillePolice1(),xx*3);//le niveau d'avancement du chargement
    conseil.setBounds(xx,(yy*4)-(2*Main.getTaillePolice1())-conseil.getYPi(),conseil.getWidth(),conseil.getHeight());//le conseil
  }
  public void addBt(){
    lancer=false;
    bt = new Bouton(g.getM("lancerLeJeu"), Main.getPj(), 111);
    bt.setFont(Main.getFont2());
    add(bt);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    bt.setBounds((int)(xx*1.5),yy*4+Main.getTaillePolice1(),xx*2,Main.getTaillePolice2());
  }
  public void addMessage(){
    message = new Desc();
    message.setTexte("");
    add(message);
  }
  public void addConseil(){
    int x = allea.getAlléa(19)+1;//de 1 a 19.
    String s = g.getM("conseil."+x);
    GString gs = new GString();
    gs.addParMorceaux(s,70,true);//ajoute 70 char par 70 char (sans couper les mots) a la GString
    conseil = new PanneauInfo(gs,(Main.getF().getWidth()*3)/5);
    add(conseil);
    repaint();
  }
}
