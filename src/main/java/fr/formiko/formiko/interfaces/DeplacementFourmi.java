package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Point;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.views.gui2d.FPanelTInt;

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
    } else { // if it's a player
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
   *@param p the Case were c want to go.
   *@version 1.3
   */
  public void unMouvement(Creature c, Case p){
    // debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi avec CCase");
    this.c = c;
    int direction = c.getCCase().getDirection(p);
    unMouvementVolontaire(direction);
  }
  /**
   *{@summary make a move to a defined Case.}<br>
   *c will move 1 Case closer to p but maybe p is more than 1 Case longer to c.getCCase()<br>
   *c will move by the unMouvement(c,direction) methode.
   *@param c the moving Creature.
   *@param p the CCase were c want to go.
   *@version 1.3
   */
  public void unMouvement(Creature c, CCase cc){
    unMouvement(c, cc.getContent());
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
      //erreur.alerte("La Fourmi " + id +" n'as pas réussi a faire un unMouvementVolontaire vers" + p.getPoint(),"Fourmi.unMouvementVolontaire");
      unMouvementAlléa();
    }
    setActionMoinsDéplacement();
    if(!(c instanceof Fourmi)){ return;}
    if (!((Fourmi)(c)).getJoueur().getIa() && (Main.getCarte().getCasesNuageuses() || Main.getCarte().getCasesSombres())){((Fourmi)(c)).getJoueur().updateCaseSN();}
  }
  /**
   *{@summary remove some action to the moving Creature}<br>
   *It will remove 10 action to a non-ant Creature or the individu cost to an ant.
   *@version 1.3
   */
  private void setActionMoinsDéplacement(){
    c.setActionMoins(c.getMovingCost());
  }
  /**
   *{@summary Move to a next Case.}<br>
   *@version 1.3
   */
  private boolean unPas(int d){
    c.setDirection(d);
    CCase cc = MapPath.getNextCCase(c.getCCase(),d);
    if(cc!=null){return unPas(cc);}
    return false; // le nombre n'était pas correcte
  }
  /**
   *{@summary Move to a next Case.}<br>
   *All moving thing use this methode to move.
   *@version 1.3
   */
  private boolean unPas(CCase p){
    if (p==null){ return false;}
    Main.getView().move(c,c.getCCase(),p);
    c.setCCase(p);
    return true;
  }
}
