#!/bin/sh
./start-stop-daemon -b --start --quiet --exec vertex -m --pidfile vertex.pid -- -user vertex 
./start-stop-daemon -b --start --quiet --exec vertex -m --pidfile tausha.pid -- -user tausha 
./start-stop-daemon -b --start --quiet --exec vertex -m --pidfile sid.pid -- -user sid 
./start-stop-daemon -b --start --quiet --exec vertex -m --pidfile dog.pid -- -user dog 
./start-stop-daemon -b --start --quiet --exec vertex -m --pidfile tux.pid -- -user tux -move yes
