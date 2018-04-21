include ../../makedefs

UFILES= vertex.u customnpc.u quest.u npc.u qWizarddlg.u \
	 qWizardContentdlg.u questbuilder.u

SRC= vertex.icn customnpc.icn quest.icn npc.icn qWizarddlg.icn \
	 qWizardContentdlg.icn questbuilder.icn

exes: vertex questbuilder

vertex: vertex.icn npc.u customnpc.u quest.u
	$(UC) -G vertex npc.u customnpc.u quest.u
	cp vertex ../../bin
	cp vertex ../server

questbuilder:  quest.u qWizarddlg.u qWizardContentdlg.u questbuilder.icn
	 $(UC) -G questbuilder quest.u qWizardContentdlg.u qWizarddlg.u

qWizarddlg.u: qWizarddlg.icn quest.u
	$(UC) $(UFLAGS) qWizarddlg 

qWizardContentdlg.u: qWizardContentdlg.icn quest.u
	$(UC) $(UFLAGS) qWizardContentdlg

quest.u: quest.icn
	$(UC) $(UFLAGS) quest

npc.u: npc.icn quest.u
	$(UC) $(UFLAGS) npc quest.u

customnpc.u: customnpc.icn npc.u
	$(UC) $(UFLAGS) customnpc npc.u

clean:
	$(DEL) $(UFILES) uniclass.dir uniclass.pag
