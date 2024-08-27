# Minesweeper
 Minesweeper game in Java


Requirements
1) Java 17 environment
2) properties folder must be in the same location as the program/jar file
3) properties folder must contain 2 files, "game.properties" and "renderer.properties"

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