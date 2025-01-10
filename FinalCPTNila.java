import arc.*;
import java.awt.*;
import java.awt.image.*;

public class FinalCPTNila{
	public static void main(String[] args){
		Console con = new Console("Shadow Scholar", 1280, 720);
		Color colBg = new Color(31, 6, 71);
		con.setBackgroundColor(colBg);

		String[][] strintooga = new String[0][0];
		String strName;
		char chrInitChoice;
		TextOutputFile quizlstfile = new TextOutputFile("quizzes.txt", true);
		
		BufferedImage imgMainMenu  = con.loadImage("mainMenu.png");
		
		con.drawImage(imgMainMenu, 0, 0);
		
		con.println();
		chrInitChoice = con.getChar();
		con.setBackgroundColor(colBg);
		con.clear();
		if (chrInitChoice == 'a'){
			quizlstfile.println(FinalCPTNila.addQuiz(con));
			quizlstfile.close();
		} else if (chrInitChoice == 'p'){

			boolean boolPlay = true;
			
			while(boolPlay == true){
				TextInputFile quizdispfile = new TextInputFile("quizzes.txt");
				String strQuizOption;
				String strQuizChoice;
				String[] strQuizList;
				char chrReplay;
				int intNumQuiz = 0;
				int intCount1 = 0;
				int intRow;
				String strChoice;
				float fltNumCorr = 0;
				float fltNumOut = 0;

				while (quizdispfile.eof() == false){
					quizdispfile.readLine();
					intNumQuiz += 1;
				}

				strQuizList = new String[intNumQuiz];

				quizdispfile.close();

				quizdispfile = new TextInputFile("quizzes.txt");

				while(quizdispfile.eof() == false && intCount1 < intNumQuiz) {
					strQuizList[intCount1] = quizdispfile.readLine();
					con.println((intCount1 + 1) + ". " + strQuizList[intCount1]);
					intCount1 += 1;
				}

				System.out.println("Number of quizzes: " + intCount1);

				quizdispfile.close();

				con.print("Enter the quiz you want to play: ");
				strQuizChoice = con.readLine();
				con.println();

				con.print("Enter your name: ");
				strName = con.readLine();

				int intCount2;

				for (intCount2 = 0; intCount2 < intCount1; intCount2++){
					if (strQuizChoice.equalsIgnoreCase(strQuizList[intCount2])){
						strintooga = FinalCPTNila.formatQuiz(strQuizList[intCount2] + ".txt");
						break;
					}
				}

				for (intRow = 0; intRow < strintooga.length; intRow++){
					con.clear();
					FinalCPTNila.displayHeader(con, strName, strQuizChoice, fltNumOut, fltNumCorr);
					System.out.println(fltNumCorr);
					System.out.println(fltNumOut);
					strChoice = FinalCPTNila.printQuiz(con, strintooga, intRow);
					con.println();

					fltNumOut += 1;

					if (strChoice.equalsIgnoreCase(strintooga[intRow][5])){
						con.println("You're right pookie!!!!");
						fltNumCorr += 1;
					} else {
						con.println("You're wrong pookie ahhhh!!!!");
					}
					con.sleep(800);
				}
				con.clear();
				con.println("\nQuiz Complete!");
				
				TextOutputFile highscoresfile = new TextOutputFile("highscores.txt", true);
				highscoresfile.println(FinalCPTNila.displayHeader(con, strName, strQuizChoice, fltNumOut, fltNumCorr));
				con.println();
				con.println("Would you like to (p)lay again, (q to quit) (m to go to main)");
				chrReplay = con.getChar();
				
				if(chrReplay == 'q'){
					con.closeConsole();
				}else if(chrReplay == 'p'){
					boolPlay = true;
				}
			}
		}
		else if(chrInitChoice == 'q'){
			con.closeConsole();
		}else if(chrInitChoice == 'l'){
			//
		}
	}

	public static String[][] formatQuiz(String fileName){
		String[][] strQuizData;

		TextInputFile file = new TextInputFile(fileName);
		int intQCount = 0;

		while (file.eof() == false) {
			String strLine = file.readLine();
			intQCount += 1;
		}

		int intTotalQ = intQCount / 6;
		strQuizData = new String[intTotalQ][7];
		int intRow;

		file.close();
		file = new TextInputFile(fileName);

		for (intRow = 0; intRow < intTotalQ; intRow++){
			strQuizData[intRow][0] = file.readLine();
			strQuizData[intRow][1] = file.readLine();
			strQuizData[intRow][2] = file.readLine();
			strQuizData[intRow][3] = file.readLine();
			strQuizData[intRow][4] = file.readLine();
			strQuizData[intRow][5] = file.readLine();
			strQuizData[intRow][6] = String.valueOf((int) (Math.random() * 100 + 1));
		}
		file.close();

		int intRow2;
		String[] strTemp;
		for (intRow2 = 0; intRow2 < intTotalQ - 1; intRow2++){
			for (intRow = 0; intRow < intTotalQ - 1 - intRow2; intRow++){
				if (Integer.parseInt(strQuizData[intRow][6]) > Integer.parseInt(strQuizData[intRow + 1][6])){
					strTemp = strQuizData[intRow];
					strQuizData[intRow] = strQuizData[intRow + 1];
					strQuizData[intRow + 1] = strTemp;
				}
			}
		}
		return strQuizData;
	}

	public static String printQuiz(Console con, String[][] strQuizData, int intRow){
		con.println("Question " + (intRow + 1) + ": " + strQuizData[intRow][0]);
		con.println("a) " + strQuizData[intRow][1]);
		con.println("b) " + strQuizData[intRow][2]);
		con.println("c) " + strQuizData[intRow][3]);
		con.println("d) " + strQuizData[intRow][4]);
		con.println();
		con.print("Enter your choice: ");
		return con.readLine();
	}

	public static String addQuiz(Console con){
		String strQuizName;

		con.print("Enter the name of your quiz: ");
		strQuizName = con.readLine();

		TextOutputFile file = new TextOutputFile(strQuizName + ".txt");
		con.println("When you are finished with your quiz simply write \"end\".");

		int intEndCheck = 0;
		String strUserQuiz;
		int intCount = 1;
		int intLine;

		while (intEndCheck == 0) {
			con.print("Question " + intCount + ": ");
			strUserQuiz = con.readLine();
			if (strUserQuiz.equalsIgnoreCase("end")){
				intEndCheck = 1;
				break;
			}

			file.println(strUserQuiz);

			for (intLine = 1; intLine <= 4; intLine++){
				con.print("Option " + intLine + ": ");
				strUserQuiz = con.readLine();
				file.println(strUserQuiz);
			}

			con.print("Correct Option: ");
			strUserQuiz = con.readLine();
			file.println(strUserQuiz);

			intCount += 1;
		}

		file.close();
		return strQuizName;
	}

	public static float displayHeader(Console con, String strUserName, String strQuizName, float fltNumOut, float fltNumCorr){
		con.println("Player: " + strUserName);
		con.println("Quiz: " + strQuizName);
		float fltScore = Math.round((fltNumCorr / fltNumOut) * 100);
		con.println("Score: " + fltScore + " %");
		con.println();

		return fltScore;
	}
}
