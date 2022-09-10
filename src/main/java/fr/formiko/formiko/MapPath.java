package fr.formiko.formiko;

import fr.formiko.usual.Point;
import fr.formiko.usual.structures.listes.Liste;
/**
*{@summary Path for map move.}<br>
*@lastEditedVersion 2.11
*@author Hydrolien
*/
public class MapPath {
  private Liste<CSquare> path;
  private Liste<Integer> movingSquareByTurn;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that build the path.}<br>
  *@param from starting CSquare
  *@param to ending CSquare
  *@lastEditedVersion 2.11
  */
  public MapPath(CSquare from, CSquare to){
    path = new Liste<CSquare>();
    movingSquareByTurn = new Liste<Integer>();
    addPath(from,to);
  }
  // GET SET -------------------------------------------------------------------
  public Liste<CSquare> getList(){return path;}
  public Liste<Integer> getMovingSquareByTurn(){return movingSquareByTurn;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Strandard toString function.}<br>
  *@lastEditedVersion 2.11
  */
  public String toString(){
    String s="";
    for (CSquare cc : path) {
      if(!s.equals("")){s+=" ";}
      s+=cc.getContent().getPoint().toString();
    }
    if(!movingSquareByTurn.isEmpty()){
      s+=" ";
      for (Integer i : movingSquareByTurn) {
        s+=" "+i;
      }
    }
    return s;
  }
  /**
  *{@summary Add path CSquare by CSquare.}<br>
  *@param from starting CSquare
  *@param to ending CSquare
  *@lastEditedVersion 2.11
  */
  public void addPath(CSquare from, CSquare to){
    if(from==null || to==null){return;}
    CSquare temp = from;
    addToPath(temp);
    while(!temp.equals(to)){
      temp = getNextCSquare(temp,to);
      addToPath(temp);
    }
  }
  /**
  *{@summary Add a single CSquare to the path.}<br>
  *If CSquare is already the last one, it don't add it.
  *@param cc CSquare to add
  *@lastEditedVersion 2.11
  */
  public void addToPath(CSquare cc){
    if(path.isEmpty() || !path.getLast().equals(cc)){ //to avoid to add an element that is already the last one in path.
      path.addTail(cc);
    }
  }
  /**
  *{@summary Update the value of max moving Square by turn.}<br>
  *It is used to place the number of turn to reach a Square on path.
  *@param c Creature that may use this path
  *@lastEditedVersion 2.11
  */
  public void updateMovingSquareByTurn(Creature c){
    movingSquareByTurn = new Liste<Integer>();
    int action = c.getAction();
    int addAction = c.getMaxAction();
    int costAction = c.getMovingCost();
    int k=0;
    for (CSquare cc : path) {
      action-=costAction;
      k++;
      if(action<1){
        movingSquareByTurn.add(k);
        action+=addAction;
        k=0;
      }
    }
    if(k!=0){
      movingSquareByTurn.add(k);
    }
  }


  //static ---
  /**
  *{@summary get the next CSquare to go to reach last Square "to".}<br>
  *@param from starting CSquare
  *@param to target CSquare
  *@lastEditedVersion 2.11
  */
  public static CSquare getNextCSquare(CSquare from, CSquare to){
    int d = from.getDirection(to);
    return getNextCSquare(from, d);
  }
  /**
  *{@summary get the next CSquare to go to reach last Square "to".}<br>
  *@param from starting CSquare
  *@param d direction where to go
  *@lastEditedVersion 2.11
  */
  public static CSquare getNextCSquare(CSquare from, int d){
    // switch (d) {
    //   case 5:
    //   return from;
    //   case 2:
    // ...
    // }
    if(d==5){ return from;}
    if(d==2){ return from.getUp();}
    if(d==6){ return from.getRigth();}
    if(d==8){ return from.getDown();}
    if(d==4){ return from.getLeft();}
    // more complicated
    if (d==1){ CSquare cc = from.getUp();
      if(cc != null){ return cc.getLeft();} return null;
    }
    if (d==3){ CSquare cc = from.getUp();
      if(cc != null){ return cc.getRigth();} return null;
    }
    if (d==7){ CSquare cc = from.getDown();
      if(cc != null){ return cc.getLeft();} return null;
    }
    if (d==9){ CSquare cc = from.getDown();
      if(cc != null){ return cc.getRigth();} return null;
    }
    return null;
  }
}
