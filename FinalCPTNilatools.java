import arc.*;

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

		con.println("Question " + (intRow + 1) + ": " + strQuizData[intRow][0]);
		con.println("a) " + strQuizData[intRow][1]);
		con.println("b) " + strQuizData[intRow][2]);
		con.println("c) " + strQuizData[intRow][3]);
		con.println("d) " + strQuizData[intRow][4]);
		con.println();

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

		while(intEndCheck == 0){
			if(strUserQuiz.equalsIgnoreCase("end")){
				intEndCheck = 1;
				break;
			}

			con.print("Question " + intCount + ": ");
			strUserQuiz = con.readLine();
			file.println(strUserQuiz);

			for(intLine = 1; intLine <= 4; intLine++){
				con.print("Option " + intLine + ": ");
				strUserQuiz = con.readLine();
				file.println(strUserQuiz);
			}

			con.print("Correct Option: ");
			strUserQuiz = con.readLine();
			file.println(strUserQuiz);

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


}
