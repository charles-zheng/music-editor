package cs3500.music.model;

import java.util.List;

/**
 * Created by Charles on 12/14/15.
 */
public class Repeat implements Comparable<Repeat> {

  /**
   *
   */
  private List<Integer> range;
  //INVARIANT: must be of either size 2 or size >= 4

  /**
   *
   *
   * @param range
   */
  public Repeat(List<Integer> range) {
    this.range = range;
  }

  /**
   *
   * @return
   */
  public int getStart() {
    return range.get(0);
  }

  /**
   *
   * @return
   */
  public boolean isComplex() {
    return range.size() > 3;
  }

  /**
   *
   * @param ending
   * @return
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
   *
   * @return
   */
  public int getEndings() {
    if (isComplex()) {
      return range.size() - 2;
    } else {
      return 1;
    }
  }

  /**
   *
   * @return
   */
  public int getEnd() {
    return range.get(range.size() - 1);
  }

  /**
   *
   * @param other
   * @return
   */
  public int compareTo(Repeat other) {
    return this.getEnd() - other.getEnd();
  }
}
