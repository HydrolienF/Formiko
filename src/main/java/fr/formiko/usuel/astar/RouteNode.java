package fr.formiko.usuel.astar;

import java.util.StringJoiner;

/**
*{@summary A node with an id &#38; the 2 score value needed to run A*.}
*taken from https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-2/src/test/java/com/baeldung/algorithms/astar
*@author sazzer
*@lastEditedVersion 2.23
*/
class RouteNode<T extends GraphNode> implements Comparable<RouteNode> {
  private final T current;
  private T previous;
  private double routeScore;
  private double estimatedScore;

  // CONSTRUCTORS --------------------------------------------------------------
  /***
  *{@summary Main constructor.}
  *@param current the current node
  *@param previous the previous node
  *@lastEditedVersion 2.23
  */
  RouteNode(T current, T previous, double routeScore, double estimatedScore) {
    this.current = current;
    this.previous = previous;
    this.routeScore = routeScore;
    this.estimatedScore = estimatedScore;
  }
  /***
  *{@summary Secondary constructor with default value null of infinity.}
  *@param current the current node
  *@lastEditedVersion 2.23
  */
  RouteNode(T current) {
    this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
  }

  // GET SET -------------------------------------------------------------------
  T getCurrent() {return current;}
  T getPrevious() {return previous;}
  double getRouteScore() {return routeScore;}
  double getEstimatedScore() {return estimatedScore;}
  void setPrevious(T previous) {this.previous = previous;}
  void setRouteScore(double routeScore) {this.routeScore = routeScore;}
  void setEstimatedScore(double estimatedScore) {this.estimatedScore = estimatedScore;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Function used to compare node.}
  *@param other an other node to compare to
  *@lastEditedVersion 2.23
  */
  @Override
  public int compareTo(RouteNode other) {
    if (this.estimatedScore > other.estimatedScore) {
      return 1;
    } else if (this.estimatedScore < other.estimatedScore) {
      return -1;
    } else {
      return 0;
    }
  }
  /**
  *{@summary Function that return all info about this as a String.}
  *@lastEditedVersion 2.23
  */
  @Override
  public String toString() {
    return new StringJoiner(", ", RouteNode.class.getSimpleName() + "[", "]").add("current=" + current)
      .add("previous=" + previous).add("routeScore=" + routeScore).add("estimatedScore=" + estimatedScore)
      .toString();
  }
}
