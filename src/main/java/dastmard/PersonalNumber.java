package dastmard;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
* Class used to represent Swedish personnummer or samordningnummer.
* Performs validity checks on construction
* @author Sohof dastmard
* @version 1.0
*/
public final class PersonalNumber implements Checkable {

  private static final Pattern regex = Pattern.compile("^(\\d{2})?(\\d{2})(\\d{2})(\\d{2})([-+]?)?(\\d{3})(\\d?)$");

  private final int realDay;   // calculated from supplied from input, e.g in coordinationNumber
  private final String fullYear;
  private final String century;
  private final String year;
  private final String month;
  private final String day;   // as supplied from input, e.g in coordinationNumber the day is not real day of the month
  private final String birthNumbers; // the 3-first digits of the 2nd part
  private final String controlNumber; // is the last digit from suppplied input to checked with luhn.
  private boolean coordinationNumber = false;

  /**
  * Create a new PersonalNumber object from a string. In the default case a coordination number
  * is not considered to be a valid personnummer.
  * @param personnummer Personal identity number as a string to create the object from.
  * @throws ParseException On parse error.
  */
  public PersonalNumber(String personnummer) throws ParseException {
    this(personnummer, false);
  }

  /**
  * Create a new PersonalNumber object from a string.
  *
  * @param personnummer Personal identity number as a string to create the object from.
  * @param coordinationNumberAllowed flag indicating if a coordinationNumber is considered to be valid personnummer.
  * @throws ParseException On parse error.
  */
  public PersonalNumber(String personnummer, boolean coordinationNumberAllowed) throws ParseException {

    if (personnummer == null) {
      throw new ParseException("Parse failure, invalid input null string.");
    }

    Matcher matches = regex.matcher(personnummer);
    if (!matches.find()) {
      throw new ParseException("Parse Failure, invalid input, not a match");
    }


    String prelimCentury = null;
    String decade = matches.group(2);
    // case 1. group(1) match then century is suppplied in the input string
    if (matches.group(1) != null && !matches.group(1).isEmpty()) {
      prelimCentury = matches.group(1);
    }
    else { // case 2. Get this year 2022 and subtract decade e.g if 4607137454
      // then 2022 - 46 = 1976. The substring (0,2) is our century,
      int born = LocalDate.now().getYear() - Integer.parseInt(decade);
      // case 2b. if person is older than 100 e.g 900118+9811, not enough to subtract
      // decade 2022 - 90 - 100 = 1832, need to subtract 100 more to get correct century
      if (!matches.group(5).isEmpty() && matches.group(5).equals("+")) {
        born -= 100;
      }
      prelimCentury = Integer.toString(born).substring(0, 2);
    }

    int prelimDay = Integer.parseInt(matches.group(4));
    if (prelimDay > 60 && coordinationNumberAllowed) {
        prelimDay -= 60;
        this.coordinationNumber = true;
    }
    else if(prelimDay > 60) {
      throw new ParseException("Parse failure, invalid input day.");
    }

    this.realDay = prelimDay;
    this.century = prelimCentury;
    this.year = decade;
    this.fullYear = century + decade;
    this.month = matches.group(3);
    this.day = matches.group(4);
    this.birthNumbers = matches.group(6);
    this.controlNumber = matches.group(7);

    try {
         DateTimeFormatter.ofPattern("yyyy MM dd").parse(String.format("%s %s %02d", fullYear, month, realDay));
     } catch (DateTimeParseException e) {
         throw new ParseException("Parse failure, invalid input DateTime.");
     }

    String str = String.format("%s%s%s%s", year, month, day, birthNumbers);

    if(!checkString(str,Integer.parseInt(controlNumber))) {
      throw new ParseException("Parse failure, checksum incorrect.");
    }

  }

  /**
  * Checks if object is a samordningsnummer
  *
  * @return True if the personnummer is a samordningnummer .
  */
  public boolean isCoordinationNumber() {
    return coordinationNumber;
  }

}
