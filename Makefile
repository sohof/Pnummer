# Created by Sohof 16 mars 2022.
JFLAGS = -d .
JC = javac

SRC = src/main/java/dastmard/App.java \
			src/main/java/dastmard/PersonalNumber.java	\
			src/main/java/dastmard/ParseException.java	\
			src/main/java/dastmard/Checkable.java	\
			src/main/java/dastmard/OrgNumber.java

default:
		$(JC) $(SRC) $(JFLAGS)

.PHONY : clean
clean:
	rm -f *.txt *.class
	rm -R dastmard
