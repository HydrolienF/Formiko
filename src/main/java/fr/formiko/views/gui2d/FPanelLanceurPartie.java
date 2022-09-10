package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usual.debug;
import fr.formiko.usual.g;

import java.awt.Graphics;

/**
*{@summary A partie launcher Panel.}<br>
*It have a big "launch game" button.
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public abstract class FPanelLanceurPartie extends FPanel{
  private FButtonLong launchButton;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It only build a launch button &#38; place it.
  *@param ac action of the launch button
  *@lastEditedVersion 2.30
  */
  public FPanelLanceurPartie(int ac){
    this.setLayout(null);
    launchButton = new FButtonLong(g.getM("lancerPartie"),getView().getPm(),ac);
    add(launchButton);
    int helb = Main.getDimY()/10;
    launchButton.setBounds(Main.getDimX()/4,Main.getDimY()-helb,Main.getDimX()/2,helb);
  }
  // GET SET -------------------------------------------------------------------
  public FButtonLong getLaunchButton(){return launchButton;}
  // FUNCTIONS -----------------------------------------------------------------
  public abstract Partie getPartie();
}
