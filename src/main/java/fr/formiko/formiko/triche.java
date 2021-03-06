package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.decoderUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.types.str;
import fr.formiko.views.gui2d.Panneau;
import fr.formiko.views.gui2d.action;

/**
*{@summary Cheat code.}<br>
*This class is used to launch cheat code for debug, test, script or fun.<br>
*Every commande can be found in data/stable/language/cmd.txt.<br>
*@author Hydrolien
*@version 1.44
*/

public class triche {
  public static GString gs;
  public static int nbrDeCommande;
  // Fonctions propre -----------------------------------------------------------
  public static void ini(){
    if(gs!=null){return;}
    gs = new GString(); //GString est une liste chainées dont le contenu est une String.
    nbrDeCommande=1;
    while (!g.get("cmd."+nbrDeCommande).equals("cmd."+nbrDeCommande)){nbrDeCommande++;}
    nbrDeCommande--;
    for (int i=1;i<=nbrDeCommande;i++ ) {
      String cmd = g.get("cmd."+i);
      String args = g.get("cmd.args."+i);
      if(args.equals("cmd.args."+i)){args = "";}
      String desc = g.get("cmd.desc."+i);
      if(cmd.length() > 4 && cmd.substring(0,3).equals("set")){
        desc = g.get("cmd.desc.set")+" "+cmd.substring(3,cmd.length());
      }else if(desc.equals("cmd."+i+".desc")){desc = g.get("descNull");}
      if(!args.equals("")){args="("+args+") ";}
      gs.add(cmd+" : "+args+desc+".");
    }
  }
  public static void commande(String s){
    if(s==null || s.equals("")){ return;}
    erreur.info("cheat commande launch : \""+s+"\"");
    String args [] = decoderUnFichier.getTableauString(s,' ');
    s = args[0];
    try { // pour ne pas avoir a géré toutes les erreurs séparement on ce contente d'afficher un message d'erreur général si l'erreur est inconue.
      //affichage
      int x=-1;int i=1;
      while(x==-1 && i<=nbrDeCommande){
        if(s.equalsIgnoreCase(g.get("cmd."+i))){x=i;}//si la commande est reconue on note le numéro de la commande et on passe a la suite.
        i++;
      }
      if(x==-1){ return;}
      try {
        if(args[0].substring(0,2).equals("//")){return;}
      }catch (Exception e) {}
      switch(x){
        case 1:
          System.out.println(g.getM("affTest")+".");
          break;
        case 2:
          aff(args[1]);
          break;
        case 3:
          affJ(args[1]);
          break;
        case 4:
          affAide();
          break;
        // pour les créatures
        case 5:
          getCreatureParId(args[1]).setNourriture(str.sToI(args[2]));
          break;
        case 6:
          getCreatureParId(args[1]).setNourritureMax(str.sToI(args[2]));
          break;
        case 7:
          getCreatureParId(args[1]).setAge(str.sToI(args[2]));
          break;
        case 8:
          getCreatureParId(args[1]).setAgeMax(str.sToI(args[2]));
          break;
        case 9:
          getCreatureParId(args[1]).setEstMort(str.sToB(args[2]));
          break;
        case 10:
          getCreatureParId(args[1]).setAction(str.sToBy(args[2]));
          break;
        case 11:
          getCreatureParId(args[1]).setActionMax(str.sToBy(args[2]));
          break;
        case 12:
          getCreatureParId(args[1]).setPheromone(str.sToBy(args[2]),str.sToBy(args[3]),str.sToBy(args[4]));
          break;
        //pour les fourmis
        case 13:
          getFourmiParId(args[1]).setTypeF(str.sToBy(args[2]));
          break;
        case 14:
          getFourmiParId(args[1]).setStade(str.sToBy(args[2]));
          break;
        case 15:
          getFourmiParId(args[1]).setMode(str.sToBy(args[2]));
          break;
        case 16:
          getFourmiParId(args[1]).setFourmiliere(getFourmiliereParId(args[2]));
          getFourmiliereParId(args[2]).getGc().add(getFourmiParId(args[1]));
          break;
        case 17:
          getFourmiParId(args[1]).setEspece(str.sToI(args[2]));
          break;
        case 18:
          getFourmiParId(args[1]).setPropreté(str.sToBy(args[2]));
          break;
        case 19:
          //getFourmiParId(args[1]).setTransported(getGraineParId(args[2]));
          break;
        // pour les graines
        case 20:
          getFourmiParId(args[1]).setDuretéMax(str.sToBy(args[2]));
          break;
        //pour les insectes
        case 21:
          getInsecteParId(args[1]).setNourritureFournie(str.sToI(args[2]));
          break;
        case 22:
          getInsecteParId(args[1]).setNourritureMangeable(str.sToBy(args[2]));
          break;

        //pour les joueurs
        case 23:
          pseudo(args);
          break;
        case 24:
          getJoueurParId(args[1]).setIa(str.sToB(args[2]));
          try {
            if (!str.sToB(args[2]) && (Main.getCarte().getCasesNuageuses() || Main.getCarte().getCasesSombres())){getJoueurParId(args[1]).actualiserCaseSN();}
          }catch (Exception e) {
            erreur.erreur("Le code triche de changement de jouer n'as pas pu actualiser les cases sombre et nuageuse.");
          }
          break;
        case 25:
          Main.getGc().getCCase(str.sToI(args[1]),str.sToI(args[2])).getContenu().afficheToi();
          break;
        case 26:
          System.out.println(Main.getGj());
          break;
        case 27:
          if(args[1].equalsIgnoreCase(g.get("cmd.type.2"))){
            Insecte in=new Insecte(Main.getGc().getCCase(str.sToI(args[2]),str.sToI(args[3])));
            in.setType(str.sToI(args[4]));
            Main.getGi().add(in);
          }else if(args[1].equalsIgnoreCase(g.get("cmd.type.3"))){
            Fourmiliere fere = getFourmiliereParId(args[2]);
            Fourmi fm = new Fourmi(fere,fere.getEspece(),3);
            fm.setStade((byte)-1);
            fm.evoluer();
            fere.getGc().add(fm);
          }else if(args[1].equalsIgnoreCase(g.get("cmd.type.4"))){
            Graine g=new Graine(Main.getGc().getCCase(str.sToI(args[2]),str.sToI(args[3])));
            Main.getGc().getCCase(str.sToI(args[2]),str.sToI(args[3])).getGg().add(g);
          }
          break;
        case 28:
          getFourmiParId(args[1]).evoluer();
          break;
        case 29://print
          String s2 = "";
          boolean doWeNeedToDoNextCmdNow = true;
          if(args.length>1){
            s2 = g.getM(args[1]);
          }
          if(args.length>2){
            doWeNeedToDoNextCmdNow=str.sToB(args[2]);
          }
          try {
            Main.getView().message(s2,doWeNeedToDoNextCmdNow);
          }catch (Exception e) {
            erreur.erreur("cheat code 29 fail to print message");
          }
          break;
        case 30:
          Main.quitter();
          break;
        case 31://attendre
          Main.getScript().setCmdSuivante(false);
          boolean b = false;
          int tourActuel = Main.getTour();
          while(!b){
            Main.getScript().setEcouteClic(false);//on n'écoute plus les clic de l'utilisateur.
            if(args[1].equalsIgnoreCase(g.get("cmd.type.1"))){// Creature
              Creature c = getCreatureParId(args[2]);
              if(args[3].equalsIgnoreCase(g.get("cmd.31.1"))){//getPoint
                b = testSupInfEga(args,c.getCCase().getContenu().getPoint());
              }else if(args[3].equalsIgnoreCase(g.get("cmd.31.2"))){//estMort
                b = c.getEstMort();
              }else if(args[3].equalsIgnoreCase(g.get("cmd.31.5"))){//getProprete
                int p = ((Fourmi) (c)).getProprete();
                if(testSupInfEga(args,p)){b=true;}
              }else if(args[3].equalsIgnoreCase(g.get("cmd.31.6"))){//getNourriture
                int p = ((Fourmi) (c)).getNourriture();
                if(testSupInfEga(args,p)){b=true;}
              }else if(args[3].equalsIgnoreCase(g.get("cmd.31.7"))){//getAction
                int p = c.getAction();
                if(testSupInfEga(args,p)){b=true;}
              }
            }else if(args[1].equalsIgnoreCase(g.get("cmd.type.8"))){
              if(args[2].equalsIgnoreCase(g.get("cmd.31.3"))){//length
                b = Main.getGi().length()==str.sToI(args[3]);
              }
            }else if(args[1].equalsIgnoreCase(g.get("cmd.type.5"))){//fourmilière
              Fourmiliere fe = Main.getGj().getJoueurParId(str.sToI(args[2])).getFere();
              if(args[3].equalsIgnoreCase(g.get("cmd.31.3"))){//length
                b = fe.length()==str.sToI(args[4]);
              }
            }else if(args[1].equalsIgnoreCase(g.get("cmd.31.4"))){//finDuTour
              b = tourActuel+1==Main.getTour();
            }
            if(b){//si la condition d'attente est bonne.
              Main.getScript().setCmdSuivante(true);
            }
            Temps.pause(50);
          }
          break;
        case 32:
          if(!Main.isCLI()){
            Panneau.getView().getPb().removePz();
          }
          break;
        case 33:
          try {
            Fourmi.setUneSeuleAction(str.sToI(args[1]));
          }catch (Exception e) {
            Fourmi.setUneSeuleAction();
          }
          break;
        case 34:
          if(args[2].equalsIgnoreCase("cmd.34.1")){
            Panneau.getView().getPs().actualiserTaille();
          }else if(args[2].equalsIgnoreCase("cmd.34.2")){
            Panneau.getView().getPs().actualiserTailleMax();
          }else if(args[2].equalsIgnoreCase("cmd.34.3")){
            Panneau.getView().getPs().actualiserTailleMin();
          }
          break;
        case 35:
          System.out.println(Main.getTemps());
          break;
        case 36:
          try {
            long xL = str.sToLThrows(args[1]);
            Main.setLangue((int)xL);
          }catch (Exception e) {
            try {
              Main.setLangue(chargerLesTraductions.getLanguage(args[1]));
            }catch (Exception e2) {
              erreur.erreur("Impossible de changer la langue");
            }
          }
          break;
        case 37:
          try {
            action.retournerAuMenu();
          }catch (Exception e) {}
          // try {
          //   Main.getView().menuMain();
          // }catch (Exception e) {}
          break;
        case 38:
          Main.getPartie().setPartieFinie(false);
          if(args.length > 3){
            Main.getPartie().finDePartie(str.sToI(args[1]), str.sToB(args[2]), str.sToI(args[3]));
          }else {
            Main.getPartie().finDePartie();
          }
          break;
        case 39:
          try {
            setMenu(args[1]);
          }catch (Exception e) {
            erreur.alerte("Une action de menu a échouée");
          }
          break;
        case 40:
          try {
            Main.getView().actionGame();
          }catch (Exception e) {
            erreur.alerte("Une action de menu a échouée");
          }
          break;
        case 41:
          try {
            Main.getView().paint();
          }catch (Exception e) {
            erreur.alerte("Une action de menu a échouée");
          }
          break;
        case 42:
          try {
            String musicName = "";
            int len = args.length;
            for (int j=1;j<len ;j++ ) {
              musicName+=args[j];
              if(j<len-1){
                musicName+=" ";
              }
            }
            musicName = str.addALaFinSiNecessaire(musicName,".mp3");
            Main.getMp().addNextMusic(musicName, true);
            Main.getMp().play();
          }catch (Exception e) {
            erreur.alerte("Une action de menu a échouée");
          }
          break;

        default:
          erreur.erreur("La commande n'as pas été reconnue.");
      }
      Main.repaint();
    }catch (Exception e) { erreur.erreur("La commande triche a échoué.");}
  }

  /**
  *{@summary set a new Menu as curent menu.}
  *@version 1.44
  */
  private static void setMenu(String s){
    int x=0;
    int k=1;
    while(x==0 && k<7){
      if(g.get("cmd.menu."+k).equals(s)){x=k;}
      k++;
    }
    switch(x){
      case 1:
        Main.getView().menuMain();
        break;
      case 2:
        Main.getView().menuNewGame();
        break;
      case 3:
        Main.getView().menuLoadAGame();
        break;
      case 4:
        Main.getView().menuPersonaliseAGame();
        break;
      case 5:
        Main.getView().menuOptions();
        break;
      case 6:
        Main.getView().actionGame();
        break;
    }
    Main.repaint();
  }

  public static boolean testSupInfEga(String args[], int p){
    boolean b=false;
    try {
      if(args.length==4){
        b = p==str.sToI(args[4]);
      }else if(args[4].equals(">")){
        b = p>str.sToI(args[5]);
      }else if(args[4].equals("<")){
        b = p<str.sToI(args[5]);
      }else if(args[4].equals("==")){
        b = p==str.sToI(args[5]);
      }else if(args[4].equals("!=")){
        b = p!=str.sToI(args[5]);
      }
    }catch (Exception e) {}
    return b;
  }
  public static boolean testSupInfEga(String args[], Point p1){
    boolean b=false;
    try {
      if(args.length==5){
        b = p1.equals(new Point(args[4]));
      }else if(args[4].equals("==")){
        b = p1.equals(new Point(args[5]));
      }else if(args[4].equals("!=")){
        b = !p1.equals(new Point(args[5]));
      }
    }catch (Exception e) {}
    return b;
  }

  public static Creature getCreatureParId(String id){
      try {
        GCreature gc = Main.getGj().getGc();
        gc.add(Main.getGi());
        Creature c = gc.getCreatureParId(str.sToI(id));
        if(c!=null){return c;}
        else{
          throw new Exception();
        }
      }catch (Exception e) {
        erreur.erreur("La créature associé a l'id "+id+" n'as pas été trouvée.");
        return null;
      }
  }
  public static Fourmi getFourmiParId(String id){
    Creature c = getCreatureParId(id);
    if (c instanceof Fourmi){
      return (Fourmi) c;
    }else{
      erreur.erreur("La Fourmi associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static Insecte getInsecteParId(String id){
    Creature c = getCreatureParId(id);
    if (c instanceof Insecte){
      return (Insecte) c;
    }else{
      erreur.erreur("L'Insecte associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  public static Fourmiliere getFourmiliereParId(String id){
    try {
      return Main.getFourmiliereParId(str.sToI(id));
    }catch (Exception e) {
      erreur.erreur("La fourmilière associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  private static Joueur getJoueurParId(String id){
    try {
      return Main.getJoueurParId(str.sToI(id));
    }catch (Exception e) {
      erreur.erreur("Le joueur associé a l'id "+id+" n'as pas été trouvée.");
      return null;
    }
  }
  /**
  *{@summary change pseudo of the player.}<br>
  *It use parameters : id, pseudo & boolean.
  *Only id is needed.
  */
  private static void pseudo(String args[]){
    //setPseudo id boolean pseudo
    Joueur j = getJoueurParId(args[1]);
    boolean permanent = false;
    String pseudo = "";
    // try {
    //   permanent = str.sToB(args[2]);
    // }catch (Exception e) {
    try {
      permanent = str.sToB(args[2]);
    }catch (Exception e2) {}
    try {
      pseudo = args[3];
    }catch (Exception e2) {}
    // }

    if(pseudo.equals("")){
      try {
        pseudo = Panneau.getView().popUpQuestion("entrezPseudo");
      }catch (Exception e) {
        erreur.alerte("Une action de menu a échouée");
      }
    }
    if(pseudo!=null && !pseudo.equals("")){
      j.setPseudo(pseudo);
      if(permanent && Main.getPartie().isSolo()){
        Main.getOp().setPseudo(pseudo);
        Main.getOp().saveOptions();
      }
    }
  }
  private static void aff(String s){
    try {
      Creature c = getCreatureParId(s);
      System.out.println(c);//si c est une fourmi ou un insecte s'est leur implémentation de toString qui sera appellée.
    }catch (Exception e3) {
      erreur.alerte("la créature "+s+" n'as pas été trouvée.");
    }
  }
  private static void affJ(String s){
    try {
      System.out.println(getJoueurParId(s));
    }catch (Exception e) {}
  }
  private static void affAide(){
    System.out.println(g.getM("triche.aide.1"));
    System.out.println(g.getM("commande")+" : "+"("+g.get("triche.aide.2")+")");
    gs.afficheToi();
  }
}
