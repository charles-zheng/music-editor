package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyListener;

/**
 * Created by catherinemclean1 on 11/19/15.
 */

public class CompositeView implements GuiView, MidiView {

  private GuiView gv;
  private MidiView mv;

  public CompositeView(MidiView mv, GuiView gv) {
    this.mv = mv;
    this.gv = gv;
  }
  @Override public void setCurrent(int x, int y) {
    gv.setCurrent(x, y);
  }

  @Override public void addListener(KeyListener k) {
    gv.addListener(k);
  }

  @Override public void paintAgain() {
    gv.paintAgain();
  }

  @Override public void shiftLeft() {
    gv.shiftLeft();
  }

  @Override public void shiftRight() {
    gv.shiftRight();
    System.out.println("ran");
  }

  @Override public void shiftUp() {
    gv.shiftUp();
  }

  @Override public void shiftDown() {
    gv.shiftDown();
  }

  @Override public void recordNotes(int time) throws InvalidMidiDataException,
      MidiUnavailableException {
    mv.recordNotes(time);
  }

  @Override public void play() throws InvalidMidiDataException {
    mv.play();
  }

  @Override public void pause() {
    mv.pause();
  }

  @Override public void rewind() {
    mv.rewind();
  }

  @Override public boolean isPaused() {
    return mv.isPaused();
  }

  /**
   * Initializes this view
   */
  @Override public void initialize() throws InvalidMidiDataException {
    gv.initialize();
    mv.initialize();
  }
}
