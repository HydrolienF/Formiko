package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;
import fr.formiko.usual.types.str;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class FPanelTBoolean extends FPanelTX{
  // private int choix;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelTBoolean(String desc){super();
    if(desc==null){ return;}
    tailleBouton = Main.getFop().getInt("buttonSizeTX");
    this.descTI = desc;
    debug.débogage("Création d'un FPanelTBoolean");
    this.setLayout(new GridBagLayout());
    int nbrDeSquare = 2;
    if (nbrDeSquare > 0){getView().getPb().setDescTI(descTI);}
    else{((FPanelBouton) pb).setDescTI("");}
    x = 2;
    y = 1;
    this.setSize(x*tailleBouton,y*tailleBouton);
    FButton tB [] = new FButton [nbrDeSquare];
    Dimension dim = new Dimension(tailleBouton,tailleBouton);
    //tB[0]=new FButton(g.get("OUI"), getView().getPp().getPj(),40);
    //tB[1]=new FButton(g.get("NON"), getView().getPp().getPj(),41);
    tB[0]=new FButton("O", getView().getPp().getPj(),40);
    tB[1]=new FButton("N", getView().getPp().getPj(),41);
    for (FButton b :tB){b.setPreferredSize(dim);}
    GridBagConstraints gbc = new GridBagConstraints();
    int k=0;
    gbc.gridx = 0;
    for (int j=0;k < nbrDeSquare;j++ ) {
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
    //FPanel.getView().getPj().repaint();
    while(id2==-1){
      id2 = getView().getPp().getPj().getPb().getChoixId();
      Time.pause(10);
    } // on a reçu 0 ou 1.
    boolean b = str.iToB(id2);
    getView().getPp().getPj().getPb().setChoixId(-1);
    getView().getPp().getPj().setDescTI("");
    getView().getPp().getPj().remove(getView().getPp().getPj().getPti());
    return b;
  }
}
