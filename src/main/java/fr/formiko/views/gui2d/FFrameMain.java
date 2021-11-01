package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

/**
*{@summary Main game container in GUI.}<br>
*This FFrame have screen size as default size and is named "Formiko".
*@author Hydrolien
*@version 2.7
*/
public class FFrameMain extends FFrame {
  private PanneauPrincipal pp;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@version 2.7
  */
  public FFrameMain(){
    super("Formiko", Main.getOp().getFrameWidth(), Main.getOp().getFrameHeight(), Main.getFullscreen());
    pp = new PanneauPrincipal();
    this.setContentPane(pp);
    endIni();
  }

  // GET SET -------------------------------------------------------------------
  public PanneauPrincipal getPp(){ return pp;}

  // FUNCTIONS -----------------------------------------------------------------
}
