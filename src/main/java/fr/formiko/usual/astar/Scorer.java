package fr.formiko.usual.astar;

/**
*{@summary Define a simple Scorer.}
*taken from https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-2/src/test/java/com/baeldung/algorithms/astar
*@author sazzer
*@lastEditedVersion 2.23
*/
public interface Scorer<T extends GraphNode> {
  double computeCost(T from, T to);
}
