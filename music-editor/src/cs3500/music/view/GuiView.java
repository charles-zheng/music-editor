package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by Charles on 11/18/15.
 */
public interface GuiView extends View {

  void setCurrent(int x, int y);

  void addListener(KeyListener k);

  void paintAgain();

  void skipToFront();

  void skipToEnd();

  void shiftLeft();

  void shiftRight();

  void shiftUp();

  void shiftDown();

}
