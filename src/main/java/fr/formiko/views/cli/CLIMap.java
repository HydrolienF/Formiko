package fr.formiko.views.cli;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.usuel.ascii;
import fr.formiko.usuel.color;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;

public class CLIMap{
  private GCase gc;
  private static GString legend; private static int xi;
  private static int unseeableChar;
  // CONSTRUCTORS --------------------------------------------------------------
  public CLIMap(GCase gc){
    this.gc=gc;
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String sr = "";
    if(gc==null || gc.getDébut()==null){
      erreur.erreur("La carte est vide","Gcase.afficheCarte");
    }else{
      xi = 0; legend = new GString();
      sr+=mapToString();
      sr+=legendToString();
    }
    return sr;
  }
  //private
  /**
  *{@summary Return a color String to represent the ally status of Creature c with the playingAnt.}
  *@version 1.38
  */
  private static String getColorAllyStatus(Creature c){
    //if c is ally of playingAnt
    String r = color.GREEN;unseeableChar+=color.GREEN.length();
    //TODO
    return r;
  }
  /**
  *{@summary Return a String representing the ObjetSurCarteAId in param.}
  *@param o The Object to represent.
  *@version 1.38
  */
  private static String objetSurCarteAIdToString(ObjetSurCarteAId o){
    String s="";
    boolean b = Main.getOs().isLinux();
    if(o instanceof Insecte){
      s+="I"; if(b){s+=getColorAllyStatus((Creature)o);} s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else if(o instanceof Graine){
      s+="G"; if(b){s+=color.BROWN;unseeableChar+=color.BROWN.length();} s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else if(o instanceof Fourmi){
      if(b){s+=getColorAllyStatus((Creature)o);}
      s+=o.getId();
      if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else{
      s+="C"; if(b){s+=color.BLACK;unseeableChar+=color.BLACK.length();} s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }
    return s;
  }

  private String legendToString(){
    String sr="";
    if (legend!=null){
      sr+=g.get("légende")+" :\n";
      sr+=legend.toString();
    }
    return sr;
  }

  private String mapToString(){
    CCase cc = gc.getDébut();
    String sr = "";
    while(cc!=null){
      sr+=mapLineToString(cc)+"\n";
      cc=cc.getBas();
    }
    return sr;
  }
  private String mapLineToString(CCase cc){
    String sr = "";
    while(cc!=null){
      sr+=caseToString(cc.getContenu());
      cc=cc.getDroite();
    }
    return sr;
  }
  private String caseToString(Case contenu){
    int taille = 4;
    int nbrDElementSurCase = contenu.getNbrDElementSurCase();
    String sr = "";
    unseeableChar=0;
    if (nbrDElementSurCase == 0){
      sr = "-";
    }else if(nbrDElementSurCase == 1){
      if(contenu.getFere() != null){
        sr = "F"+contenu.getFere().getId();
      }else if (contenu.getGc().getDébut() != null){
        sr = sr + objetSurCarteAIdToString(contenu.getGc().getDébut().getContenu());
      }else{
        sr = sr + objetSurCarteAIdToString(contenu.getGg().getDébut().getContenu());
      }
    }else{
      xi++;
      sr = ascii.getNuméroationEnAbcd(xi);
      // ajout dans la legend.
      String s = sr +" : ";
      if(contenu.getFere() != null){
        s = s + "F"+contenu.getFere().getId()+", ";
      }
      for (Creature c : contenu.getGc().toList()) {
        s+=objetSurCarteAIdToString(c)+", ";
      }
      for (Graine g : contenu.getGg().toList()) {
        s+=objetSurCarteAIdToString(g)+", ";
      }
      s = s.substring(0,s.length()-2);
      legend.ajouter(s);
      unseeableChar=0;
    }
    while (sr.length()<taille+unseeableChar){
      sr = sr + " ";
    }
    return sr;
  }
}
