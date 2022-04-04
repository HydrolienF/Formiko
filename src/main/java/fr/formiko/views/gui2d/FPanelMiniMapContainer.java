package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.Liste;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import fr.formiko.formiko.Blade;
import fr.formiko.formiko.BladeGrass;
import java.util.function.Supplier;

/**
*{@summary Panel that contain MiniMap, fBEndTurn button &#38; graphics options buttons.}<br>
*@author Hydrolien
*@lastEditedVersion 2.5
*/
public class FPanelMiniMapContainer extends FPanel {
  private static int BUTTON_RADIUS=18;
  private FButtonEndTurn fBEndTurn;
  private FPanelGraphicsOptions pgo;
  private FPanelMiniMap pmm;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.5
  */
  public FPanelMiniMapContainer() {
    super();
    fBEndTurn = new FButtonEndTurn();
    add(fBEndTurn);
    pgo = new FPanelGraphicsOptions();
    add(pgo);
    pmm = new FPanelMiniMap();
    pmm.setLocation(Main.getTailleElementGraphiqueX(BUTTON_RADIUS),Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    add(pmm);
    setSize(pmm.getWidth()+Main.getTailleElementGraphiqueX(BUTTON_RADIUS), pmm.getHeight()+Main.getTailleElementGraphiqueY(BUTTON_RADIUS));
    setLocation(FPanel.getView().getWidth()-getWidth(), FPanel.getView().getHeight()-getHeight());
    pgo.setSize(getWidth(),Main.getTailleElementGraphiqueX((int)(BUTTON_RADIUS*1.6)));
    pgo.setLocation(0,(int)(BUTTON_RADIUS*0.4));
  }
  public void build(){
    pgo.build();
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@lastEditedVersion 2.5
  */
  public void setAllActionDone(boolean b){
    fBEndTurn.setAllActionDone(b);
  }
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@lastEditedVersion 2.5
  */
  public void setFbetEnabled(boolean b){
    fBEndTurn.setEnabled(b);
  }
  /**
  *{@summary Shortcut for fBEndTurn.}<br>
  *@lastEditedVersion 2.5
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
  *@lastEditedVersion 2.5
  */
  class FButtonEndTurn extends FButton {
    private int lineSize;
    private Color color;
    private ThreadColor thCol;
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}<br>
    *@lastEditedVersion 2.5
    */
    public FButtonEndTurn() {
      super("", FPanel.getView().getPj(), 200);
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
    *@lastEditedVersion 2.5
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
    *@lastEditedVersion 2.5
    */
    @Override
    public void setEnabled(boolean b){
      super.setVisible(b);
      super.setEnabled(b);
    }

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Draw function for the FButton.}<br>
    *@lastEditedVersion 2.5
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
    *@lastEditedVersion 2.5
    */
    class ThreadColor extends Thread {
      private boolean bGreen=false;
      /**
      *{@summary Main function that call changeColor.}<br>
      *@lastEditedVersion 2.5
      */
      @Override
      public void run(){
        changeColor();
      }
      public void setGreen(){bGreen = true;}
      /**
      *{@summary Change color from green to red.}<br>
      *@lastEditedVersion 2.5
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
  *It can remove/add grid, or icon. Check {@link FPanelCarte#getStatesIconsImages(Creature)} to know the action that can be done from here.
  *@author Hydrolien
  *@lastEditedVersion 2.10
  */
  class FPanelGraphicsOptions extends FPanel {
    private Liste<FButtonPGO> buttonList;
    /**
    *{@summary Main constructor.}<br>
    *This still need to be build after have set size.
    *@lastEditedVersion 2.10
    */
    private FPanelGraphicsOptions(){
      super();
      setSize(1,1);
      buttonList = new Liste<FButtonPGO>();
    }
    private boolean isIni(){return !buttonList.isEmpty();}
    /**
    *{@summary Build function that add all the button.}<br>
    *@lastEditedVersion 2.16
    */
    private void build(){
      if(getWidth()==1 || isIni()){return;}
      addGraphicOption(350, getGridImage(4), () -> {
        return Main.getOp().getDrawGrid();
      });
      addGraphicOption(351, getRelationImage(), () -> {
        return Main.getOp().getDrawRelationsIcons();
      });
      addGraphicOption(352, getStateImage(), () -> {
        return Main.getOp().getDrawStatesIconsLevel();
      });
      addGraphicOption(353, getColoredRoundImage(false), () -> {
        return Main.getOp().getDrawAllAnthillColor();
      });
      addGraphicOption(354, getLineImage(), () -> {
        return Main.getOp().getDrawPlayerMessagePanel();
      });
      addGraphicOption(355, getSeedNeutralImage(), () -> {
        return Main.getOp().getDrawOnlyEatable();
      });
      addGraphicOption(356, getColoredRoundImage(true), () -> {
        return Main.getOp().getAntColorLevel();
      });
      addGraphicOption(357, getBladesImage(), () -> {
        return Main.getOp().getDrawBlades();
      });
      placeButtons();
    }
    /**
    *{@summary Add a graphic option as a FButton.}<br>
    *@param action the action of the button (between 350 &#38; 399)
    *@param icon the image of the button
    *@param sup a function that return a boolean (is enable), or a int/byte corresponding to a color id
    *@lastEditedVersion 2.10
    */
    private void addGraphicOption(int action, BufferedImage icon, Supplier sup){
      buttonList.add(new FButtonPGO(action, icon, sup));
    }
    /**
    *{@summary Place all buttons &#38; add it to this.}<br>
    *@lastEditedVersion 2.10
    */
    private void placeButtons(){
      int len = buttonList.length()+1;
      int k=1;
      for (FButtonPGO fb : buttonList) {
        fb.setSize(getHeight(), getHeight());
        fb.setLocation((k++)*(getWidth()-(getHeight()/2))/len+(getHeight()/2),0);
        fb.setBorderPainted(false);
        add(fb);
      }
    }
    /**
    *{@summary Return a grid image with len line &#38; len column.}<br>
    *@param len the number of line &#38; column
    *@lastEditedVersion 2.10
    */
    private BufferedImage getGridImage(int len){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      g.setColor(Color.BLACK);
      for (int i=0; i<len;i++ ) {
        int x = (int)((i+0.5)*getHeight()/len);
        // int disfase = (getHeight()/2) - x;
        // if(x>getHeight()/2){disfase=x - getHeight();}
        g.drawLine(x,0,x,getHeight());
        g.drawLine(0,x,getHeight(),x);
      }
      return bi;
    }
    /**
    *{@summary Return a text image.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getLineImage(){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      g.setColor(Color.BLACK);
      if(g instanceof Graphics2D){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
      }
      int len = 3;
      for (int i=0; i<len;i++ ) {
        int x = (int)(((i*0.5)+1)*getHeight()/len);
        g.drawLine((getHeight())/3,x,(getHeight()*2)/3,x);
      }
      return bi;
    }
    /**
    *{@summary Return a grass blade image.}<br>
    *@lastEditedVersion 2.16
    */
    private BufferedImage getBladesImage(){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      g.setColor(new Color(11,93,16));
      g.drawLine(getHeight()*2/6, getHeight()/3, (int)(getHeight()*2.5/6), getHeight()*2/3);
      g.drawLine(getHeight()*3/6, getHeight()/3, getHeight()*3/6, getHeight()*2/3);
      g.drawLine(getHeight()*4/6, getHeight()/3, (int)(getHeight()*3.5/6), getHeight()*2/3);
      // Blade b = new BladeGrass();
      // b.setLength((byte)(getHeight()/3));
      // b.draw(g,getHeight()/3,getHeight()*2/3);
      // b = new BladeGrass();
      // b.setLength((byte)(getHeight()/3));
      // b.draw(g,getHeight()*2/3,getHeight()*2/3);
      return bi;
    }
    /**
    *{@summary Return a colored round image as anthill color are draw.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getColoredRoundImage(boolean small){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      int size = getHeight()/2;
      Graphics g = bi.getGraphics();
      if(small){
        g.setColor(new Color(255,0,0,255));
      }else{
        g.setColor(Color.MAGENTA);
      }
      if(g instanceof Graphics2D){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(size/4));
      }
      if(small){
        g.fillOval(size*2/3+1,size/2,size/2,size*2/3);
        g.setColor(Color.BLACK);
        g.fillOval(size*2/3+1,1,size/2,size/2);
        g.fillOval(size*2/3+1,size,size/2,size);
      }else{
        g.drawOval(size/2,size/2,size,size);
      }
      return bi;
    }
    /**
    *{@summary Return a relation image.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getRelationImage(){
      Liste<BufferedImage> list = new Liste<BufferedImage>();
      list.add(getView().getPc().getIconImage(0));
      list.add(getView().getPc().getIconImage(1));
      list.add(getView().getPc().getIconImage(2));
      return getListeImage(list);
    }
    /**
    *{@summary Return a state image.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getStateImage(){
      Liste<BufferedImage> list = new Liste<BufferedImage>();
      list.add(getView().getPc().getStateIconImage(Main.getData().getButtonColor(2),getView().getPc().getTBIState()[0]));
      list.add(getView().getPc().getStateIconImage(Main.getData().getButtonColor(3),getView().getPc().getTBIState()[1]));
      return getListeImage(list);
    }
    /**
    *{@summary Return a state image.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getSeedNeutralImage(){
      Liste<BufferedImage> list = new Liste<BufferedImage>();
      list.add(getView().getPc().getIconImage(1));
      list.add(getView().getPc().getIconImage(4));
      return getListeImage(list);
    }
    /**
    *{@summary Return a small image from a list of images.}<br>
    *@lastEditedVersion 2.10
    */
    private BufferedImage getListeImage(Liste<BufferedImage> listIn){
      BufferedImage bi = new BufferedImage(getHeight(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = bi.getGraphics();
      int size = getHeight()/2;
      Liste<BufferedImage> list = new Liste<BufferedImage>();
      for (BufferedImage biTemp : listIn) {
        if(biTemp!=null && biTemp.getWidth()>0){
          list.add(image.resize(biTemp,size,size));
        }
      }
      getView().getPc().drawListIcons(g,list,size/2,size/2,0);
      return bi;
    }
  }
}
