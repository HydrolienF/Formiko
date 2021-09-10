package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.views.gui2d.action;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BasicStroke;
import fr.formiko.usuel.maths.math;
import java.awt.Graphics2D;

public class Panneau extends JPanel {
  private static final long serialVersionUID = -3227223889149566494L;
  private static int cptId=0;
  private final int id;

  // CONSTRUCTORS --------------------------------------------------------------
  public Panneau(){
    super();
    id=cptId++;
    setLayout(null);
    setOpaque(false);
  }
  // GET SET -------------------------------------------------------------------
  //public int getTailleDUneCase(){return tailleDUneCase;}
  //public void setTailleDUneCase(int x){tailleDUneCase = x;}
  //public int getEspaceRéservéBas(){return espaceRéservéBas;}
  //public void setEspaceRéservéBas(int x){espaceRéservéBas = x;}
  //public void setXCase(int x){xCase = x;}
  //public void setYCase(int y){yCase = y;}
  public BListener getBListener(){ return new BListener();}
  public Data getData(){return Main.getData();}
  public static ViewGUI2d getView(){return (ViewGUI2d)(Main.getView());}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary paint function with a debug tool.}
  *@version 2.6
  */
  @Override
  public void paintComponent(Graphics gTemp){
    super.paintComponent(gTemp);
    if(Main.getOp().getPaintHitBox()){
      Graphics2D g = (Graphics2D)gTemp;
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(getWidth()/100,getHeight()/100,1)));
      g.drawRect(0,0,getWidth(),getHeight());
    }
  }
  @Override
  public String toString(){
    return id+" "+super.toString();
  }
  public void doAction(int ac){
    action.doAction(ac);
  }

  class BListener implements ActionListener{
    private int compteur=0;
    //Redéfinition de la méthode actionPerformed()
    public void actionPerformed(ActionEvent arg0) {
      //Lorsque l'on clique sur le bouton, on met à jour le JLabel
      compteur++;
    }
  }
}
