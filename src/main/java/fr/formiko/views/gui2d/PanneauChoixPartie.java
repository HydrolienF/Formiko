package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.types.str;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.sauvegarderUnePartie;

import java.awt.Graphics;
import java.io.File;

public class PanneauChoixPartie extends PanneauLanceurPartie{
  private EtiquetteChoix ePartie;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauChoixPartie(){
    super(101);
    this.setLayout(null);
    GString gs = new GString(sauvegarderUnePartie.listSave());
    GString gs2 = new GString();
    boolean emptyList = true;
    for (String s : gs) {
      try {
        gs2.add(s.substring(0,s.length()-5));
        emptyList = false;
      }catch (Exception e) {}
    }
    ePartie = new EtiquetteChoix("choisirPartie",gs2);
    //ePartie.setBounds(getWidth()/5,getHeight()/5,getWidth()*3/5,getHeight()*3/5);
    //ePartie.setTaille(getWidth()*1/5,getHeight()*1/5);
    add(ePartie);
    if (emptyList) {
      getLaunchButton().setEnabled(false);
    }
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    ePartie.setBounds(getWidth()/5,getHeight()/5,getWidth()*3/5,(int)(Main.getTaillePolice2()*1.2));
    // ePartie.setTaille(getWidth()*3/5,(int)(Main.getTaillePolice2()*1.2));
    //ePartie.setPolice(Main.getFont2());
  }
  public Partie getPartie(){
    String partieName = ePartie.getSelectedItem()+"";
    str.addALaFinSiNecessaire(partieName,".save");
    Partie pa = sauvegarderUnePartie.charger(partieName);
    return pa;
  }
}
