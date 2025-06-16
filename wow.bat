@echo off
javac -cp ".;lib/postgresql-42.7.7.jar" src/Server/*.java
java -cp ".;lib/postgresql-42.7.7.jar;src/Server" Server
pause