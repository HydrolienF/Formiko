package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.math.allea;
import fr.formiko.usuel.Message;
import fr.formiko.usuel.read;
import fr.formiko.graphisme.PanneauTInt;
import fr.formiko.usuel.tableau;
import java.io.Serializable;

/**
 * {@summary Ant queen implementation.<br/>}
 * Allow an ant queen to lay<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class PondreReine implements Serializable, Pondre{
  private Fourmi f;

  public void unePonte( Creature c){
    debug.débogage("la créature "+c.getId()+" tente de pondre");
    f = (Fourmi) c;
    pondre();
    debug.débogage("fin de la ponte");
  }

  protected void pondre(){
    // Ne s'execute que si la reine a suffisement de nourriture et qu'elle est a sa fourmilière
    // diminue la nourriture de la reine.
    // génère une fourmi avec un age négatif et un mouvement nul jusqu'a sa naissance. (a la fourmilière)
    Fourmiliere fere = f.getFourmiliere();
    if (f.peutPondre()){ // f.getP().equals(p.getPointDelaFouriliere)
      //byte type = choixType(); // 0 et 1 sont a évité en début de jeu.
      byte type = 3;
      Fourmi fm = new Fourmi(f.getFourmiliere(),f.getEspece(),type);
      fere.getGc().ajouter(fm);
      fm.setAgeMax((int)((double)(f.getEspece().getIndividuParType(type).getAgeMax(0)*fm.getMultiplicateurDeDiff())));
      //f.getFourmiliere().getCCase().getContenu().getGc().ajouter(fm);
      //fere.getGc().ajouter(fm); l'ajout a la fourmilière ce fait dans le constructeur de Fourmi.
      f.setNourriture(f.getNourriture() - 12 );
      Message m = new Message("La fourmi " +fm.getId() + " est née.", fere.getId(), 3);
      f.setActionMoins(f.getEspece().getGIndividu().getIndividuParType( f.getTypeF()).getCoutPondre());
      f.setAilesCoupees(true);//une reine qui pond n'as plus d'ailes.
    }
  }
  protected byte choixType(){
    if (!f.getFere().getJoueur().getIa()){
      int ti[] = f.getEspece().getTypeDIndividu();
      //byte x = (byte) read.getInt(0,10,"type de l'oeuf",3);
      Main.getPp().getPj().addPti(ti,3);
      tableau.afficher(ti);
      byte x = (byte) PanneauTInt.getChoixId();
      if(f.getEspece().getGIndividu().getIndividuParType(x) != null){
        return x;
      }
      erreur.erreur("Le type spécifié par le joueur n'est pas défini pour cette Espece.","PondreReine.choixType","3 est le type choisi a la place.",false);
      return 3;
    }
    if (f.getFere().getGc().length() < f.getEspece().getNbrDIndividuMax()/10){ // si la fourmilière n'est pas encore a 10% de son develloppement max pas de fourmi de type 0 ou 1.
      return 3; // si il y a un type 4 ou 2 il faudrait pouvoir en faire naitre de temps en temps.
    }else{
      if(allea.getAlléa(5) == 0){ return 0;} // Reine.
      if(allea.getAlléa(5) == 0){ return 1;} // Male.
      return 3; // ouvrière.
    }
  }
}
