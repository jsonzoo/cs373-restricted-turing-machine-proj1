# Makefile for Java program

JAVAC = javac
JAVA = java
RM = rm -f

CLASS = zhu_proj1
SRC = $(CLASS).java
OBJ = $(CLASS).class

all: $(OBJ)

$(OBJ): $(SRC)
	$(JAVAC) $(SRC)

run: $(OBJ)
	$(JAVA) $(CLASS)

clean:
	$(RM) *.class