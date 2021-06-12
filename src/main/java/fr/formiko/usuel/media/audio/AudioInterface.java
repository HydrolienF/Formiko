package fr.formiko.usuel.media.audio;

import fr.formiko.usuel.erreur;

/**
*{@summary audio interface with basic commands.}<br>
*@version 1.52
*/
public interface AudioInterface {
  /**
  *{@summary play audio}<br>
  *@version 1.52
  */
  void play();
  /**
  *{@summary pause audio}<br>
  *@version 1.52
  */
  void pause();
  /**
  *{@summary resume audio}<br>
  *@version 1.52
  */
  void resume();
  /**
  *{@summary stop audio}<br>
  *@version 1.52
  */
  void stop();
}
