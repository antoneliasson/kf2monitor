kf2monitor
==========

kf2monitor is an lcdproc client. It obtains stats like current map, wave number and the top players from a running Killing Floor 2 Dedicated Server via its WebAdmin interface, and displays them on an LCD.

Screenshot
----------

Coming soon!

Building
--------

kf2monitor is a Maven project. To build and JAR' it you do the standard procedure of `mvn compile` and `mvn package`. There is a prerequisite however.

### Prerequisite: Building lcdjava

kf2monitor uses [lcdjava][] as its lcdproc client library. lcdjava hasn't yet been published to a public Maven repository so you get to build it yourself and install it to your local Maven repository before being able to build kf2monitor. Don't worry, this isn't as difficult as it may sound. Simply clone [lcdjava's source code][lcdjava], `cd` to its working directory and run `mvn package` to build and package it into *target/lcdjava-1.0-SNAPSHOT.jar*. Now run

    $ mvn install:install-file -Dfile=target/lcdjava-1.0-SNAPSHOT.jar

to install the JAR into your local repository (in *$HOME/.m2/*). You're done!

### Building kf2monitor

You should now be able to build kf2monitor by issuing a `mvn compile` in its checked out source directory.

[lcdjava]: https://github.com/boncey/lcdjava/

Rationale
---------

I'm also the semi-official maintainer of lcdjava. While working on it I needed a serious (ahem) project that uses it to see what kind of API and packaging makes sense. So I came up with this. Besides, once I thought of it I couldn't resist adding more bling to my Killing Floor 2 server, [Kitten Gaming](http://www.antoneliasson.se/kitten-gaming).
