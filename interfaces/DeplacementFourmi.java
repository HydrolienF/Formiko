package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.read;
import fr.formiko.graphisme.PanneauTInt;

public class DeplacementFourmi implements Deplacement{
  private Creature c;

  public void unMouvement(Creature c, boolean bIa){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi");
    this.c = c;
    if (bIa) {
      unMouvementAlléa();
    } else { // SI c'est un joueur
      unMouvementJoueurHumain();
    }
  }
  public void unMouvement(Creature c, CCase p){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi avec CCase");
    this.c = c;
    int direction = getDirection(c.getCCase().getContenu(),p.getContenu());
    unMouvementVolontaire(direction);
  }
  public void unMouvement(Creature c, int direction){
    debug.débogage("Le déplacement de la Creature "+c.getId()+" vien de DeplacementDUneFourmi avec direction");
    this.c = c;
    unMouvementVolontaire(direction);
  }
  public void plusieurMouvement(Creature c, CCase cc){
    CCase ccAct = c.getCCase();
    while(!ccAct.equals(cc) && c.getAction()>0){//tant qu'on est pas arrivé a la case et qu'on a encore des actions.
      unMouvement(c,cc);
      ccAct=c.getCCase();
    }
  }



  // COMMENT SONT EXECUTER LES MOUVEMENTS :
  private int getDirection(Case a, Case c){ // a case actuel / c case cible
    if (a.getX()>c.getX()){ // 1,4,7
      if (a.getY()>c.getY()){return 1;}
      if (a.getY()==c.getY()){return 4;}
      return 7;
    }else if(a.getX()<c.getX()){//3,6,9
      if (a.getY()>c.getY()){return 3;}
      if (a.getY()==c.getY()){return 6;}
      return 9;
    }else{
      if (a.getY()>c.getY()){return 2;}
      if (a.getY()==c.getY()){return 5;}
      return 8;
    }
  }
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

  private void unMouvementJoueurHumain(){
    int direction; //On a 8 direction pour ce déplacer.
    boolean b;
    do {
      int ti [] = {1,2,3,4,5,6,7,8,9};
      Main.getPp().getPj().addPti(ti,4);
      direction = PanneauTInt.getChoixId();
      //direction = read.getInt(1,9,"direction (pour la Fourmi "+  c.getId() +")",5);  // ici ca coince pour l'instant.
      if (direction==5){ c.setAction(0);return;} // c'est plus rapide si on décide de pas bougé.
      b = unPas(direction);
    } while (!b);
    setActionMoinsDéplacement();
  }
  /*private void unMouvementVolontaire(CCase p){
    if (unPas(p)){ // si on a bien bougé
      debug.débogage("La Fourmie " + c.getId() +" a fait un mouvement volontaire vers "+ p.getPoint());
    } else { // Sinon
      //erreur.alerte("La Fourmie " + id +" n'as pas réussi a faire un unMouvementVolontaire vers" + p.getPoint(),"Fourmi.unMouvementVolontaire");
      unMouvementAlléa();
    }
    setActionMoinsDéplacement();
  }*/
  private void unMouvementVolontaire(int direction){
    if (unPas(direction)){ // si on a bien bougé
      debug.débogage("La Fourmie " + c.getId() +" a fait un mouvement volontaire dans la direction "+ direction);
    } else { // Sinon
      //erreur.alerte("La Fourmie " + id +" n'as pas réussi a faire un unMouvementVolontaire vers" + p.getPoint(),"Fourmi.unMouvementVolontaire");
      unMouvementAlléa();
    }
    setActionMoinsDéplacement();
    try {
      if (!((Fourmi)(c)).getJoueur().getIa() && (Main.getCarte().getCasesNuageuses() || Main.getCarte().getCasesSombres())){((Fourmi)(c)).getJoueur().actualiserCaseSN();}
    }catch (Exception e) {}
  }
  private void setActionMoinsDéplacement(){
    try {
      c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getType()).getCoutDéplacement());
    }catch (Exception e) {
      c.setActionMoins(10);
    }
  }
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
  // tout les pas mène vers unPas(Casse p) nomallement en passant par unPas(int d).
  private boolean unPas(CCase p){
    if (p==null){ return false;}
    c.setCCase(p);
    return true;
  }
}
