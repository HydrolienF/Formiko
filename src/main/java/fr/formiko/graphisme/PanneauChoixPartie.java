package fr.formiko.graphisme;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.sauvegarderUnePartie;

import java.awt.Graphics;
import java.io.File;

public class PanneauChoixPartie extends PanneauLanceurPartie{
  public static String REPSAVE = "data/sauvegarde/";
  private EtiquetteChoix ePartie;
  private BoutonLong lancerPartie;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChoixPartie(){
    super(101);
    this.setLayout(null);
    //TODO afficher listePartie en faire choisir 1 et valider.
    /*String parties = listePartie();
    BoutonLong tb = new BoutonLong[parties.length()];
    for (BoutonLong  b : tb ) {
      b = new BoutonLong();
    }*/
    GString gs = new GString(listePartie());
    //TODO trier le gs par ordre alphabétique décroissant.
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
  private String [] listePartie(){
    File f = new File(REPSAVE);
    return f.list();
  }
  /*private void launch(String nom){
    Main.getPm().setPartie(Main.getPartieSave(REPSAVE+nom));
    Main.getPm().setLancer(true);
  }*/
  public Partie getPartie(){
    Partie pa = sauvegarderUnePartie.charger(ePartie.getSelectedItem()+"");
    return pa;
  }
}
