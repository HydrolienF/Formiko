package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.tableau;
import fr.formiko.usual.types.str;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to clean itself or an other Creature<br>
 * @author Hydrolien
 * @lastEditedVersion 2.28
 */
public class NetoyerFourmi implements Serializable, Netoyer {
  private Fourmi cible;
  private Fourmi net;
  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary An AI ant want to clean.}<br>
   *@param c The cleaning ant.
   *@return true only if ant realy clean someone.
   *@lastEditedVersion 1.3
   */
  public boolean netoyerIa(Creature c){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return false;}
    net = (Fourmi)c;
    cible = (Fourmi) net.getCSquare().getContent().getGc().filterAlliés(net).getBroodSale();
    if (cible == null){ return false;}
    netoyerPrivate();
    return true;
  }
  /**
   *{@summary A non-AI ant want to clean.}<br>
   *@param c The cleaning ant.
   *@lastEditedVersion 1.3
   */
  public void netoyer(Creature c){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return;}
    net = ((Fourmi)(c));
    if(netoyerChoix()){//défini cible.
      netoyerPrivate();
    }
  }
  /**
   *{@summary An ant clean a Creature.}<br>
   *@param c The cleaning ant.
   *@param c2 The cleaned ant.
   *@lastEditedVersion 1.3
   */
  public void netoyer(Creature c, Creature c2){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return;}
    if(!(c2 instanceof Fourmi)){ erreur.erreur("Impossible de ce faire netoyer en tant que Fourmi avec la créature "+c2.getId()); return;}
    net = (Fourmi)c;
    cible = (Fourmi)c2;
    netoyerPrivate();
  }
  /**
   *{@summary get number of non-100% clean ant.}<br>
   *@param c The cleaning ant.
   *@return the number of non-100% clean ant.
   *@lastEditedVersion 1.3
   */
  public int getNombreDeCreatureANetoyer(Creature c){
    if(c instanceof Fourmi){
      net = (Fourmi)c;
      return getFourmiPasPropre().length;
    }else{
      return 0;
    }
  }

  //sous fonctions
  /**
   *{@summary Let a player choose the ant that he want to clean.}<br>
   *@return true if the player chose an ant to clean.
   *@lastEditedVersion 2.28
   */
  private boolean netoyerChoix(){  // permet de définir cible.
    GCreature gc = net.getCSquare().getContent().getGc();
    int lengc = gc.length();
    //TODO filterr les fourmi déja propre.
    if(lengc<2){ cible = net;
      //erreur.println("1 seule fourmi.");
    }
    else{
      int t[] = getFourmiPasPropre();
      int lent = t.length;
      String s[] = new String[lent];
      for (int i=0;i<lent ;i++ ) {
        Creature c = gc.getCreatureById(t[i]);
        //a ce stade toutes les fourmis de la liste devrais être sales.
        s[i]=t[i]+" : "+c.getHealth()+"/"+"100"+" "+g.get("health");
      }
      if(s.length==0){
        return false;
      }else if(s.length==1){
        String sTemp = s[0];
        String tsSplit [] = sTemp.split(" ");
        if(tsSplit.length >= 1){
          sTemp = tsSplit[0];
        }
        int x=str.sToI(sTemp);
        cible = net.getCSquare().getContent().getGc().getFourmiById(x);
      }else{
        String id2s = Main.getView().makeUserChooseOnArray(s,g.get("Pti.desc.5"));
        int id = str.sToI(id2s.split(" ")[0]);
        cible = net.getCSquare().getContent().getGc().getFourmiById(id);
      }
    }
    return true;
  }
  /*public boolean netoyerTtLeMonde(){
    Fourmi fSale = null;//(Fourmi) this.getAlliéSurLaSquare().getBroodSaleMêmeTrèsPeu();
    if (fSale == null){ return false;}
    netoyer(fSale);
    return true;
  }*/
  /**
   *{@summary do the real cleaning action between cible ant net.}<br>
   *@lastEditedVersion 1.3
   */
  private void netoyerPrivate(){
    cible.setHealth(cible.getHealth()+net.getIndividu().getNétoyage());
    net.setActionMoins(net.getIndividu().getCoutNétoyer());
  }
  /**
   *{@summary get non-100% clean ant sort by cleaning level.}<br>
   *@return the non-100% clean ant sort by cleaning level on the same Square.
   *@lastEditedVersion 1.3
   */
  private int [] getFourmiParOrdreDeSaletéSurLaSquare(){
    GCreature gcTemp = net.getCSquare().getContent().getGc().filterAlliés(net);
    gcTemp.classerPourNetoyage(net); // on récupère l'ordre définie dans CCreature pour le netoyage.
    // int lentr = gcTemp.length();
    return gcTemp.gcToTInt();
  }
  /**
   *{@summary get non-100% clean ant.}<br>
   *@return the non-100% clean ant.
   *@lastEditedVersion 1.3
   */
  private int [] getFourmiPasPropre(){
    GCreature gc = net.getAlliéSurLaSquare();
    int t[] = getFourmiParOrdreDeSaletéSurLaSquare();int lent = t.length;
    int lentr = 0;
    //on compte le nombre de case.
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureById(t[i]);
      if(c.getHealth()<100){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui sont sale.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureById(t[i]);
      if(c.getHealth()<100){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
