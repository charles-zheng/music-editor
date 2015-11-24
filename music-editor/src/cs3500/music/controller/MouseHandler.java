package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.*;
import cs3500.music.model.*;
import cs3500.music.view.*;

/**
 * Handles all of the Mouse events.
 */
public class MouseHandler implements MouseListener {

  /**
   * Represents the view with which the mouse events will interact.
   */
  private GuiView view;

  /**
   * Constructs a new mouse handler with the given view.
   *
   * @param view
   */
  public MouseHandler(GuiView view) {
    this.view = view;
  }

  /**
   * Handles all mouse clicked events from the user.
   *
   * @param e the MouseEvent representing the user's click
   */
  @Override public void mouseClicked(MouseEvent e) {
    view.setCurrent(e.getX(), e.getY());
  }

  /**
   * Handles all mouse pressed events from the user.
   *
   * @param e the MouseEvent representing the user's pressed
   */
  @Override public void mousePressed(MouseEvent e) {

  }

  /**
   * Handles all mouse released events from the user.
   *
   * @param e the MouseEvent representing the user's release
   */
  @Override public void mouseReleased(MouseEvent e) {

  }

  /**
   * Handles all mouse entered events from the user.
   *
   * @param e the MouseEvent representing the user's enter
   */
  @Override public void mouseEntered(MouseEvent e) {

  }

  /**
   * Handles all mouse exited events from the user
   *
   * @param e the MouseEvent representing the user's exit
   */
  @Override public void mouseExited(MouseEvent e) {

  }
}
