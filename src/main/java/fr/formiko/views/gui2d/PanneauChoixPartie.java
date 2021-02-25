package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.save;

import java.awt.Graphics;
import java.io.File;

public class PanneauChoixPartie extends PanneauLanceurPartie{
  private EtiquetteChoix ePartie;
  private BoutonLong lancerPartie;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChoixPartie(){
    super(101);
    this.setLayout(null);
    GString gs = new GString(save.listSave());
    ePartie = new EtiquetteChoix("choisirPartie",gs);
    //ePartie.setBounds(getWidth()/5,getHeight()/5,getWidth()*3/5,getHeight()*3/5);
    //ePartie.setTaille(getWidth()*1/5,getHeight()*1/5);
    add(ePartie);
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    ePartie.setBounds(getWidth()/5,getHeight()/5,getWidth()*3/5,getHeight()*3/5);
    ePartie.setTaille(getWidth()*3/5,(int)(Main.getTaillePolice2()*1.2));
    //ePartie.setPolice(Main.getFont2());
  }
  public Partie getPartie(){
    Partie pa = sauvegarderUnePartie.charger(ePartie.getSelectedItem()+"");
    return pa;
  }
}
