import arc.*;
import java.awt.image.*;

public class FinalCPTNilatools{
	
	// Method to format quiz into an array
	public static String[][] formatQuiz(String fileName){
		String[][] strQuizData;

		// Open quiz file and initialise variables for looping
		TextInputFile file = new TextInputFile(fileName);
		int intCount = 0;
		
		// Count number of lines
		while(file.eof() == false){
			String strLine = file.readLine();
			intCount += 1;
		}
		
		// Calculate total number of questions
		int intTotalQ = intCount / 6;
		
		// Set up array with the question count as the row amount
		strQuizData = new String[intTotalQ][7];
		int intRow;

		file.close();
		
		// Open file again to store data onto the array
		file = new TextInputFile(fileName);

		for(intRow = 0; intRow < intTotalQ; intRow++){
			strQuizData[intRow][0] = file.readLine();
			strQuizData[intRow][1] = file.readLine();
			strQuizData[intRow][2] = file.readLine();
			strQuizData[intRow][3] = file.readLine();
			strQuizData[intRow][4] = file.readLine();
			strQuizData[intRow][5] = file.readLine();
			// Put a random integer in the 7th column to use later for bubble sorting
			strQuizData[intRow][6] = String.valueOf((int) (Math.random() * 100 + 1));
		}
		
		file.close();
		
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

	public static int printQuiz(Console con, String[][] strQuizData, int intRow){
		String strChoice;
		int intChoice = -1;
		int intQLength = 0;
		
		// Print one question at a time, with options (a-d)
		intQLength = (strQuizData[intRow][0]).length();
		
		if (intQLength >= 4 && strQuizData[intRow][0].substring(intQLength - 4, intQLength).equalsIgnoreCase(".png")){
			BufferedImage imgQuestion = con.loadImage(strQuizData[intRow][0]);
			con.println();
			con.println();
			con.println();
			con.println();
			con.drawImage(imgQuestion, 0, 150);
			con.println();
			con.println();
			con.println();
			con.println();
			con.println();
			con.println();
			con.println("a) " + strQuizData[intRow][1]);
			con.println("b) " + strQuizData[intRow][2]);
			con.println("c) " + strQuizData[intRow][3]);
			con.println("d) " + strQuizData[intRow][4]);
			con.println();
		}else{
			con.println("Question " + (intRow + 1) + ": " + strQuizData[intRow][0]);
			con.println("a) " + strQuizData[intRow][1]);
			con.println("b) " + strQuizData[intRow][2]);
			con.println("c) " + strQuizData[intRow][3]);
			con.println("d) " + strQuizData[intRow][4]);
			con.println();
		}
		

		while(intChoice == -1){
			con.print("Enter your choice (a, b, c, or d): ");
			strChoice = con.readLine();
			
			if(strChoice.equalsIgnoreCase("a")){
				intChoice = 1;
			}else if(strChoice.equalsIgnoreCase("b")){
				intChoice = 2;
			}else if(strChoice.equalsIgnoreCase("c")){
				intChoice = 3;
			}else if(strChoice.equalsIgnoreCase("d")){
				intChoice = 4;
			}else{
				con.println("Please write a valid option");
				con.println();
			}
		}
		return intChoice;
	}

	public static String addQuiz(Console con){
		String strQuizName;

		con.println("Shadow Scholar: Create Your Own Quiz");
		con.println("------------------------------------");
		con.print("Enter the name of your quiz: ");
		strQuizName = con.readLine();
		con.println();

		TextOutputFile file = new TextOutputFile(strQuizName + ".txt");

		int intEndCheck = 0;
		String strUserQuiz = "";
		int intCount = 1;
		int intLine;
		String[] strOptions;
		strOptions = new String[4];

		while(intEndCheck == 0){
			if(strUserQuiz.equalsIgnoreCase("end")){
				intEndCheck = 1;
				break;
			}

			con.print("Question " + intCount + ": ");
			strUserQuiz = con.readLine();
			file.println(strUserQuiz);

			for(intLine = 0; intLine < 4; intLine++){
				con.print("Option " + (intLine + 1) + ": ");
				strOptions[intLine] = con.readLine();
				file.println(strOptions[intLine]);
			}
			
			int intCorrectOption = 0;
			
			while(intCorrectOption < 1 || intCorrectOption > 4){
				con.print("Correct Option (1-4): ");
				intCorrectOption = con.readInt();
				if (intCorrectOption < 1 || intCorrectOption > 4){
					con.println("Invalid input");
				}
			}
			
			file.println(strOptions[intCorrectOption - 1]);

			intCount += 1;
			con.println();
			con.print("Are you finished (end) with the " + strQuizName + " Quiz? If not press and enter any key to continue: ");
			strUserQuiz = con.readLine();
		}

		file.close();
		return strQuizName;
	}

	public static float displayHeader(Console con, String strUserName, String strQuizName, float fltNumOut, float fltNumCorr){
		con.println("------------------------------------");
		con.println("Player: " + strUserName);
		con.println("Quiz: " + strQuizName);
		float fltScore = Math.round((fltNumCorr / fltNumOut) * 100);
		con.println("Score: " + fltScore + " %");
		con.println("------------------------------------");
		con.println();

		return fltScore;
	}
	
	public static void leaderBoard(Console con){
		// Open highscores file
		TextInputFile leaderBoardFile = new TextInputFile("highscores.txt");
		String strNameL = "";
		String strScore = "";
		int intCount = 0;
		String[][] strLeaderboard;
		String strLine;
		int intRow;
		int intRow2;
		int intBiggestName = 0;
		
		while(leaderBoardFile.eof() == false){
			strLine = leaderBoardFile.readLine();
			intCount += 1;
		}
		
		int intTotal = intCount / 2;
		strLeaderboard = new String[intCount][2];
		leaderBoardFile.close();
		
		leaderBoardFile = new TextInputFile("highscores.txt");
		
		for(intRow = 0; intRow < intTotal; intRow++){
			strLeaderboard[intRow][0] = leaderBoardFile.readLine();
			strLeaderboard[intRow][1] = leaderBoardFile.readLine();
		}
		
	
		
		String[] strTemp;
		for(intRow2 = 0; intRow2 < intTotal - 1; intRow2++){
			for(intRow = 0; intRow < intTotal - 1 - intRow2; intRow++){
				if(Float.parseFloat(strLeaderboard[intRow][1]) < Float.parseFloat(strLeaderboard[intRow + 1][1])){
					// Use a temporary array to move up and down a questions and their answers at a time
					strTemp = strLeaderboard[intRow];
					strLeaderboard[intRow] = strLeaderboard[intRow + 1];
					strLeaderboard[intRow + 1] = strTemp;
				}
			}
		}
		
		for(intRow = 0; intRow < intTotal; intRow++){
			if(strLeaderboard[intRow][0].length() > intBiggestName){
				intBiggestName = strLeaderboard[intRow][0].length();
			}
		}
		
		
		
		// Display header
		con.println("Shadow Scholar: Leaderboard");
		con.println("------------------------------------");
		for (intRow = 0; intRow < intTotal; intRow++) {
			String strName = strLeaderboard[intRow][0];
			String strSpaces = "";
			// Add spaces to align names
			for (intRow2 = strName.length(); intRow2 < intBiggestName + 2; intRow2++) {
				strSpaces += " ";
			}

			con.println((intRow + 1) + ". " + strName + strSpaces + "| " + strLeaderboard[intRow][1]);
		}
	}

	public static void helpScreen(Console con){
		con.println("Shadow Scholar: Help Screen");
		con.println("------------------------------------");
		con.println("Greetings, Scholar! Prepare yourself for an exciting, fun, and addictive game");
		con.println("designed to boost your general knowledge and challenge other with your own niche knowledge");
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
	
	public static void secretScreen(Console con){
		con.println("What do you call a fake noodle?");
		con.sleep(1000);
		con.println("An impasta");
		con.sleep(3000);
	}
}
