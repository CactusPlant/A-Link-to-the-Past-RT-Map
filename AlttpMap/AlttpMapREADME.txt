This was a project created to assist Speedrunners of "The Legend of Zelda: A link to the past" in finding
glitches. 

The application talks to the included LUA script while the game is running on the SNES9X emulator and 
reads specific memory addresses that it then uses Sockets to send to the SnesFramework and then the 
data in this application reads those values, and uses them to determine what map, the sections of the map,
and link's x/y position then draws them onto a canvas.

The reasoning behind this applications, is there are times while performing the Exploration Glitch(Basically
walking through dungeon walls) and overworld screen scrolling ended with situations where what appears
in games is not where the game is calculating you are in memory. This application gave a real time view
of where you were in memory, and allowed navigation through what was basically blindness.
