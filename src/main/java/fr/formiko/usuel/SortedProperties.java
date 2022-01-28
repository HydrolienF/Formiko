package fr.formiko.usuel;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.*;
/**
*{@summary An extends class from Properties that is used to save Options in Formiko.}<br>
*Cf: https://stackoverflow.com/questions/10275862/how-to-sort-properties-in-java <br>
*@lastEditedVersion 1.34
*@author Stéphane Millien
*/
public class SortedProperties extends Properties {
  /**
  *{@summary A simple extends for constructor with a default properties a args.}
  *@lastEditedVersion 1.34
  */
  public SortedProperties(Properties p){
    super(p);
  }
  /**
  *{@summary A simple extends for constructor with a default number of properties as args.}
  *@lastEditedVersion 1.34
  */
  public SortedProperties(int x){
    super(x);
  }
  /**
  *{@summary Set a sortable Set.}
  *@lastEditedVersion 1.34
  */
  @Override
  public Set<Object> keySet() {
    return Collections.unmodifiableSet(new TreeSet<Object>(super.keySet()));
  }
  /**
  *{@summary Function to force sorting.}
  *@lastEditedVersion 1.34
  */
  @Override
  public Set<Map.Entry<Object, Object>> entrySet() {
    Set<Map.Entry<Object, Object>> set1 = super.entrySet();
    Set<Map.Entry<Object, Object>> set2 = new LinkedHashSet<Map.Entry<Object, Object>>(set1.size());
    Iterator<Map.Entry<Object, Object>> iterator = set1.stream().sorted(new Comparator<Map.Entry<Object, Object>>() {
      @Override
      public int compare(java.util.Map.Entry<Object, Object> o1, java.util.Map.Entry<Object, Object> o2) {
      return o1.getKey().toString().compareTo(o2.getKey().toString());
      }
    }).iterator();
    while (iterator.hasNext())
      set2.add(iterator.next());
    return set2;
  }
  /**
  *{@summary to get the sorted keySet.}
  *@lastEditedVersion 1.34
  */
  @Override
  public synchronized Enumeration<Object> keys() {
    return Collections.enumeration(new TreeSet<Object>(super.keySet()));
  }
}
