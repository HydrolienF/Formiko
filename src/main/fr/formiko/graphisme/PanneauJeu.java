package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import fr.formiko.formiko.*;
import fr.formiko.usuel.image.image;
import fr.formiko.usuel.math.math;

public class PanneauJeu extends Panneau {
  private PanneauCarte pc;
  private PanneauBouton pb;
  private Fourmi fActuelle;
  private PanneauChargement pch;
  private PanneauFinPartie pfp;
  private PanneauEchap pe;
  private PanneauSup ps;
  private PanneauDialogue pd;
  private PanneauDialogueInf pdi;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauJeu(){
    this.setLayout(null);
  }
  // GET SET -------------------------------------------------------------------
  public Fourmi getFActuelle(){ return fActuelle;}
  public Joueur getJoueurActuel(){
    if (fActuelle!=null){
      return fActuelle.getFere().getJoueur();}
    else if(Main.getPartie().getGj().getJoueurHumain().getDébut()!=null && Main.getPartie().getGj().getJoueurHumain().length()==1){
      return Main.getPartie().getGj().getJoueurHumain().getDébut().getContenu();
    }
    return null;
  }
  public void setFActuelle(Fourmi f){fActuelle=f; Main.repaint();}
  public PanneauBouton getPb(){ return pb;}
  public PanneauCarte getPc(){ return pc;}
  public PanneauChargement getPch(){ return pch;}
  public PanneauSup getPs(){return ps;}
  public PanneauEchap getPe(){return pe;}
  public PanneauDialogue getPd(){return pd;}
  public PanneauDialogueInf getPdi(){return pdi;}
  //get set transmis
  public void addPA(){ pb.addPA();}
  public void setActionF(int x){pb.setActionF(x);}
  public void addPti(int x [], int y){pb.addPti(x,y);}
  public int getActionF(){ return pb.getActionF();}
  public void setDesc(String s){pb.setDesc(s);}
  public void setDescTI(String s){pb.setDescTI(s);}
  public PanneauTInt getPti(){return pb.getPti(); }
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    try {
      pc.setBounds(0,0,this.getWidth(),this.getHeight());
    }catch (Exception e) {}
    try {
      pb.setBounds(0,0,this.getWidth(),this.getHeight());
    }catch (Exception e) {}
    try {
      ps.setBounds(0,0,this.getWidth(),this.getHeight());
    }catch (Exception e) {}
    try {
      pe.setBounds(0,0,this.getWidth(),this.getHeight());
    }catch (Exception e) {}
    try {
      pd.setBounds(0,0,pd.getWidth(),pd.getHeight());
    }catch (Exception e) {}
  }
  public void addPd(){
    if(pd==null){pd = new PanneauDialogue();}
    if(pdi==null){
      PanneauDialogueInf.chargerFond();
      pdi = new PanneauDialogueInf();
    }
    add(pd);
    add(pdi);
  }
  public void initialiserPd(String s){
    pd.initialiser(s);
    pdi.initialiser();
    pd.setBounds(0,0,pd.getWidth(),pd.getHeight());
    revalidate();
  }
  public void removePd(){
    remove(pd);
    remove(pdi);
  }
  public void addPe(){
    pe=new PanneauEchap();
    pe.setBounds(0,0,0,0);
    add(pe);
  }
  public void removePe(){
    pe.setVisible(false);
    ps.actualiserTaille();
    revalidate();
    Main.repaint();
  }
  public void addPs(){
    ps=new PanneauSup();
    ps.setBounds(0,0,0,0);
    add(ps);
  }
  public void addPc(){
    pc = new PanneauCarte();
    pc.setBounds(0,0,0,0);
    add(pc);
  }
  public void addPb(){
    pb = new PanneauBouton();
    pb.setBounds(0,0,0,0);
    add(pb);
  }
  public void addPch(){
    pch = new PanneauChargement();
    pch.setBounds(0,0,this.getWidth(),this.getHeight());
    add(pch);
    pc.setVisible(false);
    pb.setVisible(false);
  }
  public void removePch(){
    remove(pch);
    pch = null;
    pc.setVisible(true);
    pb.setVisible(true);
  }
  public void addPfp(){
    pfp = new PanneauFinPartie();
    add(pfp);
  }
  public void addPfp(String mess, GJoueur gj){
    pfp = new PanneauFinPartie(mess,gj);
    pfp.setBounds(this.getWidth()/4,this.getHeight()/8,this.getWidth()/2,(this.getHeight()*3)/4);
    add(pfp);
    pb.setVisible(false);
    ps.setSize(0,0);
  }
  public void removePfp(){
    remove(pfp);
    pfp = null;
    pc.setVisible(true);
    pb.setVisible(true);
    ps.actualiserTaille();
  }
  /*public int getXPourCentré(int x, int taille){
    if(x<taille){ return x;}
    return x/2-(taille);
  }*/
  // ici sont synchronisées toutes les actions de jeux.
  public void doAction(byte ac){
    debug.débogage("action pj : "+ac);
    if (ac < 9 && ac > -1){
      actionZoom(ac);
    }else if(ac>=20 && ac<=31){
      if(fActuelle==null){
        erreur.erreur("aucune fourmi n'est selectionné pour réaliser l'action voulue.");
      }else{
        debug.débogage("clic qui lance "+(ac-20));
        this.getPb().setActionF(ac-20);
      }
      this.repaint();
    }else if(ac==111){
      Main.getPch().setLancer(true);
    }else if(ac==112){//retour au menu
      Main.setRetournerAuMenu(true);
      //en suite on doit revenir quasiment a la void main.
    }else if(ac==113){//retour au jeu
      removePfp();
      Main.getPartie().setContinuerLeJeu(true);
      Main.repaint();
    }else if(ac>=40){
      pb.setChoixId(this.getPb().getPti().getBoutonX(ac-40));
      getPb().remove(getPb().getPti());
      pb.setPti(new PanneauTInt(null,pb));
      this.repaint();
    }else if(ac==-9){
      Main.getScript().setCmdSuivante(true);
    }else if(ac==-10){

    }else if(ac==-11){

    }else if(ac==-12){

    }else if(ac==-13){

    }else if(ac==-14){
      Main.getF().quitter();
    }else if(ac==-15){
      pe.setVisible(false);
    }
  }public void doAction(int ac){ doAction((byte) ac);}
  public void centrerLaCarte(){
    GCase gc = Main.getGc();
    pc.setPosX(math.max(gc.getNbrX()/2 - nbrDeCaseAffichableX(),0));
    pc.setPosY(math.max(gc.getNbrY()/2 - nbrDeCaseAffichableY(),0));
  }
  public int nbrDeCaseAffichableX(){
    return (this.getWidth()/pc.getTailleDUneCase())+1;
  }
  public int nbrDeCaseAffichableY(){
    return (this.getHeight()/pc.getTailleDUneCase())+1;
  }
  public void dézoomer(byte x){
    int y1 = Main.getDimX()/Main.getGc().getNbrX();
    int pah = Main.getPa().getHeight();
    if(pah==0){pah=Main.getTailleElementGraphique(180);}
    int y2 = (Main.getDimY()-pah)/Main.getGc().getNbrY();
    int y=0;
    if(x==1){ y=math.max(y1,y2);}
    else if(x==2){ y=math.min(y1,y2);}
    pc.setTailleDUneCase(y);
    if (Main.getPartie().getEnCours()){pc.chargerImages();}
  }
  public void envoyer(int x){
    debug.débogage("choix :"+x);
  }
  public void centrerSurLaFourmi(){
    if (fActuelle==null){erreur.alerte("Impossible de centrer sur une fourmi si aucune fourmi n'est selectionné."); return;}
    int x = nbrDeCaseAffichableX();
    int y = nbrDeCaseAffichableY();
    int posX = fActuelle.getX();
    int posY = fActuelle.getX();
    pc.setPosX(posX + x/2);
    pc.setPosY(posY + y/2);
  }
  public void actionZoom(byte ac){
    if (ac==2) { // zoom
      pc.setTailleDUneCase(math.min((pc.getTailleDUneCase()*4)/3,500));pc.chargerImages();
    }else if(ac==0){ // dézoom
      pc.setTailleDUneCase(math.max((pc.getTailleDUneCase()*3)/4,10));pc.chargerImages();
    }else if(ac==1){
      pc.setPosY(math.max(pc.getPosY()-1,0));
    }else if(ac==7){
      GCase gc = Main.getGc();
      pc.setPosY(math.min(pc.getPosY()+1,gc.getNbrY()-1));
    }else if(ac==5){
      GCase gc = Main.getGc();
      pc.setPosX(math.min(pc.getPosX()+1,gc.getNbrX()-1));
    }else if(ac==3){
      pc.setPosX(math.max(pc.getPosX()-1,0));
    }else if(ac==4){
      centrerLaCarte();
    }else if(ac==6){
      //centrerSurLaFourmi(); //pour l'instant ca fait pas ce qu'il faut.
    }else if(ac==8){
      dézoomer((byte)2);
    }
    this.repaint();
  }
  public void alerte(String s, String s2){
    JOptionPane jop1 = new JOptionPane();
    jop1.showMessageDialog(null, s, s2, JOptionPane.INFORMATION_MESSAGE);
  }
  public void alerte(String s){ alerte(s,g.getM("information"));}

}
