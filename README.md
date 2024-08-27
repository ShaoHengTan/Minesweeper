# Minesweeper
 Minesweeper game in Java

Requirements:
1) Java 17 environment
2) properties folder must be in the same location as the program/jar file
3) properties folder must contain 2 files, "game.properties" and "renderer.properties"

How to run: 

1) Either export the code into a JAR file (using IDE), or use the included Jar file in the Release folder. 

2) Open command prompt and navigate to the folder path containing the JAR.
   -or-
 If in a in Windows enviroment, in the folder containing the JAR, click the address bar (for eg, "D:\Program Files\Minesweeper-app\Release"), clear the address and type cmd. Which should open cmd(command prompt) where the path is already this current folder.

3) In Cmd run the following command "java -jar <path>\Minesweeper-app.jar". For example "java -jar D:\Program Files\Minesweeper-app\Release\Minesweeper-app.jar" or "java -jar Minesweeper-app.jar".


HOW TO PLAY

1) Select the grid size
- key a number and press enter, e.g 10, will create a 10x10 grid
- invalid inputs will prompt user to key again

2) Choose the number of mines
- key a number and press enter, e.g 5, create 5 mines randomly on the grid
- cannot be more than 35% of the total cells, for eg a 10x10 grid can only have up to
35 total mines
- invalid inputs will prompt user to key again

3) Game Started
- Key a Letter(or two) followed by numbers, e.g D8 , which will correspond to a coordinate
on the grid


Note: 

-Java 17 needs to be installed in the system and added to PATH variables for the command line command 'java' to run.

-Standalone/portable Java can also be used. If so, before step (3) , simply run "setlocal" then "path=<PATH OF STANDALONE>;%path%". After which you can do step (3)

