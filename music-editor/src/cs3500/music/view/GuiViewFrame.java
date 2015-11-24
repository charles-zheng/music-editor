package cs3500.music.view;

import cs3500.music.model.*;
import cs3500.music.controller.*;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events
import java.util.Objects;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 * A graphic view represented using Swing
 */
public final class GuiViewFrame extends javax.swing.JFrame implements GuiView {

  /**
   * Represents the graphic display of the model
   */
  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  private JScrollPane js;

  /**
   * Represents the model to be displayed
   */
  private final ViewModel model;

  /**
   * Creates a new gui view
   *
   * @param m the model to be graphically represented
   */
  public GuiViewFrame(ViewModel m) {
    this.model = Objects.requireNonNull(m);
    this.displayPanel = new ConcreteGuiViewPanel(this.model);
    this.displayPanel.addMouseListener(new MouseHandler(this));

    this.setFocusable(true);

    ////frame = new JFrame("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.displayPanel.setPreferredSize(this.getPreferredSize());
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportBorder(new LineBorder(Color.RED));
    JViewport viewport = new JViewport();
    viewport.setView(displayPanel);
    viewport.setViewPosition(new Point(0, 0));
    jScrollPane.setViewport(viewport);
    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    this.add(jScrollPane, BorderLayout.CENTER);
    this.setSize(700, 500);
    this.js = jScrollPane;
  }

  /**
   * Runs this visual graphical view using midi
   */
  @Override public void initialize() {
    this.setVisible(true);
  }

  /**
   * Get the preferred size of this.displayPanel
   *
   * @return the dimension of how much of the display panel should be displayed
   */
  @Override public Dimension getPreferredSize() {
    return new Dimension(model.getFinalEndBeat() * ConcreteGuiViewPanel.BOX_SIZE + 100,
        ConcreteGuiViewPanel.BOX_SIZE * (model.getHighestPitch().getValue() - model
            .getLowestPitch().getValue()) + 100);
  }

  //TODO
  public void addListener(KeyListener kl) {
    this.addKeyListener(kl);
  }

  public void removeKeyListener(KeyListener kl) {
    super.removeKeyListener(kl);
  }

  public void removeMouseListener(MouseListener ml) {
    super.removeMouseListener(ml);
  }

  public void setCurrent(int x, int y) {
    this.model.setCurrent(x, y);
    this.repaint();
  }

  public void paintAgain(boolean playing) {
    this.displayPanel.setPreferredSize(this.getPreferredSize());
    JViewport jv = this.js.getViewport();
    int limit = (int)(jv.getViewPosition().getX() + jv.getExtentSize().getWidth());
    int curX = (model.getTimeStamp() + 2) * ConcreteGuiViewPanel.BOX_SIZE;
    int curY = (int)jv.getViewPosition().getY();

    if (playing) {
      if (curX > limit) {
        jv.setViewPosition(new Point(limit - (ConcreteGuiViewPanel.BOX_SIZE * 2), curY));
        model.setViewableRange(new Point(limit - (ConcreteGuiViewPanel.BOX_SIZE * 2), curY));
      }

      if (curX < (int)(jv.getViewPosition().getX())) {
        jv.setViewPosition(new Point(curX, curY));
        model.setViewableRange(new Point(curX, curY));
      }
    }


    repaint();
    this.displayPanel.repaint();
  }

  @Override public void repaint() {
    super.repaint();
    JViewport jv = this.js.getViewport();
    model.setViewableRange(
        new Point((int) jv.getViewPosition().getX(), (int) jv.getViewPosition().getY()));
  }

  public void skipToFront() {
    JViewport jv = this.js.getViewport();
    jv.setViewPosition(new Point(0, 0));
  }

  public void skipToEnd() {
    JViewport jv = this.js.getViewport();
    jv.setViewPosition(new Point(model.getFinalEndBeat() * ConcreteGuiViewPanel.BOX_SIZE, 0));
  }

  public void shiftLeft() {
    JViewport jv = this.js.getViewport();
    int curX = (int)jv.getViewPosition().getX();
    int curY = (int)jv.getViewPosition().getY();
    if (curX > 50) {
      jv.setViewPosition(new Point(curX - 50, curY));
    }
  }

  public void shiftRight() {
    JViewport jv = this.js.getViewport();
    int curX = (int)jv.getViewPosition().getX();
    int curY = (int)jv.getViewPosition().getY();
    if (curX < (model.getFinalEndBeat() + 2) * ConcreteGuiViewPanel.BOX_SIZE - 50) {
      jv.setViewPosition(new Point(curX + 50, curY));
    }
  }

  public void shiftUp() {
    JViewport jv = this.js.getViewport();
    int curX = (int)jv.getViewPosition().getX();
    int curY = (int)jv.getViewPosition().getY();
    if (curY > 50) {
      jv.setViewPosition(new Point(curX, curY - 50));
    }
  }

  public void shiftDown() {
    JViewport jv = this.js.getViewport();
    int curX = (int)jv.getViewPosition().getX();
    int curY = (int)jv.getViewPosition().getY();
    if (curY < (model.getHighestPitch().getValue() - model.getLowestPitch().getValue() + 1)
        * ConcreteGuiViewPanel.BOX_SIZE) {
      jv.setViewPosition(new Point(curX, curY + 50));
    }
  }

  public Dimension getViewableRange() {
    return this.js.getViewport().getExtentSize();
  }
}

