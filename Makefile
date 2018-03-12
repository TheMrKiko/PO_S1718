
all: comp run

run:  dependencies
	java -Dimport=test.import mmt.app.App

dependencies:
	export CLASSPATH=./project/mmt-core/mmt-core.jar:./project/mmt-app/mmt-app.jar:./po-uuilib/po-uuilib.jar
	
comp:
	
	@echo
	@echo Compilando o core...
	(cd project/mmt-core; make $(MFLAGS) all)
	@echo
	@echo Compilando a app...
	(cd project/mmt-app; make $(MFLAGS) all)

clean:
	@echo
	@echo Limpando o core...
	cd project/mmt-core; make $(MFLAGS) clean
	@echo
	@echo Limpando a app...
	cd project/mmt-app; make $(MFLAGS) clean