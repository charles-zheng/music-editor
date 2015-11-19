package cs3500.music.controller;

import cs3500.music.view.*;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.MouseListener;

/**
 * Created by Charles on 11/18/15.
 */
public class GuiController implements Controller {

  private GuiView view;
  private KeyboardHandler kh;
  private MouseHandler mh;

  public GuiController(GuiView view) {
    this.view = view;
    this.kh = new KeyboardHandler();
    //TODO
    Runnable add = view.addNote();
    Runnable extend = view.extendNote();
    Runnable lower = view.lowerNote();
    Runnable raise = view.raiseNote();
    Runnable shorten = view.shortenNote();
    this.kh.addTypedEvent(65, add);
    this.kh.addTypedEvent(69, extend);
    this.kh.addTypedEvent(45, lower);
    this.kh.addTypedEvent(521, raise);
    this.kh.addTypedEvent(83, shorten);
    this.view.addListener(this.kh);
  }

  public void initialize() throws InvalidMidiDataException {
    this.view.initialize();
  }



}
