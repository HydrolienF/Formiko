package fr.formiko.usuel.astar;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
*{@summary Create the graph	that will be used by RouteFinder.}
*It need a set of nodes &#38; a map of nodes &#38; there connected nodes.
*taken from https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-2/src/test/java/com/baeldung/algorithms/astar
*@author sazzer
*@lastEditedVersion 2.23
*/
public class Graph<T extends GraphNode> {
  private final Set<T> nodes;
  private final Map<String, Set<String>> connections;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@lastEditedVersion 2.23
  */
  public Graph(Set<T> nodes, Map<String, Set<String>> connections) {
    this.nodes = nodes;
    this.connections = connections;
  }

  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return a node depending of it's id.}
  *@param id id that represent a Node
  *@return the node with this id
  *@lastEditedVersion 2.23
  */
  public T getNode(String id) {
    return nodes.stream()
      .filter(node -> node.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
  }
  /**
  *{@summary Return the set of connected node to the give node.}
  *@param node node that we want connected nodes
  *@return the set of connected node
  *@lastEditedVersion 2.23
  */
  public Set<T> getConnections(T node) {
    return connections.get(node.getId()).stream()
      .map(this::getNode)
      .collect(Collectors.toSet());
  }
}
