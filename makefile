
default:
	javac \
    -cp "./stanford-corenlp-full-2018-10-05/*" \
    --module-path $$PATH_TO_FX \
    --add-modules javafx.controls \
    Main.java

run:
	java \
    --module-path $$PATH_TO_FX \
    --add-modules javafx.controls \
    Main
