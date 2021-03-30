abstract class User {
  protected char colour;
  protected Board board;

  public User(Board board, char colour) {
    this.board = board;
    this.colour = colour;
  }

  public abstract void move();
  public  char getColour() {
    return this.colour;
  }
}