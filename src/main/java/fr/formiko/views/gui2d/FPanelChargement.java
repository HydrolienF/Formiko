package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.maths.allea;

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
  private boolean lancer;
  private FButton bt;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that add message &#38; advice.}<br>
  *@lastEditedVersion 2.21
  */
  public FPanelChargement(){
    setLayout(null);
    addMessage();
    addAdvice();
    lancer=false;
    bt=null;
  }
  // GET SET -------------------------------------------------------------------
  public void setTexte(String s){ message.setTexte(s);}
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
    // bt.setFont(Main.getFont2());
    add(bt);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    bt.setBounds((int)(xx*1.5),yy*4+Main.getFontSizeText(),xx*2,Main.getFontSizeTitle());
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
    message.setBounds(xx,yy*4-Main.getFontSizeText(),xx*3);
    add(message);
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
    conseil.setLocation(Main.getF().getWidth()/5,(Main.getF().getHeight()/5*4)-(2*Main.getFontSizeText())-conseil.getHeight());
    repaint();
  }
  /**
  *{@summary Set visible &#38; load image if it haven't been done yet.}<br>
  *@lastEditedVersion 2.21
  */
  @Override
  public void setVisible(boolean visible){
    if(visible){
      Main.getData().loadImageChargement();
    }
    super.setVisible(visible);
  }
}
