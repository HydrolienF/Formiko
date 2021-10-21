package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.Panneau;
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
  //arboressence des Panneaux
  public static void initialiserToutLesPaneauxVide(){
    PanneauPrincipal pp = Panneau.getView().getPp();
    pp.build();
    pp.addPm();
    pp.getPm().build();
    //le Menu est fonctionnel ici.
  }
  public static void initialiserPanneauJeuEtDépendance(){
    if(Panneau.getView().getPj()!=null){
      erreur.alerte("This function should be call only 1 time by fenetre.");
    }
    Panneau.getView().getPp().addPj();
    Panneau.getView().getPj().setBounds(0,0,Main.getDimX(),Main.getDimY());
    Panneau.getView().getPj().addPs();
    //le panneau pp a ses 2 sous panneaux
    Panneau.getView().getPj().addPe();//ajoute le panneau complètement vide juste pour qu'il soit au 1a plan
    Panneau.getView().getPj().addPch();
    Panneau.getView().getPj().addPfp();//add empty panel.
    Panneau.getView().getPj().addPd();
    Panneau.getView().getPj().addPb();
    Panneau.getView().getPj().addPc();
    //pj a ses 4 sous panneau
    Panneau.getView().getPb().build();//plein d'élément non visible sont initialiser ici.
    Panneau.getView().getPc().build();
    keys.addBindings();
  }
}
