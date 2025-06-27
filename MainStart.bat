@echo off

rem Compila tutto da src mantenendo package e output in bin
javac -d bin src/parametri/*.java src/frames/*.java

rem Esegue la classe Main dentro il package frames
java -cp ".;bin" frames.Main

pause
