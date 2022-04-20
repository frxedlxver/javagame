# DEVELOP BRANCH

# README

./source contains uncompiled source code    
  
./compiled contains executable jar file.    
  
To play game: open command prompt in ./compiled and type java -jar javagame.jar    
  
---  
  
# Notes:  
  
## Current known issues:  
	- Save/load needs bugfixing. Not working at present.  
	- combat, inventory management, and item use not fully implemented.  
		- item use technically implemented. The methods are all there. Same for managing equipment.  
		- both will be possible once an inventory management / access interface is implemented.  
	- NPC interactions implemented in code, but no content yet.  
	- Some minor formatting issues with printing  
	- No option to change text speed.  
  
### The state of the game and engine:  
	- World/room architecture fully implemented, including:  
		- a Map class to manage room loading/writing  
			- Starting a new game loads a new map from the data in "resources/rooms.csv"  
		- Room navigation  
		- Interaction with items in room  
		- Interaction with NPCs in room  
	- Easy to add new content:   
		- No code is required when adding or modifying items, rooms, or characters. Simply edit the csv 
		to add content.  
		- Each entry in csv is structured according to header lines  
		- Items, characters, and rooms are read from csv files and processed at runtime  
		- Room descriptions are read from txts at runtime.  
			- If you create a new room, you can write its description in a corresponding [roomid].txt
			  file in "./out/resources/room_data/"  
	- my_utils.TimedPrint class handles printing.  
		- Special characters ('.', ',', '_', '\n') trigger a longer sleep time than normal characters, 
		  adding to the sense that terminal output is being written or spoken.  
		- This can be easily changed  
