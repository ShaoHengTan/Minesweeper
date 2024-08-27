# Minesweeper
 Minesweeper game in Java


How to run: 
1) Download the whole Release Folder. 
2) Run the rungame.bat.
  -or-
2) Open command prompt and navigate to the folder path containing the JAR.
3) Run the following command "java -jar Minesweeper-app.jar" or "java -jar <path>\Minesweeper-app.jar". For example "java -jar D:\Program Files\Minesweeper-app\Release\Minesweeper-app.jar".

Requirements:
1) Java 17 environment
2) properties folder must be in the same location as the program/jar file
3) properties folder must contain 2 files, "game.properties" and "renderer.properties"



HOW TO PLAY

1) Select the grid size
- key a number and press enter, e.g 10, will create a 10x10 grid
- invalid inputs will prompt user to key again

2) Choose the number of mines
- key a number and press enter, e.g 5, create 5 mines randomly on the grid
- cannot be more than 35% of the total cells, for eg a 10x10 grid can only have up to 35 total mines
- invalid inputs will prompt user to key again

3) Game Started
- Key a Letter(or two) followed by numbers, e.g D8 , which will correspond to a coordinate
on the grid
- invalid inputs will prompt user to key again



Note: 
-Java/JDK 17 needs to be installed in the system and added to PATH variables for the command line command 'java' to run.
-Standalone/portable Java can also be used. If so , simply run "setlocal" then "path=<PATH OF STANDALONE>;%path%" in command prompt before running the "java -jar Minesweeper-app.jar" command. Or add these 2 lines into the top of rungame.bat.
-"https://www.openlogic.com/openjdk-downloads" for Standalone/portable JDK
