package fr.formiko.formiko;

import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

public class GBlade extends Liste<Blade> implements Serializable {
  public GBlade(int listLength){
    for (int i=0; i<listLength; i++) {
      add(new Blade());
    }
  }
}
