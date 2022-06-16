package fr.formiko.formiko;

import fr.formiko.formiko.triche;

import java.util.Scanner;

public class ThTriche extends Thread{
  // CONSTRUCTORS --------------------------------------------------------------
  public ThTriche(){}
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void run(){
    Scanner sc = new Scanner(System.in);
    while(sc.hasNext()){
      String s = sc.nextLine();//le reste du programme tourne pendant l'attente d'une commande.
      triche.commande(s);
    }
  }
}
