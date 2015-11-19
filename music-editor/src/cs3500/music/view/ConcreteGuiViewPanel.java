package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import cs3500.music.model.*;
import cs3500.music.controller.*;

import javax.swing.*;

/**
 * Draws the model using Swing
 */
public final class ConcreteGuiViewPanel extends JPanel {

  /**
   * Represents the model to be drawn
   */
  private final ViewModel m;

  /**
   * Represents the size of each grid square
   */
  public final static int BOX_SIZE = 25;

  /**
   * Creates a new gui panel with the given model represented
   *
   * @param m the model to be drawn
   */
  public ConcreteGuiViewPanel(ViewModel m) {
    Objects.requireNonNull(m);
    this.m = m;
  }

  /**
   * Draws the model, this.m
   *
   * @param g the graphics object that draws everything
   */
  @Override public void paint(Graphics g) {
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful


    Pitch highest = Objects.requireNonNull(m.getHighestPitch());
    Pitch lowest = Objects.requireNonNull(m.getLowestPitch());
    int height = (highest.getValue() - lowest.getValue() + 1) * BOX_SIZE;

    // draws the beat numbers
    for (int i = 1; i <= m.getFinalEndBeat(); i++) {
      if (i % 4 == 0) {
        g.drawString(Integer.toString(i), 50 + (BOX_SIZE * i), 20);
      }
    }

    for (int i = highest.getValue(); i >= lowest.getValue(); i--) {
      Pitch p = new PitchImpl(i);
      // draw pitch names
      g.drawString(p.toString(), BOX_SIZE - 10,
          BOX_SIZE * (highest.getValue() - p.getValue() + 2));

      // draws the horizontal grid
      g.drawRect(50, BOX_SIZE * (highest.getValue() - p.getValue()),
          m.getFinalEndBeat() * BOX_SIZE, BOX_SIZE);
    }

    // draws the vertical grid
    for (int i = 0; i < m.getFinalEndBeat(); i++) {
      g.drawRect(BOX_SIZE * (i + 2), BOX_SIZE, BOX_SIZE, height);
    }

    // draws all the notes
    for (int i = 0; i <= m.getFinalStartBeat(); i++) {
      List<Note> ns = m.getNotesAtTime(i);
      for (Note n : ns) {
        // draw the start of the note black
        g.setColor(Color.black);
        g.fillRect((n.getStartTime() + 2) * BOX_SIZE,
            BOX_SIZE * (highest.getValue() - n.getPitch().getValue() + 1), BOX_SIZE, BOX_SIZE);

        // draw the sustained portion of the note green
        g.setColor(Color.green);
        g.fillRect(((n.getStartTime() + 3) * BOX_SIZE),
            BOX_SIZE * (highest.getValue() - n.getPitch().getValue() + 1),
            (n.getEndTime() - n.getStartTime() - 1) * BOX_SIZE, BOX_SIZE);
      }
    }

    //TODO
    System.out.println("cur: " + m.getCurBeat() + " " + m.getCurPitch());
    // draws selected box
    try {
      Note n = m.getNoteIn(new PitchImpl(m.getCurPitch()), m.getCurBeat());
      g.setColor(Color.cyan);
      g.fillRect((n.getStartTime() + 2) * BOX_SIZE,
          BOX_SIZE * (highest.getValue() - n.getPitch().getValue() + 1),
          (n.getEndTime() - n.getStartTime()) * BOX_SIZE, BOX_SIZE);
    } catch (Model.IllegalAccessNoteException e) {
      g.setColor(Color.cyan);
      g.fillRect((m.getCurBeat() + 2) * BOX_SIZE,
          BOX_SIZE * (highest.getValue() - m.getCurPitch() + 1), BOX_SIZE, BOX_SIZE);
    }

    g.setColor(Color.red);
    g.drawLine(250, 0, 250, 500);
  }

}
