import java.util.*;


public class tictactoe_game {

    private static char[][] board;
    private static char currentPlayer;
    private static boolean gameEnded;
    private static Stack<int[]> moves; // Stack to keep track of moves


    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeGame(); // Initialize the game state on startup

        while (true) {
            while (!gameEnded) {
                printBoard();
                playerMove(scanner);
                checkWinner();
                switchPlayer();
            }

            System.out.println("Game over! Do you want to restart or quit? (Enter 'restart' or 'quit')");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("quit")) {
                System.out.println("Thanks for playing! Goodbye.");
                break; // Exit the main loop
            } else {
                restartGame(); // Restart the game if not quitting
            }
        }
        scanner.close();
    }

    // initializing the game
    private static void initializeGame() {
        board = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        currentPlayer = 'X';
        gameEnded = false;
        moves = new Stack<>();
    }


    // Function to print the board
    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }



    // Function to take player input
    private static void playerMove(Scanner scanner) {
        while (true) {
            System.out.println("Player " + currentPlayer + ", enter your move (row and column between 1 and 3) or type 'undo', 'restart', or 'quit': ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("undo")) {
                undoMove();
                return;
            } else if (input.equalsIgnoreCase("restart")) {
                restartGame();
                return;
            } else if (input.equalsIgnoreCase("quit")) {
                System.out.println("Thanks for playing! Goodbye.");
                System.exit(0);
            }

            String[] parts = input.split(" ");
            if (parts.length == 2) {
                try {
                    int row = Integer.parseInt(parts[0]) - 1;
                    int col = Integer.parseInt(parts[1]) - 1;

                    if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                        board[row][col] = currentPlayer;
                        moves.push(new int[]{row, col}); // Save the move to the stack
                        break;
                    } else {
                        System.out.println("This spot is already taken or out of bounds. Try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numbers for row and column.");
                }
            } else {
                System.out.println("Please enter row and column.");
            }
        }
    }


    // Function to check winner
    private static void checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                gameEnded = true;
                return;
            }
        }

        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            printBoard();
            System.out.println("Player " + currentPlayer + " wins!");
            gameEnded = true;
            return;
        }

        // Check for a tie
        boolean boardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    boardFull = false;
                    break;
                }
            }
        }

        if (boardFull) {
            printBoard();
            System.out.println("It's a tie!");
            gameEnded = true;
        }
    }


    // Function to switch player
    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // UNDO
    private static void undoMove() {
        if (!moves.isEmpty()) {
            int[] lastMove = moves.pop(); // Get the last move
            board[lastMove[0]][lastMove[1]] = ' '; // Clear that spot
            // currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch back to the previous player
            System.out.println("Move undone. It's now Player " + currentPlayer + "'s turn.");
        } else {
            System.out.println("No moves to undo.");
        }
    }

    // RESTART
    private static void restartGame() {
        initializeGame();
        System.out.println("Game restarted. Player X goes first!");
    }
    
}
