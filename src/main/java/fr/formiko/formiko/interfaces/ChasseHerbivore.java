package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

import java.io.Serializable;
/**
 * {@summary Chasse implementation.<br>}
 * Allow a creature to do hunt as an herbivore.<br>
 * @author Hydrolien
 * @version 1.28
 */
public class ChasseHerbivore implements Serializable, Chasse {
  Creature c;

  public void setC(Creature cTemp){c=cTemp;}

  public boolean chasser(Creature c, int directionSiPasDeProie){return chasse(c);}
  /**
  *{@summary Hunt as an herbivore.<br>}
  *return true if Insecte have eat or moove.
  *@version 1.28
  */
  public boolean chasse(Creature c){
    this.c=c;
    int nourritureMangeable = 1;
    if(c instanceof Insecte){nourritureMangeable=((Insecte)(c)).getNourritureMangeable();}
    if(c.getCCase().getContenu().getNourritureInsecte() >= nourritureMangeable){
      return manger();
    }else{
      //TODO
      //l'insecte doit choisir la case la plus interessante pour lui cad :
      //scoreDeLaCase = herbe/(1+nombre d'insecte déja la)
      c.ceDeplacer(true); // ce déplacer de façon alléatoire.
      return true;
    }
  }

  /**
  *{@summary Eat as an herbivore.<br>}
  *return true if Insecte have eat.
  *@version 1.28
  */
  public boolean manger(){
    byte nourritureSurCase = c.getCCase().getContenu().getNourritureInsecte();
    int nourritureMangeable = 1;
    if(c instanceof Insecte){nourritureMangeable=((Insecte)(c)).getNourritureMangeable();}
    if (nourritureSurCase > 0){
      byte nourritureMangé = (byte) math.min(nourritureSurCase,nourritureMangeable);
      c.getCCase().getContenu().setNourritureInsecte((byte)(nourritureSurCase-nourritureMangé));
      c.setNourriture(c.getNourriture() + nourritureMangé);
      if(c instanceof Fourmi){
        c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutChasse()/2);
      }else{
        c.setActionMoins(1);
      }
      return true;
    }
    return false;
  }
}
