package cs3500.music.model;


import java.util.List;
/**
 * Created by Charles on 12/15/15.
 */
public class ComplexRepeat extends Repeat {

  public ComplexRepeat(List<Integer> range) {
    super(range);
  }

  public boolean isComplex() {
    return true;
  }

}
