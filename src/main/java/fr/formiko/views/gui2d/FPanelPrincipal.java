package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.images.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
*{@summary the main Panel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.21
*/
public class FPanelPrincipal extends FPanel {
  private FPanelJeu pj;
  private FPanelMenu pm;
  private BufferedImage img;
  private FLabel versionLabel;
  private long timeFromLastRefresh;

  private ThreadMessagesDesc th;
  private FLabel labelMessage;

  private Color topColor;
  private FPanel coloredPanel;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.27
  */
  public FPanelPrincipal(){
    super();
    // setOpaque(true);
    updateTimeFromLastRefresh();
    iniLabelMessage();
    coloredPanel=new FPanel(){
      @Override
      public void paintComponent(Graphics g) {
        if(topColor!=null){
          g.setColor(topColor);
          g.fillRect(0,0,getWidth(),getHeight());
        }
      }
    };
    add(coloredPanel);
  }
  /**
  *{@summary Add background image &#38; version label.}<br>
  *@lastEditedVersion 1.x
  */
  public void build(){
    img = Images.getImage("backgroundPP");
    img = Images.resize(img, this.getWidth(), this.getHeight());
    addVersionLabel();
  }
  // GET SET -------------------------------------------------------------------
  public FPanelJeu getPj(){ return pj;}
  public FPanelMenu getPm(){ return pm;}
  public Color getTopColor() {return topColor;}
  /**
  *{@summary Update top color &#38; it's pannel size.}<br>
  *@lastEditedVersion 2.27
  */
	public void setTopColor(Color topColor) {
    this.topColor=topColor;
    coloredPanel.setSize(getWidth(),getHeight());
  }
  public void updateTimeFromLastRefresh(){timeFromLastRefresh=System.currentTimeMillis();}
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
      // if(timeElapsed>supposedTime*2 &&  getView().getF()!=null && getView().getF().isFocused()){
        erreur.alerte("Time between 2 frame: "+timeElapsed+" (supposed to be "+supposedTime+")");
      }else{
        // erreur.info("Time between 2 frame: "+timeElapsed,0);
      }
      timeFromLastRefresh=time;
    }
  }
  /**
  *{@summary Add the curent version on screen.}<br>
  *@lastEditedVersion 2.6
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
  *@lastEditedVersion 2.6
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


  //Message part
  /**
  *{@summary Initialize the label message.}<br>
  *@lastEditedVersion 2.21
  */
  public void iniLabelMessage(){
    labelMessage = new FLabel("");
    labelMessage.setBackground(new Color(225,225,225));
    FBorder border = new FBorder();
    border.setColor(Color.BLACK);
    border.setThickness(1);
    labelMessage.setBorder(border);
    add(labelMessage);
  }
  /**
  *{@summary Update time from last move in the Thread.}
  *@lastEditedVersion 2.7
  */
  public void updateTimeFromLastMove(){
    if(th==null){return;}
    th.updateTimeFromLastMove();
  }
  /**
  *{@summary Update message.}<br>
  *It will initialize &#38; launch ThreadMessagesDesc if it is null.
  *@lastEditedVersion 2.7
  */
  public void updateThreadMessagesDesc(String message){
    if(th==null){
      th = new ThreadMessagesDesc();
      th.start();
    }
    // erreur.info("New message "+message);
    th.setMessage(message);
  }
  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Thread used to print a description message at mouse location.}<br>
  *Message is print only after 0.5s if mouse don't move.
  *@author Hydrolien
  *@lastEditedVersion 2.7
  */
  class ThreadMessagesDesc extends Thread {
    private String message;
    private long timeFromLastMove;
    private boolean needToUpdateTimeFromLastMove;
    public void setMessage(String s){message=s;}
    public void updateTimeFromLastMove(){needToUpdateTimeFromLastMove=true;}

    /**
    *{@summary Main function that update message if needed every 50ms.}<br>
    *@lastEditedVersion 2.7
    */
    @Override
    public void run(){
      needToUpdateTimeFromLastMove=false;
      while (true) {
        boolean visible = false;
        if(message!=null && !message.equals("")){
          if(needToUpdateTimeFromLastMove){
            timeFromLastMove = System.currentTimeMillis();
            needToUpdateTimeFromLastMove=false;
          }else{
            long currentTime = System.currentTimeMillis();
            long timeElapsed = currentTime-timeFromLastMove;
            if(timeElapsed>500){
              if(timeElapsed<600){
                labelMessage.setText(message);
                labelMessage.updateSize();
                Point curentLocation = MouseInfo.getPointerInfo().getLocation();
                labelMessage.setLocation((int)(curentLocation.getX()-labelMessage.getWidth()), (int)(curentLocation.getY()-labelMessage.getHeight()));
              }
              visible = true;
            }
          }
        }else{
          needToUpdateTimeFromLastMove=true;
        }
        labelMessage.setVisible(visible);
        // Time.pause(50);
        try {
          sleep(50);
        }catch (InterruptedException e) {
          erreur.erreur("thread have been interupted");
        }
      }
    }
  }
}
