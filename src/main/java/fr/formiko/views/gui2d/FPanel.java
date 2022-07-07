package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.maths.math;
import fr.formiko.views.gui2d.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FPanel extends JPanel {
  private static final long serialVersionUID = -3227223889149566494L;
  private static int cptId=0;
  protected final int id;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanel(){
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
  public BListener getBListener(){return new BListener();}
  public Data getData(){return Main.getData();}
  public static ViewGUI2d getView(){return (ViewGUI2d)(Main.getView());}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint function with a debug tool.}<br>
  *@lastEditedVersion 2.7
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
    String r = id+" "+super.toString()+" components: \n";
    // for (Component c : getComponents()) {
    //   r+=c.toString();
    // }
    return r;
  }
  /**
  *{@summary Remove a component if not null.}<br>
  *@param c component to remove
  *@lastEditedVersion 2.7
  */
  @Override
  public void remove(Component c){
    if(c!=null){
      super.remove(c);
    }
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
