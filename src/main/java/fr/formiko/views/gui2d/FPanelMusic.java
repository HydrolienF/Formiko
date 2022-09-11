package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.erreur;
import fr.formiko.usual.structures.listes.Liste;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
*{@summary A music panel with all the possible music action.}
*:<br>
*Play/Pause, next, last<br>
*It also show current music name &#38; author.<br>
*@lastEditedVersion 2.28
*@author Hydrolien
*/
public class FPanelMusic extends FPanel {
  private FLabel musicDesc;
  private FPanelMusicButton pmub;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It launch a Thread that listen music changes & update it in GUI when needed.<br>
  *@lastEditedVersion 2.28
  */
  public FPanelMusic(){
    pmub = new FPanelMusicButton();
    pmub.setLocation(0,0);
    add(pmub);
    pmub.updateSize();
    musicDesc = new FLabel();
    add(musicDesc);
    Main.getMp().addListenerChangeMusic(musicDesc);
    // Thread that update music every time it change
    new Thread(){
      /**
      *{@summary Listen music changes & update it in GUI when needed.}<br>
      *@lastEditedVersion 2.28
      */
      @Override
      public void run(){
        while(true){
          try {
            synchronized (musicDesc) {
              musicDesc.wait();
            }
          }catch (InterruptedException e) {}
            String currentMusic=Main.getMp().getCurrentMusic();
            if(currentMusic.endsWith(".mp3")){
              currentMusic=currentMusic.substring(0,currentMusic.length()-4);
            }
            erreur.info("set music description to "+currentMusic);
            musicDesc.setText(currentMusic);
            pmub.updateSize();
        }
      }
    }.start();
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void updateColorButtonPause(){pmub.updateColorButtonPause();}

  /**
  *{@summary Update panel size depending of buttons, radius, margin &#38; music description label.}<br>
  *@lastEditedVersion 2.28
  */
  protected void updateSizePmu(){
    if(pmub!=null){
      setSize(pmub.getWidth(), pmub.getHeight());
      if(musicDesc!=null){
        musicDesc.updateSize();
        setSize(Math.max(pmub.getWidth(),musicDesc.getWidth()), pmub.getHeight()+musicDesc.getHeight());
        musicDesc.setLocation(0, pmub.getHeight());
        musicDesc.setFondTransparent();
        pmub.setLocation(musicDesc.getWidth()-pmub.getWidth(), 0);
        setLocation(getView().getPb().getWidth()-getWidth(), getY());
      }
    }
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary A music buton panel with all the possible music action.}
  *:<br>
  *Play/Pause, next, last<br>
  *@lastEditedVersion 2.28
  *@author Hydrolien
  */
  private class FPanelMusicButton extends FPanelRoundButtonsContainer {
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    *@lastEditedVersion 2.28
    */
    public FPanelMusicButton(){
      super((int)(Main.getFop().getInt("fontSizeTitle")/1.8), 0.3);
    }

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Add all button to the FPanel.}<br>
    *@lastEditedVersion 2.28
    */
    @Override
    protected void addButtons(){
      buttonList.add(new FButtonPGO(395, getArrowImage(false), () -> {
        return false;
      }));
      buttonList.add(new FButtonPGO(396, getPauseImage(), () -> {
        return !Main.getMp().isRunning();
      }));
      buttonList.add(new FButtonPGO(397, getArrowImage(true), () -> {
        return false;
      }));
    }
    /**
    *{@summary Return a next or last image.}<br>
    *@param turnRigth if trun arrow is turn to the rigth
    *@lastEditedVersion 2.28
    */
    public BufferedImage getArrowImage(boolean turnRigth){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      g.setColor(Color.BLACK);
      if(g instanceof Graphics2D){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //good for all drawLine, drawCircle etc.
      }
      drawLine(g, 0.3, 0.3, 0.3, 0.7);
      drawLine(g, 0.7, 0.3, 0.7, 0.7);
      if(turnRigth){
        drawLine(g, 0.7, 0.5, 0.3, 0.7);
        drawLine(g, 0.7, 0.5, 0.3, 0.3);
      }else{
        drawLine(g, 0.3, 0.5, 0.7, 0.7);
        drawLine(g, 0.3, 0.5, 0.7, 0.3);
      }
      return bi;
    }
    /**
    *{@summary Return a pause image.}<br>
    *@lastEditedVersion 2.28
    */
    public BufferedImage getPauseImage(){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      g.setColor(Color.BLACK);
      if(g instanceof Graphics2D){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
      }
      drawLine(g, 0.4, 0.25, 0.4, 0.75);
      drawLine(g, 0.6, 0.25, 0.6, 0.75);
      return bi;
    }

    /**
    *{@summary Update the color of the button pause.}<br>
    *This is call when music have changed or being pause/resume.
    *@lastEditedVersion 2.28
    */
    public void updateColorButtonPause(){
      for (FButtonPGO fb : buttonList) {
        if(fb.getActionB()==396){
          fb.updateColor();
          return;
        }
      }
    }
    /**
    *{@summary Update panel size depending of buttons, radius, margin &#38; music description label.}<br>
    *@lastEditedVersion 2.28
    */
    @Override
    protected void updateSize(){
      super.updateSize();
      updateSizePmu();
    }
  }
}
