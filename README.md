# Project - ECI Shoot'em Up

Alien Swarm style game, with steampunk atmosphere. Using Java + libGDX.

# How to setup your environment

1) Install JDK 7 or 8 (openJDK might be fine, but Oracle JDK is preferred)

2) Install (unzip) Eclipse

3) Within Eclipse: go to Help Menu -> Eclipse Marketplace -> search for "gradle" -> Install "Gradle IDE Pack"

4) Import -> Gradle -> Gradle Project -> select cloned dir -> hit "Build model" -> Import the projects that show up


You're all set. If you're fast, this can be done in less than 5 minutes - trust me.

# Running the project

Just open the Game-desktop project. Go to src -> bolado.desktop -> Right-click DesktopLaucher.java -> Run as -> Java Application. And that's all.

Note: The game code itself is in the Game-core project. This looks weird because right now we only have a "desktop" project. But as soon as we add an "android" project, "ios" project, or "html5" project, the advantage of this structure becomes evident.

# Eclipse tip

In Window -> Preferences:

1) Add a shortkey to "Run Build"

2) In Run/Debug -> Launching -> Launch Operation, select the first radio box ("Always launch the previously launched application")

3) Now you don't have to "Right-click DesktopLaucher.java, etc" anymore. As long as that was your last launch - just use your "Run Build" hotkey from whatever project or file you are working on. 

# Exporting from Blender file

To export from the Blender file provided in the root dir, simply press "p" to start the game engine. Aaaand you're done. 

Note: No need to select objects or do anything special - just hit "p" and "esc" right away.  By now, the updated .bullet and .g3db files are already in the assets folder. No need to refresh or anything like that. Hit run on Eclipse and see the updates.
If you're on linux, you'll have to add fbx-conv/ to your LD_LIBRARY_PATH. 
