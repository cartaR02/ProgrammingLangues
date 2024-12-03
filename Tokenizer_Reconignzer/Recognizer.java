/*
 * Carter Struck Recognizer File
 */

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
    public static int totalTokens = 0;

    public static String outputFilePath = "";

    // this for one scenario to know who calls what in term()
    public static String functionCaller = "";

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
                totalTokens++;
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

        // exiting for improper tokens consumed
        if (tokenListIndex != totalTokens) {
            String errorOutput = "Error: Only consumed " + tokenListIndex + " of the " + totalTokens + " given tokens";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(errorOutput);
            } catch (IOException ex) {
                System.out.println("File Writing did not work");
            }
            System.exit(0);
        }

    }

    // VARTYPE IDENTIFIER LEFT_PARENTHESIS [arg-decl] RIGHT_PARENTHESIS
    public static void header() {
        // all if statments are inverted for cleaner code only passing down to the next
        // thing if its true

        // first one called from higher function with different reasoning error
        if (!tokensList.get(tokenListIndex).equals("VARTYPE")) {
            non_termialFileHandler("function", "header");
        }

        tokenListIndex++;

        if (!tokensList.get(tokenListIndex).equals("IDENTIFIER")) {
            handleFileError("header", "IDENTIFIER", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        if (!tokensList.get(tokenListIndex).equals("LEFT_PARENTHESIS")) {
            handleFileError("header", "LEFT_PARENTHESIS", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        // checking next one for a empty bodyv
        // when the body is not empty check whats inside and call arg_decl
        if (tokensList.get(tokenListIndex).equals("RIGHT_PARENTHESIS")) {
            // exit function
            tokenListIndex++;
            return;
        }

        // when body is filled
        arg_decl();

        if (!tokensList.get(tokenListIndex).equals("RIGHT_PARENTHESIS")) {
            // exit function
            handleFileError("header", "RIGHT_PARENTHESIS", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;
    }

    // VARTYPE IDENTIFIER {COMMA VARTYPE IDENTIFIER}
    public static void arg_decl() {

        if (!tokensList.get(tokenListIndex).equals("VARTYPE")) {
            handleFileError("arg_decl", "VARTYPE", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        if (!tokensList.get(tokenListIndex).equals("IDENTIFIER")) {
            handleFileError("arg_decl", "IDENTIFIER", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        // allows for one or more if comma
        if (tokensList.get(tokenListIndex).equals("COMMA")) {
            tokenListIndex++;
            arg_decl();
        }

    }

    // helper function for debugging
    // i know i should use a debugger but using a debugger is not brat
    public static void currPrint() {
        System.out.println(tokensList.get(tokenListIndex));
        System.out.println(tokenListIndex);
        System.out.println("_____________-");
    }

    // LEFT_BRACKET [statement-list] RIGHTBRACKET
    public static void body() {

        if (!tokensList.get(tokenListIndex).equals("LEFT_BRACKET")) {
            handleFileError("body", "LEFT_BRACKET", tokensList.get(tokenListIndex));
        }
        tokenListIndex++;

        // when statement-list is empty
        if (tokensList.get(tokenListIndex).equals("RIGHT_BRACKET")) {
            tokenListIndex++;
            return;
        }

        // when statement list should have something

        statement_list();

        if (!tokensList.get(tokenListIndex).equals("RIGHT_BRACKET")) {
            handleFileError("body", "RIGHT_BRACKET", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

    }

    // statement {statement}
    public static void statement_list() {

        statement();
        // checking if more statements
        // not consume token cause thats handles in higher func
        if (tokensList.get(tokenListIndex).equals("RIGHT_BRACKET")) {
            return;
        }
        // call again since there is more to the statement
        statement_list();

    }

    // while-loop | return | assignment
    public static void statement() {
        // tokenListIndex++;

        // taking a peek to see what needs to be done
        String tokenCurr = tokensList.get(tokenListIndex);
        if (tokenCurr.equals("WHILE_KEYWORD")) {
            while_loop();
        } else if (tokenCurr.equals("RETURN_KEYWORD")) {
            returnFunc();
        } else if (tokenCurr.equals("IDENTIFIER")) {
            assignment();
        } else {
            // not entirely sure how this ends up handling body and right bracket but it
            // does
            handleFileError("body", "RIGHT_BRACKET", tokenCurr);
        }

    }

    // WHILE_KEYWORD
    public static void while_loop() {

        // redundant cause being called into function knowing while
        if (!tokensList.get(tokenListIndex).equals("WHILE_KEYWORD")) {
            handleFileError("while-loop", "WHILE_KEYWORD", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        if (!tokensList.get(tokenListIndex).equals("LEFT_PARENTHESIS")) {
            handleFileError("while-loop", "LEFT_PARENTHESIS", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        if (tokensList.get(tokenListIndex).equals("RIGHT_PARENTHESIS")) {

        }
        // to know who calls in error handling in term()
        functionCaller = "while-loop";
        expression();

        if (!tokensList.get(tokenListIndex).equals("RIGHT_PARENTHESIS")) {
            handleFileError("while-loop", "RIGHT_PARENTHESIS", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;
        body();

    }

    // RETURN_KEYWORD expression EOL
    public static void returnFunc() {

        // redundant cause being called into function knowing return
        if (!tokensList.get(tokenListIndex).equals("RETURN_KEYWORD")) {
            handleFileError("return", "RETURN_KEYWORD", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        functionCaller = "return";
        expression();

        if (!tokensList.get(tokenListIndex).equals("EOL")) {
            handleFileError("return", "EOL", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

    }

    // IDENTIFIER EQUAL expression EOL
    public static void assignment() {

        if (!tokensList.get(tokenListIndex).equals("IDENTIFIER")) {
            handleFileError("assignment", "IDENTIFIER", tokensList.get(tokenListIndex));
        }
        tokenListIndex++;

        if (!tokensList.get(tokenListIndex).equals("EQUAL")) {
            handleFileError("assignment", "EQUAL", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

        expression();

        if (!tokensList.get(tokenListIndex).equals("EOL")) {
            handleFileError("assignment", "EOL", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

    }

    // term {BINOP term} | LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    public static void expression() {

        // check for left paren and if thats not there then do terms and if thats not
        // there then idk
        if (!tokensList.get(tokenListIndex).equals("LEFT_PARENTHESIS")) {
            term();
            // consume binop and call self again
            if (tokensList.get(tokenListIndex).equals("BINOP")) {
                tokenListIndex++;
                expression();
            }
            return;
        } // posible else?
          // hitting here means left paren

        tokenListIndex++;

        expression();

        if (!tokensList.get(tokenListIndex).equals("RIGHT_PARENTHESIS")) {
            handleFileError("expression", "RIGHT_PARENTHESIS", tokensList.get(tokenListIndex));
        }

        tokenListIndex++;

    }

    // IDENTIFIER | NUMBER
    public static void term() {

        if (!tokensList.get(tokenListIndex).equals("IDENTIFIER")
                && !tokensList.get(tokenListIndex).equals("NUMBER")) {
            non_termialFileHandler(functionCaller, "expression");
        }
        tokenListIndex++;

    }

    // slightly different file handler just a differerent string
    public static void non_termialFileHandler(String caller, String expecting) {
        String errorString = "Error: In grammar rule " + caller + ", expected a valid " + expecting
                + " non-terminal to be present but was not";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(errorString);
        } catch (IOException ex) {
            System.out.println("File Writing did not work");
        }
        System.exit(0);
    }

    // strings format | Expecting this but was that for names
    // funcBody -> whose calling the function
    // expecting ie. token #6 to be RIGHT_BRACKET
    // butWas ie. IDENTIFIER
    // handling what they are will be sent by function
    public static void handleFileError(String caller, String expecting, String butWas) {
        // increment to get correct token number since indexing is off by 1
        tokenListIndex++;
        String outputString = "Error: In grammar rule " + caller + ", expected token #" + tokenListIndex + " to be "
                + expecting + " but was " + butWas;
        // currPrint();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(outputString);
        } catch (IOException ex) {
            System.out.println("File Writing did not work");
        }
        System.exit(0);
    }

}
