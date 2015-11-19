package cs3500.music.controller;

import cs3500.music.view.*;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.MouseListener;

/**
 * Created by Charles on 11/18/15.
 */
public class ControllerImpl implements Controller {

  private CompositeView view;
  private KeyboardHandler kh;
  private MouseHandler mh;

  public ControllerImpl(CompositeView view) {
    this.view = view;
    this.kh = new KeyboardHandler();
    //TODO
    Runnable add = view.addNote();
    Runnable extend = view.extendNote();
    Runnable lower = view.lowerNote();
    Runnable raise = view.raiseNote();
    Runnable shorten = view.shortenNote();
    Runnable play = view.play();
    Runnable pause = view.pause();
    Runnable rewind = view.rewind();
    this.kh.addTypedEvent(65, add); //       'a'
    this.kh.addTypedEvent(69, extend); //    'e'
    this.kh.addTypedEvent(45, lower); //     '-'
    this.kh.addTypedEvent(521, raise); //    '+'
    this.kh.addTypedEvent(83, shorten); //   's'
    this.kh.addTypedEvent(46, play); //      '.'
    this.kh.addTypedEvent(47, pause); //     '/'
    this.kh.addTypedEvent(44, rewind); //    ','
    this.view.addListener(this.kh);
  }

  public void initialize() throws InvalidMidiDataException {
    this.view.initialize();
  }



}
