package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.image;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

/**
*{@summary Dialogue backgroud panel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.6
*/
public class FPanelDialogueInf extends FPanel {
  private static Image fond;
  private FLabel b;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Empty main constructor.}<br>
  *@lastEditedVersion 2.6
  */
  public FPanelDialogueInf(){
    super();
  }
  /**
  *{@summary Build function.}<br>
  *@lastEditedVersion 2.6
  */
  public void ini(){
    setSize(Main.getDimX(),Main.getTailleElementGraphiqueY(210));
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint function.}<br>
  *@lastEditedVersion 2.6
  */
  public void paintComponent(Graphics g){
    g.drawImage(fond,0,0,this);
    super.paintComponent(g);
  }
  /**
  *{@summary Load the backgrounf image.}<br>
  *@lastEditedVersion 2.6
  */
  public static void chargerFond(){
    fond = image.getImage("hautDeLaFenetre");
    fond = fond.getScaledInstance(Main.getDimX(), Main.getTailleElementGraphiqueY(210),Image.SCALE_SMOOTH);
  }
  /**
  *{@summary Add the FButton "next".}<br>
  *It choose best size #38; fix location.
  *@lastEditedVersion 2.6
  */
  public void addBSuivant(){
    b = new FLabel(g.get("suivant"));
    b.updateSize();
    b.setLocation(getView().getPd().getX()+getView().getPd().getWidth()-b.getWidth()-Main.getTailleElementGraphiqueX(20),getHeight()-b.getHeight()-Main.getTailleElementGraphiqueY(40));
    add(b);
  }
  /**
  *{@summary remove the FButton "next".}<br>
  *@lastEditedVersion 2.6
  */
  public void removeBSuivant(){
    remove(b);
  }
}
