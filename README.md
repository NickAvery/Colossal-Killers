# Colossal Killers
This is the repository of the semester project for CS428 at University of Idaho

The premise is: save "the Village" from the (progressively larger) dino attacks (from class notes)

## Developers:
- Paul Martin
- Nick Avery
- James Young
- Tim Sonnen
- Joe Carter
- Kevin Dorscher
- Jackson Taylor
- Hunter Barnett

## Requirements

**Client**

- [Java](java.org) JDK 7 or 8

**Server**

- [Unicon](unicon.org)
- make


## Building

**Client (Windows)**

```
$ cd Client
$ gradlew.bat desktop:build
```

**Client (Linux/Mac OSX)**

```
$ cd Client
$ ./gradlew desktop:build
```

**Server**

```
$ cd Server/cve
$ make
```

## Running

**Client (Windows)**

```
$ cd Client
$ gradlew.bat desktop:run
```

**Client (Linux/Mac OSX)**

```
$ cd Client
$ ./gradlew desktop:run
```

**Server (Windows)**

```
$ cd Server/cve/bin
$ cved.exe -m "Colossal-Killers"
```

**Server (Linux/Mac OSX)**

```
$ cd Server/cve/bin
$ ./cved -m "Colossal-Killers"
```
