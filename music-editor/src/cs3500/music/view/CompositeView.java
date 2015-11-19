package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
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

  @Override public Runnable pause() {
    return mv.pause();
  }

  @Override public Runnable play() {
    return mv.play();
  }

  @Override public Runnable rewind() {
    return mv.rewind();
  }

  @Override public Runnable addNote() {
    return gv.addNote();
  }

  @Override public Runnable extendNote() {
    return gv.extendNote();
  }

  @Override public Runnable shortenNote() {
    return gv.shortenNote();
  }

  @Override public Runnable lowerNote() {
    return gv.lowerNote();
  }

  @Override public Runnable raiseNote() {
    return gv.raiseNote();
  }

  /**
   * Initializes this view
   */
  @Override public void initialize() throws InvalidMidiDataException {
    gv.initialize();
    mv.initialize();
  }
}
