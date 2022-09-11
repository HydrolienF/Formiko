package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

/**
*{@summary Main game container in GUI.}<br>
*This FFrame have screen size as default size and is named "Formiko".
*@author Hydrolien
*@lastEditedVersion 2.7
*/
public class FFrameMain extends FFrame {
  private FPanelPrincipal pp;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *@lastEditedVersion 2.7
  */
  public FFrameMain(){
    super("Formiko", Main.getFop().getInt("frameWidth"), Main.getFop().getInt("frameHeight"), Main.getFop().getBoolean("fullscreen"));
    pp = new FPanelPrincipal();
    this.setContentPane(pp);
    endIni();
  }

  // GET SET -------------------------------------------------------------------
  public FPanelPrincipal getPp(){ return pp;}

  // FUNCTIONS -----------------------------------------------------------------
}
