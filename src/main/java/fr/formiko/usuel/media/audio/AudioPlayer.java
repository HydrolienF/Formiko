package fr.formiko.usuel.media.audio;

import fr.formiko.usuel.Chrono;
import fr.formiko.usuel.erreur;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
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
public class AudioPlayer {
  private File file;
  private boolean loop;
  private int maxTime;
  private Chrono chrono;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary main constructor}<br>
  *@param fileName name of the file.
  *@param loop if true, we will loop when audio end.
  *@param maxTime time to stop.
  *@version 1.46
  */
  public AudioPlayer(String fileName, boolean loop, int maxTime){
    file = new File(fileName);
    this.loop=loop;
    this.maxTime=maxTime;
    chrono=new Chrono();
  }
  /**
  *{@summary secondary constructor}<br>
  *@param fileName name of the file.
  *@param loop if true, we will loop when audio end.
  *@version 1.46
  */
  public AudioPlayer(String fileName, boolean loop){
    this(fileName,loop,Integer.MAX_VALUE);
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
  *{@summary resume audio &#38; time}<br>
  *@version 1.46
  */
  public void resume(){
    //TODO resume audio
    chrono.resume();
  }
  /**
  *{@summary stop audio &#38; time}<br>
  *@version 1.46
  */
  public void stop(){
    maxTime=0;
    chrono.stop();
  }
  //private --------------------------------------------------------------------
  /**
  *{@summary open file & do sounds.}<br>
  *@version 1.46
  */
  private void doSounds(){
    try (final AudioInputStream in = getAudioInputStream(file)) {
      final AudioFormat outFormat = getOutFormat(in.getFormat());
      final Info info = new Info(SourceDataLine.class, outFormat);
      try (final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
        if (line != null) {
          line.open(outFormat);
          line.start();
          stream(getAudioInputStream(outFormat, in), line);
          line.drain();
          line.stop();
        }
      }
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
      throw new IllegalStateException(e);
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
    for (int n = 0; n != -1 && chrono.getDuree() < maxTime; n = in.read(buffer, 0, buffer.length)) {
      line.write(buffer, 0, n);
      chrono.updateDuree();
    }
  }
}
