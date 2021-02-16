package fr.formiko.formiko.interfaces;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.interfaces.TrophallaxieFourmi;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.tests.TestCaseMuet;

public class TrophallaxieFourmiTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------

  //trophallaxie

  //trophallaxieId

  //trophallaxer utilise l'interface graphique. Je ne vois pas comment le testé quand même.

  //getCreatureQuiOnFaim
  @Test
  public void testGetCreatureQuiOnFaim(){//on suppose que getAlliéSurLaCase & getAlliéSurLaCaseSansThis sont correcte (meme si on les test un peu).
    Main.initialisation();
    Carte mapo = new Carte(new GCase(1,1));
    Main.setPartie(new Partie(1,1,mapo,1.0));
    Joueur j = new Joueur(1,true,mapo);
    GJoueur gj = new GJoueur();
    gj.ajouter(j);
    Main.getPartie().setGj(gj);

    Fourmiliere fere = j.getFere();
    Fourmi f = fere.getGc().getFourmiParId(1);
    f.setPheromone(new Pheromone(0,0,0));
    assertTrue(f!=null);
    assertTrue(f.getEspece()!=null);
    Espece e = f.getEspece();
    fere.getGc().add(new Fourmi(fere,e,3));
    int nbrDeCreature = f.getAlliéSurLaCase().length();
    int t [] = f.getAlliéSurLaCaseSansThis().toTId();
    assertEquals(2,nbrDeCreature);
    Fourmi f2 = fere.getGc().getFourmiParId(2);
    assertTrue(f2!=null);
    f2.setNourriture(f2.getNourritureMax()-1);
    int r [] = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(1,r.length);
    f2.setNourriture(f2.getNourritureMax());
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(0,r.length);
    f2.setNourriture(0);
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(1,r.length);
    fere.getGc().add(new Fourmi(fere,e,3));
    f2.setNourriture(0);
    fere.getGc().getFourmiParId(3).setNourriture(0);
    t = f.getAlliéSurLaCaseSansThis().toTId();
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(2,r.length);

    //avec 1 insecte sans Pheromone proche
    Insecte i1 = new Insecte();
    i1.setNourriture(0);
    i1.setPheromone(new Pheromone(-100,10,30));
    //gi.ajouter(i1);
    t = f.getAlliéSurLaCaseSansThis().toTId();
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(2,r.length);
    //avec 1 insecte avec Pheromone proche
    Insecte i2 = new Insecte();
    i2.setNourriture(0);
    i2.setPheromone(new Pheromone(0,0,0));
    //gi.ajouter(i2);
    t = f.getAlliéSurLaCaseSansThis().toTId();
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(3,r.length);
    assertTrue(tableau.contient(r,i2.getId()));

    i2.setPheromone(new Pheromone(1,1,1));
    t = f.getAlliéSurLaCaseSansThis().toTId();
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(3,r.length);
    assertTrue(tableau.contient(r,i2.getId()));

    i2.setPheromone(new Pheromone(4,10,10));
    t = f.getAlliéSurLaCaseSansThis().toTId();
    r = f.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertEquals(2,r.length);
    assertTrue(!tableau.contient(r,i2.getId()));
    // avec des insectes
    //sans ressemblance phéromonale
    t = i2.getAlliéSurLaCaseSansThis().toTId();
    r = i2.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertTrue(r==null);
    //avec ressemblance phéromonale.
    i2.setPheromone(new Pheromone(0,0,0));
    t = i2.getAlliéSurLaCaseSansThis().toTId();
    r = i2.trophallaxie.getCreatureQuiOnFaim(t,f);
    assertTrue(r==null);
  }
}
