include ../../../makedefs

all: uniproxy

uniproxy: uniproxy.c
	$(CC) $(CFLAGS) uniproxy.c -o uniproxy$(EXE)
	$(COPY) uniproxy$(EXE) ../../../bin

clean:
	$(DEL) uniproxy a.out