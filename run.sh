#!/usr/bin/sh

# This file only can run UNIX based system such as Linux or MacOS or Android
javac -d out src/Barang.java src/Main.java
java -cp out src/Main
