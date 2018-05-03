#
# makefile for the CVE client
#

# Inherit common definitions. Mainly platform specific command names.
include ../../makedefs

#
# Object file macros. Used mainly in gigantic link commands.
#

#
# UFILES are the .u files for the current directory (i.e. the client)
#
UFILES= nsh.u nsh-world.u avatar.u session.u voip.u \
	camera.u nshdlg.u texture.u pop3Ddlg.u \
	cSceneGr.u dispatch.u subwin3d.u subwin2d.u \
	CourseTabItem.u Moodle.u dehtml.u  menubar.u \
	version.u mapimage.u penguin.u dog.u \
	questdlg.u  questinvitationdlg.u datopen.u \
	../common/groups.u ../common/pendingsdlg.u ../common/forwarddlg.u \
        projects.u newsfeed.u account.u \
        profile.u
#
# SHARED are the .u files shared between client and server
#
SHARED=	../common/SceneGraph.u ../common/utilities.u ../common/services.u ../common/fileTransfer.u \
	../common/logger.u ../common/commands.u ../common/portHandler.u ../common/cmdhelp.u ../common/groups.u \
	../common/pendingsdlg.u ../common/forwarddlg.u

#
# IDE are the .u files that make up the CVE IDE
#
IDE=    ../ide/buffertabset.u ../ide/buffertabitem.u ../ide/classbrowser.u \
	../ide/cetl.u ../ide/projeditor.u ../ide/templates.u \
	../ide/cproject.u ../ide/jproject.u ../ide/uproject.u \
	../ide/hfiledialog.u ../ide/definitions.u ../ide/ide.u ../ide/imgs.u \
	../ide/utags.u ../ide/shelletl.u \
	../ide/configoptions.u ../ide/matchparen.u

#
# MODEL are the .u files that make up the CVE virtual spaces and objects
#
MODEL= 	../model/Opening.u ../model/Chair.u ../model/Table.u \
	../model/Room.u ../model/Computer.u ../model/Printer.u \
	../model/Pen.u ../model/Door.u ../model/Book.u ../model/Box.u \
	../model/Obstacle.u ../model/Table.u ../model/Wall.u \
	../model/TiledWall.u ../model/Windowblinds.u  ../model/WhiteBoard.u \
	../model/ToolsUtilities.u ../model/CVEBuilder.u ../model/Ramp.u \
	../model/Actor.u ../model/Elevator.u ../model/Object3D.u \
	../model/Attach.u ../model/Portal.u ../model/HeightField.u


SRC= nsh.icn nsh-world.icn avatar.icn session.icn voip.icn camera.icn nshdlg.icn \
	texture.icn pop3Ddlg.icn \
	cSceneGr.icn dispatch.icn subwin3d.icn subwin2d.icn ../SceneGraph.icn \
	cmdhelp.icn ../utilities.icn CourseTabItem.icn Moodle.icn dehtml.icn \
	menubar.icn mapimage.icn ../services.icn penguin.icn dog.icn \
	CourseTabItem.icn questdlg.icn questinvitationdlg.icn ../common/groups.icn \
	../common/pendingsdlg.icn ../common/forwarddlg.icn projects.icn newsfeed.icn profile.icn

#
# List of targets to build, starting with library directories.
#
exes: shared model npc ide nsh.cmd cve scriptandpause

$(SHARED): shared

#
# Directory targets. Recursively call make in the other directories.
#
shared:
	cd ../common && make

model:
	cd ../model && make

ide: 
	cd ../ide && make

npc: 
	cd ../npc && make quest.u

#
# Executable targets, built by linking .u files.
#
nsh.cmd: $(UFILES) $(MODEL) $(IDE) $(SHARED)
	$(UC) -Z -G -o nsh.cmd  $(UFILES) $(MODEL) $(IDE) $(SHARED) ../npc/quest.u
	$(COPY) nsh.cmd ../../bin

cve: cve.u logindlg.u amaker.u ../common/commands.u ../common/utilities.u ../common/logger.u ../common/fileTransfer.u ../common/portHandler.u session.u Moodle.u CourseTabItem.u version.u datopen.u
	$(UC) -G cve.u logindlg.u amaker.u ../common/commands.u ../common/utilities.u ../common/logger.u ../common/fileTransfer.u ../common/portHandler.u session.u Moodle.u CourseTabItem.u version.u datopen.u
	-$(COPY) cve$(EXE) ../../bin

scriptandpause: scriptandpause.icn
	$(UC) scriptandpause
	-$(COPY) scriptandpause$(EXE) ../../bin

#
# Source file targets. Normally a compile with a -c -u option.
# Extra dependencies only when $include, import, or inherit.
# Packages may have internal ordering requirements.
#

datopen.u: datopen.icn
	$(UC) -c -u datopen

mapimage.u: mapimage.icn
	$(UC) -c -u mapimage

# dependencies need re-examining
Moodle.u: nshdlg.icn Moodle.icn
	$(UC) -c -u Moodle

# dependencies need re-examining
CourseTabItem.u: nshdlg.icn CourseTabItem.icn
	$(UC) -c -u CourseTabItem

cve.u: cve.icn
	$(UC) -c -u cve ../common/utilities.u

voip.u: voip.icn
	$(UC) -c -u voip.icn

camera.u: camera.icn
	$(UC) -c -u camera.icn

# dependencies need re-examining
nshdlg.u: nshdlg.icn Moodle.icn CourseTabItem.icn questinvitationdlg.u \
	questdlg.u projects.u  newsfeed.u profile.u camera.u
	$(UC) -c -u -DNSH nshdlg

menubar.u: menubar.icn 
	$(UC) -c -u menubar

# dependencies need re-examining
dispatch.u: dispatch.icn questinvitationdlg.u questdlg.u ../npc/quest.u pop3Ddlg.u
	$(UC) -c -u dispatch

pop3Ddlg.u: pop3Ddlg.icn
	 $(UC) -c -u pop3Ddlg

subwin3d.u: subwin3d.icn
	$(UC) -c -u subwin3d

subwin2d.u: subwin2d.icn
	$(UC) -c -u subwin2d

texture.u: texture.icn
	$(UC) -c -u texture

logindlg.u: logindlg.icn
	$(UC) -c -u logindlg

session.u: session.icn
	$(UC) -c -u session

avatar.u: avatar.icn
	$(UC) -c -u avatar

questinvitationdlg.u: questinvitationdlg.icn
	$(UC) -c -u questinvitationdlg

questdlg.u: questdlg.icn
	$(UC) -c -u questdlg

account.u: account.icn
	$(UC) -c -u account

# $include's ../defaults
nsh-world.u: nsh-world.icn ../common/defaults.icn 
	$(UC) -c -u nsh-world

nsh.u: nsh.icn
	$(UC) -c -u nsh

dehtml.u: dehtml.icn
	$(UC) -c -u dehtml

# client Scene Graph inherits from ../SceneGraph
cSceneGr.u: cSceneGr.icn ../common/SceneGraph.u
	IPATH=../common $(UC) -c -u cSceneGr

dog.u: dog.icn 
	$(UC) -c -u dog

penguin.u: penguin.icn 
	$(UC) -c -u penguin

projects.u: projects.icn
	$(UC) -c -u projects

profile.u: profile.icn
	$(UC) -c -u profile

newsfeed.u: newsfeed.icn 
	$(UC) -c -u newsfeed

amaker.u: amaker.icn
	unicon -c amaker

version.u: ../common/version.icn
	$(UC) -c -u ../common/version

#
# Utility targets.
#

zip:
	zip cve-client.zip $(SRC) makefile cve.icn

clean:
	$(DEL) $(UFILES) nsh.cmd uniclass.dir uniclass.pag ../../bin/nsh.cmd ../uniclass.dir ../uniclass.pag cve cve.u logindlg.u amaker.u portHandler.u Moodle.u CourseTabItem.u  menubar.u *~
	cd ../model && make clean

#amaker.cmd:
#	$(UC) -o amaker.cmd amaker.u
#	$(COPY) amaker.cmd ../../bin
