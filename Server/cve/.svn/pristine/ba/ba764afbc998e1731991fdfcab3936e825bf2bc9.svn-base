include ../../../makedefs

# Copyright 1999-2004 Gentoo Technologies, Inc.
# Distributed under the terms of the GNU General Public License v2
# $Header: /home/jeffery/tmp/cve/cve/src/server/service/Makefile,v 1.1.1.1 2007-04-17 22:03:07 jeffery Exp $

LD = gcc

CFLAGS = -Wall -O2
DESTDIR =

SBIN_TARGETS = start-stop-daemon

TARGET = $(SBIN_TARGETS)

all: $(TARGET)

start-stop-daemon: start-stop-daemon.c
	$(CC) -o $@ $(CFLAGS) -DHAVE_ERROR_H -DHAVE_TIOCNOTTY -DHAVE_SETSID $^
	$(COPY) start-stop-daemon ../../../bin

clean:
	$(DEL) $(TARGET)
	$(DEL) *.o *~ core
