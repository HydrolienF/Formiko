package fr.formiko.formiko.interfaces;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import java.io.Serializable;
import fr.formiko.usuel.math.math;
/**
 * {@summary Chasse implementation.<br/>}
 * Allow a creature to do hunt as an herbivore.<br/>
 * @author Hydrolien
 * @version 1.28
 */
public class ChasseHerbivore implements Serializable, Chasse {
  Creature c;
  public void chasser(Creature c, int directionSiPasDeProie){chasse(c);}
  /**
  *{@summary Hunt as an herbivore.<br>}
  *@version 1.28
  */
  public void chasse(Creature c){
    this.c=c;
    int nourritureMangeable = 1;
    if(c instanceof Insecte){nourritureMangeable=((Insecte)(c)).getNourritureMangeable();}
    if(c.getNourriture()<c.getNourritureMax()/2 && c.getCCase().getContenu().getNourritureInsecte() > nourritureMangeable){
      manger();
    }else{
      c.ceDeplacer(true); // ce déplacer de façon alléatoire.
    }
  }

  /**
  *{@summary Eat as an herbivore.<br>}
  *@version 1.28
  */
  public void manger(){
    byte nourritureSurCase = c.getCCase().getContenu().getNourritureInsecte();
    int nourritureMangeable = 1;
    if(c instanceof Insecte){nourritureMangeable=((Insecte)(c)).getNourritureMangeable();}
    if (nourritureSurCase > 0){
      byte nourritureMangé = (byte) math.min(nourritureSurCase,nourritureMangeable);
      c.getCCase().getContenu().setNourritureInsecte((byte)(nourritureSurCase-nourritureMangé));
      c.setNourriture(c.getNourriture() + nourritureMangé);
    }
  }

}
