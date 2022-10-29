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

/**
*{@summary Formiko JPanel implementation.}<br>
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public class FPanel extends JPanel {
  private static int cptId=0;
  protected final int id;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It have an id, a null layout &#38; is translucent
  *@lastEditedVersion 2.30
  */
  public FPanel(){
    super();
    id=cptId++;
    setLayout(null);
    setOpaque(false);
  }
  // GET SET -------------------------------------------------------------------
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
    if(Main.getFop().getBoolean("paintHitBox")){
      Graphics2D g = (Graphics2D)gTemp;
      g.setColor(Color.RED);
      g.setStroke(new BasicStroke(math.max(getWidth()/100,getHeight()/100,1)));
      g.drawRect(0,0,getWidth(),getHeight());
    }
  }
  /**
  *{@summary toString with id.}<br>
  *@lastEditedVersion 2.30
  */
  @Override
  public String toString(){
    String r = id+" "+super.toString();
    // r+=" components: \n";
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
  /**
  *{@summary Center this on parent component.}<br>
  *@lastEditedVersion 2.30
  */
  public void centerInParent(){
    if(getParent()==null){
      erreur.alerte("can't center component with null parent.");
    }else{
      setLocation((getParent().getWidth()-getWidth())/2,(getParent().getHeight()-getHeight())/2);
    }
  }
}
