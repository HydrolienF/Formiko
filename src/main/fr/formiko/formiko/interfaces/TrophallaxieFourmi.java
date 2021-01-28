package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.graphisme.PanneauTInt;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.maths.math;
import fr.formiko.graphisme.BoiteListeDefilante;
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to do a trophallaxis<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class TrophallaxieFourmi implements Serializable, Trophallaxie {
  /**
   *{@summary make a trophallaxis.<br/>}
   *This methode will check that no food will be lost and no food will be create.<br>
   *If the trophallaxis work well it will cost action to the 2 creatures.
   *@param c The trophallaxing Creature.
   *@param c2 The target of the trophallaxie.
   *@param nourritureDonnée The amount of food transferred.
   *@version 1.3
   */
  public void trophallaxie(Creature c, Creature c2, int nourritureDonnée){
    if (c==null || c2 == null){ erreur.alerte("Une des créatures impliqués dans la Trophalaxie n'as pas pue être trouvé");return;}
    if (nourritureDonnée < 1){ erreur.alerte("Impossible de trophallaxer une quantité négtive ou nul de nourriture"); return;}
    debug.débogage("Trophallaxie de "+nourritureDonnée+ " souhaité, de "+c.getId()+" vers "+c2.getId());
    debug.débogage("Lancement de la trophallaxie de la créature "+c.getId()+" vers la créature "+c2.getId() +" transfère de "+nourritureDonnée +" voulu");
    if(!(c instanceof Fourmi)){erreur.alerte("Impossible de trophallaxer depuis une créature qui n'est pas une Fourmi");return;}
    Fourmi f1 = (Fourmi) c;
    if (f1.getNourriture() < nourritureDonnée){ // si f1 donne trop
      erreur.erreur("Une fourmi a voulu donnée plus de nourriture que ce qu'elle a","TrophallaxieFourmi","Elle ne donnera que la moitié de sa nourriture par mesure de précaution");
      nourritureDonnée = f1.getNourriture()/2;
    }
    if ((c2.getNourritureMax() - c2.getNourriture()) < nourritureDonnée){ // si c2 ne peu pas tt recevoir
      nourritureDonnée = c2.getNourritureMax() - c2.getNourriture();
    }
    f1.setNourriture(f1.getNourriture()-nourritureDonnée);
    c2.setNourriture(c2.getNourriture()+nourritureDonnée);
    if(nourritureDonnée>0){
      f1.setActionMoins(f1.getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutTrophallaxie());
      if(c2 instanceof Fourmi){
        c2.setActionMoins(f1.getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutTrophallaxie());
      }else{
        c2.setActionMoins(1);
      }
    }
    debug.débogage("Trophallaxie de "+nourritureDonnée+ " éffectuée");
  }
  /**
   *{@summary do a trophallaxis to an id<br/>}
   *@param id The target Creature to fined on the Case.
   *@version 1.3
   */
  public void trophallaxie(Creature c, int id, int nourritureDonnée){
    debug.débogage("Recherche de la créature "+id+" sur la case "+c.getCCase().getContenu().toString());
    trophallaxie(c,c.getCCase().getContenu().getGc().getCreatureParId(id), nourritureDonnée);
  }
  /**
   *{@summary player trophallaxis<br>}
   *1a find the target ant that can be chose &#38; make the player chose 1.<br>
   *2a ask the amount of food transferred by the player.<br>
   *3a do the trophallaxis.<br>
   *@param c The creature who whant to give food.
   *@version 1.3
   */
  public void trophallaxer(Creature c){
    if(!(c instanceof Fourmi)){erreur.alerte("Impossible de trophallaxer depuis une créature qui n'est pas une Fourmi");return;}
    Fourmi f = (Fourmi)c;
    GCreature gc = f.getCCase().getContenu().getGc().filtreAlliés(f); //ne prend que les allié.
    int lengc = gc.length();
    if(lengc < 2){
      erreur.erreurGXVide("GFourmi");
    }else{
      //id de la foumi cible.
      int t [] = f.getAlliéSurLaCaseSansThis().toTId(); //ne prend que les alliées.
      t = getCreatureQuiOnFaim(t,c);
      int lent = t.length;
      String s[] = new String[lent];
      for (int i=0;i<lent ;i++ ) {
        Creature cTemp = gc.getCreatureParId(t[i]);
        String sTemp = "";
        if(cTemp instanceof Fourmi){sTemp = " ("+((Fourmi)(cTemp)).getStringStade()+")";}
        else {sTemp = " ("+cTemp.getNom()+")";}
        s[i]=t[i]+" : "+cTemp.getNourriture()+"/"+cTemp.getNourritureMax()+" "+g.get("nourriture")+sTemp;
      }
      int id2;
      if(t.length==1){
        id2=t[0];
      }else{
        BoiteListeDefilante bld = new BoiteListeDefilante();
        id2 = bld.getChoixId(s,g.get("pti.desc.1"));
        if(id2==-1){
          erreur.erreur("Impossible de trophallaxer","TrophallaxieFourmi");
          return;
        }
      }
      //quantité de nourriture échangé.
      Creature c2 = f.getCCase().getContenu().getGc().getCreatureParId(id2);
      int nour = math.min(c2.getNourritureMax()-c2.getNourriture(),f.getNourriture());
      if(nour<1){erreur.alerte("Impossible de donner 0 nourriture","TrophallaxieFourmi");return;}
      t = new int [nour];
      for (int i=0;i<nour ;i++ ) {
        t[i]=i+1;
      }
      BoiteListeDefilante bld = new BoiteListeDefilante();
      int n = bld.getChoixId(t,g.get("pti.desc.2"));
      trophallaxie(c,c2,n);
    }
  }
  /**
   *{@summary find hungry ant.<br/>}
   *@param t The id list of the ant.
   *@param net The creature who whant to give food.
   *@version 1.7
   */
  public int [] getCreatureQuiOnFaim(int t[],Creature net){
    if(!(net instanceof Fourmi)){erreur.alerte("Impossible de trophallaxer depuis une créature qui n'est pas une Fourmi");return new int[0];}
    Fourmi f = (Fourmi)net;
    GCreature gc = f.getAlliéSurLaCaseSansThis();
    int lent = t.length;
    int lentr = 0;
    //on compte le nombre de fourmi a nourrir.
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureParId(t[i]);
      if(c!=null && c.getNourriture()!=c.getNourritureMax()){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui on faim.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureParId(t[i]);
      if(c!=null && c.getNourriture()!=c.getNourritureMax()){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
