package fr.formiko.usuel.astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
*{@summary Find the route from a T to a T.}
*taken from https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-2/src/test/java/com/baeldung/algorithms/astar
*@author sazzer
*@lastEditedVersion 2.23
*/
public class RouteFinder<T extends GraphNode> {
  private final Graph<T> graph;
  private final Scorer<T> nextNodeScorer;
  private final Scorer<T> targetScorer;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@param graph the graph used to find a route
  *@nextNodeScorer a scorer to calculate the score of next nodes
  *@targetScorer a scorer to calculate the score between from &#38; to nodes
  *@lastEditedVersion 2.23
  */
  public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
    this.graph = graph;
    this.nextNodeScorer = nextNodeScorer;
    this.targetScorer = targetScorer;
  }

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Main function of the package that return a path.}
  *@param from node where it start
  *@param to node where it end
  *@return a list of T that represent the path to follow as a shortest path
  *@lastEditedVersion 2.23
  */
  public List<T> findRoute(T from, T to) {
    Map<T, RouteNode<T>> allNodes = new HashMap<>();
    Queue<RouteNode<T>> openSet = new PriorityQueue<>();

    RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
    allNodes.put(from, start);
    openSet.add(start);

    while (!openSet.isEmpty()) {
      //System.out.println("Open Set contains: " + openSet.stream().map(RouteNode::getCurrent).collect(Collectors.toSet()));
      RouteNode<T> next = openSet.poll();
      //System.out.println("Looking at node: " + next);
      if (next.getCurrent().equals(to)) {
        //System.out.println("Found our destination!");

        List<T> route = new ArrayList<>();
        RouteNode<T> current = next;
        do {
          route.add(0, current.getCurrent());
          current = allNodes.get(current.getPrevious());
        } while (current != null);

        //System.out.println("Route: " + route);
        return route;
      }

      graph.getConnections(next.getCurrent()).forEach(connection -> {
        double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
        RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
        allNodes.put(connection, nextNode);

        if (nextNode.getRouteScore() > newScore) {
          nextNode.setPrevious(next.getCurrent());
          nextNode.setRouteScore(newScore);
          nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
          openSet.add(nextNode);
          //System.out.println("Found a better route to node: " + nextNode);
        }
      });
    }

    throw new IllegalStateException("No route found");
  }

}
