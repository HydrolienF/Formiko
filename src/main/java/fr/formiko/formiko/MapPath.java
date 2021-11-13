package fr.formiko.formiko;

import fr.formiko.usuel.Point;
import fr.formiko.usuel.structures.listes.Liste;

public class MapPath {
  private Liste<CCase> path;
  public MapPath(CCase from, CCase to){
    path = new Liste<CCase>();
    addPath(from,to);
  }
  public void addPath(CCase from, CCase to){
    CCase temp = from;
    path.add(temp);

  }

  /**
   *{@summary getDirection to use to move to c.}<br>
   *@param a Actual Case.
   *@param c Target Case.
   *@return the direction to go to c (from a).
   *@version 2.4
   */
  public static int getDirection(Case a, Case c) {
    return getDirection(a.getPoint(), c.getPoint());
  }
  /**
   *{@summary getDirection to use to move to c.}<br>
   *@param a Actual Point.
   *@param c Target Point.
   *@return the direction to go to c (from a).
   *@version 2.4
   */
  public static int getDirection(Point a, Point c) {
    if (a.getX()>c.getX()){ // 1,4,7
      if (a.getY()>c.getY()){return 1;}
      if (a.getY()==c.getY()){return 4;}
      return 7;
    }else if(a.getX()<c.getX()){//3,6,9
      if (a.getY()>c.getY()){return 3;}
      if (a.getY()==c.getY()){return 6;}
      return 9;
    }else{//2,5,8
      if (a.getY()>c.getY()){return 2;}
      if (a.getY()==c.getY()){return 5;}
      return 8;
    }
  }
}
