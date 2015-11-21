package cs3500.music.view;

import cs3500.music.model.*;
import cs3500.music.controller.*;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events
import java.util.Objects;

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

    ////frame = new JFrame("Music Editor");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.displayPanel.setPreferredSize(this.getPreferredSize());
    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setViewportBorder(new LineBorder(Color.RED));
    jScrollPane.setViewportView(displayPanel);
    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    this.setFocusable(true);
    this.add(jScrollPane, BorderLayout.CENTER);
    this.setSize(700, 500);
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

  }

  public void removeMouseListener(MouseListener ml) {
    //TODO: fill this out
  }

  public void setCurrent(int x, int y) {
    this.model.setCurrent(x, y);
    this.repaint();
  }

  /*@Override public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.red);
    g2d.drawLine(350, 0, 350, 500);
  }*/

  /*public void paintComponent(Graphics g){
    g.setColor(Color.red);
    g.drawLine(350, 0, 350, 500);
    displayPanel.paintComponents(g);
  }*/

  @Override public void paintAgain() {
    repaint();
    this.displayPanel.repaint();
  }

}

