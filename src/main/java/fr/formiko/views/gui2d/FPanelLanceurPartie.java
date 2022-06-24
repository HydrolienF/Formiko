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
*@lastEditedVersion 1.x
*/
public abstract class FPanelLanceurPartie extends FPanel{
  private BoutonLong launchButton;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *It only build a launch button &#38; place it.
  *@param ac action of the launch button
  *@lastEditedVersion 1.x
  */
  public FPanelLanceurPartie(int ac){
    this.setLayout(null);
    launchButton = new BoutonLong(g.getM("lancerPartie"),getView().getPm(),ac);
    add(launchButton);
    int wi = Main.getDimX();
    int he = Main.getDimY();
    int wi2 = wi/2;
    launchButton.setBounds(wi2/2,Main.getDimY()-launchButton.getYBL(),wi2,launchButton.getYBL());
  }
  // GET SET -------------------------------------------------------------------
  public BoutonLong getLaunchButton(){return launchButton;}
  // FUNCTIONS -----------------------------------------------------------------
  public abstract Partie getPartie();
}
