package fr.formiko.formiko;

import fr.formiko.formiko.triche;

import java.util.Scanner;

/**
*{@summary Listen all command line entry &#38; launch a cheat code of each one.}
*@lastEditedVersion 2.30
*@author Hydrolien
*/
public class ThTriche extends Thread {
  // CONSTRUCTORS --------------------------------------------------------------
  public ThTriche(){}
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Listen all command line entry &#38; launch a cheat code of each one.}
  *@lastEditedVersion 2.30
  */
  @Override
  public void run(){
    Scanner sc = new Scanner(System.in);
    while(sc.hasNext()){
      String s = sc.nextLine();//le reste du programme tourne pendant l'attente d'une commande.
      triche.commande(s);
    }
  }
}
