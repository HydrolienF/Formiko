package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Touches;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Bouton extends JButton implements MouseListener{
  protected final int id; protected static int cpt=1;
  protected Image img;
  protected String nom;
  protected Panneau p;
  protected int action;
  protected boolean bordure=true;
  protected Color cFond;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Bouton(String str, Panneau p, int action, Image imag){
    super();id=cpt; cpt++;setBorderPainted(false);setOpaque(false);
    img = imag; String s = "non null"; if(imag==null){ s="null";}
    debug.débogage("Création du bouton "+str+" avec une image "+s);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this); //Grâce à cette instruction, notre objet va s'écouter  Dès qu'un événement de la souris sera intercepté, il en sera averti
    this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    addKeyListener(new Touches());
  }
  public Bouton(String str, Panneau p, int action){
    super(str);id=cpt; cpt++;setBorderPainted(false);setOpaque(false);
    debug.débogage("Création du bouton "+str);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this);
    this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    this.setFont(Main.getFont1());
    addKeyListener(new Touches());
  }
  //public Bouton (String str, Panneau p, int action, Image i){ this(str,p,(byte) action,i);}
  //public Bouton(String str,Panneau p, byte action,String imageX){this(str,p,action,image.getImage(imageX));}
  //public Bouton(String s, Panneau p, int ac,String i){ this(s,p,(byte)ac,i);}
  //public Bouton(String s, Panneau p, int ac){ this(s,p,(byte)ac);}
  // GET SET --------------------------------------------------------------------
  public String getNom(){ return nom;}
  public void setNom(String s){nom=s;debug.débogage("le nom a été changé pour "+s);}
  public int getActionB(){ return action;}
  public void setActionB(int x){action =x; }
  public void setBordure(boolean b){bordure=b;}
  public void setCFond(Color c){cFond=c;}
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.4);}
  public void setDesc(String s){
    if(Main.getPp().getPj()==null){ erreur.erreur("pj null");}
    try {
      Main.getPp().getPj().getPb().setDesc(s);
    }catch (Exception e) {erreur.alerte("Impossible de setDesc pour le bouton.");}
  }
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    /*GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);
    g2d.setPaint(gp);
    //g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    g2d.setColor(Color.black);*/
    /*try {
      //g.setBackround(new Color(255,100,255,100));
      g2d.drawImage(this.img,0,0, null);
    }catch (Exception e) {
      g2d.drawString(this.nom, this.getWidth() / 2 - (this.getWidth() /  2 /2 ), (this.getHeight() / 2) + 5);
    }*/
    //if(img==null){g2d.drawString(this.nom, this.getWidth() / 2 - (this.getWidth() /  2 /2 ), (this.getHeight() / 2) + 5);}
    if(img==null){
      //g2d.drawString(this.nom,0,this.getHeight()*2/3);
      //FontMetrics fm = new FontMetrics(Main.getFont1());//new FontRenderContext(null, false, false);
      //Rectangle2D rect = fm.getStringBounds(nom,g);
      //new FontRenderContext(null, false, false)
      //le fond
      if(cFond!=null){
        g2d.setColor(cFond);
        g2d.fillRect(0,0,getWidth(),getHeight());
      }
      //le texte
      g2d.setColor(new Color(0,0,0));
      g2d.drawString(nom,0,this.getHeight()*2/3);
    }else{g2d.drawImage(this.img,0,0, null);}
    if(bordure){peintBordure(g2d);}
  }
  /*
  public Rectangle2D getStringBounds(String str,FontRenderContext frc);
  c'est sencé pemetre d'avoir juste la dimention qu'il faut pour un textes
  */
  public void peintBordure(Graphics2D g){
    byte x = Main.getBordureBouton();
    if(x<1){return;}
    BasicStroke ligne = new BasicStroke(x);
    g.setStroke(ligne);
    g.drawRect( 0, 0, getWidth(), getHeight());
  }

  //Méthode appelée lors du clic de souris
  public void mouseClicked(MouseEvent event) {}

  //Méthode appelée lors du survol de la souris
  public void mouseEntered(MouseEvent event) {
    String clé = "";
    try { //on essaie de récupéré le raccourci clavier associé.
      clé = ""+(char)Main.getKey(action+"");
    }catch (Exception e) {}
    if(clé!=""){clé="("+g.get("raccourci")+" \""+clé+"\")";}
    setDesc(g.get("bouton.desc."+action)+clé);
  }

  //Méthode appelée lorsque la souris sort de la zone du bouton
  public void mouseExited(MouseEvent event) {
    setDesc("");
  }

  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
  public void mousePressed(MouseEvent event) { }

  //Méthode appelée lorsque l'on relâche le clic de souris
  public void mouseReleased(MouseEvent event) {
    debug.débogage("Un bouton a été cliqué, l'action "+action+" vas être effectué.");
    p.doAction(action);
  }
}
