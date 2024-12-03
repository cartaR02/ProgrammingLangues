import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    // Java version of given regex code
    public static boolean validNumber(String aLexeme){
        String regex = "^[0-9][0-9]*$";
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(aLexeme);
        return regexMatcher.matches();
    }

    public static boolean validIdentifier(String aLexeme){
        String regex = "^[a-zA-Z][a-zA-Z0-9]*$";
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(aLexeme);
        return regexMatcher.matches();
    }
    // returns string of proper number/identifer format if true
    // returns null otherwise
    // usually never gets called without being a number or identifier
    public static String checkValidity(String identifier){
        String returnedString = "";
        if (validNumber(identifier)){ 
            returnedString += "NUMBER ";
        }else if (validIdentifier(identifier)){
            returnedString += "IDENTIFIER ";
        }else {
            return "";
        }
        return returnedString + identifier + "\n";
    }
    public static void main(String[] args) {

        if (args.length < 1){
            System.out.println("Input more !!");
            return;
        }

        String fileInputString = "";
        try (Scanner scanner = new Scanner(new File(args[0]))){
            while (scanner.hasNextLine()){
                fileInputString += scanner.nextLine();
            }
        } catch (FileNotFoundException ex) {
            return;
        }
        String outputFilePath = args[1];

        HashMap<String, String> tokenStrings = new HashMap<String, String>();
        tokenStrings.put("int", "VARTYPE");
        tokenStrings.put("main", "IDENTIFIER");
        tokenStrings.put("while", "WHILE_KEYWORD");
        tokenStrings.put("return", "RETURN_KEYWORD");
        tokenStrings.put("void", "VARTYPE");
        tokenStrings.put("!=", "BINOP");
        tokenStrings.put("==", "BINOP");


        HashMap<Character, String> tokenChars = new HashMap<Character, String>();
        tokenChars.put('(', "LEFT_PARENTHESIS");
        tokenChars.put(')', "RIGHT_PARENTHESIS");
        tokenChars.put('}', "RIGHT_BRACKET");
        tokenChars.put('{', "LEFT_BRACKET");
        tokenChars.put('=', "EQUAL");
        tokenChars.put(',', "COMMA");
        tokenChars.put(';', "EOL");
        tokenChars.put('+', "BINOP");
        tokenChars.put('*', "BINOP");
        tokenChars.put('%', "BINOP");



        String currentString = "";
        String outputString = "";
        // checks string char by char
        // make strings of characters and check for those cases
        /*
         * thought proccess
         * make strings of characters and check for those case
         * ex main then find main in hash and assign
         * during that check for the char for ( ) 
         * then take those as the current thing and take the string before if 
         * it exists implying that its a identifier since we got to 
         * a char without assigning it to something
         * spaces are ignored
         */
        for (int i = 0; i < fileInputString.length(); i++) {
            Character currentChar = fileInputString.charAt(i);

            // check single chars
            // treat spaces as skips 
            if (currentChar.equals(' ') | currentChar.equals('\t')){
                if (tokenStrings.containsKey(currentString)){
                    outputString += tokenStrings.get(currentString) + " " + currentString + "\n"; 
                    currentString = "";
                    continue;
                }
                String toConcat = checkValidity(currentString);
                if (!toConcat.equals("")){
                    currentString = "";
                    outputString += toConcat;
                }
                continue;
            }

            // checkign singular chars first
            if (tokenChars.containsKey(currentChar) | currentChar.equals('!')){
                if (currentString.length() != 0){
                    // handles change in character that was not a known thing
                    String toConcat = checkValidity(currentString);
                    if (!toConcat.equals("")){
                        currentString = "";
                        outputString += toConcat;
                        
                    }
                
                }
                // peek ahead to if the next is == or !=
                // != should always hit but mainly for grabbing the = after !
                // when = followed by = then grabs it and handles it then i++
                if (currentChar.equals('=') | currentChar.equals('!')){
                    Character nextChar = fileInputString.charAt(i+1);
                    String posibleString = currentChar.toString() + nextChar.toString();
                    if (tokenStrings.containsKey(posibleString)){
                        outputString += tokenStrings.get(posibleString) + " " + posibleString + "\n";
                        i++;
                        continue;
                    }
                }                
                outputString += tokenChars.get(currentChar) + " " + currentChar + "\n";
                continue;
            }

            currentString += currentChar;
            if (tokenStrings.containsKey(currentString)){
                outputString += tokenStrings.get(currentString) + " " + currentString + "\n"; 
                currentString = "";
            }
        } //for file chars
        if(tokenStrings.containsKey(currentString)){
            outputString += tokenStrings.get(currentString) + " " + currentString;
        }else{
            String toConcat = checkValidity(currentString);
            if (!toConcat.equals("")){
                outputString += toConcat;
            } 
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))){
            writer.write(outputString);
        } catch (IOException ex) {
            System.out.println("Hope it works");
        }
    }
}
