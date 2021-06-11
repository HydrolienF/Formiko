package fr.formiko.usuel.media.audio;

import fr.formiko.usuel.erreur;
import fr.formiko.usuel.listes.GString;

public class MusicPlayer implements AudioInterface {
  private AudioPlayer audioPlayer;
  private boolean musicPaused;
  private GString nextMusics;
  // CONSTRUCTORS --------------------------------------------------------------
  public MusicPlayer(){
    nextMusics = new GString();
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary play next music.}<br>
  *@version 1.52
  */
  public void play(){
    if(audioPlayer!=null){audioPlayer.stop();}
    audioPlayer = new AudioPlayer(getNextMusique());
    audioPlayer.play();
    musicPaused=false;
  }
  /**
  *{@summary pause curent music.}<br>
  *@see MusicPlayer#resume()
  *@version 1.52
  */
  public void pause(){
    if(audioPlayer!=null && !musicPaused){
      audioPlayer.pause();
      musicPaused=true;
    }
  }
  /**
  *{@summary resume curent music.}<br>
  *@see MusicPlayer#pause()
  *@version 1.52
  */
  public void resume(){
    if(audioPlayer!=null && musicPaused){
      audioPlayer.resume();
      musicPaused=false;
    }
  }
  /**
  *{@summary stop curent music.}<br>
  *We need play() to start music again.<br>
  *@version 1.52
  */
  public void stop(){
    if(audioPlayer!=null){
      audioPlayer.stop();
      audioPlayer=null;
      musicPaused=true;
    }
  }
  //private --------------------------------------------------------------------
  private String getNextMusique(){
    if(nextMusics.isEmpty()){
      return getRandomMusic();
    }else{
      String music = nextMusics.getItem(0);
      // nextMusics.remove(0);
      return music;
    }
  }
  private String getRandomMusic(){
    //TODO
    return "";
  }
}
