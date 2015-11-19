package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.*;
import cs3500.music.model.*;
import cs3500.music.view.*;

/**
 * Handles all of the Mouse events
 */
public class MouseHandler implements MouseListener {

  /**
   * Represents the view that mouse events will interact
   */
  private GuiView view;
  
  public MouseHandler(GuiView view) {
    this.view = view;
  }

  @Override public void mouseClicked(MouseEvent e) {
    view.setCurrent(e.getX(), e.getY());
  }

  @Override public void mousePressed(MouseEvent e) {

  }

  @Override public void mouseReleased(MouseEvent e) {

  }

  @Override public void mouseEntered(MouseEvent e) {

  }

  @Override public void mouseExited(MouseEvent e) {

  }
}
