package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.*;
import cs3500.music.model.*;
import cs3500.music.view.*;

/**
 * Created by Charles on 11/18/15.
 */

public class MouseHandler implements MouseListener {

  private GuiView m;

  public MouseHandler(GuiView m) {
    this.m = m;
  }

  @Override public void mouseClicked(MouseEvent e) {
    m.setCurrent(e.getX(), e.getY());
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
