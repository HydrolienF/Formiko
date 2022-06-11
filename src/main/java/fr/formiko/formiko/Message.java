package fr.formiko.formiko;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class Message implements Serializable{
  // En fonction du joueur qui joue son tour, il faudrait afficher ou non les messages selon ses préférences.
  // + afficher a chaque joueur les messages qui sont éxectuté sur des cases qu'il voie.
  // A chaque affichage d'un message on l'envoie a la liste de tt les messages (utile pour débogué) et au joueur concerné si le type du message est accèpté chez eux.
  static int i=0; int id;
  private String expediteur;
  private String texte;
  private int idDuJoueurConcerné;
  private int typeDeMessage;
  private static byte niveauDeDétailDeLAffichage = 1;
  // String texte contient le corps du message,
  // les types de message a ma verion 0.14.0 sont :
  // 0 type null
  // 1 type mort de Fourmi
  // 2 type mort d'insecte / chasse
  // 3 type naissance de Fourmi
  // 4 combat
  // 5 trophallaxie
  // 6 déroulement du jeux

  // piste de simplification pas forcément adaptée :
  //Dialoguer d = (s) -> erreur.println("Tu as dis : " + s);
  //d.parler("Bonjour");


  // CONSTRUCTORS ----------------------------------------------------------------
  public Message(String texte, int idDuJoueurConcerné, int typeDeMessage, String expediteur){
    id = i; i++;
    this.texte = g.getM(texte);
    this.idDuJoueurConcerné = idDuJoueurConcerné;
    this.typeDeMessage = typeDeMessage;
    this.expediteur = expediteur;
    try {
      debug.débogage("id = "+idDuJoueurConcerné);
      if (idDuJoueurConcerné==-1){
        GJoueur gj = Main.getGj().getJoueurHumain();
        // debug.débogage(gj.length()+" joueurs on été detecté comme humain");
        gj.addMessage(this);
      }else if(idDuJoueurConcerné>0){
        Main.getJoueurById(idDuJoueurConcerné).addMessage(this);
      }
    }catch (Exception e) {}
  }
  // message d'un joueur :
  public Message(String texte, int idDuJoueurConcerné, String expediteur){
    this(texte, idDuJoueurConcerné, 0, expediteur);
  }
  public Message(String texte, String expediteur){
    this(texte, -1, expediteur);
  }

  // message de la console :
  public Message(String texte, int idDuJoueurConcerné, int typeDeMessage){
    this(texte, idDuJoueurConcerné, typeDeMessage, null);
  }
  public Message(String texte, int idDuJoueurConcerné){
    this(texte, idDuJoueurConcerné, 0);
  }
  public Message(String texte){
    this(texte, -1, 0);
  }

  // GET SET ----------------------------------------------------------------------
  public int getId(){ return id;}
  // FUNCTIONS -----------------------------------------------------------------
  public void afficheToi(){ // idéalement la méthode d'affichage n'affiche que si les paramètres d'affichage du joueur le lui demande.
    //if (Main.getNiveauDeDétailDeLAffichage()>0){
    if (niveauDeDétailDeLAffichage>0){
      erreur.println(description());
    }
  }
  public String description(){
    if(expediteur!=null){
      return expediteur +" : " + texte;
    }else{
      return texte;
    }
  }
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Message)){return false;}
    if (((Message)(o)).getId()==getId()){ return true;}
    return false;
  }
  // message particuliers :
  public static void messageMort(Fourmi f, int raison, Creature cr){
    try {
      debug.débogage("Message de morts");
      // le message est destiné au joueurs qui voie la fourmi ou qui possède la fourmi.
      // la fourmi neutre / allié / énemie (id) est morte / a été infectée par une bactérie mortelle / est morte de vieillesse / est morte face au mandibule de la foumi / a été aspergé d'acide par la fourmi / l'insecte idDuTueur.
      GJoueur gj = Main.getGj().getJoueurHumain();
      if(gj.length()==0){
        String nom;
        if (f!=null) {
          nom = f.getNom();
        }else{
          nom = g.get("creature");
        }
        new Message(g.getM("la")+" "+nom+" "+f.getId()+" "+g.get("du")+" "+g.get("joueur")+" "+f.getJoueur().getId()+" "+ g.get("mort"+raison));
        Main.setPlayingAnt(f); //to refrech playingant info
        return;
      }
      //Ici on doit filterr les joueurs qui ne vois pas la case ou la fourmi meurt.


      String laNotre = g.getM("la");
      for (Joueur j : gj) {
        String status = g.get("neutre");
        Fourmi r =null;
        try {
          r = j.getFere().getGc().getReine(); // par défaut la reine sert a identifié si la fourmi est alliées neutre ou énemies.
          if (r==null){ r= (Fourmi)(j.getFere().getGc().getFirst());}//sinon on prend la 1a fourmi du GCreature.
        }catch (Exception e) {}
        if (r!=null){
          if (((Creature)f).getEstAllié(r)){
            status = g.get("allié");
          }else if (((Creature)f).getEstEnnemi(r)){
            status = g.get("ennemi");
          }
          if(j.equals(f.getJoueur())){ laNotre = g.getM("votre"); status="";}
          else{status = status+" ";}
        }
        String nom = "";
        if(cr==null){
          nom="null";
        }else{
          nom=cr.getNom();
        }
        String tueur = g.getOr("la","le")+" "+nom;
        String texte = laNotre +" "+ g.get("fourmi")+" "+status+"("+f.getId()+")"+" "+ g.get("mort"+raison)+" "+tueur+".";
        new Message(texte,j.getId());
      }
    }catch (Exception e2) {
      erreur.alerte("death message fail.");
    }
  }
  public static void messageMort(Fourmi f, int r){
    messageMort(f,r,(Creature)null);
  }
  public static void messageMort(Fourmi f){
    messageMort(f,0);
  }
}
