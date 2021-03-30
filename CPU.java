import java.util.*;


public class CPU extends User {


  public CPU(Board board, char colour) {
    super(board, colour);
  }


  public void move() {
    Random ran = new Random();
    int tryColumn = ran.nextInt(this.board.getColumns()) + 1;
    while(!this.board.addCounterColumn(this.colour, tryColumn)) {
      tryColumn = ran.nextInt(this.board.getColumns()) + 1;
    }
  }
}