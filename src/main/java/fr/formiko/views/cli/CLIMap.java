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

/**
*{@summary Represent the Map in CLI mode.}<br>
*CLI = Console Line Interface.
*@version 1.38
*@author Hydrolien
*/
public class CLIMap{
  private GCase gc;
  private static GString legend; private static int xi;
  private static int unseeableChar;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main contructor.}<br>
  *@param gc The GCase to print.<br>
  *@version 1.38
  */
  public CLIMap(GCase gc){
    this.gc=gc;
  }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Main function of CLIMap.}<br>
  *If on linux it will use color for the map.
  *@return Printable String that represent GString.<br>
  *@version 1.38
  */
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
    Fourmi f = Main.getPartie().getPlayingAnt();
    if(f==null){return "";}
    String r="";
    if(c.getEstAllié(f)){
      r = color.GREEN;unseeableChar+=color.GREEN.length();
    }else if(c.getEstEnnemi(f)){
      r = color.RED;unseeableChar+=color.RED.length();
    }else{
      r = color.YELLOW;unseeableChar+=color.YELLOW.length();
    }
    //if c is ally of playingAnt
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
      if(b){s+=getColorAllyStatus((Creature)o);} s+="I"; s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else if(o instanceof Graine){
      if(b){s+=color.BROWN;unseeableChar+=color.BROWN.length();} s+="G"; s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else if(o instanceof Fourmi){
      if(b){s+=getColorAllyStatus((Creature)o);}
      s+=o.getId();
      if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }else{
      if(b){s+=color.BLACK;unseeableChar+=color.BLACK.length();} s+="C"; s+=o.getId(); if(b){s+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();}
    }
    return s;
  }
  /**
  *{@summary Return the legend as a String.}<br>
  *Legend can be empty or can have line that look like this "A : F1, 5, G12 \n B : 8, 3 etc."<br>
  *@version 1.38
  */
  private String legendToString(){
    String sr="";
    if (legend!=null){
      sr+=g.get("légende")+" :\n";
      sr+=legend.toString();
    }
    return sr;
  }
  /**
  *{@summary Return the map as a String.}<br>
  *All map infos are stored in a GCase, this.gc.
  *@version 1.38
  */
  private String mapToString(){
    CCase cc = gc.getDébut();
    String sr = "";
    while(cc!=null){
      sr+=mapLineToString(cc)+"\n";
      cc=cc.getBas();
    }
    return sr;
  }
  /**
  *{@summary Return a line of a map as a String.}<br>
  *@param cc The 1a CCase of the line.
  *@version 1.38
  */
  private String mapLineToString(CCase cc){
    String sr = "";
    while(cc!=null){
      sr+=caseToString(cc.getContenu());
      cc=cc.getDroite();
    }
    return sr;
  }
  /**
  *{@summary Return a case as a String.}<br>
  *This string have a fix length.<br>
  *@version 1.38
  */
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
