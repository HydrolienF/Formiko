package fr.formiko.formiko;

import fr.formiko.usual.structures.listes.Liste;

import java.io.Serializable;

/**
*{@summary Groups of vegetal blade, used to represent grass &#38; moss on map.}<br>
*@author Hydrolien
*@lastEditedVersion 2.16
*/
public class GBlade extends Liste<Blade> implements Serializable {
  private byte type;
  /**
  *{@summary Main contructor with a given type.}<br>
  *@lastEditedVersion 2.16
  */
  public GBlade(int type){
    this.type = (byte)type;
  }
  /**
  *{@summary Add blades of the GBlade type.}<br>
  *@param x how much to add
  *@lastEditedVersion 2.16
  */
  public void addBlades(int x){
    if(!Main.getView().isBladesEnable()){return;}
    for (int i=0; i<x; i++) {
      add(Blade.newBlade(type));
    }
  }
  /**
  *{@summary remove blades.}<br>
  * We always remove the older blade first.
  *@param x how much to remove
  *@lastEditedVersion 2.16
  */
  public void removeBlades(int x){
    if(!Main.getView().isBladesEnable()){return;}
    //TODO use subList(x,len) may be a better idea. But it imply to get len...
    for (int i=0; i<x; i++) {
      remove(0);
    }
  }
}
