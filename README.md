# Web Kanban Board

This project was done as a final project for the Linguage de Programação II course at Instituto Metrópole Digital. It consists of a web system that implements an task boards that can be use as a Kaban Board.

## Set up:
1) Import the project using maven
2) Make a database for the project - I used postgres
3) Make a folder in your pc where the imagens that can be upload in the appllication will be stored
4) Change in the application.properties file
   - spring.datasource.username= <your database user here>
   - spring.datasource.password= <your database password here>
   - file.path= <the path to your image file here>
5) Execute KanbanBoardApplication.java
