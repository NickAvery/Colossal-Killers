include ../../makedefs

UFILES= Obstacle.u Chair.u Table.u Box.u Room.u Opening.u Door.u ToolsUtilities.u Pen.u Book.u  Wall.u WhiteBoard.u Computer.u Printer.u  Windowblinds.u TiledWall.u CVEBuilder.u Actor.u Attach.u Ramp.u EchoActor.u GCActor.u MarkovActor.u ScheduleActor.u Elevator.u Object3D.u Portal.u HeightField.u

SRC= Obstacle.icn Chair.icn Table.icn Box.icn Room.icn Opening.icn Door.icn ToolsUtilities.icn Pen.icn Book.icn  Wall.icn Printer.icn  Computer.icn  WhiteBoard.icn Windowblinds.icn TiledWall.icn CVEBuilder.icn Actor.icn Attach.icn Ramp.icn EchoActor.icn GCActor.icn MarkovActor.icn ScheduleActor.icn Elevator.icn Object3D.icn Portal.icn HeightField.icn

all: $(UFILES)

Actor.u: Actor.icn
	$(UC) -c -u Actor

Elevator.u: Elevator.icn
	$(UC) -c -u Elevator

Attach.u: Attach.icn
	$(UC) -c -u Attach

Obstacle.u: Obstacle.icn
	$(UC) -c -u Obstacle

Object3D.u: Object3D.icn
	 $(UC) -c -u Object3D

Chair.u: Chair.icn Obstacle.u
	$(UC) -c -u Chair

Ramp.u: Ramp.icn Obstacle.u
	$(UC) -c -u Ramp

Table.u: Table.icn Obstacle.u
	$(UC) -c -u Table

Box.u: Box.icn
	$(UC) -c -u Box

Room.u: Room.icn Box.u
	$(UC) -c -u Room

Opening.u: Opening.icn
	$(UC) -c -u Opening

Door.u: Door.icn Opening.u
	$(UC) -c -u Door

Portal.u: Portal.icn Opening.u
	$(UC) -c -u Portal

ToolsUtilities.u: ToolsUtilities.icn
	$(UC) -c -u ToolsUtilities

Pen.u: Pen.icn ToolsUtilities.u
	$(UC) -c -u Pen

Book.u: Book.icn ToolsUtilities.u
	$(UC) -c -u Book 

Computer.u: Computer.icn Obstacle.u
	$(UC) -c -u Computer

Printer.u: Printer.icn Obstacle.u
	$(UC) -c -u Printer

Wall.u: Wall.icn
	$(UC) -c -u Wall

WhiteBoard.u: WhiteBoard.icn Wall.u
	$(UC) -c -u WhiteBoard

Windowblinds.u: Windowblinds.icn Wall.u
	$(UC) -c -u Windowblinds

TiledWall.u: TiledWall.icn
	$(UC) -c -u TiledWall

CVEBuilder.u: CVEBuilder.icn Actor.u
	$(UC) -c -u CVEBuilder


EchoActor.u: EchoActor.icn Actor.u
	$(UC) -c -u EchoActor

GCActor.u: GCActor.icn Actor.u
	$(UC) -c -u GCActor

MarkovActor.u: MarkovActor.icn Actor.u
	$(UC) -c -u MarkovActor

ScheduleActor.u: ScheduleActor.icn Actor.u
	$(UC) -c -u ScheduleActor

HeightField.u: HeightField.icn Obstacle.u
	$(UC) -c -u HeightField

pure:
	$(MAKE) clean
	$(MAKE) all

%.u:	%.icn
	 $(UC) $(UFLAGS) $<
clean:
	$(DEL) $(UFILES) uniclass.*
