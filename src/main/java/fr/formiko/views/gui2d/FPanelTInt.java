package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class FPanelTInt extends FPanelTX{
  private int ti [];
  private int choix;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelTInt(int ti [],String desc){super();
    tailleBouton = Main.getFop().getInt("buttonSizeTX");
    this.descTI = desc;
    debug.débogage("Création d'un FPanelTInt");
    this.setLayout(new GridBagLayout());
    this.ti=ti;
    if(ti!=null){
      int nbrDeSquare = ti.length;
      if (nbrDeSquare > 0){getView().getPb().setDescTI(descTI);}
      else{((FPanelBouton) pb).setDescTI("");}
      x = ((int) (Math.sqrt(nbrDeSquare)))+1;
      y = x;
      //if (nbrDeSquare==9 || nbrDeSquare==4 || nbrDeSquare==16 || nbrDeSquare==25){x--;} // petite correction de la ligne 2 au dessus qui ne prend pas ses cas en compte.
      if (x*x > nbrDeSquare+x){ // si la dernière ligne n'est pas néssésaire
        y=x-1;
      }
      this.setSize(x*tailleBouton,y*tailleBouton);
      FButton tB [] = new FButton [nbrDeSquare];
      Dimension dim = new Dimension(tailleBouton,tailleBouton);
      for (int i=0;i<nbrDeSquare ;i++ ) {
        tB[i]=new FButton(ti[i]+"", getView().getPp().getPj(),40+i);
        tB[i].setFont(Main.getFontTitle());
      }
      for (FButton b :tB){b.setPreferredSize(dim);}
      GridBagConstraints gbc = new GridBagConstraints();
      int k=0;
      for (int i=0;i<x ;i++ ) {
        gbc.gridy = i;
        for (int j=0;j<y && k < nbrDeSquare;j++ ) {
          gbc.gridx = j;
          this.add(tB[k],gbc);k++;
        }
      }
      debug.débogage(k+" boutons ont été ajoutés");
      //Main.getF().repaint();
    }else{
      debug.débogage("initialisation null de pti.");
    }
  }
  public FPanelTInt(int t[],FPanelBouton pb){this(t,"null");}
  // GET SET -------------------------------------------------------------------
  public int getBoutonX(int x){ if(x > -1 && ti!=null && x < ti.length){return ti[x];}return -1;}
  // FUNCTIONS -----------------------------------------------------------------
  public static int getChoixId(){
    int id2 = -1;
    debug.débogage("lancement d'une boucle de choix.");
    while(id2==-1){
      id2 = getView().getPp().getPj().getPb().getChoixId();
      Time.pause(10);
    }
    getView().getPp().getPj().getPb().setChoixId(-1);
    getView().getPp().getPj().setDescTI("");
    getView().getPp().getPj().remove(getView().getPp().getPj().getPti());
    return id2;
  }
}
