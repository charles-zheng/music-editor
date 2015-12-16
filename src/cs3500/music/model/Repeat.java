package cs3500.music.model;

import java.util.List;

/**
 * Represents a repeat in a piece of music
 */
public class Repeat implements Comparable<Repeat> {

  /**
   * Stores the start and end(s) of this repeat
   */
  private List<Integer> range;
  //INVARIANT: must be of either size 2 or size >= 4

  /**
   * Constructs a new Repeat with the given range
   *
   * @param range the range to make this repeat with
   * @throws IllegalArgumentException if the range is not in
   * consecutive order
   */
  public Repeat(List<Integer> range) {
    for (int i = 0; i < range.size(); i++) {
      if (i != 0) {
        if (range.get(i) < range.get(i - 1)) {
          throw new IllegalArgumentException("Must be in consecutive order");
        }
      }
    }
    this.range = range;
  }

  /**
   * Gets the start beat of this repeat
   *
   * @return the start beat of this repeat
   */
  public int getStart() {
    return range.get(0);
  }

  /**
   * Is this repeat a complex repeat?
   *
   * @return whether or not this range is complex
   */
  public boolean isComplex() {
    return range.size() > 3;
  }

  /**
   * Get the last beat of the given ending
   *
   * @param ending which ending variation to get the last beat of
   * @return the last beat of the given variation
   */
  public int getEnding(int ending) {
    if (ending >= range.size()) {
      throw new IllegalArgumentException("No such ending");
    }
    if (isComplex()) {
      return range.get(ending + 1);
    } else {
      return range.get(ending);
    }
  }

  /**
   * Get the number of endings in this repeat
   *
   * @return the number of endings
   */
  public int getEndings() {
    if (isComplex()) {
      return range.size() - 2;
    } else {
      return 1;
    }
  }

  /**
   * Get the last beat of this repeat
   *
   * @return the last beat of this repeat
   */
  public int getEnd() {
    return range.get(range.size() - 1);
  }

  /**
   * Does this repeat end before or after the given repeat?
   *
   * @param other the repeat to compare with this
   * @return a positive integer if this repeat ends after the given repeat,
   * zero if they end at the same time, and a negative number otherwise
   */
  public int compareTo(Repeat other) {
    return this.getEnd() - other.getEnd();
  }
}
