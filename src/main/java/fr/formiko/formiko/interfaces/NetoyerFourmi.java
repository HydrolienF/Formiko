package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.graphisme.BoiteListeDefilante;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.io.Serializable;

/**
 * {@summary Ant implementation.<br>}
 * Allow an ant to clean itself or an other Creature<br>
 * @author Hydrolien
 * @version 1.1
 */
public class NetoyerFourmi implements Serializable, Netoyer {
  private Fourmi cible;
  private Fourmi net;
  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary An AI ant want to clean.<br>}
   *@param c The cleaning ant.
   *@return true only if ant realy clean someone.
   *@version 1.3
   */
  public boolean netoyerIa(Creature c){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return false;}
    net = (Fourmi)c;
    cible = (Fourmi) net.getCCase().getContenu().getGc().filtreAlliés(net).getCouvainSale();
    if (cible == null){ return false;}
    netoyerPrivate();
    return true;
  }
  /**
   *{@summary A non-AI ant want to clean.<br>}
   *@param c The cleaning ant.
   *@version 1.3
   */
  public void netoyer(Creature c){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return;}
    net = ((Fourmi)(c));
    if(netoyerChoix()){//défini cible.
      netoyerPrivate();
    }
  }
  /**
   *{@summary An ant clean a Creature.<br>}
   *@param c The cleaning ant.
   *@param c2 The cleaned ant.
   *@version 1.3
   */
  public void netoyer(Creature c, Creature c2){
    if(!(c instanceof Fourmi)){ erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId()); return;}
    if(!(c2 instanceof Fourmi)){ erreur.erreur("Impossible de ce faire netoyer en tant que Fourmi avec la créature "+c2.getId()); return;}
    net = (Fourmi)c;
    cible = (Fourmi)c2;
    netoyerPrivate();
  }
  /**
   *{@summary get number of non-100% clean ant.<br>}
   *@param c The cleaning ant.
   *@return the number of non-100% clean ant.
   *@version 1.3
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
   *{@summary Let a player choose the ant that he want to clean.<br>}
   *@return true if the player chose an ant to clean.
   *@version 1.3
   */
  private boolean netoyerChoix(){  // permet de définir cible.
    GCreature gc = net.getCCase().getContenu().getGc();
    int lengc = gc.length();
    //TODO filtrer les fourmi déja propre.
    if(lengc<2){ cible = net;
      //System.out.println("1 seule fourmi.");
    }
    else{
      int t[] = getFourmiPasPropre();
      int lent = t.length;
      String s[] = new String[lent];
      for (int i=0;i<lent ;i++ ) {
        Creature c = gc.getCreatureParId(t[i]);
        //a ce stade toutes les fourmis de la liste devrais être sales.
        s[i]=t[i]+" : "+c.getProprete()+"/"+"100"+" "+g.get("propreté");
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
        cible = net.getCCase().getContenu().getGc().getFourmiParId(x);
      }else{
        BoiteListeDefilante bld = new BoiteListeDefilante();
        int x = bld.getChoixId(s,g.get("pti.desc.5"));
        cible = net.getCCase().getContenu().getGc().getFourmiParId(x);
      }
    }
    return true;
  }
  /*public boolean netoyerTtLeMonde(){
    Fourmi fSale = null;//(Fourmi) this.getAlliéSurLaCase().getCouvainSaleMêmeTrèsPeu();
    if (fSale == null){ return false;}
    netoyer(fSale);
    return true;
  }*/
  /**
   *{@summary do the real cleaning action between cible ant net.<br>}
   *@version 1.3
   */
  private void netoyerPrivate(){
    cible.setPropreté(cible.getPropreté()+net.getIndividu().getNétoyage());
    net.setActionMoins(net.getIndividu().getCoutNétoyer());
  }
  /**
   *{@summary get non-100% clean ant sort by cleaning level.<br>}
   *@return the non-100% clean ant sort by cleaning level on the same Case.
   *@version 1.3
   */
  private int [] getFourmiParOrdreDeSaletéSurLaCase(){
    GCreature gcTemp = net.getCCase().getContenu().getGc().filtreAlliés(net);
    gcTemp.classerPourNetoyage(); // on récupère l'ordre définie dans CCreature pour le netoyage.
    int lentr = gcTemp.length();
    return gcTemp.gcToTInt();
  }
  /**
   *{@summary get non-100% clean ant.<br>}
   *@return the non-100% clean ant.
   *@version 1.3
   */
  private int [] getFourmiPasPropre(){
    GCreature gc = net.getAlliéSurLaCase();
    int t[] = getFourmiParOrdreDeSaletéSurLaCase();int lent = t.length;
    int lentr = 0;
    //on compte le nombre de case.
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureParId(t[i]);
      if(c.getProprete()<100){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui sont sale.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureParId(t[i]);
      if(c.getProprete()<100){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
