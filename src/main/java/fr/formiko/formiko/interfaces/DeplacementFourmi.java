package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.views.gui2d.PanneauTInt;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to moove<br>
 * @author Hydrolien
 * @version 1.1
 */
public class DeplacementFourmi implements Serializable, Deplacement{
  private Creature c;
  /**
   *{@summary make a moove.}<br>
   *@param c the moving Creature.
   *@param bIa Is c moved by an AI or a player.
   *@version 1.3
   */
  public void unMouvement(Creature c, boolean bIa){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi");
    this.c = c;
    if (bIa) {
      unMouvementAlléa();
    } else { // SI c'est un joueur
      CCase cc = Main.getView().getCCase();
      if(cc==null){return;}
      plusieurMouvement(c,cc);
    }
  }
  /**
   *{@summary make a move to a defined Case.}<br>
   *c will move 1 Case closer to p but maybe p is more than 1 Case longer to c.getCCase()<br>
   *c will move by the unMouvement(c,direction) methode.
   *@param c the moving Creature.
   *@param p the CCase were c want to go.
   *@version 1.3
   */
  public void unMouvement(Creature c, CCase p){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi avec CCase");
    this.c = c;
    int direction = getDirection(c.getCCase().getContenu(),p.getContenu());
    unMouvementVolontaire(direction);
  }
  /**
   *{@summary make a moove in a defined direction.}<br>
   *c will move 1 Case closer to p but maybe p is more than 1 Case longer to c.getCCase()
   *@param c the moving Creature.
   *@param direction the direction to move. (It's a number place as on the keyboard 1= North West, 2 = North, etc)
   *@version 1.3
   */
  public void unMouvement(Creature c, int direction){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi avec direction");
    this.c = c;
    unMouvementVolontaire(direction);
  }
  /**
   *{@summary make as much moove as we can/need to a defined Case.}<br>
   *c will stop moving when it will have reach cc or when it actions will be &#60; 0
   *c will move by the unMouvement(c,cc) methode.
   *@param c the moving Creature.
   *@param cc the CCase were c want to go.
   *@version 1.3
   */
  public void plusieurMouvement(Creature c, CCase cc){
    CCase ccAct = c.getCCase();
    while(!ccAct.equals(cc) && c.getAction()>0){//tant qu'on est pas arrivé a la case et qu'on a encore des actions.
      unMouvement(c,cc);
      ccAct=c.getCCase();
    }
  }



  // COMMENT SONT EXECUTE LES MOUVEMENTS :
  /**
   *{@summary getDirection to use to move to c.}<br>
   *@param a Actual Case.
   *@param c Target Case.
   *@return the direction to go to c (from a).
   *@version 1.3
   */
  private int getDirection(Case a, Case c){ // a case actuel / c case cible
    if (a.getX()>c.getX()){ // 1,4,7
      if (a.getY()>c.getY()){return 1;}
      if (a.getY()==c.getY()){return 4;}
      return 7;
    }else if(a.getX()<c.getX()){//3,6,9
      if (a.getY()>c.getY()){return 3;}
      if (a.getY()==c.getY()){return 6;}
      return 9;
    }else{//2,5,8
      if (a.getY()>c.getY()){return 2;}
      if (a.getY()==c.getY()){return 5;}
      return 8;
    }
  }
  /**
   *{@summary make a random moove.}<br>
   *@version 1.3
   */
  private void unMouvementAlléa(){
    // cette fonction permet au ia de bouger de manière alléatoire
    int direction; //On a 8 directions pour ce déplacer car le 5 ne nous fait pas bougé.
    boolean b;
    do {
      do {
        direction = allea.getAlléa(8)+1;
      } while (direction==5);
      b = unPas(direction);
    } while (!b);
    setActionMoinsDéplacement();
  }
  /**
  *{@summary make a willing moove.}<br>
  *@param direction the direction to move. (It's a number place as on the keyboard 1= North West, 2 = North, etc)
  *@version 1.3
  */
  private void unMouvementVolontaire(int direction){
    if (unPas(direction)){ // si on a bien bougé
      debug.débogage("La Fourmie " + c.getId() +" a fait un mouvement volontaire dans la direction "+ direction);
    } else { // Sinon
      //erreur.alerte("La Fourmie " + id +" n'as pas réussi a faire un unMouvementVolontaire vers" + p.getPoint(),"Fourmi.unMouvementVolontaire");
      unMouvementAlléa();
    }
    setActionMoinsDéplacement();
    if(!(c instanceof Fourmi)){ return;}
    if (!((Fourmi)(c)).getJoueur().getIa() && (Main.getCarte().getCasesNuageuses() || Main.getCarte().getCasesSombres())){((Fourmi)(c)).getJoueur().actualiserCaseSN();}
  }
  /**
   *{@summary remove some action to the moving Creature}<br>
   *It will remove 10 action to a non-ant Creature or the individu cost to an ant.
   *@version 1.3
   */
  private void setActionMoinsDéplacement(){
    if(!(c instanceof Fourmi)){ c.setActionMoins(10); return;}
    c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutDéplacement());
  }
  /**
   *{@summary Move to a next Case.}<br>
   *@version 1.3
   */
  private boolean unPas(int d){
    c.setDirection(d);
    if(d==5){ return true;}
    if(d==2){ return unPas(c.getCCase().getHaut());}
    if(d==6){ return unPas(c.getCCase().getDroite());}
    if(d==8){ return unPas(c.getCCase().getBas());}
    if(d==4){ return unPas(c.getCCase().getGauche());}
    // les plus compliqué :
    if (d==1){ CCase cc = c.getCCase().getHaut();
      if(cc != null){ return unPas(cc.getGauche());} return false;
    }
    if (d==3){ CCase cc = c.getCCase().getHaut();
      if(cc != null){ return unPas(cc.getDroite());} return false;
    }
    if (d==7){ CCase cc = c.getCCase().getBas();
      if(cc != null){ return unPas(cc.getGauche());} return false;
    }
    if (d==9){ CCase cc = c.getCCase().getBas();
      if(cc != null){ return unPas(cc.getDroite());} return false;
    }
    return false; // le nombre n'était pas correcte
  }
  /**
   *{@summary Move to a next Case.}<br>
   *All moving thing use this methode to move.
   *@version 1.3
   */
  private boolean unPas(CCase p){
    if (p==null){ return false;}
    c.setCCase(p);
    return true;
  }
}
