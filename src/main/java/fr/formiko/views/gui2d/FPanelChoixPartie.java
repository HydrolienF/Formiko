package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usual.types.str;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.formiko.sauvegarderUnePartie;

import java.awt.Graphics;
import java.io.File;

/**
*{@summary A partie launcher Panel from a save.}<br>
*@author Hydrolien
*@lastEditedVersion 1.x
*/
public class FPanelChoixPartie extends FPanelLanceurPartie {
  private EtiquetteChoix ePartie;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Create a new Panel that can launch a game.}
  * Action to launch game is 101.
  *@lastEditedVersion 2.22
  */
  public FPanelChoixPartie(){
    super(101);
    this.setLayout(null);
    GString gs2 = new GString(sauvegarderUnePartie.listSave());
    ePartie = new EtiquetteChoix("choisirPartie",gs2);
    add(ePartie);
    if (gs2.isEmpty()) {
      getLaunchButton().setEnabled(false);
    }
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    ePartie.setBounds(getWidth()/5,getHeight()/5,getWidth()*3/5,(int)(Main.getFontSizeTitle()*1.2));
    // ePartie.setTaille(getWidth()*3/5,(int)(Main.getFontSizeTitle()*1.2));
    //ePartie.setFontText(Main.getFont2());
    super.paintComponent(g);
  }
  public Partie getPartie(){
    // erreur.info("getPartie",20);
    String partieName = ePartie.getSelectedItem()+"";
    partieName = str.addALaFinSiNecessaire(partieName,".save");
    Partie pa = sauvegarderUnePartie.charger(partieName);
    return pa;
  }
}
