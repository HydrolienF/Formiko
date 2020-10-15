package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.graphisme.BoiteListeDefilante;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.conversiondetype.str;

public class NetoyerFourmi implements Netoyer {
  private Fourmi cible;
  private Fourmi net;
  // Fonctions propre -----------------------------------------------------------
  public boolean netoyerIa(Creature c){
    try {
      net = (Fourmi)c;
    }catch (Exception e) {
      erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId());
    }
    cible = (Fourmi) net.getCCase().getContenu().getGc().filtreAlliés(net).getCouvainSale();
    if (cible == null){ return false;}
    netoyerPrivate();
    return true;
  }
  public void netoyer(Creature c, Creature c2){
    try {
      net = (Fourmi)c;
      cible = (Fourmi)c2;
    }catch (Exception e) {
      erreur.erreur("Impossible de netoyer en tant que Fourmi la creature "+c2.getId()+" avec la créature "+c.getId());
    }
    netoyerPrivate();
  }
  public void netoyer(Creature c){
    try {
      net = ((Fourmi)(c));
    }catch (Exception e) {
      erreur.erreur("Impossible de netoyer en tant que Fourmi avec la créature "+c.getId());
    }
    if(netoyerChoix()){//défini cible.
      netoyerPrivate();
    }
  }
  public int getNombreDeCreatureANetoyer(Creature c){
    if(c instanceof Fourmi){
      net = (Fourmi)c;
      return getFourmiPasPropre().length;
    }else{
      return 0;
    }
  }

  //sous fonctions
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
        Fourmi f = gc.getFourmiParId(t[i]);
        //if(f.getProprete()!=100){
          s[i]=t[i]+" : "+f.getProprete()+"/"+"100"+" "+g.get("propreté");
        /*}else{
          System.out.println("Une fourmi est retiré car déja propre");//@a
          try {
            s = tableau.retirer(s,i);
            i--;
            lent--;
          }catch (Exception e) {
            erreur.alerte("Une Fourmi n'as pas pue etre retirer d'un  tableau");
          }*/
        //}
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
        System.out.println("plusieur choix");//@a
        BoiteListeDefilante bld = new BoiteListeDefilante();
        int x = bld.getChoixId(s,g.get("pti.desc.5"));
        cible = net.getCCase().getContenu().getGc().getFourmiParId(x);
      }
    }
    return true;
  }
  /*public boolean netoyerTtLeMonde(){
    Fourmi fSale = null;//(Fourmi) this.getCCase().getContenu().getGc().filtreAlliés(this).getCouvainSaleMêmeTrèsPeu();
    if (fSale == null){ return false;}
    netoyer(fSale);
    return true;
  }*/
  private void netoyerPrivate(){
    cible.setPropreté(cible.getPropreté()+net.getIndividu().getNétoyage());
    net.setActionMoins(net.getIndividu().getCoutNétoyer());
  }
  private int [] getFourmiParOrdreDeSaletéSurLaCase(){
    GCreature gcTemp = net.getCCase().getContenu().getGc().filtreAlliés(net);
    gcTemp.classerPourNetoyage(); // on récupère l'ordre définie dans CCreature pour le netoyage.
    int lentr = gcTemp.length();
    return gcTemp.gcToTInt();
  }
  private int [] getFourmiPasPropre(){
    GCreature gc = net.getCCase().getContenu().getGc();
    int t[] = getFourmiParOrdreDeSaletéSurLaCase();int lent = t.length;
    int lentr = 0;
    //on compte le nombre de case.
    for (int i=0;i<lent ;i++ ) {
      Fourmi f = gc.getFourmiParId(t[i]);
      if(f.getProprete()!=100){
        lentr++;
      }
    }
    //on ajoute seuelement les fourmis qui sont sale.
    int tr[] = new int[lentr];int k=0;
    for (int i=0;i<lent ;i++ ) {
      Fourmi f = gc.getFourmiParId(t[i]);
      if(f.getProprete()!=100){
        tr[k] = t[i];k++;
      }
    }
    return tr;
  }
}
