# Pnummer
Parsing Identity Numbers

Application reads strings from standard and parses them. If the strings
don't conform to certain rules, it logs them to a file. 
Input strings can be of 3 differing types: personnummer, samordningsnummer or organisationsnummer.

App accepts following options:  
 -O  parse strings as organisationsnummer 
 -S  parse strings as personnummer or samordningsnummer (both accepted) 

No option means parse strings as personnummer only.
You can run from console and enter one string at a time, only errors will be logged, no output visible.
You can pipe in a text file containing the strings, one string per line.  

Build application either using Maven or run a simple makefile. 

### Maven instructions
**to build run command:**  
mvn package  
**to clean build files in target folder run command:**  
mvn clean  
**to run app:**    
java -cp target/Pnummer-1.0-SNAPSHOT.jar dastmard.App 

### Make instructions

**to compile/build run command: a folder "dastmard" will be created in current diretory containing all classes**.  
make  
**to run app:**.   

java dastmard.App    

**to clean/remove all build files/folders including any log files created**. 
make clean 
