package fr.formiko.usuel.listes;

import java.io.Serializable;
import java.util.Iterator;

// Custom Linked List class using Generics
public class List<T> implements Iterable<T>, Serializable {
  private Node<T> head, tail;
  // CONSTRUCTORS --------------------------------------------------------------
  public List(){}
  // GET SET -------------------------------------------------------------------
  public Node<T> getHead(){return head;}
  public Node<T> getTail(){return tail;}
  // FUNCTIONS -----------------------------------------------------------------
  // add new Element at tail of the linked list
  public void add(T content){
    Node<T> node = new Node<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      tail.setNext(node);
      tail = node;
    }
  }
  public void add(List<T> list){
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
  // return Iterator instance
  public Iterator<T> iterator(){
    return new ListIterator<T>(this);
  }
}

class ListIterator<T> implements Iterator<T> {
    private Node<T> current;

    // initialize pointer to head of the list for iteration
    public ListIterator(List<T> list){
      current = list.getHead();
    }

    // returns false if next element does not exist
    public boolean hasNext(){return current != null;}

    // return current content and update pointer
    public T next(){
      T content = current.getContent();
      current = current.getNext();
      return content;
    }

    // implement if needed
    public void remove(){
      throw new UnsupportedOperationException();
    }
}

// Constituent Node of Linked List
class Node<T> {
  private T content;
  private Node<T> next;
  public Node(T content, Node<T> next){
      this.content = content;
      this.next = next;
  }
  // GET SET -------------------------------------------------------------------
  public void setContent(T content){this.content = content;}
  public void setNext(Node<T> next){this.next = next;}
  public T getContent(){return content;}
  public Node<T> getNext(){return next;}
}
