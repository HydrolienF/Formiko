package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.FPanel;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.*;
import fr.formiko.usuel.types.str;

import java.awt.Image;
import java.io.File;

public class ini {

  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  //arboressence des FPanelx
  public static void initialiserToutLesPaneauxVide(){
    FPanelPrincipal pp = FPanel.getView().getPp();
    pp.build();
    pp.addPm();
    pp.getPm().build();
    //le Menu est fonctionnel ici.
  }
  public static void initialiserFPanelJeuEtDépendance(){
    if(FPanel.getView().getPj()!=null){
      erreur.alerte("This function should be call only 1 time by fenetre.");
    }
    FPanel.getView().getPp().addPj();
    FPanel.getView().getPj().setBounds(0,0,Main.getDimX(),Main.getDimY());
    //le panneau pp a ses 2 sous panneaux
    FPanel.getView().getPj().addPe();//ajoute le panneau complètement vide juste pour qu'il soit au 1a plan
    FPanel.getView().getPj().addPch();
    FPanel.getView().getPj().addPfp();//add empty panel.
    FPanel.getView().getPj().addPd();
    FPanel.getView().getPj().addPb();
    FPanel.getView().getPj().addPs();
    FPanel.getView().getPj().addPc();
    //pj a ses 4 sous panneau
    FPanel.getView().getPb().build();//plein d'élément non visible sont initialiser ici.
    FPanel.getView().getPc().build();
    keys.addBindings();
  }
}
