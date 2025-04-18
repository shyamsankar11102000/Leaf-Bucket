#!/bin/bash
mkdir -p out
javac src/*.java -d out
echo "Compilation complete. Run using: java -cp out Main"
