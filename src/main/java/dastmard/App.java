package dastmard;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
* Class used to read strings from standard input and check
* if the string is a Swedish personnummer.
*
* @author Sohof dastmard
* @version 1.0
*/
public class App
{
  enum InputType {
    PERSONNR, SAMORDNR, ORGNR
  }

  private static final Logger logger = Logger.getLogger(App.class.getName());
  private static FileHandler handler;
  private static InputType TYP = InputType.PERSONNR; // default input type

  public static void main(String[] args) {

    if(args.length > 0 && !args[0].strip().isEmpty()) {

      String option = args[0].strip();

      if(option.equals("-O")){
        TYP = InputType.ORGNR;
        System.out.println("Checking for organisationsnummer ... ");
      }
      if(option.equals("-S")) {
        TYP = InputType.SAMORDNR;
        System.out.println("Checking for person/samordningsnummer ... ");
      }
    }
    else {
      System.out.println("Checking for personsnummer ... ");
    }


    try (Scanner sc = new Scanner(System.in)) {

      handler = new FileHandler("logfile.%g.txt",1024 * 1024, 3); // set max size and nr of files.
      handler.setFormatter(new SimpleFormatter()); // choose simple instead of default xml
      logger.addHandler(handler);
      logger.setUseParentHandlers(false); // supress output to standard out

      while (sc.hasNext()) {
        String nextString = sc.nextLine();

        try {

          if (TYP == InputType.ORGNR) {
            OrgNumber.newOrgNumber(nextString);
          }
          if (TYP == InputType.SAMORDNR) {
            PersonalNumber.newSamOrPersonalNumber(nextString);
          }
          if (TYP == InputType.PERSONNR) {
            PersonalNumber.newPersonalNumber(nextString);
          }

        } catch (ParseException ex) {

          logger.log(Level.SEVERE, "Input string: "+ nextString + " Caused "+ ex.getMessage());

        } // end inner try-catch
      } // end while loop

    } catch(Exception e) {
      System.err.println("Exception reading reading input ");
      e.printStackTrace();
    }

  }
}
