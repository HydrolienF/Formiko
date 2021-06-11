package fr.formiko.usuel.listes;

import java.io.Serializable;
import java.util.Iterator;

/**
*{@summary Custom Linked List class using Generics.}<br>
*@version 1.41
*@author Hydrolien
*/
public class List<T> implements Iterable<T>, Serializable {
  protected Node<T> head, tail;
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
    //if(containt(content)){return;}
    Node<T> node = new Node<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      tail.setNext(node);
      tail = node;
    }
  }
  /**
  *{@summary Update tail element.}<br>
  *@version 1.41
  */
  public void updateTail(){
    Node<T> node = getHead();
    while(node.getNext()!=null){
      node=node.getNext();
    }
    tail=node;
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
  public void add(List<T> list){addList(list);}
  /**
  *{@summary Return true is list is empty.}<br>
  *It's a better function than doing list.length()==0.
  *@version 1.52
  */
  public boolean isEmpty(){
    for (T t : this ) {
      return false;
    }
    return true;
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
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof List)){return false;}
    List gs = (List)o;
    if(getHead()==null && gs.getHead()==null){return true;}
    if(getHead()==null || gs.getHead()==null){return false;}
    return getHead().equals(gs.getHead());
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
        System.out.println("pas dans le bon if");
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
  *{@summary Return a long string with all the list value split by '\n'.}<br>
  *@version 1.41
  */
  public String toStringLong(){
    String r = "";
    for (T t : this ) {
      r+= t.toString();
      r+= "\n";
    }
    return r;
  }
  /**
  *{@summary return the xa item}<br>
  *@version 1.31
  */
  public T getItem(int id){
    if(id<0){return null;}
    for (T item : this ) {
      if(id==0){return item;}
      id--;
    }
    return null;
  }
  /**
  *{@summary copy only different item.}<br>
  *@version 1.41
  */
  public void removeDuplicateItem(){
    List<T> newList = new List<T>();
    for (T t : this ) {
      if (!newList.containt(t)){
        newList.add(t);
      }
    }
    head = newList.getHead();
    tail = newList.getTail();
  }
  /**
  *{@summary Delete the 1a t element}<br>
  *@param t the element to remove.
  *@return true if it have been remove
  *@version 1.41
  */
  public boolean remove(T t){
    if(getHead()==null || t==null){return false;}
    return getHead().remove(t);
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
}
/**
*{@summary Constituent Node of the Linked List}<br>
*@version 1.41
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
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Equals function that also test next.}<br>
  *@version 1.41
  */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o){
    if(o==null){return false;}
    if(o instanceof Node){
      try {
        Node<T> node = (Node<T>)o;
        if(getContent().equals(node.getContent())){
          if(getNext()==null && node.getNext()==null){return true;}
          if(getNext()==null || node.getNext()==null){return false;}
          return getNext().equals(node.getNext());
        }
      }catch (Exception e) {}
    }
    return false;
  }
  /**
  *{@summary Delete the 1a t element}<br>
  *@param t the element to remove.
  *@return true if it have been remove
  *@version 1.41
  */
  public boolean remove(T t){
    if(getNext() == null){return false;}
    if(getNext().getContent().equals(t)){
      next = getNext().getNext();//go over the element.
      return true;
    }
    return getNext().remove(t);
  }
}
