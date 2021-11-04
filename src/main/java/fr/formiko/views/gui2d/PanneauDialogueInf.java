package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

/**
*{@summary Dialogue backgroud panel.}<br>
*@author Hydrolien
*@version 2.6
*/
public class PanneauDialogueInf extends Panneau {
  private static Image fond;
  private FLabel b;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Empty main constructor.}<br>
  *@version 2.6
  */
  public PanneauDialogueInf(){
    super();
  }
  /**
  *{@summary Build function.}<br>
  *@version 2.6
  */
  public void initialiser(){
    setSize(Main.getDimX(),Main.getTailleElementGraphiqueY(210));
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint function.}<br>
  *@version 2.6
  */
  public void paintComponent(Graphics g){
    g.drawImage(fond,0,0,this);
    super.paintComponent(g);
  }
  /**
  *{@summary Load the backgrounf image.}<br>
  *@version 2.6
  */
  public static void chargerFond(){
    fond = image.getImage("hautDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getTailleElementGraphiqueY(210),Image.SCALE_SMOOTH);
  }
  /**
  *{@summary Add the FButton "next".}<br>
  *It choose best size #38; fix location.
  *@version 2.6
  */
  public void addBSuivant(){
    b = new FLabel(g.get("suivant"));
    b.updateSize();
    b.setLocation(getView().getPd().getX()+getView().getPd().getWidth()-b.getWidth()-Main.getTailleElementGraphiqueX(20),getHeight()-b.getHeight()-Main.getTailleElementGraphiqueY(40));
    add(b);
  }
  /**
  *{@summary remove the FButton "next".}<br>
  *@version 2.6
  */
  public void removeBSuivant(){
    remove(b);
  }
}
