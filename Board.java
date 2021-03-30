import java.util.*;


///Public Methods///

public class Board {
  private boolean victory = false;
  private int rows;
  private int columns;
  private int nToWin;
  private ArrayList<ArrayList<Character>> board;


  public Board(int rows, int columns, int nToWin) {
    this.board = new ArrayList<>();
    this.rows = rows;
    this.columns = columns;
    this.nToWin = nToWin;
    for (int i = 0; i < this.columns; i++) {
      ArrayList<Character> column = new ArrayList<>();
      this.board.add(column);
    }
    this.printBoard();
  }


  public int getColumns() {
    return this.columns;
  }


  public boolean getVictory() {
    return this.victory;
  }


  public boolean addCounterColumn(char counterColour, int column) {
    ArrayList<Character> selectedColumn = this.board.get(column - 1);
    // Add a counter if there is a space in the column
    // Restrict additional counters if board is completely full and notify a player that they have drawn the game
    if (selectedColumn.size() < this.rows) {
      selectedColumn.add(counterColour);
      this.printBoard();
      boolean win = this.checkVictory(counterColour, column - 1, selectedColumn.size() - 1, this.nToWin);
      if (win) {
        this.printWinOrDraw(counterColour);
      }
      if (this.boardFilled()) {
        this.victory = true;
        System.out.println("You have drawn the game");
      }
      return true;
    } else {
      return false;
    }
  }


//Private Methods///

  private void printWinOrDraw(char counterColour) {
    System.out.println(counterColour + " has won!");
    this.victory = true;
  }


  private boolean boardFilled() {
    boolean full = true;
    for (ArrayList<Character> column : this.board) {
      if (column.size() < this.rows) {
        full = false;
      }
    }
    return full;
  }


  private boolean checkVictory(char counterColour, int column, int row, int nToWin) {
    boolean horizontal = this.checkHoriWin(counterColour, row, column, nToWin);
    boolean vertical = this.checkVertiWin(counterColour, column, nToWin);
    boolean forwardDiagonal = this.checkDiagWin(counterColour, row, column, nToWin, true);
    boolean backwardDiagonal = this.checkDiagWin(counterColour, row, column, nToWin, false);
    // Check if winning condition have been met
    return (vertical  || horizontal|| forwardDiagonal || backwardDiagonal);
  }


private boolean checkHoriWin(char counterColour, int row, int column, int nToWin) {
    // Check for number of consecutive counters of the same colour within any given row, constituting a win with a horizontal connection
    int equalRow = 1;
    int leftPointer = column;
    boolean EqualLeft = true;
    int rightPointer = column;
    boolean equalRight = true;
    // Incrementally extend the left pointer until it reaches a counter with a contrasting colour to current 
    while (EqualLeft) {
      if (leftPointer <= 0) {
        break;
      }
      boolean counterLeft = this.board.get(leftPointer - 1).size() > row;
      if (counterLeft) {
        if (this.board.get(leftPointer -1).get(row) == counterColour) {
          leftPointer--;
          equalRow++;
        } else {
          EqualLeft = false;
        }
      } else {
        EqualLeft = false;
      }
      if (equalRow >= nToWin) {
        return true;
      }
    }
    //Incrementally extend the left pointer until it reaches a counter with a contrasting colour to current 
    while (equalRight) {
      if (rightPointer >= this.columns - 1) {
        break;
      }
      boolean counterRight = this.board.get(rightPointer + 1).size() > row;
      if (counterRight) {
        if (this.board.get(rightPointer + 1).get(row) == counterColour) {
          rightPointer++;
          equalRow++;
        } else {
          equalRight = false;
        }
      } else {
        equalRight = false;
      } 
      if (equalRow >= nToWin) {
        return true;
      }
    }
    return false;
  }


  // Method to check for winning condition (vertical): if the number of counters in any given column match the number specified to constitute a win (N), then return true
  private boolean checkVertiWin(char counterColour, int column, int nToWin) {
    ArrayList<Character> selectedColumn = this.board.get(column);
    int n = selectedColumn.size();
    if (n < nToWin) {
      // Else, if the number of counters in said columns does not match the number specified constituting a win (N) return false
      return false;
    }
    // Count number of counters in reverse order (beginning with the top counter to reduce the amount of memory required to count)
    for (int row = n - 1; row >= n - nToWin; row--) {
      if (selectedColumn.get(row) != counterColour) {
        // If the colour of a given counter in said column does not match the preceding counter-colour, the 'connection' is broken
        return false;
      }
    }
    return true;
  }


// Method to check for winning condition (diaganol): if the number of counters in any combination of given rows & columns match the number specified to constitute a win (N), then return true
  private boolean checkDiagWin(char counterColour, int row, int column, int nToWin, boolean isDiag) {
    int equalRow = 1;
    int leftPointer = column;
    int leftRow = row;
    boolean EqualLeft = true;
    int rightPointer = column;
    int rightRow = row;
    boolean equalRight = true;
    // Else, if the number of counters in said rows & columns does not match the number specified constituting a win (N) return false
    // Incrementally extend the left pointer until it reaches a counter with a contrasting colour 
    while (EqualLeft) {
      leftRow += isDiag ? -1 : 1;
      if (leftPointer == 0 || leftRow < 0) {
        break;
      }
      boolean counterLeft = this.board.get(leftPointer -1).size() > leftRow;
      if (counterLeft) {
        if (this.board.get(leftPointer -1).get(leftRow) == counterColour) {
          leftPointer--;
          equalRow++;
        } else {
          EqualLeft = false;
        }
      } else {
        EqualLeft = false;
      }
      if (equalRow >= nToWin) {
        return true;
      }
    }
    while (equalRight) {
      rightRow += isDiag ? 1 : -1;
      if (rightPointer == this.columns - 1 || rightRow < 0) {
        break;
      }
      boolean counterRight = this.board.get(rightPointer + 1).size() > rightRow;
      if (counterRight) {
        if (this.board.get(rightPointer + 1).get(rightRow) == counterColour) {
          rightPointer++;
          equalRow++;
        } else {
          equalRight = false;
        }
      } else {
        equalRight = false;
      } 
      if (equalRow >= nToWin) {
        return true;
      }
    }
    return false;
  }


// Method to print board game is played on (void used to specify that method does not have return value)
  private void printBoard(){
		for (int i = this.rows - 1; i >= 0; i--) {
			for (int j = 0; j < this.columns; j++) {
				if (this.board.get(j).size() > i) {
					System.out.print("| " + this.board.get(j).get(i) + " ");
				} else {
					System.out.print("|   ");
				}
			}
			System.out.println("|");
		}
    for (int column = 1; column <= this.columns; column++) {
      System.out.print("   " + column);
    }
    System.out.println();
	}
}