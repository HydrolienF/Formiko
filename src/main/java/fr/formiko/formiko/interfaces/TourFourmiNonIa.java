package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.formiko.interfaces.TourFourmi;
import fr.formiko.views.gui2d.Question;
import fr.formiko.usual.Temps;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.tableau;
import fr.formiko.usual.exceptions.ClassTypeException;

import java.io.Serializable;

/**
 * {@summary Ant implementation for real player.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @lastEditedVersion 2.5
 */
public class TourFourmiNonIa extends TourFourmi implements Serializable, Tour {
  /**
  *{@summary Do turn actions for an ant.}
  *There is no order to do actions because player choose it.
  *@lastEditedVersion 2.5
  */
  @Override
  public void tour(){
    if((Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()) || Main.getRetournerAuMenu()){return;}
    //if(Main.getPartie().getIdPlayingAnt()!=-1 && Main.getPartie().getIdPlayingAnt()!=f.getId()){return;}
    Main.setPlayingAnt(f);
    if(Main.getOp().getAutoCleaning()){
      cleanItself();
    }
    String m = "";
    int choix = -1;
    while(f.getAction()>0 && !f.getFere().getJoueur().getIsTurnEnded() && choix!=-2 && f.getMode()==-1) {
      // try {
      //   synchronized (this) {
      //     wait();
      //   }
      // }catch (InterruptedException | IllegalMonitorStateException e) {
      //   erreur.alerte("wait fail for "+e);
      // }
      Temps.sleep();
      choix = (byte)(getChoixBouton()-1);
      if(choix==-2){
        // f.setActionTo0();
        return;
      }
      m = faire(choix);
      if(choix==12 || choix==13){ //Main.getPs().setAntIdToPlay(-1);
        f.setActionTo0();
        return;
      }else if(choix==14){
        f.setActionTo0();
        // endTurn();
        Main.getPartie().setContinuerLeJeu(false);
        return;
      }
    }
    // TODO #410 move to endTurn.
    // if(f.isAutoMode() && f.getFere().getGc().isAllInAutoModeOrHaveDoneAllAction()){
    //   return;
    // }
    if (f.getMode() == 0){
      // m = "chasser / ce déplacer pour chasser (Ou Récolter des graines)";
      f.eat(100);
    }else if(f.getMode() == 3){
      // m = "Nourrir et Nétoyer";
      backHomeAndShareFood();
    }
    // TODO #410 if(f.getMode()==-1){
    f.setActionTo0();
    // }
    Main.setPlayingAnt(null);
  }
  @Override
  public void endTurn(Creature c){
    if(c==null){throw new NullPointerException();}
    if(!(c instanceof Fourmi)){throw new ClassTypeException("Fourmi","Creature");}
    setF((Fourmi) c);
    // TODO #410 if all non automode ant have played.
    // if (f.getMode() == 0){
    //   // m = "chasser / ce déplacer pour chasser (Ou Récolter des graines)";
    //   f.eat(100);
    // }else if(f.getMode() == 3){
    //   // m = "Nourrir et Nétoyer";
    //   backHomeAndShareFood();
    // }
    super.endTurn();
  }
  /**
  *{@summary Do turn actions that can be done without action.}
  *There is no order to do actions because player choose it.
  *@lastEditedVersion 2.5
  */
  public void allowToDisableAutoMode(){
    erreur.info("allowToDisableAutoMode");
    if((Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()) || Main.getRetournerAuMenu()){return;}
    Main.setPlayingAnt(f);
    byte choix=-2;
    try {
      choix = (byte)(getChoixBouton()-1);
    }catch (NullPointerException e) {
      erreur.erreur("can not do ant action because of NullPointerException");
    }
    if(choix==-2){
      f.setActionTo0();
      return;
    }
    erreur.info("faire("+choix+")");
    faire(choix);
    Main.setPlayingAnt(null);
  }



  // old part to choose the action to do with the ant.---------------------------
  public byte getChoixBouton(){
    byte choix = -1;
    int t [] = null;
    f.setBActionHaveChange(true);
    //la fourmi doit finir son tour si elle n'as plus d'action, sauf si le joueur a spécifiquement cliqué dessus.
    while (choix==-1 && !f.getFere().getJoueur().getIsTurnEnded() && !Main.getRetournerAuMenu() && (f.getAction()>0 || f.getFere().getWaitingForEndTurn())) {
      // erreur.println("wait");
      // try {
      //   synchronized (this) {
      //     wait();
      //   }
      // }catch (InterruptedException | IllegalMonitorStateException e) {
      //   erreur.alerte("wait fail for "+e);
      // }
      //   erreur.println("stop wainting choix buton "+choix);
      Temps.sleep();
      //if tour fini par clic sur Entrer
      if (f.getBActionHaveChange()){
        t = getTActionFourmi();
        f.setBActionHaveChange(false);
      }else{
        t = null;
      }
      choix = (byte) Main.getView().getAntChoice(t);
      if(f.getFere().getJoueur().getIsTurnEnded()){
        f.setActionTo0();
      }
    }choix++;
    return choix;
  }

  private int [] getTActionFourmi(){
    if(f.getUneSeuleAction()!=-1){
      if(f.getUneSeuleAction()==20){return new int[0];}
      int t []= new int [1];
      t[0]=(int)f.getUneSeuleAction();
      return t;
    }else{
      int t []=new int [11];
      for (int i=0;i<11 ;i++ ) {
        t[i]=i;
      }
      GCreature gcCase = f.getCCase().getContent().getGc();
      // t=tableau.retirerX(t,0); //TODO #229
      if(f.getAction()<=0 || f.getIndividu().getMovingCost() == -1){ t=tableau.retirerX(t,0);}
      if(f.getAction()<=0 || f.getIndividu().getCoutChasse() == -1 || gcCase.getGi().length()==0 || !f.chasse.canHuntMore(f)){ t=tableau.retirerX(t,1);}
      if(f.getAction()<=0 || !f.canLay()){ t=tableau.retirerX(t,2);}
      if(f.getAction()<=0 || f.getIndividu().getCoutTrophallaxie() == -1 || gcCase.filterAlliés(f).filterFaimMax().length() < 2 || f.getFood()<1){ t=tableau.retirerX(t,3);}
      if(f.getAction()<=0 || f.getIndividu().getCoutNétoyer() == -1 ||(f.netoyer.getNombreDeCreatureANetoyer(f))==0){ t=tableau.retirerX(t,4);}
      if(f.getAction()<=0 || !f.getEspece().getGranivore()){
        t=tableau.retirerX(t,5);
        t=tableau.retirerX(t,6);
      }
      return t;
    }
  }
  private String faire(int choix){
    String m = switch(choix){
      case 0 :
        Main.getView().setMoveMode(true);
        yield "ceDeplacer";
      case 1 :
        f.chasse();
        yield "chasse";
      case 2 :
        f.pondre();
        yield "pondre";
      case 3 :
        f.trophallaxer();
        yield "trophallaxer";
      case 4:
        f.netoyer();
        yield "ce netoyer ou nétoyer une autre fourmi";
      case 5:
        //mangerGraine();
        yield "mangerGraine";
      case 6 :
        //casserGraine();
        yield "casserGraine";
      case 7 :
        if(f.getMode()!=0){
          f.setMode(0);
        }else{
          f.setMode(-1);
        }
        yield "setMode0";
      case 8 :
        if(f.getMode()!=3){
          f.setMode(3);
        }else{
          f.setMode(-1);
        }
        yield "setMode1";
      case 9 :
        f.setActionTo0();
        yield "ne rien faire";
      case 10 :
        try {
          Question q = new Question("supprimerFourmi.1","supprimerFourmi.2");
          if(q.getChoix()){
            f.mourir(4);
            yield "supprimer la fourmi";
          }
          yield "ne pas supprimer la fourmi";
        }catch (Exception e) {
          f.mourir(4);
          yield "supprimer la fourmi";
        }
      case 12 :
        yield "change Fourmi";
      case 13 :
        f.getFere().getGc().setAction0AndEndTurn();
        yield "endTurn";
      case 14 :
        yield "endGame";
      default :
        yield "le choix "+choix+" n'est pas possible";
      };
    return m;
  }
}
