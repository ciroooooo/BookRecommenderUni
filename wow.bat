@echo off
javac -cp ".;lib/postgresql-42.7.7.jar" -d bin src/parametri/*.java
javac -cp ".;lib/postgresql-42.7.7.jar;bin" -d bin src/Server/*.java
java -cp ".;lib/postgresql-42.7.7.jar;bin" server.Server

pause
