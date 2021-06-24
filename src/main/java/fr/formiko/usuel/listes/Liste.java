package fr.formiko.usuel.listes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Collection;

/**
*{@summary Custom Linked Liste class using Generics.}<br>
*@version 1.52
*@author Hydrolien
*/
public class Liste<T> implements Iterable<T>, Serializable, List<T> {
  protected Node<T> head, tail;
  // CONSTRUCTORS --------------------------------------------------------------
  public Liste(){}
  // GET SET -------------------------------------------------------------------
  public Node<T> getHead(){return head;}
  public Node<T> getTail(){return tail;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary add new Element at tail of the linked list}<br>
  *content content of the element to add.
  *@version 1.31
  */
  public void addTail(T content){
    if(content==null){return;}
    //if(contains(content)){return;}
    Node<T> node = new Node<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      tail.setNext(node);
      tail = node;
    }
  }
  /**
  *{@summary add new Element at head of the linked list}<br>
  *content content of the element to add.
  *@version 1.52
  */
  public void addHead(T content){
    if(content==null){return;}
    //if(contains(content)){return;}
    Node<T> node = new Node<>(content, null);
    if (head == null)
      tail = head = node;
    else {
      node.setNext(head);
      head = node;
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
  /**
  *{@summary add new Element at the default place. At the tail of the linked list}<br>
  *@version 1.31
  */
  public boolean add(T content){
    addTail(content);
    return true;
  }
  @Override
  public void add(int index, T content){
    throw new UnsupportedOperationException();
    //TODO
    // addTail(content);
    // return true;
  }
  /**
  *{@summary add an other linked list at tail of the linked list}<br>
  *@version 1.31
  */
  public void addList(Liste<T> list){
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
  public void add(Liste<T> list){addList(list);}
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
  *@version 2.1
  */
  public int size(){
    int cpt = 0;
    for (T t : this ) {
      cpt++;
    }
    return cpt;
  }public int length(){return size();}
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@version 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Liste)){return false;}
    Liste gs = (Liste)o;
    if(getHead()==null && gs.getHead()==null){return true;}
    if(getHead()==null || gs.getHead()==null){return false;}
    return getHead().equals(gs.getHead());
  }
  /**
  *{@summary Return true if this contains T content.}<br>
  *@version 2.1
  */
  @SuppressWarnings("unchecked")
  public boolean contains(Object o){
    T content = null;
    try {
      content = (T)o;
    }catch (Exception e) {}
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
  *@version 2.1
  */
  @Override
  public T get(int index){
    if(index<0){return null;}
    for (T item : this ) {
      if(index==0){return item;}
      index--;
    }
    return null;
  }
  public T getItem(int index){return get(index);}

  /**
  *{@summary copy only different item.}<br>
  *@version 1.41
  */
  public boolean removeDuplicateItem(){
    Liste<T> newList = new Liste<T>();
    boolean flag=false;
    for (T t : this ) {
      if (!newList.contains(t)){
        newList.add(t);
      }else{
        flag=true;
      }
    }
    head = newList.getHead();
    tail = newList.getTail();
    return flag;
  }
  /**
  *{@summary Delete the xa element}<br>
  *@param i the number of the element to remove.
  *@return true if it have been remove
  *@version 1.52
  */
  public boolean removeItem(int i){
    if(getHead()==null || i<0){return false;}
    if(i==0){head=getHead().getNext(); return true;}
    return getHead().removeItem(i);
  }
  @Override
  public T remove(int i){
    removeItem(i);
    return null;
  }
  /**
  *{@summary Delete the 1a t element}<br>
  *@param t the element to remove.
  *@return true if it have been remove
  *@version 1.52
  */
  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o){
    try {
      T t = (T)o;
      if(getHead()==null || t==null){return false;}
      if(getHead().getContent().equals(t)){head=getHead().getNext(); return true;}
      return getHead().remove(t);
    }catch (Exception e) {
      return false;
    }
  }
  @Override
  public Liste<T> subList(int a, int b){
    throw new UnsupportedOperationException();
  }
  @Override
  public ListIterator<T> listIterator(){
    throw new UnsupportedOperationException();
  }
  @Override
  public ListIterator<T> listIterator(int index){
    throw new UnsupportedOperationException();
  }
  @Override
  public int indexOf(Object o){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public int lastIndexOf(Object o){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public T set(int index, T t){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override
  public boolean retainAll​(Collection<?> c){
    throw new UnsupportedOperationException();
  }
  @Override
  public boolean containsAll​(Collection<?> c){
    throw new UnsupportedOperationException();
  }
  @Override
  public <T> T[] toArray(T[] a){
    throw new UnsupportedOperationException();
  }
  @Override
  public Object[] toArray(){
    throw new UnsupportedOperationException();
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean addAll​(Collection<? extends T> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        if(!add((T)o)){
          flag=false;
        }
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean addAll​(int index, Collection<? extends T> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        add(index, (T)o);
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  @SuppressWarnings("unchecked")
  public boolean removeAll​(Collection<?> c){
    boolean flag = true;
    for (Object o : c) {
      try {
        if(!remove((T)o)){
          flag=false;
        }
      }catch (Exception e) {
        flag=false;
      }
    }
    return flag;
  }
  @Override
  public void clear(){
    head = null;
    tail = null;
  }
  /**
  *{@summary return the coresponding Iterator}<br>
  *@version 1.31
  */
  public Iterator<T> iterator(){
    return new ListeIterator<T>(this);
  }
}
/**
*{@summary Iterator of the Linked Liste}<br>
*@version 1.31
*@author Hydrolien
*/
class ListeIterator<T> implements Iterator<T> {
  private Node<T> current;

  /**
  *{@summary Initialize pointer to head of the list for iteration.}<br>
  *@version 1.31
  */
  public ListeIterator(Liste<T> list){
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
*{@summary Constituent Node of the Linked Liste}<br>
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
  *{@summary Delete the xa element}<br>
  *@param i the number of the element to remove.
  *@return true if it have been remove
  *@version 1.52
  */
  public boolean removeItem(int i){
    if(getNext() == null){return false;}
    if(i==0){
      next = getNext().getNext();//go over the element.
      return true;
    }
    return getNext().removeItem(i-1);
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
