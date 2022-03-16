package dastmard;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
* Class used to represent a Swedish OrgNumber. Performs validity checks on construction
* @author Sohof dastmard
* @version 1.0
*/
public final class OrgNumber implements Checkable {

  private static final Pattern regex = Pattern.compile("^(\\d{2})?(\\d{2})(\\d{2})(\\d{2})([-]?)?(\\d{3})(\\d?)$");
  private final String fullYear;
  private final String century;
  private final String year;
  private final String month;
  private final String day;
  private final String threeNumbers; // the 3-first digits of the 2nd part
  private final String controlNumber; // is the last digit from suppplied input to checked with luhn.

  /**
  * Create a new OrgNumber object from a string.
  * @param orgnr Organization identity number as a string to create the object from.
  * @throws ParseException On parse error.
  */
  public OrgNumber(String orgnr) throws ParseException {

    if (orgnr == null) {
      throw new ParseException("Parse failure, invalid input null string.");
    }

    Matcher matches = regex.matcher(orgnr);
    if (!matches.find()) {
      throw new ParseException("Parse Failure, invalid input, not a match");
    }

    String prelimCentury = ""; // group(1) match then century is suppplied in the input string
    if (matches.group(1) != null && !matches.group(1).isEmpty()) {
      prelimCentury = matches.group(1);
    }

    this.century = prelimCentury;
    this.year = matches.group(2);
    this.fullYear = century + year;
    this.month = matches.group(3);
    this.day = matches.group(4);
    this.threeNumbers = matches.group(6);
    this.controlNumber = matches.group(7);

    if(Integer.parseInt(month) < 20) {
      throw new ParseException("Parse failure, middle number less than 20.");
    }

    String str = String.format("%s%s%s%s", year, month, day, threeNumbers);

    if(!checkString(str,Integer.parseInt(controlNumber))) {
      throw new ParseException("Parse failure, checksum incorrect.");
    }

  }

}
