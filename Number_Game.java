import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Number_Game {
    static Scanner sc = new Scanner(System.in);
    private int random_num, lvl, numPlayers, i, currentPlayerIndex;
    private static int range;
    // private Timer timer;
    // private TimerTask task;
    // private int timeRemaining;
    private String playerName;
    private List <String> leaderboard = new ArrayList<>();
    private List <Player> players = new ArrayList<>();

    class Player extends Object {
        private String name;
        private int score;

        public Player(String name) {
            this.name = name;
            this.score = 0;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void score(int points) {
            this.score += points;
        }
    }

    void numberOfPlayers() {
       System.out.println("Enter the number of players:");
        try {
            numPlayers = sc.nextInt();
            sc.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..Please enter a valid integer");
            sc.nextLine(); // Clear the invalid input
            numberOfPlayers(); // Prompt again for input
            return;
        }

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            String playerName = sc.nextLine();
            players.add(new Player(playerName));
        }
    }

    void comments() {
    // void startTimer(int seconds) {
    //     timer = new Timer();
    //     timeRemaining = seconds;
    //     task = new TimerTask() {
    //         public void run() {
    //             if (timeRemaining > 0) {
    //                 System.out.println("Time remaining: " + timeRemaining + " seconds");
    //                 timeRemaining--;
    //             } else {
    //                 System.out.println("\nTime's up! You took too long to guess");
    //                 playAgain();
    //                 cancelTimer(); // Cancel the timer if time is up
    //             }
    //         }
    //     };
    //     timer.schedule(task, seconds * 1000);
    // }

    // void cancelTimer() {
    //     timer.cancel(); // Cancel the timer
    // }
    }

    int chooseLevel() {
        synchronized (sc) {
            System.out.println("Enter 1 for Easy (<=10)");
            System.out.println("Enter 2 for Medium (<=50)");
            System.out.println("Enter 3 for Hard (<=100)");
        
            try {
                lvl = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input..Please enter a valid integer");
                sc.nextLine(); // Clear the invalid input
                chooseLevel(); // Prompt again for input
            }
        }
        return lvl;
    }

    int levelRange() {
        lvl = chooseLevel();
        switch(lvl) {
            case 1:
                range = 10;
                // startTimer(5); // 30 seconds time limit for Easy level
                break;
            case 2:
                range = 50;
                // startTimer(10); // 60 seconds time limit for Medium level
                break;
            case 3:
                range = 100;
                // startTimer(15); // 90 seconds time limit for Hard level
                break;
            default:
                System.out.println("Invalid Input!");
                chooseLevel();
        }
        return range;
    }

    int randomNumber() {
        int n = levelRange();
        double r = Math.random(); // Generating random number
        random_num = (int) ((r * n) + 1) ; // Converting number to integer
        return random_num;
    }

    void checkRandom(int n, int attempts) {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (random_num == n && attempts == 1) {
            i++;
            System.out.println("Congratulations, " + currentPlayer.getName() + "! You guessed the number on your first attempt");
            currentPlayer.score(10);
            specialRound(currentPlayer);
        } else if (random_num == n) {
            i++;
            System.out.println("Congratulations, " + currentPlayer.getName() + "! You guessed the number in " + i + " attempts");
            currentPlayer.score(5);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player
            playTurn(); // Start the next player's turn
        } else if (random_num < n) {
            System.out.println("Try a smaller number");
            enterNumber(attempts + 1);
        } else if (random_num > n) {
            System.out.println("Try a larger number");
            enterNumber(attempts + 1);
        }
    }

    void enterNumber(int  attempts) {
        synchronized (sc) {
            try {
                System.out.print("Enter a number:");
                int n = sc.nextInt(); // Read the integer input
                checkRandom(n, attempts);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input..Please enter a valid integer");
                sc.nextLine(); // Clear the invalid input
                enterNumber(1); // Prompt again for input
            }
        }
    }

    void playAgain() {
        synchronized (sc) {
            System.out.println("Enter 1 to play again with the same players");
            System.out.println("Enter 2 to play again with new players");
            System.out.println("Enter 3 to view leaderboard");
            System.out.println("Enter 4 to generate a random number");
            System.out.println("Enter 5 to end the game");

            try {
                int x = sc.nextInt();
                switch (x) {
                    case 1: // Play again with same players
                        currentPlayerIndex = 0; // Reset to the first player
                        for (Player player : players) {
                            player.score = 0; // Reset player scores
                        }
                        levelRange(); // Start the game again
                        break;
                    case 2: //Play again with new players
                        i = 0;
                        players.clear(); // Clear existing players
                        numberOfPlayers(); // Add new players
                        currentPlayerIndex = 0; // Reset to the first player
                        chooseLevel(); // Start the game again
                        enterNumber(1);
                        playTurn();
                        break;
                    case 3: //View leaderboard
                        updateLeaderboard();
                        displayLeaderboard();
                        playAgain();
                        break;
                    case 4: //Generate a random number
                        randomNumber();
                        System.out.println("Random number: " + random_num + "\n\n");
                        playAgain();
                        break;
                    case 5: //End the game
                        System.out.println("Thanks for playing❤️");
                        updateLeaderboard(); // Add player's score to the leaderboard
                        displayLeaderboard(); // Display the leaderboard
                        System.exit(0); // Terminate the program
                        break;
                    default:
                        System.out.println("Invalid Option! \n\n");
                        playAgain();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input..Please enter a valid integer");
                sc.nextLine(); // Clear the invalid input
                playAgain(); // Prompt again for input
            }
        }
    }

    void playTurn() {
        if(players.size() == i) {
            System.out.println();
            playAgain();
        } else {
            System.out.println("It's " + players.get(currentPlayerIndex).getName() + "'s turn");
            randomNumber();
            enterNumber(1);
        }
    }

    void specialRound(Player currentPlayer) {
        System.out.println("You have entered the special round, " + currentPlayer.getName());
        System.out.println("Answer a bonus question to earn extra points");

        System.out.println("What is the capital of France?");
        System.out.print("Your answer: ");
        sc.nextLine(); // Clear the buffer
        String answer = sc.nextLine().trim();

        if (answer.equalsIgnoreCase("Paris")) {
            System.out.println("Correct answer! You earned 2 extra points\n");
            currentPlayer.score(2);
        } else {
            System.out.println("Wrong answer! No extra points earned\n");
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player
        if (currentPlayerIndex == 0) { // All players have played one round
            playAgain();
        } else {
            playTurn(); // Start the next player's turn
        }
    }

    void updateLeaderboard() {
        for (Player player : players) {
            leaderboard.add(player.getName() + ": " + player.getScore());
        }
        Collections.sort(leaderboard, Collections.reverseOrder());
        // if (leaderboard.size() > 5) {
        //     leaderboard.remove(5);
        // }
    }

    void displayLeaderboard() {
       System.out.println("===== Leaderboard =====");
        for (String entry : leaderboard) {
            System.out.println(entry);
        }
    }
    public static void main(String args[]){
        Number_Game num = new Number_Game();
        System.out.println("Welcome to Number Guessing Game!");
        num.numberOfPlayers();
        System.out.println("Hello players! Let's begin");
        num.randomNumber();
        num.enterNumber(1);
        num.updateLeaderboard();
        num.displayLeaderboard();
        sc.close();
    }
}