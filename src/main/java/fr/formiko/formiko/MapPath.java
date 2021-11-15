package fr.formiko.formiko;

import fr.formiko.usuel.Point;
import fr.formiko.usuel.structures.listes.Liste;
/**
*{@summary Path for map move.}<br>
*@version 2.11
*@author Hydrolien
*/
public class MapPath {
  private Liste<CCase> path;
  private Liste<Integer> movingCaseByTurn; //TODO #229
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that build the path.}<br>
  *@param from starting CCase
  *@param to ending CCase
  *@version 2.11
  */
  public MapPath(CCase from, CCase to){
    path = new Liste<CCase>();
    movingCaseByTurn = new Liste<Integer>();
    addPath(from,to);
  }
  // GET SET -------------------------------------------------------------------
  public Liste<CCase> getList(){return path;}
  public  Liste<Integer> getMovingCaseByTurn(){return movingCaseByTurn;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Strandard toString function.}<br>
  *@version 2.11
  */
  public String toString(){
    String s="";
    for (CCase cc : path) {
      if(!s.equals("")){s+=" ";}
      s+=cc.getContent().getPoint().toString();
    }
    if(!movingCaseByTurn.isEmpty()){
      s+=" ";
      for (Integer i : movingCaseByTurn) {
        s+=" "+i;
      }
    }
    return s;
  }
  /**
  *{@summary Add path CCase by CCase.}<br>
  *@param from starting CCase
  *@param to ending CCase
  *@version 2.11
  */
  public void addPath(CCase from, CCase to){
    if(from==null || to==null){return;}
    CCase temp = from;
    addToPath(temp);
    while(!temp.equals(to)){
      temp = getNextCCase(temp,to);
      addToPath(temp);
    }
  }
  /**
  *{@summary Add a single CCase to the path.}<br>
  *If CCase is already the last one, it don't add it.
  *@param cc CCase to add
  *@version 2.11
  */
  public void addToPath(CCase cc){
    if(path.isEmpty() || !path.getLast().equals(cc)){ //to avoid to add an element that is already the last one in path.
      path.addTail(cc);
    }
  }
  public void updateMovingCaseByTurn(Creature c){
    movingCaseByTurn = new Liste<Integer>();
    int action = c.getAction();
    int addAction = c.getMaxAction();
    int costAction = c.getMovingCost();
    int k=0;
    for (CCase cc : path) {
      action-=costAction;
      k++;
      if(action<1){
        movingCaseByTurn.add(k);
        action+=addAction;
        k=0;
      }
    }
    if(k!=0){
      movingCaseByTurn.add(k);
    }
  }


  //static ---
  /**
  *{@summary get the next CCase to go to reach last Case "to".}<br>
  *@param from starting CCase
  *@param to target CCase
  *@version 2.11
  */
  public static CCase getNextCCase(CCase from, CCase to){
    int d = from.getDirection(to);
    return getNextCCase(from, d);
  }
  /**
  *{@summary get the next CCase to go to reach last Case "to".}<br>
  *@param from starting CCase
  *@param d direction where to go
  *@version 2.11
  */
  public static CCase getNextCCase(CCase from, int d){
    // switch (d) {
    //   case 5:
    //   return from;
    //   case 2:
    // ...
    // }
    if(d==5){ return from;}
    if(d==2){ return from.getHaut();}
    if(d==6){ return from.getDroite();}
    if(d==8){ return from.getBas();}
    if(d==4){ return from.getGauche();}
    // more complicated
    if (d==1){ CCase cc = from.getHaut();
      if(cc != null){ return cc.getGauche();} return null;
    }
    if (d==3){ CCase cc = from.getHaut();
      if(cc != null){ return cc.getDroite();} return null;
    }
    if (d==7){ CCase cc = from.getBas();
      if(cc != null){ return cc.getGauche();} return null;
    }
    if (d==9){ CCase cc = from.getBas();
      if(cc != null){ return cc.getDroite();} return null;
    }
    return null;
  }
}
