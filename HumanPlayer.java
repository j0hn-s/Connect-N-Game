import java.util.*;


public class HumanPlayer extends User {
  private Scanner sc = new Scanner(System.in);


  public HumanPlayer(Board board, char colour) {
    super(board, colour);
  }


  public void move() {
    int column = this.getInput();
    this.board.addCounterColumn(this.colour, column);
  }
  private int getInput() {
    System.out.println("\nEnter column of choice: ");
    return this.sc.nextInt();
  }
}