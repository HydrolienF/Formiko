package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.views.gui2d.FPanelTInt;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.read;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

/**
 * {@summary Ant queen implementation.}<br>
 * Allow an ant queen to lay<br>
 * @author Hydrolien
 * @version 1.1
 */
public class PondreReine implements Serializable, Pondre{
  private static int FOOD_COST_TO_LAY = 12;
  private Fourmi f;
  /**
  *Lay an egg with Creature c
  *@version 1.13
  */
  public void unePonte(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de pondre");
    if(c instanceof Fourmi){
      f = (Fourmi) c;
      pondre();
      debug.débogage("fin de la ponte");
    }else{
      erreur.erreurType("Fourmi");
    }
  }
  /**
  *{@summary Return true if can lay an egg.}
  *@version 1.41
  */
  public boolean canLay(Creature c){
    if(c.getAction()<1){return false;}
    if(c.getFood()<=FOOD_COST_TO_LAY+1){return false;}
    if (c instanceof Fourmi) {
      return ((Fourmi)c).estALaFere();
    }
    return true;
  }
  /**
  *Lay an egg.
  *@version 1.13
  */
  protected void pondre(){
    // Ne s'execute que si la reine a suffisement de food et qu'elle est a sa fourmilière
    // diminue la food de la reine.
    // génère une fourmi avec un age négatif et un mouvement nul jusqu'a sa naissance. (a la fourmilière)
    Fourmiliere fere = f.getFourmiliere();
    if (canLay(f)){ // f.getP().equals(p.getPointDelaFouriliere)
      //byte type = choixType(); // 0 et 1 sont a évité en début de jeu.
      byte type = 3;
      Fourmi fm = new Fourmi(f.getFourmiliere(),f.getEspece(),type);
      fere.getGc().add(fm); //TODO remove when doing #190
      fm.setMaxAge((int)((double)(f.getEspece().getIndividuByType(type).getMaxAge(0)*fm.getMultiplicateurDeDiff())));
      //f.getFourmiliere().getCCase().getContent().getGc().add(fm);
      //fere.getGc().add(fm); l'ajout a la fourmilière ce fait dans le constructeur de Fourmi.
      f.setFood(f.getFood() - FOOD_COST_TO_LAY);
      Message m = new Message(g.getM("la")+" "+f.getNom()+" "+fm.getId() + " "+g.get("pondreReine.1")+".", fere.getId(), 3);
      Main.setPlayingAnt(f); //to refrech playingant info
      f.setActionMoins(f.getIndividu().getCoutPondre());
      f.setCutWings(true);//une reine qui pond n'as plus d'ailes.
      // Main.getView().; //TODO
    }
  }
  //TODO #131
  /**
  *Choose a typeF for the egg.
  *@version 1.13
  */
  protected byte choixType(){
    if (!f.getFere().getJoueur().getIa()){
      int ti[] = f.getEspece().getAviableType();
      //byte x = (byte) read.getInt(0,10,"type de l'oeuf",3);
      //TODO
      // getView().getPp().getPj().addPti(ti,3);
      tableau.afficher(ti);
      byte x = (byte) FPanelTInt.getChoixId();
      if(f.getEspece().getGIndividu().getIndividuByType(x) != null){
        return x;
      }
      erreur.erreur("Le type spécifié par le joueur n'est pas défini pour cette Espece.","3 est le type choisi a la place.",false,1);
      return 3;
    }
    //ia
    if (f.getFere().getGc().length() < f.getEspece().getNbrDIndividuMax()/10){ // si la fourmilière n'est pas encore a 10% de son dévelloppement max pas de fourmi de type 0 ou 1.
      return 3; // si il y a un type 4 ou 2 il faudrait pouvoir en faire naitre de temps en temps.
    }else{
      int x = allea.getAlléa(5);
      if(x == 0){ return 0;} // Reine.
      if(x == 1){ return 1;} // Male.
      return 3; // ouvrière.
    }
  }
}
