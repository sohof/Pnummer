# Pnummer
Parsing Identity Numbers

Application reads strings from standard and parses them. If the strings
don't conform to certain rules, it logs them to a file. 
Input strings can be of 3 differing types: personnummer, samordningsnummer or organisationsnummer.

App accepts following options: 
 -O  parse strings as organisationsnummer
 -S  parse strings as personnummer or samordningsnummer (both accepted)

No option means parse strings as personnummer only.
You can either run from console and enter one string at a time, only errors will be logged.
Or pipe in a text file containing the strings, one string per line. 

Build application either using Maven or run a simple makefile. 

# Maven instructions
## mvn package 
  compile
## mvn clean 
  clean build files in target folder
  

# Make instructions

## make 
  build files folder "dastmard" will be created in current diretory containing all classes
## dastmard.App 
  run program
## make clean
  clean/remove all build files including any log files created
