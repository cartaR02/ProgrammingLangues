import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Recognizer {
    // overarching tokens and index that everything has access to.
    public static ArrayList<String> tokensList = new ArrayList<>();
    public static int tokenListIndex = 0;

    public static String outputFilePath = "";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Input More!!");
            return;
        }

        // set to output txt to avoid having to input all the time
        // try (Scanner scanner = new Scanner(new File("output.txt"))){
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            while (scanner.hasNextLine()) {
                // slit space [0]
                // gets the actual token and not the name associated with it
                tokensList.add(scanner.nextLine().split(" ")[0]);
            }
        } catch (FileNotFoundException ex) {
            return;
        }

        // set as first arg
        outputFilePath = args[1];
        // String outputFilePath = args[0];
        // sSystem.out.println(fileInputString);

        // initiating first call
        function();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("PARSED!!!");
        } catch (IOException ex) {
            System.out.println("File Writing did not work");
        }

    }

    // header body
    public static void function() {
        header();
        body();
    }

    // VARTYPE IDENTIFIER LEFT_PARENTHESIS [arg-decl] RIGHT_PARENTHESIS
    public static void header() {
        if (!tokensList.get(tokenListIndex).equals("VARTYPE")) {
            handleFileError("header", "VARTYPE", tokensList.get(tokenListIndex));
        }

    }

    // VARTYPE IDENTIFIER {COMMA VARTYPE IDENTIFIER}
    public static void arg_decl() {

    }

    // LEFT_BRACKET [statement-list] RIGHTBRACKET
    public static void body() {

    }

    public static void statment_list() {

    }

    public static void statment() {

    }

    //
    public static void while_loop() {

    }

    // RETURN_KEYWORD expression EOL
    public static void returnFunc() {

    }

    // IDENTIFIER EQUAL expression EOL
    public static void assignment() {

    }

    // term {BINOP term} | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    public static void expression() {

    }

    // IDENTIFIER | NUMBER
    public static void term() {

    }

    // strings format | Expecting this but was that for names
    // funcBody -> whose calling the function
    // expecting ie. token #6 to be RIGHT_BRACKET
    // butWas ie. IDENTIFIER
    // handling what they are will be sent by function
    public static void handleFileError(String caller, String expecting, String butWas) {
        String outputString = "Error: In grammer " + caller + ", expected " + expecting;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(outputString);
        } catch (IOException ex) {
            System.out.println("File Writing did not work");
        }
        System.exit(0);
    }

}
