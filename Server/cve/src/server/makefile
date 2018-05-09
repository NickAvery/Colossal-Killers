include ../../makedefs

# makefile for cved, the CVE Server
SRC=    server.icn group.icn user.icn dino.icn general.icn \
	StateHandler.icn  \
	sessionmc.icn sessionConn.icn cved.icn sSceneGr.icn \
	services.icn \
	AgentHandler.icn

UFILES= server.u group.u user.u dino.u general.u \
	StateHandler.u \
	sessionmc.u sessionConn.u cved.u sSceneGr.u \
	AgentHandler.u

SHARED=	../common/SceneGraph.u ../common/utilities.u ../common/services.u ../common/fileTransfer.u ../common/logger.u ../common/commands.u ../common/portHandler.u ../common/version.u ../common/cmdhelp.u

MODEL= 	../model/Opening.u ../model/Chair.u ../model/Table.u \
	../model/Room.u ../model/Computer.u ../model/Printer.u \
	../model/Pen.u ../model/Door.u ../model/Book.u ../model/Box.u \
	../model/Obstacle.u ../model/Table.u ../model/Wall.u \
	../model/TiledWall.u ../model/Windowblinds.u  ../model/WhiteBoard.u \
	../model/ToolsUtilities.u ../model/CVEBuilder.u ../model/Ramp.u \
	../model/EchoActor.u ../model/GCActor.u ../model/Elevator.u \
	../model/MarkovActor.u ../model/ScheduleActor.u ../model/Portal.u \
	../model/HeightField.u

NPC= 	../npc/npc.u ../npc/customnpc.u ../npc/quest.u


.SUFFIXES: .icn .u

.icn.u:
	$(UC) $(UFLAGS) $<

all: account_admin create_db cved watchdog 

$(SHARED): shared

shared:
	cd ../common && make SceneGraph.u utilities.u services.u fileTransfer.u logger.u commands.u portHandler.u version.u cmdhelp.u

model: 
	cd ../model && make Opening.u Chair.u Table.u Room.u Computer.u Printer.u Pen.u Door.u Book.u Box.u Obstacle.u Table.u Wall.u TiledWall.u Windowblinds.u WhiteBoard.u ToolsUtilities.u CVEBuilder.u Ramp.u EchoActor.u GCActor.u Elevator.u MarkovActor.u ScheduleActor.u Portal.u HeightField.u

npc: 
	cd ../npc && make

account_admin: shared account_admin.icn 
	$(UC) account_admin.icn ../common/commands.u
	$(COPY) account_admin$(EXE) ../../bin

watchdog: shared watchdog.icn
	$(UC) watchdog.icn ../common/commands.u ../common/logger.u ../common/utilities.u
	$(COPY) watchdog$(EXE) ../../bin

create_db: create_db.icn general.u
	$(UC) -G create_db.icn general.u

cved: shared model $(UFILES) $(MODEL) $(NPC)
	$(UC) -o cved $(UFILES) $(MODEL) $(SHARED) $(NPC)
	$(COPY) cved$(EXE) ../../bin
#	cd service && make

user.u: user.icn
	$(UC) -c -u user

sessionConn.u: sessionConn.icn
	$(UC) -c -u sessionConn

group.u: group.icn
	$(UC) -c -u group

general.u: general.icn
	$(UC) -c -u general

server.u: server.icn
	$(UC) -c -u server

cved.u: cved.icn
	$(UC) -c -u cved

StateHandler.u: StateHandler.icn
	$(UC) -c -u StateHandler

sSceneGr.u: sSceneGr.icn ../common/SceneGraph.u
	IPATH=.. $(UC) -c -u sSceneGr

../SceneGraph.u: ../common/SceneGraph.icn
	cd .. && make

AgentHandler.u: AgentHandler.icn
	$(UC) -c -u AgentHandler

../npc/customnpc.u: ../npc/customnpc.icn
	cd ../npc && make

zip: $(COMMON) $(SVR) makefile
	zip cved.zip $(COMMON) $(SVR) makefile

clean: 
	$(DEL) $(UFILES) cved watchdog create_db account_admin uniclass.dir uniclass.pag ../../bin/cved ../uniclass.dir ../uniclass.pag tmp.log
	cd service && make clean
	cd ../model && make clean
	cd ../npc && make clean	
