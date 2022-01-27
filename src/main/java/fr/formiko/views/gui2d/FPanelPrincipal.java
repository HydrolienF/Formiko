package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.image;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
*{@summary the main Panel.}<br>
*@author Hydrolien
*@version 1.x
*/
public class FPanelPrincipal extends FPanel {
  private FPanelJeu pj;
  private FPanelMenu pm;
  private BufferedImage img;
  private FLabel versionLabel;
  private long timeFromLastRefresh;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 1.x
  */
  public FPanelPrincipal(){
    super();
    setOpaque(true);
  }
  /**
  *{@summary Add background image &#38; version label.}<br>
  *@version 1.x
  */
  public void build(){
    img = image.getImage("backgroundPP");
    img = image.resize(img, this.getWidth(), this.getHeight());
    addVersionLabel();
  }
  // GET SET -------------------------------------------------------------------
  public FPanelJeu getPj(){ return pj;}
  public FPanelMenu getPm(){ return pm;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //good for all drawLine, drawCircle etc.
    // g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE); //not good for straight line
    ThMove.updateQueue();
    if (img!=null){
      g.drawImage(img,0,0,this);
    }
    debug.débogage("taille du paneau secondaire : x="+this.getWidth()+", y="+this.getHeight());
    if(debug.getPerformance()){
      getView().setCurentFPS(getView().getCurentFPS()+1);
      long time = System.currentTimeMillis();
      long timeElapsed = time-timeFromLastRefresh;
      int supposedTime = 1000/Main.getOp().getFps();
      if(timeElapsed>supposedTime*4 &&  getView().getF()!=null && getView().getF().isFocused()){
        erreur.alerte("Time bewteen 2 frame: "+timeElapsed+" (supposed to be "+supposedTime+")");
      }else{
        // erreur.info("Time bewteen 2 frame: "+timeElapsed,0);
      }
      timeFromLastRefresh=time;
    }
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
      pm = new FPanelMenu();
      this.add(pm);
    }
    pm.setOpaque(false);
    pm.setVisible(true);
  }
  public void addPj(){
    if(pj==null){
      pj = new FPanelJeu();
      this.add(pj);
    }
    pj.setOpaque(false);
    pj.setVisible(true);
  }
  public synchronized void removePm(){
    // if(pm==null){
    //   erreur.alerte("Impossible de remove le FPanelMenu.");
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
