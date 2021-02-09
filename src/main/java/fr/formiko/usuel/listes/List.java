package fr.formiko.usuel.listes;

import java.io.Serializable;
import java.util.Iterator;

/**
*{@summary Custom Linked List class using Generics}<br>
*@version 1.31
*@author Hydrolien
*/
public class List<T> implements Iterable<T>, Serializable {
  private Node<T> head, tail;
  // CONSTRUCTORS --------------------------------------------------------------
  public List(){}
  // GET SET -------------------------------------------------------------------
  public Node<T> getHead(){return head;}
  public Node<T> getTail(){return tail;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary add new Element at tail of the linked list}<br>
  *@version 1.31
  */
  public void addTail(T content){
    if(content==null){return;}
    if(containt(content)){return;}
    Node<T> node = new Node<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      tail.setNext(node);
      tail = node;
    }
  }
  /***
  *{@summary add new Element at the default place. At the tail of the linked list}<br>
  *@version 1.31
  */
  public void add(T content){addTail(content);}
  /**
  *{@summary add an other linked list at tail of the linked list}<br>
  *@version 1.31
  */
  public void addList(List<T> list){
    if(list == null || list.getHead() == null){ return;}
    if (getTail() == null){
      head = list.getHead();
      tail = list.getTail();
    }else {
      tail.setNext(list.getHead());
      //list.getHead().add(tail);
      tail = list.getTail();
    }
  }
  /**
  *{@summary Return the number of element.}<br>
  *@version 1.31
  */
  public int length(){
    int cpt = 0;
    for (T t : this ) {
      cpt++;
    }
    return cpt;
  }
  /**
  *{@summary Return true if this containt T content.}<br>
  *@version 1.31
  */
  public boolean containt(T content){
    if(content==null){return false;}
    for (T t : this ) {
      //TODO #197 it do not use the overriding equals methode (cf ListTest for more information)
      //if(content instanceof T && t instanceof T){
        if(((T)(t)).equals(content)){return true;}
      /*}else{
        System.out.println("pas dans le bon if");//@a
      }*/
    }
    return false;
  }
  /**
  *{@summary Return a long string with all the list value split by ' '.}<br>
  *@version 1.31
  */
  @Override
  public String toString(){
    String r = "";
    for (T t : this ) {
      r+= t.toString();
      r+= " ";
    }
    return r;
  }
  /**
  *{@summary return the coresponding Iterator}<br>
  *@version 1.31
  */
  public Iterator<T> iterator(){
    return new ListIterator<T>(this);
  }
}
/**
*{@summary Iterator of the Linked List}<br>
*@version 1.31
*@author Hydrolien
*/
class ListIterator<T> implements Iterator<T> {
    private Node<T> current;

    /**
    *{@summary Initialize pointer to head of the list for iteration.}<br>
    *@version 1.31
    */
    public ListIterator(List<T> list){
      current = list.getHead();
    }

    public boolean hasNext(){return current != null;}

    /**
    *{@summary Return current content and update pointer.}<br>
    *@version 1.31
    */
    public T next(){
      T content = current.getContent();
      current = current.getNext();
      return content;
    }
    /**
    *{@summary Unsupported operation}<br>
    *It may been add in futur version.
    *@version 1.31
    */
    public void remove(){
      throw new UnsupportedOperationException();
    }
}
/**
*{@summary Constituent Node of the Linked List}<br>
*@version 1.31
*@author Hydrolien
*/
class Node<T> {
  private T content;
  private Node<T> next;
  /**
  *{@summary Main constructor for Node.}<br>
  *@version 1.31
  */
  public Node(T content, Node<T> next){
      this.content = content;
      this.next = next;
  }
  // GET SET -------------------------------------------------------------------
  public T getContent(){return content;}
  public void setContent(T content){this.content = content;}
  public Node<T> getNext(){return next;}
  public void setNext(Node<T> next){this.next = next;}
}
