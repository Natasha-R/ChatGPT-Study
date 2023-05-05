package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Position {
  private int x;
  private int y;

  public Position(int x, int y) {
    this.x=x;
    this.y=y;
  }
}
