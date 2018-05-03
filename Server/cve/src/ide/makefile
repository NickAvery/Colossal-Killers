include ../../makedefs

UFILES=ide.u classbrowser.u templates.u definitions.u utags.u buffertabset.u buffertabitem.u imgs.u cproject.u jproject.u uproject.u projeditor.u cetl.u configoptions.u hfiledialog.u idol.u preproce.u yyerror.u unigram.u unilex.u unix.u unicon.u matchparen.u shelletl.u langabstract.u syntaxetl.u scetl.u

all: ide.u classbrowser.u templates.u definitions.u utags.u buffertabset.u buffertabitem.u imgs.u cproject.u jproject.u uproject.u projeditor.u cetl.u configoptions.u hfiledialog.u idol.u preproce.u yyerror.u unigram.u unilex.u unix.u unicon.u matchparen.u shelletl.u langabstract.u syntaxetl.u scetl.u

UNICONPACKAGE=idol.u preproce.u unicon.u unigram.u unilex.u yyerror.u

ide.u: ide.icn utags.u templates.u definitions.u cetl.u buffertabitem.u buffertabset.u uproject.u cproject.u jproject.u projeditor.u hfiledialog.u classbrowser.u imgs.u shelletl.u langabstract.u syntaxetl.u scetl.u
	unicon $(UFLAGS) ide

classbrowser.u: classbrowser.icn imgs.u
	unicon $(UFLAGS) classbrowser

buffertabset.u: buffertabset.icn
	unicon $(UFLAGS) buffertabset

buffertabitem.u: buffertabitem.icn
	unicon $(UFLAGS) buffertabitem

templates.u: templates.icn
	unicon $(UFLAGS) templates

definitions.u: definitions.icn
	unicon $(UFLAGS) definitions

utags.u: utags.icn
	unicon $(UFLAGS) utags

imgs.u: imgs.icn
	unicon $(UFLAGS) imgs

matchparen.u: matchparen.icn
	unicon $(UFLAGS)  matchparen

configoptions.u: configoptions.icn
	unicon $(UFLAGS) configoptions

cproject.u: cproject.icn
	unicon $(UFLAGS) cproject

jproject.u: jproject.icn
	unicon $(UFLAGS) jproject

uproject.u: uproject.icn
	unicon $(UFLAGS) uproject

projeditor.u: projeditor.icn
	unicon $(UFLAGS) projeditor

hfiledialog.u: hfiledialog.icn
	unicon $(UFLAGS) hfiledialog

shelletl.u: shelletl.icn
	unicon $(UFLAGS) shelletl

syntaxetl.u: syntaxetl.icn
	unicon $(UFLAGS) syntaxetl

scetl.u: scetl.icn syntaxetl.u cetl.u
	unicon $(UFLAGS) scetl

cetl.u: cetl.icn $(UNICONPACKAGE)
	unicon $(UFLAGS) cetl

langabstract.u: langabstract.icn
	unicon $(UFLAGS) langabstract

#cetl.icn:
#	cvs update cetl.icn

idol.u: idol.icn
	unicon $(UFLAGS) idol

preproce.u: preproce.icn
	unicon $(UFLAGS) preproce

yyerror.u: yyerror.icn
	unicon $(UFLAGS) yyerror

unigram.u: unigram.icn
	unicon $(UFLAGS) unigram

unilex.u: unilex.icn
	unicon $(UFLAGS) unilex

unix.u : unix.icn
	unicon $(UFLAGS) unix

unicon.u: unicon.icn
	unicon $(UFLAGS) unicon

clean:
	$(DEL) $(UFILES) uniclass.dir uniclass.pag *~
