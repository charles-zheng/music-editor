package cs3500.music.controller;

import cs3500.music.view.*;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.MouseListener;

/**
 * Created by Charles on 11/18/15.
 */
public class ControllerImpl implements Controller {

  private GuiView view;
  private KeyboardHandler kh;
  private MouseHandler mh;

  public ControllerImpl(GuiView view) {
    this.view = view;
    this.kh = new KeyboardHandler();
    GuiViewFrame.AddNewNote add = ((GuiViewFrame)view).new AddNewNote();
    GuiViewFrame.ExtendNote extend = ((GuiViewFrame)view).new ExtendNote();
    GuiViewFrame.LowerNote lower = ((GuiViewFrame)view).new LowerNote();
    GuiViewFrame.RaiseNote raise = ((GuiViewFrame)view).new RaiseNote();
    this.kh.addTypedEvent(65, add);
    this.kh.addTypedEvent(69, extend);
    this.kh.addTypedEvent(45, lower);
    this.kh.addTypedEvent(521, raise);
    this.view.addListener(this.kh);
  }

  public void initialize() throws InvalidMidiDataException {
    this.view.initialize();
  }



}
