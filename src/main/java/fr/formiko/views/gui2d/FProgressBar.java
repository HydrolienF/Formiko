package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;

import java.awt.Color;
import javax.swing.JProgressBar;

/**
*{@summary A personalized JProgressBar.}<br>
*@version 2.7
*@author Hydrolien
*/
public class FProgressBar extends JProgressBar {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.7
  */
  public FProgressBar(){
    super();
    setBorderPainted(true);
    // setBorder(new FBorder());
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Set state represent by a color.}<br>
  *state can be set to every aviable button color.
  *@version 2.7
  */
  public void setState(int state){
    Color c = Main.getData().getButtonColor();
    if(c!=null){
      setBackground(c);
    }else{
      erreur.alerte("Can't set state of the FProgressBar because color is null");
    }
  }
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------

}
