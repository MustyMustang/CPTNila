import arc.*;
import java.awt.*;
import java.awt.image.*;

public class FinalCPTNilatools{
	
	// Method to format quiz into an array
	public static String[][] formatQuiz(String fileName){
		String[][] strQuizData;

		// Open quiz file and initialise variables for looping
		TextInputFile fileQuiz = new TextInputFile(fileName);
		int intCount = 0;
		
		// Count number of lines
		while(fileQuiz.eof() == false){
			String strLine = fileQuiz.readLine();
			intCount += 1;
		}
		
		// Calculate total number of questions
		int intTotalQ = intCount / 6;
		
		// Set up array with the question count as the row amount
		strQuizData = new String[intTotalQ][7];
		int intRow;

		fileQuiz.close();
		
		// Open file again to store data into the array
		fileQuiz = new TextInputFile(fileName);

		for(intRow = 0; intRow < intTotalQ; intRow++){
			strQuizData[intRow][0] = fileQuiz.readLine();
			strQuizData[intRow][1] = fileQuiz.readLine();
			strQuizData[intRow][2] = fileQuiz.readLine();
			strQuizData[intRow][3] = fileQuiz.readLine();
			strQuizData[intRow][4] = fileQuiz.readLine();
			strQuizData[intRow][5] = fileQuiz.readLine();
			// Put a random integer in the 7th column to use later for bubble sorting
			strQuizData[intRow][6] = String.valueOf((int) (Math.random() * 100 + 1));
		}
		
		fileQuiz.close();
		
		// Bubble Sort
		int intRow2;
		String[] strTemp;
		for(intRow2 = 0; intRow2 < intTotalQ - 1; intRow2++){
			for(intRow = 0; intRow < intTotalQ - 1 - intRow2; intRow++){
				if(Integer.parseInt(strQuizData[intRow][6]) > Integer.parseInt(strQuizData[intRow + 1][6])){
					// Use a temporary array to move up and down a questions and their answers at a time
					strTemp = strQuizData[intRow];
					strQuizData[intRow] = strQuizData[intRow + 1];
					strQuizData[intRow + 1] = strTemp;
				}
			}
		}
		// Return the array with the bubble sorted information
		return strQuizData;
	}
	
	// Method to print out the quiz
	public static int printQuiz(Console con, String[][] strQuizData, int intRow){
		
		// Create and initialise variables for printing and user's choice input 
		String strChoice;
		int intChoice = -1;
		int intQLength = 0;
		
		// Get length of the question name to later index for image buffering
		intQLength = (strQuizData[intRow][0]).length();
		
		// Check whether the question is text based or if it is necessary to draw and image
		if (intQLength >= 4 && strQuizData[intRow][0].substring(intQLength - 4, intQLength).equalsIgnoreCase(".png")){
			BufferedImage imgQuestion = con.loadImage(strQuizData[intRow][0]);
			// Use line spacing to print out images between print statements
			con.println();
			con.println();
			con.println();
			con.println();
			con.drawImage(imgQuestion, 0, 250);
			con.println();
			con.println();
			con.println();
			con.println();
			con.println();
			con.println();
			// Print out answer options
			con.println("a) " + strQuizData[intRow][1]);
			con.println("b) " + strQuizData[intRow][2]);
			con.println("c) " + strQuizData[intRow][3]);
			con.println("d) " + strQuizData[intRow][4]);
			con.println();
		}else{
			// Print out questions an answer options
			con.println("Question " + (intRow + 1) + ": " + strQuizData[intRow][0]);
			con.println("a) " + strQuizData[intRow][1]);
			con.println("b) " + strQuizData[intRow][2]);
			con.println("c) " + strQuizData[intRow][3]);
			con.println("d) " + strQuizData[intRow][4]);
			con.println();
		}
		
		// Ask user to enter their choice until a valid choice is chosen
		while(intChoice == -1){
			con.print("Enter your choice (a, b, c, or d): ");
			strChoice = con.readLine();
			
			// User types in a, b, c, or, d, which correlates to an index number to later check if its the right/wrong answer
			if(strChoice.equalsIgnoreCase("a")){
				intChoice = 1;
			}else if(strChoice.equalsIgnoreCase("b")){
				intChoice = 2;
			}else if(strChoice.equalsIgnoreCase("c")){
				intChoice = 3;
			}else if(strChoice.equalsIgnoreCase("d")){
				intChoice = 4;
			}else{
				// Ask user to write a valid option
				con.println("Please write a valid option");
			}
		}
		// Return the index number of the option selected
		return intChoice;
	}
	
	// Method for user to add a quiz
	public static String addQuiz(Console con){
		
		// Create an initialise variables for looping and quiz array formatting
		String strQuizName;
		int intEndCheck = 0;
		String strUserQuiz = "";
		int intCount = 1;
		int intLine;
		String[] strOptions;
		strOptions = new String[4];
		int intCorrectOption;

		// Print header
		con.println("Shadow Scholar: Create Your Own Quiz");
		con.println("------------------------------------");
		
		// Ask user for the name of their quiz
		con.print("Enter the name of your quiz: ");
		strQuizName = con.readLine();
		con.println();
		
		// Open a new text file with the user selected quiz
		TextOutputFile fileUser = new TextOutputFile(strQuizName + ".txt");

		// Check whether the user has chosen to end their quiz
		while(intEndCheck == 0){
			if(strUserQuiz.equalsIgnoreCase("end")){
				intEndCheck = 1;
				break;
			}
			
			// Prompt user to write out a question
			con.print("Question " + intCount + ": ");
			strUserQuiz = con.readLine();
			// Print the options to the file
			fileUser.println(strUserQuiz);
			
			// Prompt user to write the 4 answer options
			for(intLine = 0; intLine < 4; intLine++){
				con.print("Option " + (intLine + 1) + ": ");
				strOptions[intLine] = con.readLine();
				// Print the options to the file
				fileUser.println(strOptions[intLine]);
			}
			
			intCorrectOption = 0; // Reset value for the next correct option
			
			// User selects the correct option out of thr 4 options given
			while(intCorrectOption < 1 || intCorrectOption > 4){
				con.print("Correct Option (1-4): ");
				intCorrectOption = con.readInt();
				if (intCorrectOption < 1 || intCorrectOption > 4){
					con.println("Invalid input"); // Ensure the input is valid
				}
			}
			
			fileUser.println(strOptions[intCorrectOption - 1]); // Print the correct option to the file

			intCount += 1;
			con.println();
			
			con.print("Are you finished (end) with the " + strQuizName + " Quiz? If not press enter to continue: "); // Ask the user if they are finished with their quiz
			strUserQuiz = con.readLine();
		}

		fileUser.close();
		return strQuizName; // Return the name of the quiz
	}
	
	// Method to print out header for game play
	public static double displayHeader(Console con, String strUserName, String strQuizName, double dblNumOut, double dblNumCorr, int intNumQuiz){
		
		// Variables
		BufferedImage imgstar = con.loadImage("star.png"); // Buffer image for star icon
		int intCurrentWidth = 0; // Progress bar with 0 width
		int intFinalWidth = (int)((dblNumOut / intNumQuiz) * 1280); // Final width of the progress bar
		double dblScore = Math.round(dblNumCorr / dblNumOut * 10000.0) / 100.0; // Calculate score to 2 decimal places
		Color colPro = new Color(47, 140, 36); // Initialise colour of progress bar
		con.setDrawColor(colPro); // Set colour of progress bar
		con.println();
		
		// Display number of questions done out of total number of questions
		con.println("  " + (int) dblNumOut + "/" + intNumQuiz);
		con.drawImage(imgstar, -5, 0); // Draw star to put under the question count
		
		// Animate progress bar
		while (intCurrentWidth < intFinalWidth){
			intCurrentWidth += 20; // Add width incrementally
			if (intCurrentWidth > intFinalWidth){
				intCurrentWidth = intFinalWidth; // Cap the width to the final value
			}
			con.fillRect(12, 31, intCurrentWidth, 18); // Draw the progress bar
			con.drawImage(imgstar, -5, 0);
			con.sleep(20); // Short time frames between animation
			con.repaint(); 
		}
		con.println();
		con.println();
		
		// Display header
		con.println("Shadow Scholar: Quiz Playing");
		con.println("------------------------------------");
		con.println("Player: " + strUserName);
		con.println("Quiz: " + strQuizName);
		con.println("Score: " + dblScore + " %");
		con.println("------------------------------------");
		con.println();

		return dblScore; // Return the score
	}

public static char leaderBoard(Console con){
	// Create and initialize variables
	String[][] strLeaderboard;
	int intCount = 0;
	int intScroll = 1; // Start with the first section (1 for top 24, 2 for the rest)
	String strLine;
	char chrKeyPressed = ' ';
	int intDone = 1;

	// Open highscores file
	TextInputFile leaderBoardFile = new TextInputFile("highscores.txt");

	// Count number of lines in the leaderboard file
	while (!leaderBoardFile.eof()){
		strLine = leaderBoardFile.readLine();
		intCount++;
	}

	// Calculate number of scores (assume each entry has a name and a score)
	int intTotal = intCount / 2;

	// Create leaderboard array to hold names and scores
	strLeaderboard = new String[intTotal][2];

	leaderBoardFile.close();

	// Reopen leaderboard file to store values in the array
	leaderBoardFile = new TextInputFile("highscores.txt");

	// Store name and score for each row in the array
	for (int intRow = 0; intRow < intTotal; intRow++){
		strLeaderboard[intRow][0] = leaderBoardFile.readLine(); // Name
		strLeaderboard[intRow][1] = leaderBoardFile.readLine(); // Score
	}

	// Close the file after reading
	leaderBoardFile.close();

	// Bubble sort leaderboard by scores in descending order
	for (int intRow2 = 0; intRow2 < intTotal - 1; intRow2++){
		for (int intRow = 0; intRow < intTotal - 1 - intRow2; intRow++){
			if (Double.parseDouble(strLeaderboard[intRow][1]) < Double.parseDouble(strLeaderboard[intRow + 1][1])){
				// Swap rows
				String[] strTemp = strLeaderboard[intRow];
				strLeaderboard[intRow] = strLeaderboard[intRow + 1];
				strLeaderboard[intRow + 1] = strTemp;
			}
		}
	}

	// Display leaderboard
	while(intDone == 1){
		if(intScroll == 1){
			con.clear();
			con.println("Shadow Scholar: Leaderboard");
			con.println("------------------------------------");
			for (int intRow = 0; intRow < 24 && intRow < intTotal; intRow++){ // Show the top 24 scores
				con.println((intRow + 1) + "." + strLeaderboard[intRow][0] + " | " + strLeaderboard[intRow][1]);
			}
			con.println();
			con.println("Press enter to see the rest..."); // Ask user if they want to see the rest of the scores
			con.println("(q)uit or go to (m)ain menu"); // Ask user if they would like to go to the main menu or quit

			// Wait for any key to be pressed
			chrKeyPressed = con.getChar();
			
			// Check if they want to continue to look at leaderboard, quit or go to the main menu
			if(chrKeyPressed == 'q' || chrKeyPressed == 'm'){
				intDone = 0;
				return chrKeyPressed; // Return their option
			}else{
				intScroll = 2; // Flip to the next section
			}
		} else if(intScroll == 2){
			// Display the remaining entries
			con.clear();
			con.println("Shadow Scholar: Leaderboard (Continued)"); // Display header
			con.println("------------------------------------");
			for (int intRow = 24; intRow < intTotal; intRow++){ // Display the rest of the scores
				con.println((intRow + 1) + ". " + strLeaderboard[intRow][0] + " | " + strLeaderboard[intRow][1]);
			}
			con.println();
			con.println("Press enter to go back to the top...");
			con.println("(q)uit or go to (m)ain menu");

			// Wait for any key to be pressed
			chrKeyPressed = con.getChar();
			
			// Check if they want to continue to look at leaderboard, quit or go to the main menu
			if(chrKeyPressed == 'q' || chrKeyPressed == 'm'){
				intDone = 0;
				return chrKeyPressed; // Return their option
			}else{
				intScroll = 1; // Flip to the next section
			}
		}
	}
	return chrKeyPressed;
}

	
	// Method to display help screen
	public static void helpScreen(Console con){
		
		// Print out useful tips to aid with gameplay
		con.println("Shadow Scholar: Help Screen");
		con.println("------------------------------------");
		con.println("Greetings, Scholar! Prepare yourself for an exciting, fun, and addictive game");
		con.println("designed to boost your general knowledge and challenge others with your own niche knowledge");
		con.println();
		
		con.println("How to Play");
		con.println("Use key inputs to select from the main menu:");
		con.println("P: Play Quizzes");
		con.println("A: Add Quizzes");
		con.println("L: Show Leaderboard");
		con.println("Q: Quit Game");
		con.println();
		
		con.println("Playing Quizzes");
		con.println("Questions appear one at a time");
		con.println("Enter the answer options which corresponds with a, b, c, or ,d and hit enter");
		con.println("When correct, a point is added to your score, when wrong the correct answer is displayed");
		con.println("for future reference");
	}
	
	// Method to print out secret screen for a few seconds
	public static void secretScreen(Console con){
		con.println("What do you call a fake noodle?");
		con.sleep(1000);
		con.println("An impasta");
		con.sleep(3000);
	}
	
	// Method to print out the quiz options
	public static String[] printQuizOptions(Console con){
		
		// Create and initialise variables
		int intNumQuiz = 0;
		int intQCount = 0;
		String[] strQuizList;
		
		// Open file with quiz names
		TextInputFile quizListFile = new TextInputFile("quizzes.txt");
		
		// Count number of quizzes from file
		while(quizListFile.eof() == false){
			quizListFile.readLine();
			intNumQuiz += 1;
		}

		// Put quizzes into an array for later iteration
		strQuizList = new String[intNumQuiz];
		quizListFile.close();
		quizListFile = new TextInputFile("quizzes.txt");

		// Display header
		con.println("Shadow Scholar: Quiz Selection");
		con.println("------------------------------------");
		
		// Print out quiz options in a numbered list to console
		while(quizListFile.eof() == false && intQCount < intNumQuiz){
			strQuizList[intQCount] = quizListFile.readLine();
			int intQuizLength = strQuizList[intQCount].length();
			strQuizList[intQCount] = (strQuizList[intQCount]).substring(0, 1).toUpperCase() + (strQuizList[intQCount]).substring(1, intQuizLength);
			con.println((intQCount + 1) + ". " + (strQuizList[intQCount]));
			intQCount += 1;
		}

		quizListFile.close();
		return strQuizList; // Return the array with the quiz names
	}
	
	
	// Method to display the main menu and get user input
	public static char displayMainMenu(Console con, BufferedImage imgMainMenu, Color colBg){
		con.clear();
		con.drawImage(imgMainMenu, 0, 0);
		con.println();
		char chrInitChoice = con.getChar();
		con.setBackgroundColor(colBg);
		
		return chrInitChoice; // Return the user's choice
	}
}
