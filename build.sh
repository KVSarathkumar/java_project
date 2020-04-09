#!/usr/bin/env bash

javac \
    -cp "/home/np/dloads/nlp/stanford-corenlp-full-2018-10-05/*" \
    --module-path $PATH_TO_FX \
    --add-modules javafx.controls \
    Main.java
