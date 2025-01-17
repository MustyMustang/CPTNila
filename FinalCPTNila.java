import arc.*;
import java.awt.*;
import java.awt.image.*;

public class FinalCPTNila{
	public static void main(String[] args){
		
		// Create and initialise console formatting
		Console con = new Console("Shadow Scholar", 1280, 720);
		Color colBg = new Color(31, 6, 71);
		con.setBackgroundColor(colBg);

		// Create and initialise loop variables and main menu screen
		int intDone = 0;
		String[][] strQuizData = new String[0][0];
		BufferedImage imgMainMenu = con.loadImage("mainMenu.png");
		String strName;
		char chrInitChoice;
		
		// Loop for running throughout the whole game
		while(intDone == 0){
			con.clear();
			con.drawImage(imgMainMenu, 0, 0);
			
			// Ask user for the option they want to do
			con.println();
			chrInitChoice = con.getChar();
			con.setBackgroundColor(colBg);
			
			// Loop if they choose to play a quiz
			if(chrInitChoice == 'p'){
				int intReplay = 1;
				char chrReplay;

				while(intReplay == 1){
					// Initialise and create variables for quiz playing
					TextInputFile quizListFile = new TextInputFile("quizzes.txt");
					int intQuizChoice;
					String strQuizChoice = "";
					String[] strQuizList;
					int intQuizLength = 0;
					int intQCount = 0;
					float fltNumCorr = 0;
					float fltNumOut = 0; 
					int intNumQuiz = 0;
					int intRow;
					int intIndex;

					// Count number of quizzes from file
					while(quizListFile.eof() == false){
						quizListFile.readLine();
						intNumQuiz += 1;
					}

					// Put quizzes into an array for later iteration
					strQuizList = new String[intNumQuiz];
					quizListFile.close();
					quizListFile = new TextInputFile("quizzes.txt");

					// Clear screen and print header
					con.clear();
					con.println("Shadow Scholar: Quiz Selection");
					con.println("------------------------------------");

					
					// Print out quiz options in a numbered list to console
					while(quizListFile.eof() == false && intQCount < intNumQuiz){
						strQuizList[intQCount] = quizListFile.readLine();
						intQuizLength = strQuizList[intQCount].length();
						strQuizList[intQCount] = (strQuizList[intQCount]).substring(0, 1).toUpperCase() + (strQuizList[intQCount]).substring(1, intQuizLength);
						con.println((intQCount + 1) + ". " + (strQuizList[intQCount]));
						intQCount += 1;
					}

					quizListFile.close();

					// Get quiz selection from user
					con.println();
					con.print("Enter the quiz you want to play (1 - " + (intNumQuiz) + "): ");
					intQuizChoice = con.readInt();

					// Make sure the quiz choice is an option from the numbered list
					if(intQuizChoice >= 1 && intQuizChoice <= intNumQuiz){
						strQuizChoice = strQuizList[intQuizChoice - 1];
						System.out.println("Quiz Choice: " + strQuizChoice);
						// Open file from the selected choice
						strQuizData = FinalCPTNilatools.formatQuiz(strQuizChoice.toLowerCase() + ".txt");
					}else{
						System.out.println("Invalid Input");
					}
					
					// Ask user for their name
					con.println();
					con.print("Enter your name: ");
					strName = con.readLine();
					con.println();

					// Loop to print out questions and header bar with information
					for(intRow = 0; intRow < strQuizData.length; intRow++){
						con.clear();
						con.println("Shadow Scholar: Quiz Playing");
						FinalCPTNilatools.displayHeader(con, strName, strQuizChoice, fltNumOut, fltNumCorr);
						
						// Get the index number associated with the letter the user selected
						intIndex = FinalCPTNilatools.printQuiz(con, strQuizData, intRow);

						con.println();
						fltNumOut += 1;
						
						// Check if the user is correct or wrong
						if(strQuizData[intRow][intIndex].equals(strQuizData[intRow][5])){
							fltNumCorr += 1;
							con.println("You are Correct!");
						}else{
							con.println("You are Wrong! The Correct Answer was: " + strQuizData[intRow][5]);
						}
						
						// Wait a few miliseconds until printing out the next question
						con.println();
						con.sleep(800);
					}
					con.clear();
					con.println();
					con.setBackgroundColor(colBg);
					con.println("Quiz Completed!");

					// Score saved to highscores file
					TextOutputFile highscoresfile = new TextOutputFile("highscores.txt", true);
					highscoresfile.println(strName);
					highscoresfile.println(FinalCPTNilatools.displayHeader(con, strName, strQuizChoice, fltNumOut, fltNumCorr));
					highscoresfile.close();
					
					// Ask user what they want to do after playing the quiz
					con.println();
					con.print("Would you like to (p)lay again, return to (m)ain menu, or (q)uit?");
					chrReplay = con.getChar();
					con.clear();

					if(chrReplay == 'q'){
						// Quit the game
						intDone = 1;
						intReplay = 0;
						con.closeConsole();
					}else if(chrReplay == 'm'){
						// Go back to the main screen
						intReplay = 0;
					}else if(chrReplay == 'p'){
						// intReplay is set to 
						intReplay = 1;
					}else{
						intReplay = 0;
						System.out.println("Invalid Input. Going back to the main menu");
					}
				}
			
			// Condition when user chooses to add a quiz
			}else if(chrInitChoice == 'a'){
				// Open quizzes file and output the name 
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
				}else if(chrInitChoice == 'm'){
					intDone = 0;
				}else{
					System.out.println("Invalid Input. Going back to the main menu");
				}
				
			// User chooses to quit game
			}else if(chrInitChoice == 'q'){
				intDone = 1;
				con.closeConsole();
			
			// User chooses to look at the leaderboard
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
					System.out.println("Invalid Input. Going back to main menu");
				}
			
			// If s is pressed, print out the secret joke to the screen
			}else if(chrInitChoice == 's'){
				FinalCPTNilatools.secretScreen(con);
			// If one of the options from the main menu is not selected, it will return back to the main menu
			}else{
				System.out.println("Invalid Input. Going back to main menu");
			}
		}
	}

}

// numbering for quiz list should be fool proof
