kf2monitor
==========

kf2monitor is an lcdproc client. It obtains stats like current map, wave number and the top players from a running Killing Floor 2 Dedicated Server via its WebAdmin interface, and displays them on an LCD.

Hardware
--------

You need some kind of character display connected to an lcdproc somewhere this application can access it. It doesn't have to be on the same computer as long as there is a path for TCP traffic from kf2monitor to lcdproc.

I use a HD44780 compatible 20x4 alphanumeric display connected to a [C.H.I.P.][chip]. A Raspberry Pi or similar will probably also work. How to connect the hardware and set up lcdproc is outside the scope of this README.

[chip]: https://getchip.com/

Pictures
----------

There are three screens. Screen 1 shows general information about the server (name, map, difficulty).

![Game info screen](doc/screen1.jpg)

If there are no players on the server, the bottom line of screen 1 reads "No players" and screens 2 and 3 are hidden.

Screen 2 shows the current wave and number of connected players and spectators.

![Game status screen](doc/screen2.jpg)

For those who haven't played Killing Floor, wave 8/7 is not an error; it is the boss wave.

Screen 3 shows the top 3 players (by kill count) and their perks.

![Players screen](doc/screen3.jpg)

Building
--------

kf2monitor is a Maven project. To build and JAR' it you do the standard procedure of `mvn compile` and `mvn package`. There is a prerequisite however.

### Prerequisite: Building lcdjava

kf2monitor uses [lcdjava][] as its lcdproc client library. lcdjava hasn't yet been published to a public Maven repository so you get to build it yourself and install it to your local Maven repository before being able to build kf2monitor. Don't worry, this isn't as difficult as it may sound. Simply clone [lcdjava's source code][lcdjava], `cd` to its working directory and run `mvn package` to build and package it into *target/lcdjava-1.0-SNAPSHOT.jar*. Now run

    $ mvn install:install-file -Dfile=target/lcdjava-1.0-SNAPSHOT.jar

to install the JAR into your local repository (in *$HOME/.m2/*). You're done!

If you do this after you already imported the project into your IDE from the Maven POM you may have to nuke the project and re-import it to get your IDE to see the lcdjava classes.

### Building kf2monitor

You should now be able to build kf2monitor by issuing a `mvn compile` in its checked out source directory. Unless I've broken the API of lcdjava of course, since it doesn't have a stable release yet.

[lcdjava]: https://github.com/boncey/lcdjava/

Rationale
---------

I'm also the semi-official maintainer of lcdjava. While working on it I needed a serious (ahem) project that uses it to see what kind of API and packaging makes sense. So I came up with this. Besides, once I thought of it I couldn't resist adding more bling to my Killing Floor 2 server, [Kitten Gaming](http://www.antoneliasson.se/kitten-gaming).

Known bugs
----------

There is basically no error checking. The program will explode spectacularly if anything should go wrong at any time.

In particular:

 - While the KF2 server changes map it refuses (in TCP terminology) connections to the WebAdmin interface. kf2monitor does not handle this.
