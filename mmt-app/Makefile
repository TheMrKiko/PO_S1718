JAVADIR=
JARNAME=mmt-app

all:
	(cd src; javac -cp /afs/.ist.utl.pt/users/1/6/ist186416/2º\ Ano/1º\ Semestre/PO/Projecto/po-uuilib/po-uuilib.jar:/afs/.ist.utl.pt/users/1/6/ist186416/2º\ Ano/1º\ Semestre/PO/Projecto/project/mmt-core/mmt-core.jar -encoding UTF-8 `find . -name \*.java`)
	(cd src; jar cvf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java` )

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`
