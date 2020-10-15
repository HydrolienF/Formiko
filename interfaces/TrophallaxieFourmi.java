package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.graphisme.PanneauTInt;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.math.math;
import fr.formiko.graphisme.BoiteListeDefilante;

public class TrophallaxieFourmi implements Trophallaxie {

  public void trophallaxie(Creature c, Creature c2, int nourritureDonnée){
    if (c==null || c2 == null){ erreur.alerte("Une des créatures impliqués dans la Trophalaxie n'as pas pue être trouvé");return;}
    if (nourritureDonnée < 1){ erreur.alerte("Impossible de trophalaxer une quantité négtive ou nul de nourriture"); return;}
    debug.débogage("Trophallaxie de "+nourritureDonnée+ " souhaité, de "+c.getId()+" vers "+c2.getId());
    debug.débogage("Lancement de la trophallaxie de la créature "+c.getId()+" vers la créature "+c2.getId() +" transfère de "+nourritureDonnée +" voulu");
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
      f1.setActionMoins(f1.getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getType()).getCoutTrophallaxie());
      try{
        c2.setActionMoins(f1.getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getType()).getCoutTrophallaxie());
      }catch (Exception e) {
        c2.setActionMoins(1);
      }
    }
    debug.débogage("Trophallaxie de "+nourritureDonnée+ " éffectuée");
  }
  public void trophallaxie(Creature c, int id, int nourritureDonnée){
    debug.débogage("Recherche de la créature "+id+" sur la case");
    c.getCCase().getContenu().afficheToi();
    trophallaxie(c,c.getCCase().getContenu().getGc().getCreatureParId(id), nourritureDonnée);
  }
  public void trophallaxer(Creature c){
    Fourmi f = (Fourmi)c;
    GCreature gc = f.getCCase().getContenu().getGc();
    int lengc = gc.length();
    if(lengc < 2){
      erreur.erreurGXVide("GFourmi");
    }else{
      //id de la foumi cible.
      int t [] = f.getIdFourmiDifférenteSurLaCase();
      t = getFourmiQuiOnFaim(t,c);
      int lent = t.length;
      String s[] = new String[lent];
      for (int i=0;i<lent ;i++ ) {
        Creature cTemp = gc.getCreatureParId(t[i]);
        String sTemp = "";
        try {
          sTemp = " ("+((Fourmi)(cTemp)).getStringStade()+")";
        }catch (Exception e) {}
        s[i]=t[i]+" : "+cTemp.getNourriture()+"/"+cTemp.getNourritureMax()+" "+g.get("nourriture")+sTemp;
      }
      int id2;
      if(t.length==1){
        id2=t[0];
      }else{
        BoiteListeDefilante bld = new BoiteListeDefilante();
        id2 = bld.getChoixId(s,"pti.desc.1");
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
      int n = bld.getChoixId(t,"pti.desc.2");
      trophallaxie(c,c2,n);
    }
  }
  private int [] getFourmiQuiOnFaim(int t[],Creature net){
    GCreature gc = net.getCCase().getContenu().getGc();
    int lent = t.length;
    int lentr = 0;
    //on compte le nombre de fourmi a nourrir.
    for (int i=0;i<lent ;i++ ) {
      Fourmi f = gc.getFourmiParId(t[i]);
      if(f!=null && f.getNourriture()!=f.getNourritureMax()){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui on faim.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Fourmi f = gc.getFourmiParId(t[i]);
      if(f!=null && f.getNourriture()!=f.getNourritureMax()){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
