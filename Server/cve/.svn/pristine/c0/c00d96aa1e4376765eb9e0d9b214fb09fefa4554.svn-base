#
# CVE src makefile
#

include ../../makedefs

SRCU= utilities.u SceneGraph.u services.u fileTransfer.u logger.u \
	commands.u portHandler.u cmdhelp.u version.u groups.u pendingsdlg.u \
	forwarddlg.u defaults.u

all: $(SRCU)

defaults.u: defaults.icn
	$(UC) -c -u defaults

utilities.u: utilities.icn
	$(UC) -c -u utilities

SceneGraph.u: SceneGraph.icn defaults.icn
	$(UC) -c -u SceneGraph

services.u: services.icn
	$(UC) -c -u services

fileTransfer.u: fileTransfer.icn
	$(UC) -c -u fileTransfer

logger.u: logger.icn
	$(UC) -c -u logger

commands.u: commands.icn
	$(UC) -c -u commands

cmdhelp.u: cmdhelp.icn
	$(UC) -c -u cmdhelp

portHandler.u: portHandler.icn
	$(UC) -c -u portHandler

version.u: version.icn
	$(UC) -c -u version

groups.u: groups.icn
	$(UC) -c -u groups

pendingsdlg.u: pendingsdlg.icn
	$(UC) -c -u pendingsdlg

forwarddlg.u: forwarddlg.icn
	$(UC) -c -u forwarddlg

clean:
	$(DEL) $(SRCU) uniclass.* *~
