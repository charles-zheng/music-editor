package cs3500.music.controller;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Created by Charles on 11/18/15.
 */
public interface Controller {
  void initialize() throws InvalidMidiDataException;
}
