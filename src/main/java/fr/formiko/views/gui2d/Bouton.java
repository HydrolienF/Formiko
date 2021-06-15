package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.images.Pixel;

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

/**
*{@summary Extends of Button with some added functions.}<br>
*There is a default color for normal button, focus button &#38; disable button.<br>
*Defaults colors are in Data.<br>
*@author Hydrolien
*@version 1.54
*/
public class Bouton extends JButton implements MouseListener{
  protected final int id; protected static int cpt=1;
  protected Image img;
  protected String nom;
  protected Panneau p;
  protected int action;
  protected boolean bordure=true;
  protected Color cFond;
  protected boolean cFondUseAlpha;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Bouton(String str, Panneau p, int action, Image imag){
    super();id=cpt; cpt++;setBorderPainted(false);setOpaque(false);
    img = imag; String s = "non null"; if(imag==null){ s="null";}
    debug.débogage("Création du bouton "+str+" avec une image "+s);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this); //Grâce à cette instruction, notre objet va s'écouter  Dès qu'un événement de la souris sera intercepté, il en sera averti
    // this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    setCFond(Main.getData().getButtonColor());
    setContentAreaFilled(false);
    setFocusPainted(false); //Diasble paint swap to next button when do tab.
    setCFondUseAlpha(true);

  }
  public Bouton(String str, Panneau p, int action){
    super(str);id=cpt; cpt++;setBorderPainted(false);setOpaque(false);
    debug.débogage("Création du bouton "+str);
    this.nom = str; this.p = p; this.action = action;
    this.addMouseListener(this);
    // if (p!=null) {
    //   this.addActionListener(p.getBListener()); // permet a p d'écouter le bouton.
    // }
    this.setFont(Main.getFont1());
    setCFond(Main.getData().getButtonColor());
    setForeground(Color.BLACK);
    super.setBackground(cFond);
    setContentAreaFilled(false);
    setFocusPainted(false); //paint swap to next button when do tab disable.
    setCFondUseAlpha(true);
  }
  // GET SET --------------------------------------------------------------------
  public String getNom(){ return nom;}
  public void setNom(String s){nom=s;debug.débogage("le nom a été changé pour "+s);}
  public int getActionB(){ return action;}
  public void setActionB(int x){action =x; }
  public void setBordure(boolean b){bordure=b;}
  public void setCFond(Color c){cFond=c;}
  public static int getDimY(){ return (int)(Main.getOp().getTaillePolice1()*1.4);}
  public void setDesc(String s){
    if(Panneau.getView().getPp().getPj()==null){ erreur.erreur("pj null");}
    try {
      Panneau.getView().getPp().getPj().getPb().setDesc(s);
    }catch (Exception e) {erreur.alerte("Impossible de setDesc pour le bouton.");}
  }
  public void setCFondUseAlpha(boolean b){cFondUseAlpha=b;}
  /**
  *{@summary return background color with or without alpha.}
  *With alpha if cFondUseAlpha==True;
  *@version 1.54
  */
  public Color getBackgroundColor(){
    if(cFondUseAlpha){
      return cFond;
    }else{
      return new Color(cFond.getRed(),cFond.getGreen(),cFond.getBlue(),255);
    }
  }
  // Fonctions propre ----------------------------------------------------------
  /**
  *{@summary To draw component.}<br>
  *It draw a fill rectangle as background color.
  *Draw it there alow to have alpha color.
  *@version 1.54
  */
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    if(img==null){
      //FontMetrics fm = new FontMetrics(Main.getFont1());//new FontRenderContext(null, false, false);
      //Rectangle2D rect = fm.getStringBounds(nom,g);
      //new FontRenderContext(null, false, false)
      //le fond
      if(cFond!=null && !isOpaque()){
        g2d.setColor(getBackgroundColor());
        g2d.fillRect(0,0,getWidth(),getHeight());
      }
      setText(nom);
    }else{g2d.drawImage(this.img,0,0, null);}
    if(bordure){paintBorder(g2d);}
    super.paintComponent(g);
  }
  /**
  *{@summary Draw border.}<br>
  *@author Hydrolien
  *@version 1.54
  */
  public void paintBorder(Graphics2D g){
    g.setColor(new Color(cFond.getRed(),cFond.getGreen(),cFond.getBlue()));
    byte x = Main.getBordureBouton();
    if(x<1){return;}
    BasicStroke ligne = new BasicStroke(x);
    g.setStroke(ligne);
    g.drawRect( 0, 0, getWidth(), getHeight());
  }
  @Override
  public void setEnabled(boolean b){
    if(b){
      setCFond(Main.getData().getButtonColor());
    }else{
      setCFond(Main.getData().getButtonDisableColor());
    }
    super.setEnabled(b);
  }

  //Méthode appelée lors du clic de souris
  @Override
  public void mouseClicked(MouseEvent event) {}

  //Méthode appelée lors du survol de la souris
  @Override
  public void mouseEntered(MouseEvent event) {
    if(!isEnabled()){return;}
    String clé = "";
    try { //on essaie de récupéré le raccourci clavier associé.
      clé = ""+(char)Main.getKey(action+"");
    }catch (Exception e) {}
    if(clé!=""){clé="("+g.get("raccourci")+" \""+clé+"\")";}
    setDesc(g.get("bouton.desc."+action)+clé);
    if(isEnabled()){
      setCFond(Main.getData().getButtonFocusColor());
      repaint();
    }
  }

  //Méthode appelée lorsque la souris sort de la zone du bouton
  @Override
  public void mouseExited(MouseEvent event) {
    setDesc("");
    if(isEnabled()){
      setCFond(Main.getData().getButtonColor());
      repaint();
    }
  }

  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
  @Override
  public void mousePressed(MouseEvent event) {}

  //Méthode appelée lorsque l'on relâche le clic de souris
  @Override
  public void mouseReleased(MouseEvent event) {
    if(!isEnabled()){return;}
    debug.débogage("Un bouton a été cliqué, l'action "+action+" vas être effectué.");
    Main.doAction(action);
  }
}
