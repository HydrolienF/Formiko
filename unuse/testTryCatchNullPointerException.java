package fr.formiko.usuel;

import java.util.Random;

public class testTryCatchNullPointerException {

  public static void doTest(){
    int nbr = 100000000;
    System.out.println(testTryCatchVsPreCheck(nbr,1));
    System.out.println(testTryCatchVsPreCheck(nbr,0.5));
    System.out.println(testTryCatchVsPreCheck(nbr,0.1));
    System.out.println(testTryCatchVsPreCheck(nbr,0));
  }

  public static String testTryCatchVsPreCheck(int numberOfTest, double partOfNull){
    long timeTryCatch, timePreCheck, timeToIni, timeToIni2;
    Chrono ch = new Chrono();
    ch.start();
    testEmpty(numberOfTest, partOfNull);
    ch.stop();
    timeToIni = ch.getDuree();

    ch = new Chrono();
    ch.start();
    testEmpty(numberOfTest, partOfNull);
    ch.stop();
    timeToIni2 = ch.getDuree();

    ch = new Chrono();
    ch.start();
    testTryCatch(numberOfTest, partOfNull);
    ch.stop();
    timeTryCatch = ch.getDuree();

    ch = new Chrono();
    ch.start();
    testPreCheck(numberOfTest, partOfNull);
    ch.stop();
    timePreCheck = ch.getDuree();
    return "for "+numberOfTest+" & "+partOfNull+" null value : "+timeToIni+" "+timeToIni2+" "+timeTryCatch+" "+timePreCheck;
  }
  public static void testTryCatch(int numberOfTest, double partOfNull){
    for (int i=0; i<numberOfTest; i++) {
      Obj o = Obj.newObj(partOfNull);
      try {
        o.getS();
      }catch (NullPointerException e) {}
    }
  }
  public static void testPreCheck(int numberOfTest, double partOfNull){
    for (int i=0; i<numberOfTest; i++) {
      Obj o = Obj.newObj(partOfNull);
      if(o!=null){
        o.getS();
      }
    }
  }
  public static void testEmpty(int numberOfTest, double partOfNull){
    for (int i=0; i<numberOfTest; i++) {
      Obj o = Obj.newObj(partOfNull);
    }
  }
}
class Obj{
  private static Random random = new Random();
  String s;
  public Obj(String s){
    this.s=s;
  }
  public static Obj newObj(double partOfNull){
    if(random.nextDouble() > partOfNull){
      return null;
    }else{
      return new Obj("str");
    }
  }
  public String getS(){return s;}
}
