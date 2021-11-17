package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCreature;
import fr.formiko.formiko.Main;
import fr.formiko.views.gui2d.BoiteListeDefilante;
import fr.formiko.views.gui2d.FPanelTInt;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to do a trophallaxis<br>
 * @author Hydrolien
 * @version 1.1
 */
public class TrophallaxieFourmi implements Serializable, Trophallaxie {
  /**
   *{@summary make a trophallaxis.}<br>
   *This methode will check that no food will be lost and no food will be create.<br>
   *If the trophallaxis work well it will cost action to the 2 creatures.
   *@param c The trophallaxing Creature.
   *@param c2 The target of the trophallaxie.
   *@param foodDonnée The amount of food transferred.
   *@version 1.3
   */
  public void trophallaxie(Creature c, Creature c2, int foodDonnée){
    if (c==null || c2 == null){ erreur.alerte("Une des créatures impliqués dans la Trophalaxie n'as pas pue être trouvé");return;}
    if (foodDonnée < 1){ erreur.alerte("Impossible de trophallaxer une quantité négtive ou nul de food"); return;}
    debug.débogage("Trophallaxie de "+foodDonnée+ " souhaité, de "+c.getId()+" vers "+c2.getId());
    debug.débogage("Lancement de la trophallaxie de la créature "+c.getId()+" vers la créature "+c2.getId() +" transfère de "+foodDonnée +" voulu");
    if(!(c instanceof Fourmi)){erreur.alerte("Impossible de trophallaxer depuis une créature qui n'est pas une Fourmi");return;}
    Fourmi f1 = (Fourmi) c;
    if (f1.getFood() < foodDonnée){ // si f1 donne trop
      erreur.erreur("Une fourmi a voulu donnée plus de food que ce qu'elle a","Elle ne donnera que la moitié de sa food par mesure de précaution");
      foodDonnée = f1.getFood()/2;
    }
    if ((c2.getMaxFood() - c2.getFood()) < foodDonnée){ // si c2 ne peu pas tt recevoir
      foodDonnée = c2.getMaxFood() - c2.getFood();
    }
    f1.setFood(f1.getFood()-foodDonnée);
    c2.setFood(c2.getFood()+foodDonnée);
    if(foodDonnée>0){
      f1.setActionMoins(f1.getEspece().getGIndividu().getIndividuByType(((Fourmi) c).getTypeF()).getCoutTrophallaxie());
      if(c2 instanceof Fourmi){
        c2.setActionMoins(f1.getEspece().getGIndividu().getIndividuByType(((Fourmi) c).getTypeF()).getCoutTrophallaxie());
      }else{
        c2.setActionMoins(1);
      }
    }
    debug.débogage("Trophallaxie de "+foodDonnée+ " éffectuée");
  }
  /**
   *{@summary do a trophallaxis to an id}<br>
   *@param id The target Creature to fined on the Case.
   *@version 1.3
   */
  public void trophallaxie(Creature c, int id, int foodDonnée){
    debug.débogage("Recherche de la créature "+id+" sur la case "+c.getCCase().getContent().toString());
    trophallaxie(c,c.getCCase().getContent().getGc().getCreatureParId(id), foodDonnée);
  }
  /**
   *{@summary player trophallaxis}<br>
   *1a find the target ant that can be chose &#38; make the player chose 1.<br>
   *2a ask the amount of food transferred by the player.<br>
   *3a do the trophallaxis.<br>
   *@param c The creature who whant to give food.
   *@version 1.3
   */
  public void trophallaxer(Creature c){
    if(!(c instanceof Fourmi)){erreur.alerte("Impossible de trophallaxer depuis une créature qui n'est pas une Fourmi");return;}
    Fourmi f = (Fourmi)c;
    GCreature gc = f.getCCase().getContent().getGc().filterAlliés(f); //ne prend que les allié.
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
        s[i]=t[i]+" : "+cTemp.getFood()+"/"+cTemp.getMaxFood()+" "+g.get("food")+sTemp;
      }
      int id2;
      if(t.length==1){
        id2=t[0];
      }else{
        BoiteListeDefilante bld = new BoiteListeDefilante();
        id2 = bld.getChoixId(s,g.get("pti.desc.1"));
        if(id2==-1){
          erreur.erreur("Impossible de trophallaxer");
          return;
        }
      }
      //quantité de food échangé.
      Creature c2 = f.getCCase().getContent().getGc().getCreatureParId(id2);
      int nour = math.min(c2.getMaxFood()-c2.getFood(),f.getFood());
      if(nour<1){erreur.alerte("Impossible de donner 0 food");return;}
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
   *{@summary find hungry ant.}<br>
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
      if(c!=null && c.getFood()!=c.getMaxFood()){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui on faim.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Creature c = gc.getCreatureParId(t[i]);
      if(c!=null && c.getFood()!=c.getMaxFood()){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
