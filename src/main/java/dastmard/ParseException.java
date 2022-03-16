package dastmard;
/**
* Signals that an error has been reached while parsing.
* @author Sohof dastmard
* @version 1.0
*/
public class ParseException extends Exception {
    ParseException(String message) {
        super(message);
    }
}
