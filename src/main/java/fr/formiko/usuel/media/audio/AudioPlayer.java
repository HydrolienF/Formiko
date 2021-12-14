package fr.formiko.usuel.media.audio;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Chrono;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.maths.math;

import java.io.File;
import java.io.IOException;
import java.lang.Thread;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
*{@summary to read and launch audio file.}<br>
*cf https://odoepner.wordpress.com/2013/07/19/play-mp3-or-ogg-using-javax-sound-sampled-mp3spi-vorbisspi/
*@author Hydrolien, Oliver Doepner
*@version 1.46
*/
public class AudioPlayer implements AudioInterface {
  private File file;
  private boolean loop;
  private int maxTime;
  private Chrono chrono;
  private AudioThread at;
  private boolean isMusique;
  private float volume;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary main constructor}<br>
  *@param fileName name of the file.
  *@param loop if true, we will loop when audio end.
  *@param maxTime time to stop.
  *@param isMusique true if this audio player is for a music (That's meen that MusicPlayer need to play next after this).
  *@version 1.52
  */
  public AudioPlayer(String fileName, boolean loop, int maxTime, boolean isMusique){
    file = new File(fileName);
    this.loop=loop;
    this.maxTime=maxTime;
    chrono=new Chrono();
    this.isMusique=isMusique;
    volume=0.0f;
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@param loop if true, we will loop when audio end.
  *@param maxTime time to stop.
  *@version 1.46
  */
  public AudioPlayer(String fileName, boolean loop, int maxTime){
    this(fileName, loop, maxTime, false);
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@param loop if true, we will loop when audio end.
  *@version 1.46
  */
  public AudioPlayer(String fileName, boolean loop){
    this(fileName,loop,Integer.MAX_VALUE, false);
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@param isMusique true if this audio player is for a music (That's meen that MusicPlayer need to play next after this).
  *@version 1.52
  */
  public AudioPlayer(boolean isMusique, String fileName){
    this(fileName,false,Integer.MAX_VALUE,isMusique);
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@param maxTime time to stop.
  *@version 1.46
  */
  public AudioPlayer(String fileName, int maxTime){
    this(fileName,false,maxTime);
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@version 1.46
  */
  public AudioPlayer(String fileName){
    this(fileName,false);
  }
  // GET SET -------------------------------------------------------------------
  public File getFile(){return file;}
  public void setFile(File f){file=f;}
  public boolean getLoop(){return loop;}
  public void setLoop(boolean f){loop=f;}
  public int getMaxTime(){return maxTime;}
  public void setMaxTime(int f){maxTime=f;}
  public Chrono getChrono(){return chrono;}
  public void setChrono(Chrono f){chrono=f;}
  public boolean getIsMusique(){return isMusique;}
  public void setIsMusique(boolean f){isMusique=f;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary play audio &#38; launch time}<br>
  *@version 1.46
  */
  public void play() {
    chrono.start();
    do {
      try {
        doSounds();
      }catch (Exception e) {
        erreur.erreur("Unable to launch audio file "+file.getName());
        loop=false;
      }
    } while (loop && chrono.getDuree() < maxTime);
  }
  /**
  *{@summary pause audio &#38; time}<br>
  *@version 1.46
  */
  public void pause(){
    //TODO pause audio
    chrono.pause();
  }
  /**
  *{@summary Resume audio &#38; time.}<br>
  *@version 1.46
  */
  public void resume(){
    //TODO resume audio
    chrono.resume();
  }
  /**
  *{@summary Stop audio &#38; time.}<br>
  *@version 1.46
  */
  public void stop(){
    maxTime=0;
    //TODO stop audio
    at.interrupt();
    chrono.stop();
  }
  /**
  *{@summary Modify volume.}<br>
  *@param vol volume in %
  *@version 2.14
  */
  public void setVolume(int vol){
    volume = (float)math.between(0, 100, vol)/100f;
    if(at!=null){
      at.updateVolume();
    }
  }
  //private --------------------------------------------------------------------
  private void doSounds(){
    at = new AudioThread(this);
    at.start();
  }
  class AudioThread extends Thread {
    private AudioPlayer ap;
    private boolean normallyEnded;
    private FloatControl gainControl;

    public AudioThread(AudioPlayer ap){
      this.ap=ap;
    }
    @Override
    public void run(){
      normallyEnded=true;
      doSounds();
      if(ap.getIsMusique() && normallyEnded){
        Main.getMp().next();
      }
    }
    /**
    *{@summary open file &#38; do sounds.}<br>
    *@version 1.46
    */
    private void doSounds(){
      try (final AudioInputStream in = getAudioInputStream(ap.getFile())) {
        final AudioFormat outFormat = getOutFormat(in.getFormat());
        final Info info = new Info(SourceDataLine.class, outFormat);
        try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
          if (line != null) {
            line.open(outFormat);
            gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            updateVolume();
            line.start();
            stream(getAudioInputStream(outFormat, in), line);
            line.drain();
            line.stop();
          }
        }
      } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
        // throw new IllegalStateException(e);
        erreur.erreur("Can't do sound");
      }
    }
    private void updateVolume(){
      float decibels;
      float vol = (float)Math.sqrt(Math.sqrt(volume));
      decibels = (vol*gainControl.getMaximum()) + ((1-vol)*gainControl.getMinimum());
      decibels = math.between(gainControl.getMinimum(), gainControl.getMaximum(), decibels);
      if(gainControl!=null){
        gainControl.setValue(decibels); // Reduce volume by 10 decibels
      }else{
        erreur.alerte("Volume can't be modify");
      }
    }
    /**
    *{@summary get audio format.}<br>
    *@version 1.46
    */
    private AudioFormat getOutFormat(AudioFormat inFormat) {
      final int ch = inFormat.getChannels();
      final float rate = inFormat.getSampleRate();
      return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }
    /**
    *{@summary do sounds by reading line by line.}<br>
    *@version 1.46
    */
    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
      final byte[] buffer = new byte[4096];
      for (int n = 0; n != -1 && ap.getChrono().getDuree() < ap.getMaxTime(); n = in.read(buffer, 0, buffer.length)) {
        line.write(buffer, 0, n);
        ap.getChrono().updateDuree();
      }
    }
    /**
    *{@summary interrupt game &#38; don't launch next music.}<br>
    *@version 1.52
    */
    @Override
    public void interrupt(){
      normallyEnded=false;
      super.interrupt();
    }
  }
}
