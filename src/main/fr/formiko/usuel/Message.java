package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.*;
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
  //Dialoguer d = (s) -> System.out.println("Tu as dis : " + s);
  //d.parler("Bonjour");


  // CONSTRUCTEUR -----------------------------------------------------------------
  public Message(String texte, int idDuJoueurConcerné, int typeDeMessage, String expediteur){
    id = i; i++;
    this.texte = texte;
    this.idDuJoueurConcerné = idDuJoueurConcerné;
    this.typeDeMessage = typeDeMessage;
    this.expediteur = expediteur;
    try {
      debug.débogage("id = "+idDuJoueurConcerné);
      if (idDuJoueurConcerné==-1){
        GJoueur gj = Main.getGj().getJoueurHumain();
        debug.débogage(gj.length()+" joueurs on été detecté comme humain");
        gj.addMessage(this);
      }else if(idDuJoueurConcerné>0){
        Main.getJoueurParId(idDuJoueurConcerné).addMessage(this);
      }
    }catch (Exception e) {}
    //this.afficheToi();
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
    this(texte, idDuJoueurConcerné, typeDeMessage, g.get("Message",1,"Console"));
  }
  public Message(String texte, int idDuJoueurConcerné){
    this(texte, idDuJoueurConcerné, 0);
  }
  public Message(String texte){
    this(texte, -1, 0);
  }

  // GET SET -----------------------------------------------------------------------
  public int getId(){ return id;}
  // Fonctions propre -----------------------------------------------------------
  public void afficheToi(){ // idélment la méthode d'affichage n'affiche que si les paramètres d'affichage du joueur le lui demande.
    //if (Main.getNiveauDeDétailDeLAffichage()>0){
    if (niveauDeDétailDeLAffichage>0){
      System.out.println(description());
    }
  }
  public String description(){
    return expediteur +" : " + texte;
  }
  public boolean equals(Message m){
    if (m.getId()==id){ return true;}
    return false;
  }
  // message particuliers :
  public static void messageMort(Fourmi f, int raison, Creature cr){
    debug.débogage("Message de morts");
    // le message est destiné au joueurs qui voie la fourmi ou qui possède la fourmi.
    // la fourmi neutre / allié / énemie (id) est morte / a été infectée par une bactérie mortelle / est morte de vieillesse / est morte face au mandibule de la foumi / a été aspergé d'acide par la fourmi / l'insecte idDuTueur.
    GJoueur gj = Main.getGj().getJoueurHumain();
    if(gj.length()==0){
      System.out.println("La fourmi "+f.getId()+" du joueur "+f.getJoueur().getId()+" "+ g.get("mort"+raison));
      return;}
    debug.débogage(gj.length()+" joueur humains");
    //Ici on doit filtrer les joueurs qui ne vois pas la case ou la fourmi meurt.



    CJoueur cj = gj.getDébut();
    String laNotre = g.getM("la");
    while(cj!=null){
      Joueur j = cj.getContenu();
      String status = g.get("neutre");
      Fourmi r =null;
      try {
        r = j.getFere().getGc().getReine(); // par défaut la reine sert a identifié si la fourmi est alliées neutre ou énemies.
        if (r==null){ r= (Fourmi)(j.getFere().getGc().getDébut().getContenu());}//sinon on prend la 1a fourmi du GCreature.
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
      String tueur = "";
      if (cr!= null){
        if(cr instanceof Fourmi){
          Fourmi fTemp = (Fourmi)cr;
          tueur = g.get("la")+" "+g.get("fourmi");
        }else if(cr instanceof Insecte){
          Insecte iTemp = (Insecte)cr;
          tueur = g.get("l'")+" "+g.get("insecte")+g.get("n");
        }else{
          tueur = g.get("la")+" "+g.get("créature")+g.get("n");
        }
        tueur = " "+tueur+" "+cr.getId();
      }
      String texte = laNotre +" "+ g.get("fourmi")+" "+status+"("+f.getId()+")"+" "+ g.get("mort"+raison)+tueur+".";
      debug.débogage("1 nouveau message de mort pour le joueur "+cj.getContenu().getId());
      new Message(texte,cj.getContenu().getId());
      cj=cj.getSuivant();
    }
  }
  public static void messageMort(Fourmi f, int r){
    messageMort(f,r,(Creature)null);
  }
  public static void messageMort(Fourmi f){
    messageMort(f,0);
  }
}
