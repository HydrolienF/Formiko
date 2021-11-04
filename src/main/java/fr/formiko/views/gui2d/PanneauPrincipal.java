package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.images.image;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
*{@summary the main Panel.}<br>
*@author Hydrolien
*@version 1.x
*/
public class PanneauPrincipal extends Panneau {
  private PanneauJeu pj;
  private PanneauMenu pm;
  private Image img;
  private FLabel versionLabel;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 1.x
  */
  public PanneauPrincipal(){
    super();
    setOpaque(true);
  }
  /**
  *{@summary Add background image &#38; version label.}<br>
  *@version 1.x
  */
  public void build(){
    img = image.getImage("backgroundPP");
    img = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
    addVersionLabel();
  }
  // GET SET -------------------------------------------------------------------
  public PanneauJeu getPj(){ return pj;}
  public PanneauMenu getPm(){ return pm;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    ThMove.updateQueue();
    if (img!=null){
      g.drawImage(img,0,0,this);
    }
    debug.débogage("taille du paneau secondaire : x="+this.getWidth()+", y="+this.getHeight());
  }
  /**
  *{@summary Add the curent version on screen.}<br>
  *@version 2.6
  */
  private void addVersionLabel(){
    versionLabel = new FLabel("");
    versionLabel.setFont(new Font(versionLabel.getFont().getFontName(),versionLabel.getFont().getStyle(),14));
    versionLabel.setFondTransparent();
    // versionLabel.updateSize();
    add(versionLabel);
  }
  /**
  *{@summary Update the curent version print on screen.}<br>
  *@version 2.6
  */
  public void updateVersionLabel(){
    String version = Main.getFolder().getCurentVersion();
    if(version==null){return;}
    versionLabel.setText(version);
    versionLabel.updateSize();
  }

  public void addPm(){
    if(pm==null){
      pm = new PanneauMenu();
      this.add(pm);
    }
    pm.setOpaque(false);
    pm.setVisible(true);
  }
  public void addPj(){
    if(pj==null){
      pj = new PanneauJeu();
      this.add(pj);
    }
    pj.setOpaque(false);
    pj.setVisible(true);
  }
  public synchronized void removePm(){
    // if(pm==null){
    //   erreur.alerte("Impossible de remove le PanneauMenu.");
    //   return;
    // }
    pm.setVisible(false);
    remove(pm);
    pm=null;
  }
  public void removePj(){
    pj.setVisible(false);
    remove(pj);
    pj=null;
  }
}
