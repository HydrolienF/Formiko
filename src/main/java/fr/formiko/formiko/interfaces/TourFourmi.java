package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @version 1.24
 */
public class TourFourmi implements Serializable, Tour{
  private Fourmi f;
  public void setF(Fourmi fTemp){f=fTemp;}
  /**
  *PLay 1 turn with Creature f.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c!=null && c instanceof Fourmi){
      setF((Fourmi) c);
      tour();
      debug.débogage("fin du tour de la fourmi.");
    }else{
      erreur.erreurType("Fourmi","TourFourmi");
    }
  }

  /**
  *Do turn actions
  *@version 1.29
  */
  public void tour(){
    //f.preTour();
    f.eat(5);
    f.runAway();
    cleanItself();
    f.eat(20);
    feedOther(10);
    cleanOther();
    f.eat(80);
    //reproduce();
    backHomeAndShareFood();
    f.eat(100);
    finTour();
  }
  /**
  *{@summary To be sur that the ant will be clean.<br>}
  *@version 1.29
  */
  public void cleanItself(){
    while(f.getAction()>0 && f.wantClean() && !(f.netoyer instanceof NetoyerNull)){ f.ceNetoyer();}
  }
  /**
  *{@summary Give food by trophallaxis if other ant need it.<br>}
  *An Ant try to give all it food exept foodToQueep%.<br>
  *An ant try to give food if : it have action left, it have more food than foodToQueep%, it can use Trophallaxie interface.
  *@version 1.29
  */
  public void feedOther(int foodToQueep){
    while(f.getAction()>0 && !(f.isHungry(foodToQueep)) && !(f.trophallaxie instanceof TrophallaxieNull)){
      Creature toFeed = aNourrir();
      if(toFeed==null){return;}
      int nourritureDonnée = f.getNourriture()-((f.getNourritureMax()*foodToQueep)/100);
      f.trophallaxie(toFeed,nourritureDonnée);
    }
  }
  /**
  *{@summary Search the ant that need the more food.<br>}
  *We 1a feed our ant queen if it wantFood.<br>
  *We 2a feed every creature that is concidered as an ally & want food.<br>
  *Finaly we try to feed the queen even if she didn't absolutly need food.<br>
  *@version 1.29
  */
  public Creature aNourrir(){
    GCreature gc = f.getCCase().getContenu().getGc().filtreAlliés(f).filtreFaimMax();
    gc.delete(f);
    Creature r = gc.getReine();
    if (r!=null && r.wantFood()) {
      return r;
    }
    GCreature gc2 = gc.filtreWantFood();
    if(gc2.getDébut()!=null){
      return gc2.getDébut().getContenu();
    }
    return r; //r can be null.
  }
  /**
  *{@summary Clean if other ant need it.<br>}
  *@version 1.29
  */
  public void cleanOther(){
    while(f.getAction()>0 && !(f.netoyer instanceof NetoyerNull)){
      Creature toClean = aNetoyer();
      if(toClean==null){return;}
      f.netoyer(toClean);
    }
  }
  /**
  *{@summary Search the ant that need the more to be clean.<br>}
  *We 1a clean our ant queen if it wantClean.<br>
  *We 2a clean every creature that is concidered as an ally & want clean.<br>
  *@version 1.29
  */
  public Creature aNetoyer(){
    GCreature gc = f.getCCase().getContenu().getGc().filtreAlliés(f).filtrePropreteMax();
    gc.delete(f);
    Creature r = gc.getReine();
    if (r!=null && r.wantClean()) {
      return r;
    }
    GCreature gc2 = gc.filtreWantClean();
    if(gc2.getDébut()!=null){
      return gc2.getDébut().getContenu();
    }
    return null;
  }
  /**
  *{@summary Back home and share food to the Creature that need it.<br>}
  *@version 1.29
  */
  public void backHomeAndShareFood(){
    backHome();
    feedOther(10);
    cleanOther();
  }
  /**
  *{@summary Back home.<br>}
  *We find the direction to be closer to the anthill. And then we go there. <br>
  *@version 1.29
  */
  public void backHome(){
    while (f.getAction()>0 && !f.estALaFere()) {
      int directionDeLaFourmilière = f.getCCase().getDirection(f.getFourmiliere().getCCase());
      f.ceDeplacer(directionDeLaFourmilière);
    }
  }
  /**
  *{@summary End a turn as an Ant.<br>}
  *@version 1.29
  */
  public void finTour(){
    debug.débogage("Fin du tour de la Fourmi");
    // Un tour ça coute en age et en nourriture;
    f.setAgePlus1(); f.salir();
    if (f.getStade() == 0 || f.getStade() == -1 || f.getStade() == -2) {f.setNourritureMoinsConsomNourriture();}
    // if contition de température appartient a l'intervale idéale (et que stade = -1, -2 ou -3) : re setAgePlus1();
    try { //TODO move that to ViewGUI
      if (!f.getFere().getJoueur().getIa()) { //pour un joueur humain.
        Main.getPj().setFActuelle(null);
        Main.getPb().setVisiblePa(false);
      }
      Main.getPs().setIdFourmiAjoué(-1);
    }catch (Exception e) {
      erreur.alerte("Une action graphique n'as pas pu etre lancé.","TourFourmi");
    }
  }
}
