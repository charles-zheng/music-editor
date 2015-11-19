package cs3500.music.controller;

import cs3500.music.view.*;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Created by Charles on 11/19/15.
 */
public class MidiController implements Controller {

  private MidiView view;

  public MidiController(MidiView view) {
    this.view = view;
  }

  public void initialize() throws InvalidMidiDataException {
    this.view.initialize();
  }

}
