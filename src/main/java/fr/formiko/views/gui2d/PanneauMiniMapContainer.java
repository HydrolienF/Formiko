package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

/**
*{@summary Panel that contain MiniMap, fBEndTurn button &#38; graphics options buttons.}<br>
*@author Hydrolien
*@version 2.5
*/
public class PanneauMiniMapContainer extends Panneau {
  private static int BUTTON_RADIUS=18;
  private FButtonEndTurn fBEndTurn;
  private PanneauGraphicsOptions pgo;
  private PanneauMiniMap pmm;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.5
  */
  public PanneauMiniMapContainer() {
    super();
    fBEndTurn = new FButtonEndTurn();
    add(fBEndTurn);
    pgo = new PanneauGraphicsOptions();
    add(pgo);
    pmm = new PanneauMiniMap();
    pmm.setLocation(Main.getTailleElementGraphiqueX(BUTTON_RADIUS),Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    add(pmm);
    setSize(pmm.getWidth()+Main.getTailleElementGraphiqueX(BUTTON_RADIUS), pmm.getHeight()+Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    setLocation(Panneau.getView().getWidth()-getWidth(), Panneau.getView().getHeight()-getHeight());
    pgo.setSize(getWidth(),Main.getTailleElementGraphiqueX((int)(BUTTON_RADIUS*1.6)));
    pgo.setLocation(0,(int)(BUTTON_RADIUS*0.4));
    pgo.build();
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@version 2.5
  */
  public void setAllActionDone(boolean b){
    fBEndTurn.setAllActionDone(b);
  }
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@version 2.5
  */
  public void setFbetEnabled(boolean b){
    fBEndTurn.setEnabled(b);
  }
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@version 2.5
  */
  public boolean getFbetEnabled(){
    return fBEndTurn.isEnabled();
  }


  // FUNCTIONS -----------------------------------------------------------------


  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary FButton to end turn.}<br>
  *This button is green when player still have action to do otherwise red.
  *@author Hydrolien
  *@version 2.5
  */
  class FButtonEndTurn extends FButton {
    private int lineSize;
    private Color color;
    private ThreadColor thCol;
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    *@version 2.5
    */
    public FButtonEndTurn() {
      super("", Panneau.getView().getPj(), Main.getTailleElementGraphique(200));
      setSize(Main.getTailleElementGraphique(BUTTON_RADIUS*2));
      setLocation(0,0);
      lineSize = Main.getTailleElementGraphique(3);
      setBorderPainted(false);
      boolean b = false;
      try {
        b=Main.getPlayingJoueur().getFere().getGc().haveDoneAllActionAviable();
      }catch (NullPointerException e) {}
      setAllActionDone(b);
    }

    // GET SET -------------------------------------------------------------------
    /**
    *{@summary change color of the button depending of player's actions left.}<br>
    *Green if player still have action to do otherwise red.<br>
    *If animations are enabled it will take 0,255 s to turn red.<br>
    *@version 2.5
    */
    public void setAllActionDone(boolean b){
      if(b){
        if(Main.getOp().getAnimationEnable() && getView().getActionGameOn()){
          if(!color.equals(Color.GREEN)){return;} //if color is not Green (is red or transforming to red) don't do anything.
          try {
            if(thCol==null || thCol.getState()==Thread.State.TERMINATED){
              thCol = new ThreadColor();
              thCol.start();
            }
          }catch (Exception e) {
            erreur.alerte("fail to set color as animation");
            color = Color.RED;
          }
        }else{
          color = Color.RED;
        }
      }else{
        //if is still turning red.
        if(Main.getOp().getAnimationEnable() && getView().getActionGameOn() && thCol!=null && thCol.getState()!=Thread.State.TERMINATED){
          thCol.setGreen();
        }else{
          color = Color.GREEN;
        }
      }
    }
    /**
    *{@summary setEnabled that also hide button if disable.}<br>
    *@version 2.5
    */
    @Override
    public void setEnabled(boolean b){
      super.setVisible(b);
      super.setEnabled(b);
    }

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Draw function for the FButton.}<br>
    *@version 2.5
    */
    public void paintComponent(Graphics gTemp){
      Graphics2D g = (Graphics2D)gTemp;
      BasicStroke line = new BasicStroke(lineSize);
      g.setStroke(line);
      g.setColor(color);
      g.fillOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
      g.setColor(Color.BLACK);
      g.drawOval(lineSize/2,lineSize/2,getWidth()-lineSize,getHeight()-lineSize);
    }
    /**
    *{@summary To change color from green to red.}<br>
    *@author Hydrolien
    *@version 2.5
    */
    class ThreadColor extends Thread {
      private boolean bGreen=false;
      /**
      *{@summary Main function that call changeColor.}<br>
      *@version 2.5
      */
      @Override
      public void run(){
        changeColor();
      }
      public void setGreen(){bGreen = true;}
      /**
      *{@summary Change color from green to red.}<br>
      *@version 2.5
      */
      private void changeColor(){
        for (int i=0; i<255; i+=5) {
          Temps.pause(5);
          if(bGreen){
            color=Color.GREEN;
            bGreen=false;
            return;
          }
          // if(red){
          color = new Color(math.between(0,255,i),math.between(0,255,255-i),0);
          // }else{
          //   color = new Color(math.between(0,255,255-i),math.between(0,255,i),0);
          // }
        }
      }
    }
  }
  /**
  *{@summary Change map face.}<br>
  *It can remove/add grid, or icon. Check {@link PanneauCarte#getStatesIconsImages(Creature)} to know the action that can be done from here.
  *@author Hydrolien
  *@version 2.10
  */
  class PanneauGraphicsOptions extends Panneau {
    private Liste<FButtonPGO> buttonList;
    /**
    *{@summary Main constructor.}<br>
    *This still need to be build after have set size.
    *@version 2.10
    */
    private PanneauGraphicsOptions(){
      super();
      setSize(1,1);
      buttonList = new Liste<FButtonPGO>();
    }
    /**
    *{@summary Build function that add all the button.}<br>
    *@version 2.10
    */
    private void build(){
      if(getWidth()==1){return;}
      addGraphicOption(350, null, () -> {
        return Main.getOp().getDrawGrid();
      });
      addGraphicOption(351, null, () -> {
        return Main.getOp().getDrawRelationsIcons();
      });
      addGraphicOption(352, null, () -> {
        return Main.getOp().getDrawStatesIconsLevel();
      });
      // addGraphicOption(353, bi); etc
      placeButtons();
    }
    /**
    *{@summary Add a graphic option as a FButton.}<br>
    *@param action the action of the button (between 350 &#38; 399)
    *@param icon the image of the button
    *@param sup a function that return a boolean (is enable), or a int/byte corresponding to a color id
    *@version 2.10
    */
    private void addGraphicOption(int action, BufferedImage icon, Supplier sup){
      buttonList.add(new FButtonPGO(action, icon, sup));
    }
    /**
    *{@summary Place all buttons &#38; add it to this.}<br>
    *@version 2.10
    */
    private void placeButtons(){
      int len = buttonList.length();
      int k=1;
      for (FButtonPGO fb : buttonList) {
        fb.setSize(getHeight(), getHeight());
        fb.setLocation((k++)*(getWidth()-getHeight())/len,0);
        fb.setBorderPainted(false);
        add(fb);
      }
    }
  }
}
