package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.CheckFunction;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.allea;
import fr.formiko.usual.structures.listes.GString;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
*{@summary A loading screen to wait for game launch.}<br>
*@lastEditedVersion 2.21
*/
public class FPanelChargement extends FPanel {
  private FButton b;
  // private int tempsTotalDeChargement;
  private FLabel message;
  private FTextArea conseil;
  private FPanelCheckFunction pcf;
  private boolean lancer;
  private FButton bt;
  private FProgressBar progressBar;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that add message &#38; advice.}<br>
  *@lastEditedVersion 2.21
  */
  public FPanelChargement(){
    setLayout(null);
    addMessage();
    addAdvice();
    // addPcf();
    lancer=false;
    bt=null;
  }
  // GET SET -------------------------------------------------------------------
  public void setTexte(String s){message.setTexte(s);}
  /**
  *{@summary Update the percentage done of the ProgressBar for loading game status.}<br>
  *@lastEditedVersion 2.30
  */
  public void setPercentageDone(int percentageDone){
    progressBar.setValue(percentageDone);
  }
  public boolean getLancer(){return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public boolean canBeClose(){return bt!=null;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Draw this if partie is running.}<br>
  *@lastEditedVersion 2.21
  */
  public void paintComponent(Graphics g){
    if(Main.getPartie()!=null && !Main.getPartie().getEnCours()){return;}
    // debug.g("FPanelChargement",this.getWidth(),this.getHeight());
    if(Main.getData().getImageChargement()!=null){
      g.drawImage(Main.getData().getImageChargement(),0,0,this);
    }
  }
  /**
  *{@summary Add a launch button.}<br>
  *@lastEditedVersion 2.21
  */
  public void addBt(){
    setLancer(false);
    bt = new FButton(g.getM("lancerLeJeu"), FPanel.getView().getPj(), 111);
    // bt.setFont(Main.getFontTitle());
    add(bt);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    bt.setBounds((int)(xx*1.5),yy*4+Main.getFop().getInt("fontSizeText"),xx*2,Main.getFop().getInt("fontSizeTitle"));
  }
  /**
  *{@summary Add a loaging message.}<br>
  *@lastEditedVersion 2.21
  */
  private void addMessage(){
    message = new FLabel();
    message.setTexte("");
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    message.setBounds(xx,yy*4-Main.getFop().getInt("fontSizeText"),xx*3);
    message.setBackground(new Color(0,0,0,0));
    message.setOpaque(false);
    add(message);
    progressBar = new FProgressBar();
    progressBar.setMaximum(100);
    progressBar.setBounds(xx,yy*4-Main.getFop().getInt("fontSizeText"),xx*3, FLabel.getDimY());
    add(progressBar);
  }
  /**
  *{@summary Add an advice.}<br>
  *@lastEditedVersion 2.21
  */
  private void addAdvice(){
    int x = allea.getAlléa(19)+1;//de 1 a 19.
    String s = g.getM("conseil."+x);
    conseil = new FTextArea(s,(Main.getF().getWidth()*3)/5);
    // conseil.setMinimumSize(new Dimension((Main.getF().getWidth()*3)/5, FLabel.getDimY()));
    add(conseil);
    conseil.setLocation(Main.getF().getWidth()/5,(Main.getF().getHeight()/5*4)-(2*Main.getFop().getInt("fontSizeText"))-conseil.getHeight());
    repaint();
  }
  /**
  *{@summary Add an FPanelCheckFunction to be able not to wait for this panel.}<br>
  *@lastEditedVersion 2.28
  */
  private void addPcf(){
    // TODO fix location & size
    CheckFunction cf = new CheckFunction(){
      /**
      *{@summary Update Options value if user have enable checkbox.}<br>
      *@lastEditedVersion 2.26
      */
      @Override
      protected void exec(){
        Main.getFop().set("whaitBeforeLaunchGame",true);
        Main.saveOp();
      }
    };
    cf.setText(g.get("dontAskAgain"));
    FPanelCheckFunction pcf = new FPanelCheckFunction(cf);
    pcf.setLocation(Main.getF().getWidth()/5,(Main.getF().getHeight()/5*4)+(2*Main.getFop().getInt("fontSizeText"))-conseil.getHeight());
    pcf.setSize((Main.getF().getWidth()*3)/5, FLabel.getDimY());
    add(pcf);
  }
  /**
  *{@summary Set visible &#38; load image if it haven't been done yet.}<br>
  *@lastEditedVersion 2.21
  */
  @Override
  public void setVisible(boolean visible){
    if(visible){
      Main.iniCptMessageChargement();
      Main.getData().loadImageChargement();
    }
    super.setVisible(visible);
  }
}
