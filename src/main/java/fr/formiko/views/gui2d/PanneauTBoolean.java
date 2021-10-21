package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.types.str;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanneauTBoolean extends PanneauTX{
  private int choix;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauTBoolean(String desc){super();
    if(desc==null){ return;}
    tailleBouton = Main.getTailleBoutonTX();
    this.descTI = desc;
    debug.débogage("Création d'un PanneauTBoolean");
    this.setLayout(new GridBagLayout());
    int nbrDeCase = 2;
    if (nbrDeCase > 0){getView().getPb().setDescTI(descTI);}
    else{((PanneauBouton) pb).setDescTI("");}
    x = 2;
    y = 1;
    this.setSize(x*tailleBouton,y*tailleBouton);
    FButton tB [] = new FButton [nbrDeCase];
    Dimension dim = new Dimension(tailleBouton,tailleBouton);
    //tB[0]=new FButton(g.get("OUI"), getView().getPp().getPj(),40);
    //tB[1]=new FButton(g.get("NON"), getView().getPp().getPj(),41);
    tB[0]=new FButton("O", getView().getPp().getPj(),40);
    tB[1]=new FButton("N", getView().getPp().getPj(),41);
    for (FButton b :tB){b.setPreferredSize(dim);}
    GridBagConstraints gbc = new GridBagConstraints();
    int k=0;
    gbc.gridx = 0;
    for (int j=0;k < nbrDeCase;j++ ) {
      gbc.gridy = j;
      this.add(tB[k],gbc);k++;
    }
    debug.débogage(k+" boutons ont été ajoutés");
  }
  // GET SET -------------------------------------------------------------------
  // FUNCTIONS -----------------------------------------------------------------
  public static boolean getChoixId(){
    int id2 = -1;
    debug.débogage("lancement d'une boucle de choix.");
    //Panneau.getView().getPj().repaint();
    while(id2==-1){
      id2 = getView().getPp().getPj().getPb().getChoixId();
      Temps.pause(10);
    } // on a reçu 0 ou 1.
    boolean b = str.iToB(id2);
    getView().getPp().getPj().getPb().setChoixId(-1);
    getView().getPp().getPj().setDescTI("");
    getView().getPp().getPj().remove(getView().getPp().getPj().getPti());
    return b;
  }
}
