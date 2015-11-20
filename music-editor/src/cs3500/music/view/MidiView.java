package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.KeyListener;

/**
 * Created by catherinemclean1 on 11/19/15.
 */
public interface MidiView extends View {

  void play() throws InvalidMidiDataException;

  void pause();

  void rewind();

  boolean isPaused();
}
