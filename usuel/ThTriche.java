package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.41.2
import java.util.Scanner;
import fr.formiko.formiko.triche;

public class ThTriche extends Thread{
  // CONSTRUCTEUR ---------------------------------------------------------------
  public ThTriche(){}
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public void run(){
    triche.ini();
    Scanner sc = new Scanner(System.in);
    while(true){
      String s = sc.nextLine();//le reste du programme tourne pendant l'attente d'une commande.
      debug.débogage("Commande : "+s);
      triche.commande(s);
      debug.débogage("fin de la commande");
    }
  }
}