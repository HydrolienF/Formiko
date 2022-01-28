package fr.formiko.usuel.structures;

import fr.formiko.formiko.Creature;

import java.io.Serializable;
import java.lang.UnsupportedOperationException;

/**
*{@summary Node Object extends by listeNode &#38; TreeNode.}<br>
*It have some getter that can be used only if it's a list node or only if it's a tree node.
*@author Hydrolien
*@lastEditedVersion 2.6
*/
public class Node<T> implements Serializable {
  // protected T content;

  // public T getContent(){return content;}
  // public void setContent(T content){this.content=content;}

  public Node<T> getChildren(int x){throw new UnsupportedOperationException();}
  // public Node<T> getNext(){throw new UnsupportedOperationException();}
}
