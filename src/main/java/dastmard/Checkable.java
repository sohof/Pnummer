package dastmard;

/**
* The Checkable interface provides ways to perform simple checksum checks.
* It provides a default method to perform a check based on luhns algorithm

* @author Sohof dastmard
* @version 1.0
*/
public interface Checkable
{

  default boolean checkString(String purportedCC, int controlNumber) {

    final int nrDigits = purportedCC.length();
    final int parity = 0;   // fixed parity for swedish pers,sam, org.. should other be calc.
    int sum = 0;
    for (int i = 0; i < nrDigits; i++ ) {

      int digit = Character.getNumericValue(purportedCC.charAt(i));
      if ((i % 2) == parity) {
        digit *= 2;
      }
      if (digit > 9) {
        digit -= 9;
      }
      sum += digit;
    }
    return controlNumber == ((10 - (sum % 10)) % 10);
  }
}
