package fr.formiko.views.cli;

import fr.formiko.formiko.CSquare;
import fr.formiko.formiko.Square;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.ObjetSurCarteAId;
import fr.formiko.usual.Ascii;
import fr.formiko.usual.color;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;

/**
*{@summary Represent the Map in CLI mode.}<br>
*CLI = Console Line Interface.
*@lastEditedVersion 1.39
*@author Hydrolien
*/
public class CLIMap{
  private GSquare gc;
  private static GString legend; private static int xi;
  private static int unseeableChar;
  private int sizeSquare = 4;
  private CSquare lookedCSquare;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main contructor.}<br>
  *@param gc The GSquare to print.<br>
  *@lastEditedVersion 1.38
  */
  public CLIMap(GSquare gc){
    this.gc=gc;
  }
  // GET SET -------------------------------------------------------------------
  public CSquare getLookedCSquare(){return lookedCSquare;}
  public void setLookedCSquare(CSquare cc){lookedCSquare=cc;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Main function of CLIMap.}<br>
  *If on linux it will use color for the map.
  *@return Printable String that represent GString.<br>
  *@lastEditedVersion 1.38
  */
  public String toString(){
    String sr = "";
    if(gc==null || gc.getHead()==null){
      erreur.erreur("La carte est vide");
    }else{
      xi = 0; legend = new GString();
      sr+=mapToMapString();
      sr+=legendToString();
      if(getLookedCSquare()!=null){
        sr+="\n"+g.getM("lookedCSquare")+" : "+getLookedCSquare().toString();
      }
    }
    return sr;
  }
  //private
  /**
  *{@summary Return a color String to represent the ally status of Creature c with the playingAnt.}
  *@lastEditedVersion 1.38
  */
  private static String getColorAllyStatus(Creature c){
    Fourmi f = Main.getPlayingAnt();
    if(f==null){return "";}
    String r="";
    if(c.equals(f)){
      r = color.GREEN_FLASH;unseeableChar+=color.GREEN_FLASH.length();
    }else if(c.getEstEnnemi(f)){
      r = color.RED;unseeableChar+=color.RED.length();
    }else if(c.getEstAllié(f)){
      r = color.GREEN;unseeableChar+=color.GREEN.length();
    }else{
      r = color.YELLOW;unseeableChar+=color.YELLOW.length();
    }
    return r;
  }
  /**
  *{@summary Return a String representing the ObjetSurCarteAId in param.}
  *@param o The Object to represent.
  *@lastEditedVersion 1.38
  */
  //public only for test
  public static String objetSurCarteAIdToString(ObjetSurCarteAId o, boolean colored){
    String s="";
    boolean b = colored;
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
  public static String objetSurCarteAIdToString(ObjetSurCarteAId o){return objetSurCarteAIdToString(o,true);}
  /**
  *{@summary Return the legend as a String.}<br>
  *Legend can be empty or can have line that look like this "A : F1, 5, G12 \n B : 8, 3 etc."<br>
  *@lastEditedVersion 1.38
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
  *{@summary Return the map as a String easy to use.}<br>
  *@lastEditedVersion 1.38
  */
  //public only for test
  public String mapToMapString(){
    CSquare cc = gc.getHead();
    String sr = "";
    Joueur j = Main.getPlayingJoueur();
    int xi2=0;
    sr+=" ";
    sr+=color.UNDERLINE;
    sr+=color.BLUE;
    sr+=" ";
    int len = gc.getWidth();
    for (int i=0;i<len ;i++ ) {
      String sTemp = i+" ";
      while (sTemp.length()<sizeSquare) {
        sTemp+=" ";
      }
      sr+=sTemp;
    }
    sr+=color.NEUTRAL;
    sr+="\n";
    while(cc!=null){
      xi2++;
      sr+=color.BLUE;
      sr+=Ascii.intToLetterCode(xi2)+"|";
      sr+=color.NEUTRAL;
      sr+=mapLineToString(cc,j)+"\n";
      cc=cc.getDown();
    }
    return sr;
  }
  /**
  *{@summary Return the map as a String.}<br>
  *All map infos are stored in a GSquare, this.gc.
  *@lastEditedVersion 1.38
  */
  //public only for test
  public String mapToString(){
    CSquare cc = gc.getHead();
    String sr = "";
    Joueur j = Main.getPlayingJoueur();
    while(cc!=null){
      sr+=mapLineToString(cc,j)+"\n";
      cc=cc.getDown();
    }
    return sr;
  }
  /**
  *{@summary Return a line of a map as a String.}<br>
  *@param cc The 1a CSquare of the line.
  *@lastEditedVersion 1.39
  */
  //public only for test
  public String mapLineToString(CSquare cc, Joueur j){
    String sr = "";
    while(cc!=null){
      if(j==null){sr+=caseToString(cc.getContent(),false,false);}
      sr+=caseToString(cc,j);
      cc=cc.getRigth();
    }
    return sr;
  }
  /**
  *{@summary Return a case as a String.}<br>
  *This string have a fix length.<br>
  *@param cc CSquare that contain Square to print.
  *@param j Player that will be used to know if cases need to be nuageuse or sombre.
  *@lastEditedVersion 1.38
  */
  //public only for test
  public String caseToString(CSquare cc, Joueur j){
    return caseToString(cc.getContent(),j.isSquareNuageuse(cc),j.isSquareSombre(cc));
  }
  /**
  *{@summary Return a Square as a String.}<br>
  *This string have a fix length.<br>
  *@param contenu Square to print.
  *@param caseNuageuse Boolean to know if player have explored the Square.
  *@param caseSombre Boolean to know if player is curently seeing the Square.
  *@lastEditedVersion 1.38
  */
  //public only for test
  public String caseToString(Square contenu, boolean caseNuageuse, boolean caseSombre){
    int nbrDElementSurSquare = contenu.getNbrDElementSurSquare();
    String sr = "";
    unseeableChar=0;
    //if case need to be hide :
    if(Main.getPartie().getCarte().getSquaresNuageuses()==true && caseNuageuse){
      while (sr.length()<sizeSquare+unseeableChar){sr = sr + "■";}
      return sr;
    }else if(Main.getPartie().getCarte().getSquaresSombres()==true && caseSombre){
      if(contenu.getFere() != null){
        sr = "F"+contenu.getFere().getId();
      }
      sr+=caseColor(contenu);
      while (sr.length()<sizeSquare+unseeableChar){sr = sr + "□";}
    }else{
      if (nbrDElementSurSquare == 0){
        // if(!Os.getOs().isLinux()){sr = "-";}
      }else if(nbrDElementSurSquare == 1){
        if(contenu.getFere() != null){
          sr = "F"+contenu.getFere().getId();
        }else if (contenu.getGc().getHead() != null){
          sr = sr + objetSurCarteAIdToString(contenu.getGc().getFirst());
        }else{
          sr = sr + objetSurCarteAIdToString(contenu.getGg().getFirst());
        }
      }else{
        xi++;
        sr = Ascii.intToLetterCode(xi);
        // ajout dans la legend.
        String s = sr +" : ";
        if(contenu.getFere() != null){
          s = s + "F"+contenu.getFere().getId()+", ";
        }
        for (Creature c : contenu.getGc()) {
          s+=objetSurCarteAIdToString(c)+", ";
        }
        for (Graine g : contenu.getGg()) {
          s+=objetSurCarteAIdToString(g)+", ";
        }
        s = s.substring(0,s.length()-2);
        legend.add(s);
        unseeableChar=0;
      }
    }
    if(sr.length()<sizeSquare+unseeableChar){
      sr+=caseColor(contenu);
    }
    while (sr.length()<sizeSquare+unseeableChar){sr = sr + " ";}
    sr+=color.NEUTRAL;unseeableChar+=color.NEUTRAL.length();
    return sr;
  }
  /**
  *{@summary Return the backgroud color of a Square.}<br>
  *@param c Square to print.
  *@lastEditedVersion 1.38
  */
  //public only for test
  public String caseColor(Square c){
    String sr="";
    switch (c.getType()) {
      case 0 :
      sr+=color.BLACK_BACKGROUND;unseeableChar+=color.BLACK_BACKGROUND.length();
      break;
      case 1 :
      sr+=color.GREEN_BACKGROUND;unseeableChar+=color.GREEN_BACKGROUND.length();
      break;
      case 2 :
      sr+=color.PURPLE_BACKGROUND;unseeableChar+=color.PURPLE_BACKGROUND.length();
      break;
      case 3 :
      sr+=color.YELLOW_BACKGROUND;unseeableChar+=color.YELLOW_BACKGROUND.length();
      break;
      //default :
      //break;
    }
    return sr;
  }
}
