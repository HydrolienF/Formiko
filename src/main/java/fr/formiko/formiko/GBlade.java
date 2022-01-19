package fr.formiko.formiko;

import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

public class GBlade extends Liste<Blade> implements Serializable {
  public GBlade(){

  }
  public void addBlades(int x){
    if(!Main.getView().isBladesEnable()){return;}
    for (int i=0; i<x; i++) {
      add(new Blade());
    }
  }
  public void removeBlades(int x){
    if(!Main.getView().isBladesEnable()){return;}
    //TODO use subList(x,len) may be a better idea. But it imply to get len...
    for (int i=0; i<x; i++) {
      remove(0);
    }
  }
}
