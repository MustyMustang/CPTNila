import arc.*;
import java.awt.*;
import java.awt.image.*;

public class FinalCPTNila{
	public static void main(String[] args){
		
		// Create and initialise console formatting
		Console con = new Console("┌── 🌙 ━ Shadow Scholar ━ 🌙 ──┐", 1280, 720);
		Color colBg = new Color(180, 58, 58);
		con.setBackgroundColor(colBg);

		// Create and initialise loop variables and main menu screen
		int intDone = 0;
		String[][] strQuizData = new String[0][0];
		BufferedImage imgMainMenu = con.loadImage("mainMenu.png");
		String strName;
		char chrInitChoice;
		
		// Loop for running throughout the whole game
		while(intDone == 0){
			// Call the method to display the main menu and get the user's choice
			chrInitChoice = FinalCPTNilatools.displayMainMenu(con, imgMainMenu, colBg);

			// Loop if they choose to play a quiz
			if(chrInitChoice == 'p'){
				int intReplay = 1;
				char chrReplay;

				while(intReplay == 1){
					// Initialise and create variables for quiz playing
					int intQuizChoice;
					String strQuizChoice = "";
					String[] strQuizList;
					int intNumQuiz = 0;
					double dblNumCorr = 0;
					double dblNumOut = 0;
					int intRow;
					int intIndex;

					// Call method to print quiz options
					strQuizList = FinalCPTNilatools.printQuizOptions(con);
					intNumQuiz = strQuizList.length; // Calculate the number of quizzes
					
					// Get quiz selection from user
					con.println();
					intQuizChoice = -1; // Initialize the user's quiz choice

					// Keep asking the user until they enter a valid quiz choice
					while (intQuizChoice < 1 || intQuizChoice > intNumQuiz){
						con.print("Enter the quiz you want to play (1 - " + (intNumQuiz) + "): ");
						
						// Get user's choice
						intQuizChoice = con.readInt();
						
						// Check if the input is valid
						if(intQuizChoice < 1 || intQuizChoice > intNumQuiz){
							con.println("Please enter a valid input: ");
							System.out.println("Invalid Input");
						}
					}

					// Once input is valid open the text file
					strQuizChoice = strQuizList[intQuizChoice - 1];
					System.out.println("Quiz Choice: " + strQuizChoice);
					// Open file from the selected choice
					strQuizData = FinalCPTNilatools.formatQuiz(strQuizChoice.toLowerCase() + ".txt");

					
					// Ask user for their name
					con.println();
					con.print("Enter your name: ");
					strName = con.readLine();
					con.println();

					// Loop to print out questions and header bar with information
					for(intRow = 0; intRow < strQuizData.length; intRow++){
						con.clear(); // Clear for next screen display
						FinalCPTNilatools.displayHeader(con, strName, strQuizChoice, dblNumOut, dblNumCorr, strQuizData.length);
						// Get the index number associated with the letter the user selected
						intIndex = FinalCPTNilatools.printQuiz(con, strQuizData, intRow);

						con.println();
						dblNumOut += 1; // Increment the count of questions that have been displayed to the user
						
						// Check if the user is correct or incorrect
						if(strQuizData[intRow][intIndex].equals(strQuizData[intRow][5])){
							dblNumCorr += 1;
							con.println("You are Correct!");
							con.sleep(800);
						}else{
							con.println("You are Wrong! The Correct Answer was: " + strQuizData[intRow][5]);
							con.sleep(3000); // Sleep for a longer time frame to let the user see the correct answer
						}

						con.println();
					}
					
					// Clear the screen fully and only display the score, quiz name and player's name once the game is completed
					con.clear();
					con.repaint();
					con.setBackgroundColor(colBg);
					TextOutputFile highscoresfile = new TextOutputFile("highscores.txt", true);
					highscoresfile.println(strName); // Store the name to the highscores file
					highscoresfile.println(FinalCPTNilatools.displayHeader(con, strName, strQuizChoice, dblNumOut, dblNumCorr, strQuizData.length)); // Store the score to the highscores file
					highscoresfile.close(); 
					con.println();
					con.println();
					con.println();
					con.println("Quiz Completed!");
					
					// Ask user what they want to do after playing the quiz
					con.println();
					con.print("Would you like to (p)lay again, return to (m)ain menu, or (q)uit?");
					chrReplay = con.getChar();
					con.clear();
					
					// Get choice of user after they have complete the quiz
					if(chrReplay == 'q'){
						// Quit the game
						intDone = 1;
						intReplay = 0;
						con.closeConsole();
					}else if(chrReplay == 'm'){
						// Go back to the main screen
						intReplay = 0;
					}else if(chrReplay == 'p'){
						// User can play another quiz
						intReplay = 1;
					}else{
						// Invalid input is entered and user is sent back to main menu
						intReplay = 0;
						System.out.println("Invalid Input. Going back to the main menu");
					}
				}
					
			// Condition when user chooses to add a quiz
			}else if(chrInitChoice == 'a'){
				// Open quizzes file and output the name of the user's quiz
				TextOutputFile quizListOutput = new TextOutputFile("quizzes.txt", true);
				quizListOutput.println(FinalCPTNilatools.addQuiz(con));
				quizListOutput.close();
				
				// User chooses to go to main menu or quit
				con.println("(m)ain menu, (q)uit");
				chrInitChoice = con.getChar();
				
				// User chooses to quit game
				if(chrInitChoice == 'q'){
					intDone = 1;
					con.closeConsole();
				// User chooses to go back to main menu
				}else if(chrInitChoice == 'm'){
					intDone = 0;
				// Invalid input is entered and user is sent back to main menu
				}else{
					System.out.println("Invalid Input. Going back to the main menu");
				}
				
			// User chooses to quit game
			}else if(chrInitChoice == 'q'){
				intDone = 1;
				con.closeConsole();
			
			// User chooses to view the leaderboard
			}else if(chrInitChoice == 'l'){
				
				FinalCPTNilatools.leaderBoard(con);

				con.println();
				con.println("(q)uit or (m)ain menu");
				chrInitChoice = con.getChar();
				
				// Quit game or head back to main menu from leaderboard screen
				if(chrInitChoice == 'q'){
					intDone = 1;
					con.closeConsole();
				}else if(chrInitChoice == 'm'){
					intDone = 0;
				}else{
					System.out.println("Invalid Input. Going back to main menu");
				}
			// If help screen is chosen from main menu print out aiding instructions
			}else if(chrInitChoice == 'h'){
				FinalCPTNilatools.helpScreen(con);
				con.println();
				con.println("(q)uit or (m)ain menu");
				chrInitChoice = con.getChar();
				
				// Quit game or head back to main menu from leaderboard screen
				if(chrInitChoice == 'q'){
					intDone = 1;
					con.closeConsole();
				}else if(chrInitChoice == 'm'){
					intDone = 0;
				}else{
					// Invalid input is entered and user is sent back to main menu
					System.out.println("Invalid Input. Going back to main menu");
				}
			
			// If s is pressed, print out the secret joke to the screen
			}else if(chrInitChoice == 's'){
				FinalCPTNilatools.secretScreen(con);
			// // Invalid input is entered and user is sent back to main menu
			}else{
				System.out.println("Invalid Input. Going back to main menu");
			}
		}
	}
}


