package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.FPanel;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.*;
import fr.formiko.usual.types.str;

import java.awt.Image;
import java.io.File;

/**
*{@summary Static class to initialize FPanel.}<br>
*@author Hydrolien
*@lastEditedVersion 2.12
*/
public class ini {

  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Initialize empty FPanel of the Menu.}<br>
  *@lastEditedVersion 2.12
  */
  public static void initializeEmptyFPanel(){
    FPanelPrincipal pp = FPanel.getView().getPp();
    pp.build();
    pp.addPm();
    // pp.getPm().build();
    //le Menu est fonctionnel ici.
  }
  /**
  *{@summary Initialize empty FPanel of the Action game &#39; sub panel.}<br>
  *@lastEditedVersion 2.12
  */
  public static void initializeFPanelJeuAndSubpanel(){
    if(FPanel.getView().getPj()!=null){
      erreur.alerte("This function should be call only 1 time by fenetre.");
    }
    FPanel.getView().getPp().addPj();
    FPanel.getView().getPj().setBounds(0,0,Main.getDimX(),Main.getDimY());
    //pp have it's 2 subpanel
    FPanel.getView().getPj().addPe();//add empty panel at 1a plan.
    FPanel.getView().getPj().addPch();
    FPanel.getView().getPj().addPsd();
    FPanel.getView().getPj().addPfp();//add empty panel.
    FPanel.getView().getPj().addPd();
    FPanel.getView().getPj().addPmmo();
    FPanel.getView().getPj().addPb();
    FPanel.getView().getPj().addPs();
    FPanel.getView().getPj().addPText();
    FPanel.getView().getPj().addPc();
    //pj have all his subpanel
    FPanel.getView().getPb().build();//many unseable element are initialize here
    FPanel.getView().getPc().build();
    FPanel.getView().getPmmo().addSubPanel(FPanel.getView().getPc());
    FPanel.getView().getPmmo().addSubPanel(FPanel.getView().getPs());
    FPanel.getView().getPmmo().build();
    keys.addBindings();
  }
}
