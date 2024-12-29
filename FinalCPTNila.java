import arc.*;

public class FinalCPTNila {
    public static void main(String[] args) {
        Console con = new Console(1280, 720);

        String strQuizChoice;
        String[][] intooga;

        con.print("Enter the game of your choice: ");
        strQuizChoice = con.readLine();

        intooga = FinalCPTNila.cars(strQuizChoice);

        for (int intRow = 0; intRow < intooga.length; intRow++) {
            con.println("Question " + (intRow + 1) + ": " + intooga[intRow][0]);
            con.println("a) " + intooga[intRow][1]);
            con.println("b) " + intooga[intRow][2]);
            con.println("c) " + intooga[intRow][3]);
            con.println("d) " + intooga[intRow][4]);
            con.println();
        }
    }

    public static String[][] cars(String strQuizChoice) {
        String[][] strCarsQuiz = new String[0][0];

        if (strQuizChoice.equalsIgnoreCase("Cars")) {
            TextInputFile carsfile = new TextInputFile("cars.txt");

            int questionCount = 0;
            while (!carsfile.eof()) {
                String line = carsfile.readLine();
                if (line.length() > 0) {
                    questionCount++;
                }
            }

            carsfile.close();
            carsfile = new TextInputFile("cars.txt");

            int totalQuestions = questionCount / 6;
            strCarsQuiz = new String[totalQuestions][5];

            for (int intRow = 0; intRow < totalQuestions; intRow++) {
                strCarsQuiz[intRow][0] = carsfile.readLine();
                strCarsQuiz[intRow][1] = carsfile.readLine();
                strCarsQuiz[intRow][2] = carsfile.readLine();
                strCarsQuiz[intRow][3] = carsfile.readLine();
                strCarsQuiz[intRow][4] = carsfile.readLine(); 
                carsfile.readLine();
            }

            carsfile.close();
        }

        return strCarsQuiz;
    }
}
