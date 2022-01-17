package fr.formiko.formiko;

import fr.formiko.usuel.Point;

import java.util.Random;

public class Blade extends Point {
  private byte direction; //0 to 90
  // private byte length; //0 to 100
  private static Random rand;
  public Blade(){
    if(rand==null){rand=new Random();}
    direction = rand.nextInt(91);
  }
  public byte getDirection(){return direction;}
  public byte getLength(){return 4;}
}
