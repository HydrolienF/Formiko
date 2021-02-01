package fr.formiko.formiko;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.g;
import fr.formiko.usuel.liste.GString;

public class triche {
  public static GString gs;
  public static int nbrDeCommande = 20;
  // Fonctions propre -----------------------------------------------------------
  public static void ini(){
    gs = new GString(); //GString est une liste chainées dont le contenu est une String.
    for (int i=0;i<nbrDeCommande ;i++ ) {
      String desc = g.get("cmd."+i+".desc");
      if(desc.equals("cmd."+i+".desc")){desc = g.get("descNull");}
      gs.add(g.get("cmd."+i)+" : "+desc);
    }
  }
  public static synchronized void commande(String s){
    if(s==null || s.equals("")){ return;}
    String args [] = decoderUnFichier.getTableauString(s,' ');
    s = args[0];
    try { // pour ne pas avoir a géré toutes les erreurs séparement on ce contente d'afficher un message d'erreur général si l'erreur est inconue.
      //affichage
      int x=-1;int i=0;
      while(x==-1 && i<nbrDeCommande){
        if(s.equals(g.get("cmd."+i))){x=i;}//si la commande est reconue on note le numérau de la commande et on passe a la suite.
      }
      if(x==-1){ return;}
      switch(x){
        case g.get("test"):
          System.out.println("test effectué avec succès");
          break;
        case g.get("aff"):
          aff(args[1]);
          break;
        case g.get("affJ"):
          affJ(args[1]);
          break;
        case g.get("aide"):
          affAide();
          break;
        // pour les créatures
        case g.get("setNourriture"):
          getCreatureParId(args[1]).setNourriture(str.sToI(args[2]));
          break;
        case g.get("setNourritureMax"):
          getCreatureParId(args[1]).setNourritureMax(str.sToI(args[2]));
          break;
        case g.get("setAge"):
          getCreatureParId(args[1]).setAge(str.sToI(args[2]));
          break;
        case g.get("setAgeMax"):
          getCreatureParId(args[1]).setAgeMax(str.sToI(args[2]));
          break;
        case g.get("setEstMort"):
          getCreatureParId(args[1]).setEstMort(str.sToB(args[2]));
          break;
        case g.get("setAction"):
          getCreatureParId(args[1]).setAction(str.sToBy(args[2]));
          break;
        case g.get("setActionMax"):
          getCreatureParId(args[1]).setActionMax(str.sToBy(args[2]));
          break;
        case g.get("setPhéromone"):
          getCreatureParId(args[1]).setPhéromone(str.sToBy(args[2]),str.sToBy(args[3]),str.sToBy(args[4]));
          break;
        //pour les fourmis
        case g.get("setType"):
          getFourmiParId(args[1]).setType(str.sToBy(args[2]));
          break;
        case g.get("setStade"):
          getFourmiParId(args[1]).setStade(str.sToBy(args[2]));
          break;
        case "setMode":
          getFourmiParId(args[1]).setMode(str.sToBy(args[2]));
          break;
        case g.get("setFourmilière"):
          getFourmiParId(args[1]).setFourmiliere(getFourmiliereParId(args[2]));
          getFourmiliereParId(args[2]).getGc().ajouter(getFourmiParId(args[1]));
          break;
        case g.get("setEspece"):
          getFourmiParId(args[1]).setEspece(str.sToI(args[2]));
          break;
        case g.get("setPropreté"):
          getFourmiParId(args[1]).setPropreté(str.sToBy(args[2]));
          break;
        case g.get("setTransporté"):
          //getFourmiParId(args[1]).setTransporté(getGraineParId(args[2]));
          break;
        case g.get("setDuretéMax"):
          getFourmiParId(args[1]).setDuretéMax(str.sToBy(args[2]));
          break;
        //pour les insectes
        case g.get("setNourritureFournie"):
          getInsecteParId(args[1]).setNourritureFournie(str.sToI(args[2]));
          break;
        case g.get("setNourritureMangeable"):
          getInsecteParId(args[1]).setNourritureMangeable(str.sToBy(args[2]));
          break;
        // pour les graines

        //pour les joueurs
        case g.get("setPseudo"):
          getJoueurParId(args[1]).setPseudo(args[2]);
          break;
        case g.get("setIa"):
          getJoueurParId(args[1]).setIa(str.sToB(args[2]));
          break;
      }
      //version orriginale :
      /*if (s.equals("test")){
        System.out.println("test éffectué");
      }else if (s.equals("aff")){
        aff(args[1]);
      }
      // pour les créatures
      else if(s.equals("setNourriture")){
        getCreatureParId(args[1]).setNourriture(str.sToI(args[2]));
      }else if(s.equals("setNourritureMax")){
        getCreatureParId(args[1]).setNourritureMax(str.sToI(args[2]));
      }else if(s.equals("setAge")){
        getCreatureParId(args[1]).setAge(str.sToI(args[2]));
      }else if(s.equals("setAgeMax")){
        getCreatureParId(args[1]).setAgeMax(str.sToI(args[2]));
      }else if(s.equals("setEstMort")){
        getCreatureParId(args[1]).setEstMort(str.sToB(args[2]));
      }else if(s.equals("setAction")){
        getCreatureParId(args[1]).setAction(str.sToBy(args[2]));
      }else if(s.equals("setActionMax")){
        getCreatureParId(args[1]).setActionMax(str.sToBy(args[2]));
      }else if(s.equals("setPhéromone")){
        getCreatureParId(args[1]).setPhéromone(str.sToBy(args[2]),str.sToBy(args[3]),str.sToBy(args[4]));
      }
      //pour les fourmis
      else if(s.equals("setType")){
        getFourmiParId(args[1]).setType(str.sToBy(args[2]));
      }else if(s.equals("setStade")){
        getFourmiParId(args[1]).setStade(str.sToBy(args[2]));
      }else if(s.equals("setMode")){
        getFourmiParId(args[1]).setMode(str.sToBy(args[2]));
      }else if(s.equals("setFourmiliere") || s.equals("setFere")){
        getFourmiParId(args[1]).setFourmiliere(getFourmiliereParId(args[2]));
      }else if(s.equals("setEspece")){
        getFourmiParId(args[1]).setEspece(str.sToI(args[2]));
      }else if(s.equals("setPropreté")){
        getFourmiParId(args[1]).setPropreté(str.sToBy(args[2]));
      }else if(s.equals("setTransporté")){
        //getFourmiParId(args[1]).setTransporté(getGraineParId(args[2]));
      }else if(s.equals("setDuretéMax")){
        getFourmiParId(args[1]).setDuretéMax(str.sToBy(args[2]));
      }
      //pour les insectes
      else if(s.equals("setNourritureFournie")){
        getInsecteParId(args[1]).setNourritureFournie(str.sToI(args[2]));
      }else if(s.equals("setNourritureMangeable")){
        getInsecteParId(args[1]).setNourritureMangeable(str.sToBy(args[2]));
      }
      // pour les graines

      //pour les joueurs
      else if(s.equals("setPseudo")){
        getJoueurParId(args[1]).setPseudo(args[2]);
      }else if(s.equals("setIa")){
        getJoueurParId(args[1]).setIa(str.sToB(args[2]));
      }*/
      Main.repaint();
    }catch (Exception e) { erreur.erreur("La commande triche a échoué");}
  }




  public static Creature getCreatureParId(String id){
      try {
        return Main.getGj().getGc().getCreatureParId(str.sToI(id));
      }catch (Exception e) {
        erreur.erreur("La créature associé a l'id "+id+" n'as pas été trouvée.");
        return null;
      }
  }
  public static Fourmi getFourmiParId(String id){
    try {
      return (Fourmi) getCreatureParId(id);
    }catch (Exception e) {
      erreur.erreur("La Fourmi associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static Insecte getInsecteParId(String id){
    try {
      return (Insecte) getCreatureParId(id);
    }catch (Exception e) {
      erreur.erreur("L'Insecte associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static Fourmiliere getFourmiliereParId(String id){
    try {
      return Main.getFourmiliereParId(str.sToI(id));
    }catch (Exception e) {
      erreur.erreur("La créature associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static Joueur getJoueurParId(String id){
    try {
      return Main.getJoueurParId(str.sToI(id));
    }catch (Exception e) {
      erreur.erreur("La créature associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static void aff(String s){
    try {
      getFourmiParId(s).afficheToi();
    }catch (Exception e) {
      try {
        getCreatureParId(s).afficheToi();
      }catch (Exception e2) {
        erreur.alerte("la créature "+s+" n'as pas été trouvée.");
        /*try {
          getFourmiliereParId(s).afficheToi();
        }catch (Exception e3) {}*/
      }
    }
  }
  public static void affJ(String s){
    try {
      getJoueurParId(s).afficheToi();
    }catch (Exception e) {}
  }
  public static void affAide(){
    System.out.println(g.getM("triche.aide.1"));
    System.out.println(g.getM("commande")+" : ");

  }
}
