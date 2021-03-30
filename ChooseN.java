import java.util.*;


///Public Methods///

public class ChooseN {
  private Scanner sc = new Scanner(System.in);
  private Board board;
  private ArrayList<User> users;
  private int nToWin;
  private HashMap<Character, String> colours = new HashMap<Character, String>();
  private HashSet<Character> selectedColours = new HashSet<>();
  

  public ChooseN(int rows, int columns) {
    int[] choice = this.getChoice();
    this.nToWin = choice[0];
    int CPUs = choice[1];
    this.board = new Board(rows, columns, this.nToWin);
    this.counterColour();
  new HumanPlayer(this.board, 'r');
    ArrayList<User> users = new ArrayList<>();
    users.add(this.addUser(true));
    for (int i = 0; i < CPUs; i++) {
      users.add(this.addUser(false));
    }
    this.users = users;
    this.printLoadout();
    this.playOpponent();
  }


///Private Methods///

  private void printLoadout() {
    System.out.println("\nWelcome to Connect" + this.nToWin + "!\n");
		System.out.println("You are playing against " + this.users.size() + " opponents: " + this.listUserColours());
    System.out.println("\nA user wins by connecting " + this.nToWin + " counters in a horizontal row, vertical column or in a diagonal (with a positive or negative gradient)\n");
    System.out.println("Please enter the number of the column you would like to place your counter into\n");
		System.out.println();
  }


private void playOpponent() {
    while(!this.board.getVictory()) {
      for (User user : users) {
        user.move();
        if (this.board.getVictory()) break;
      }
    }
  }


  private void counterColour() {
    this.colours.put('b', "Blue");
    this.colours.put('g', "Green");
    this.colours.put('o', "Orange");
    this.colours.put('p', "Purple");
    this.colours.put('r', "Red");
    this.colours.put('y', "Yellow");
  }


  private int[] getChoice() {
    int[] choice = new int[2];
    System.out.println("\nEnter a number of connections constituting to win:");
    choice[0] = this.sc.nextInt();
    System.out.println("Enter the number of CPU opponents you would like to play against:");
    choice[1] = this.sc.nextInt();
    return choice;
  }


  private User addUser(boolean human) {
    char sampleColour = '\0';
    for (char colour : this.colours.keySet()) {
      if (this.selectedColours.contains(colour)) {
        continue;
      } else {
        sampleColour = colour;
        this.selectedColours.add(colour);
        break;
      }
    }
    return human ? new HumanPlayer(this.board, sampleColour) : new CPU(this.board, sampleColour);
  }


  private String listUserColours() {
    String userColours = "";
    for (int i = 0; i < this.users.size(); i++) {
      char token = this.users.get(i).getColour();
      String colour = this.colours.get(token);
      if (i == 0) {
        userColours += "user " + (i + 1) + " is " + colour;
      } else if (i == this.users.size() - 1) {
        userColours += " and User " + (i + 1) + " is " + colour;
      } else {
        userColours += ", User " + (i + 1) + " is " + colour;
      }
    }
    return userColours;
  }
}