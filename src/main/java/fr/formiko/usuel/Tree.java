package fr.formiko.usuel;

import fr.formiko.usuel.listes.Liste;

import java.io.Serializable;

/**
*{@summary Custom Tree class using Generics.}<br>
*@version 2.6
*@author Hydrolien
*/
public class Tree<T> implements Serializable{
  private Node<T> root;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}
  *@version 2.6
  */
  public Tree(T rootData) {
    root = new Node<T>();
  }
  // GET SET -------------------------------------------------------------------
  public Node<T> getRoot(){return root;}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Node used by tree.}<br>
  *It have a parent, children &#38; data.
  *@version 2.6
  *@author Hydrolien
  */
  public static class Node<T> {
    private T data;
    private final Node<T> parent;
    private Liste<Node<T>> children;

    // CONSTRUCTORS --------------------------------------------------------------
    /**
    *{@summary Main constructor.}
    *@version 2.6
    */
    public Node(Node<T> parent){
      this.parent=parent;
      children = new Liste<Node<T>>();
    }
    /**
    *{@summary Secondary constructor used only by the Tree constructor.}
    *@version 2.6
    */
    public Node(){
      this(null);
    }
    // GET SET -------------------------------------------------------------------
    public Node<T> getParent(){return parent;}
    public Node<T> getChildren(int index){return children.get(index);}
    public T getData(){return data;}
    public void setData(T data){this.data=data;}

    // FUNCTIONS -----------------------------------------------------------------
    /**
    *{@summary Add a child to the children list.}
    *@param childData the data of the child.
    *@version 2.6
    */
    public void addChildren(T childData){
      Node<T> node = new Node<T>(this);
      node.setData(childData);
      children.add(node);
    }
    /**
    *{@summary Add a child to the children list.}
    *@version 2.6
    */
    public void addChildren(){
      children.add(new Node<T>(this));
    }
  }
}
