import arc.*;
import java.awt.*;
import java.awt.image.*;

public class FinalCPTNila{
	public static void main(String[] args){
		Console con = new Console("Multiple Choice Quiz", 1280, 720);
		Color colBg = new Color(31, 6, 71);
		con.setBackgroundColor(colBg);
		
		String[][] strintooga = new String [0][0];
		String strName;
		String strInitChoice;
		TextOutputFile quizlstfile = new TextOutputFile("quizzes.txt", true);
		
		con.print("Enter your name: ");
		strName = con.readLine();
		
		con.println("Select what you want to do: ");
		con.println("1. Play Quizzes");
		con.println("2. View High Scores");
		con.println("3. Help Screen");
		con.println("4. Quit Game");
		con.println("5. Add Quiz");
		
		strInitChoice = con.readLine();
		
		if(strInitChoice.equalsIgnoreCase("Add Quiz")){
			quizlstfile.println(FinalCPTNila.addQuiz(con));
			quizlstfile.close();
		}else if(strInitChoice.equalsIgnoreCase("Play Quizzes")){
			TextInputFile quizdispfile = new TextInputFile("quizzes.txt");
			
			String strQuizOption;
			String strQuizChoice;
			String[] strQuizList;
			int intNumQuiz = 0;
			int intCount1 = 0;
			
			while(quizdispfile.eof() == false){
				quizdispfile.readLine();
				intNumQuiz += 1;
			}
			
			strQuizList = new String[intNumQuiz];
			
			quizdispfile.close();
			
			quizdispfile = new TextInputFile("quizzes.txt");
			
			while(quizdispfile.eof() == false){
				strQuizList[intCount1] = quizdispfile.readLine();
				con.println((intCount1 + 1) + ". " + strQuizList[intCount1]);
				intCount1 += 1;
			}
			
			con.print("howdy" + intCount1);
			
			quizdispfile.close();
			
			con.print("Enter the quiz you want to play: ");
			strQuizChoice = con.readLine();
			
			int intCount2;
			
			for(intCount2 = 0; intCount2 < intCount1; intCount2++){
				if(strQuizChoice.equalsIgnoreCase(strQuizList[intCount2])){
					strintooga = FinalCPTNila.formatQuiz(strQuizList[intCount2] + ".txt");
					break;
				}
			}
			
			int intRow;
			String strChoice;
			int intNumCorr = 0;
			int intNumOut = 0;
			
			for(intRow = 0; intRow < strintooga.length; intRow++){
				con.println("Question " + (intRow + 1) + ": " + strintooga[intRow][0]);
				con.println("a) " + strintooga[intRow][1]);
				con.println("b) " + strintooga[intRow][2]);
				con.println("c) " + strintooga[intRow][3]);
				con.println("d) " + strintooga[intRow][4]);
				con.println();
				con.print("Enter your choice: ");
				strChoice = con.readLine();
				con.println();
				
				intNumOut += 1;
				
				if(strChoice.equalsIgnoreCase(strintooga[intRow][5])){
					con.println("You're right pookie!!!!");
					intNumCorr += 1;
				}else{
					con.println("Youre wrong pookie ahhhh!!!!");
				}
				
				FinalCPTNila.show(strName, strQuizChoice, intNumOut, intNumCorr, con);
				
			}
		}else if(strInitChoice.equalsIgnoreCase("Quit Game")){
			con.closeConsole();
		}
			
	}
	
	public static String[][] formatQuiz(String fileName){
		String[][] strQuizData;
		
		TextInputFile file = new TextInputFile(fileName);
		int intQCount = 0;
		
		while(file.eof() == false){
			String strLine = file.readLine();
			intQCount += 1;
		}
		
		int intTotalQ = intQCount / 6;
		strQuizData = new String[intTotalQ][7];
		int intRow;
		
		file.close();
		file = new TextInputFile(fileName);
		
		for(intRow = 0; intRow < intTotalQ; intRow++){
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
		
		for(intRow2 = 0; intRow2 < intQCount - 1; intRow2++){
			for(intRow = 0; intRow < intTotalQ - 1 - intRow2; intRow++){
				if(Integer.parseInt(strQuizData[intRow][6]) > Integer.parseInt(strQuizData[intRow + 1][6])){
					strTemp = strQuizData[intRow];
					strQuizData[intRow] = strQuizData[intRow + 1];
					strQuizData[intRow + 1] = strTemp;
				}
			}
		}
		return strQuizData;
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
		
		while(intEndCheck == 0){
			con.print("Question " + intCount + ": ");
			strUserQuiz = con.readLine();
			if(strUserQuiz.equalsIgnoreCase("end")){
				intEndCheck = 1;
				break;
			}
			
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
			
		}
			
		file.close();
		return strQuizName;
	}
	
	public static void show(String strUserName, String strQuizName, int intNumOut, int intNumCorr, Console con){
		con.print(strUserName);
		con.print(strQuizName);
		con.print((int)((intNumCorr / intNumOut) * 100));
	}
	
}
