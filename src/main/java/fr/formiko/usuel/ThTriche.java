package fr.formiko.usuel;

import fr.formiko.formiko.triche;

import java.util.Scanner;

public class ThTriche extends Thread{
  // CONSTRUCTEUR ---------------------------------------------------------------
  public ThTriche(){}
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    triche.ini();
    Scanner sc = new Scanner(System.in);
    while(sc.hasNext()){
      String s = sc.nextLine();//le reste du programme tourne pendant l'attente d'une commande.
      debug.débogage("Commande : "+s);
      triche.commande(s);
      debug.débogage("fin de la commande");
    }
  }
}
