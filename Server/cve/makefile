include makedefs

COMMON= commands.icn

COMMONU= commands.u

GIF=wall2.gif walltest.gif whiteboard.gif

CLIENT=unicron.icn session.icn world.icn cve.icn

OBJ=unicron.u session.u world.u cve.u

SVR=unicrond.icn user.icn

SOBJ=unicrond.u user.u

.SUFFIXES: .icn .u

.icn.u:
	$(UC) $(UFLAGS) $<

all: subdirs

subdirs:
	cd src/common && make
	cd src/model && make
	cd src/ide && make
	cd src/npc && make
	cd src/client && make
	cd src/server && make

makedefs:
	./configure

# source distribution zip
distro:	
	-$(DEL) uni-cve.zip
	-$(DEL) uni-cve
	mkdir uni-cve
	mkdir uni-cve/unicon
	mkdir uni-cve/cve
	cp makefile configure README.txt makedefs.in uni-cve/cve
	cp -r src uni-cve/cve 
	cp -r dat uni-cve/cve
	cp -r ../unicon/* uni-cve/unicon
	cp dest/make.sh dest/README.uni-cve uni-cve
	cp dest/build.sh uni-cve/unicon
	cd uni-cve/cve; make clean
	cd uni-cve/unicon;make Pure
	cp dest/utext.txt uni-cve/cve/dat/uidaho/textures
	cd uni-cve/cve/dat/uidaho/textures;cat utext.txt | xargs rm -rf
	cd uni-cve/cve/dat/uidaho/textures;ls > removal.txt
	cp -r dat/uidaho/textures/* uni-cve/cve/dat/uidaho/textures
	cd uni-cve/cve/dat/uidaho/textures;cat removal.txt | xargs rm -rf
	cp dest/ustext.txt uni-cve/cve/dat/uidaho/texsmall
	cd uni-cve/cve/dat/uidaho/texsmall;cat ustext.txt | xargs rm -rf
	cd uni-cve/cve/dat/uidaho/texsmall;ls > removal.txt
	cp -r dat/uidaho/texsmall/* uni-cve/cve/dat/uidaho/texsmall
	cd uni-cve/cve/dat/uidaho/texsmall;cat removal.txt | xargs rm -rf
	cp dest/ntext.txt uni-cve/cve/dat/nmsu/textures
	cd uni-cve/cve/dat/nmsu/textures;cat ntext.txt | xargs rm -rf
	cd uni-cve/cve/dat/nmsu/textures;ls > removal.txt
	cp -r dat/nmsu/textures/* uni-cve/cve/dat/nmsu/textures
	cd uni-cve/cve/dat/nmsu/textures;cat removal.txt | xargs rm -rf
	cp dest/nstext.txt uni-cve/cve/dat/nmsu/texsmall
	cd uni-cve/cve/dat/nmsu/texsmall;cat nstext.txt | xargs rm -rf
	cd uni-cve/cve/dat/nmsu/texsmall;ls > removal.txt
	cp -r dat/nmsu/texsmall/* uni-cve/cve/dat/nmsu/texsmall
	cd uni-cve/cve/dat/nmsu/texsmall;cat removal.txt | xargs rm -rf
	cd uni-cve;find * -type d -name CVS | xargs rm -rf
	cd uni-cve;find -name ".svn" | xargs rm -rf
	cd uni-cve/cve;rm -f *.log;cd dat/users;rm -r *
	cd dat/users;cp -r dog guest sid system tux ../../uni-cve/cve/dat/users
	rm uni-cve/cve/dat/images/cve_128.png
	mkdir uni-cve/cve/bin
	zip -9 -r uni-cve.zip uni-cve
	-$(DEL) uni-cve


zip:
	-$(DEL) cve.zip
	zip cve.zip  makefile configure README makedefs.in
	zip -r cve.zip src/*/*.icn src/*/makefile
	zip -r cve.zip src/server/service
	zip -r cve.zip dat
	-zip cve.zip  bin/plink.exe bin/nsh$(EXE)
	zip cve.zip bin/start-cved bin/stop-cved

unidoc:
	UniDoc src/*/icn src/*/*.icn

unibook: unidoc
	unibook

# binary distribution zip; keep in sync w/ cve.iss for Windows
binzip:
	-$(DEL) cve-bin.zip
	zip -r cve-bin bin/[abd-z]* cve bin/nsh.cmd iconx
	zip -r cve-bin dat/*.*
	zip -r cve-bin dat/3dmodels/*.*
	zip -r cve-bin dat/help/*.*
	zip -r cve-bin dat/users/tux/*.*
	zip -r cve-bin dat/users/tux/quests/*.*
	zip -r cve-bin dat/users/dog/*.*
	zip -r cve-bin dat/users/dog/quests/*.*
	zip -r cve-bin dat/users/sid/*.*
	zip -r cve-bin dat/users/sid/quests/*.*
	zip -r cve-bin dat/users/tausha/*.*
	zip -r cve-bin dat/users/guest/*.*
	zip -r cve-bin dat/users/cjeffery/*.*
	zip -r cve-bin dat/images/*.*
	zip -r cve-bin dat/images/letters/*.*
	zip -r cve-bin dat/textures/*.*
	zip -r cve-bin dat/nmsu/*.*
	zip -r cve-bin dat/nmsu/agents/echo/*.*
	zip -r cve-bin dat/nmsu/agents/gc/*.*
	zip -r cve-bin dat/nmsu/agents/secretary/*.*
	zip -r cve-bin dat/nmsu/edges/*.dat
	zip -r cve-bin dat/nmsu/images/*.*
	zip -r cve-bin dat/nmsu/nodes/*.dat
	zip -r cve-bin dat/nmsu/sounds/*.*
	zip -r cve-bin dat/nmsu/texsmall/*.*
	zip -r cve-bin dat/nmsu/textures/*.*
	zip -r cve-bin dat/nmsu/whiteboards/*.*
	zip -r cve-bin dat/uidaho/*.*
	zip -r cve-bin dat/uidaho/images/*.*
	zip -r cve-bin dat/uidaho/room/basement/*.*
	zip -r cve-bin dat/uidaho/room/corridor1/*.*
	zip -r cve-bin dat/uidaho/room/corridor2/*.*
	zip -r cve-bin dat/uidaho/room/corridor3/*.*
	zip -r cve-bin dat/uidaho/room/csac/*.*
	zip -r cve-bin dat/uidaho/room/elevator/*.*
	zip -r cve-bin dat/uidaho/room/ground/*.*
	zip -r cve-bin dat/uidaho/room/jeb013/*.*
	zip -r cve-bin dat/uidaho/room/jeb025/*.*
	zip -r cve-bin dat/uidaho/room/jeb104/*.*
	zip -r cve-bin dat/uidaho/room/jeb221/*.*
	zip -r cve-bin dat/uidaho/room/jeb222/*.*
	zip -r cve-bin dat/uidaho/room/jeb230/*.*
	zip -r cve-bin dat/uidaho/room/jeb236/*.*
	zip -r cve-bin dat/uidaho/room/jeb237/*.*
	zip -r cve-bin dat/uidaho/room/jeb328/*.*
	zip -r cve-bin dat/uidaho/room/jeb331/*.*
	zip -r cve-bin dat/uidaho/room/jebstair/*.*
	zip -r cve-bin dat/uidaho/room/jebstair/*.*
	zip -r cve-bin dat/uidaho/textures/*.*
	zip -r cve-bin dat/uidaho/users/guest/*.*

clean:
	cd src/common && make clean
	cd src/model && make clean
	cd src/npc && make clean
	cd src/client && make clean
	cd src/ide && make clean
	cd src/server && make clean
