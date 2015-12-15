package cs3500.music.model;

import java.util.List;

/**
 * Created by Charles on 12/14/15.
 */
public class Repeat implements Comparable<Repeat> {

  protected List<Integer> range;

  public Repeat(List<Integer> range) {
    this.range = range;
  }

  public int getStart() {
    return range.get(0);
  }

  public boolean isComplex() {
    return range.size() > 3;
  }

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

  public int getEndings() {
    if (isComplex()) {
      return range.size() - 2;
    } else {
      return 1;
    }
  }

  public int getEnd() {
    return range.get(range.size() - 1);
  }

  public int compareTo(Repeat other) {
    return this.getEnd() - other.getEnd();
  }
}
