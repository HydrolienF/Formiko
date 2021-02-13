package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.formiko.interfaces.TourFourmi;
import fr.formiko.graphisme.Question;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

/**
 * {@summary Ant implementation for real player.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @version 1.30
 */
public class TourFourmiNonIa extends TourFourmi implements Serializable, Tour {
  /**
  *{@summary Do turn actions for an ant.}
  *There is no order to do actions because player choose it.
  *@version 1.30
  */
  @Override
  public void tour(){
    try {
      //TODO move to View
      Main.getPj().setFActuelle(f);
      Main.getPb().addPI();
      Main.getPb().addPIJ();
    }catch (Exception e) {
      erreur.alerte("1 graphics action can't be launch","TourFourmiNonIa");
    }
    String m = "";
    int choix = -1; f.setMode(-1);
    while(f.getAction()>0 && choix!=-2 && f.getMode()==-1){
      Temps.pause(50);
      choix = (byte)(getChoixJoueur()-1);
      if(choix==-2){
        return;
      }
      m = faire(choix);
    }
    if (f.getMode() == 0){
      m = "chasser / ce déplacer pour chasser (Ou Récolter des graines)";
      f.eat(100);
    }else if(f.getMode() == 3){
      backHomeAndShareFood(); m = "Nourrir et Nétoyer";
    }
    try {
      Main.getPs().setIdFourmiAjoué(-1);
    }catch (Exception e) {
      erreur.alerte("1 graphics action can't be launch","TourFourmiNonIa");
    }
    finTour();
  }



  // old part to choose the action to do with the ant.---------------------------
  public byte getChoixBouton(){
    byte choix = (byte) Main.getPj().getActionF();
    while (choix==-1) {
      choix = (byte) Main.getPj().getActionF();
      Temps.pause(50);
      if (f.getBUneSeuleAction()){
        //TODO move to View
        Main.getPb().removePa();
        Main.getPb().addPa(getTActionFourmi());
        f.setBUneSeuleAction(false);
      }
      /*if(bActualiserTaille){
        Main.getPs().actualiserTailleMax();
        Main.getPs().revalidate();
        Main.repaint();
        bActualiserTaille=false;
      }*/
    }choix++;
    return choix;
  }
  public byte getChoixJoueur(){
    int [] t = getTActionFourmi();
    Main.getPb().removePa();
    Main.getPb().addPa(t);
    // on attend une action de la fenetre.
    debug.débogage("lancement de l'attente du bouton");
    byte choix = getChoixBouton();
    debug.débogage("action de Fourmi lancé "+choix);
    Main.getPj().setActionF(-1); //TODO move to View
    return choix;
  }
  public int [] getTActionFourmi(){
    if(f.getUneSeuleAction()!=-1){
      if(f.getUneSeuleAction()==20){return new int[0];}
      int t []= new int [1];
      t[0]=(int)f.getUneSeuleAction();
      return t;
    }
    int t []=new int [11];
    for (int i=0;i<11 ;i++ ) {
      t[i]=i;
    }
    GCreature gcCase = f.getCCase().getContenu().getGc();
    //if(f.getIndividu().getCoutDéplacement() == -1){ t=tableau.retirerX(t,0);}
    t=tableau.retirerX(t,0); //on retire l'anciène méthode de déplacement.
    if(f.getIndividu().getCoutChasse() == -1 || gcCase.getGi().length()==0 || !f.chasse.canHuntMore(f)){ t=tableau.retirerX(t,1);}
    if(!f.peutPondre()){ t=tableau.retirerX(t,2);}
    if(f.getIndividu().getCoutTrophallaxie() == -1 || gcCase.filtreAlliés(f).filtreFaimMax().length() < 2 || f.getNourriture()<1){ t=tableau.retirerX(t,3);}
    if(f.getIndividu().getCoutNétoyer() == -1 ||(f.netoyer.getNombreDeCreatureANetoyer(f))==0){ t=tableau.retirerX(t,4);}
    if(!f.getEspece().getGranivore()){
      t=tableau.retirerX(t,5);
      t=tableau.retirerX(t,6);
    }
    return t;
  }
  public String faire(int choix){
    boolean estIa = f.getFere().getJoueur().getIa();
    String m = switch(choix){
      case 0 :
        f.ceDeplacer(estIa);
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
        f.setMode(0);
        yield "setMode0";
      case 8 :
        f.setMode(3);
        yield "setMode1";
      case 9 :
        f.setAction(0);
        yield "ne rien faire";
      case 10 :
        Question q = new Question("supprimerFourmi.1","supprimerFourmi.2");
        if(q.getChoix()){
          f.mourir(4);
          yield "supprimer la fourmi";
        }
        yield "ne pas supprimer la fourmi";
      default :
        yield "le choix "+choix+" n'est pas possible";
      };
    return m;
  }
}
