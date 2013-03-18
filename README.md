Welcome to the Board Game Clue

For the next team who wil be working on this project

About Classes:

1: All classes with multiple Constructors call a function called "__init__" to ensure that all parameters are filled.
2: Each class that need extra functions for testing are below the comment "FOR TESTNG ONLY"
	- These functions should not be used for anything other than testing purposes
3: A description of each class can be found at the top of that class file 
4: Any section that is marked with a "TODO" is something that would be nice to implement but not required

About Configuration Files

1: Default files are hard coded in the defult construtor. This was done for testing purposes
2: All characters that denote a room ARE case sentsive. 
	- For example, A cell witht he character 'A' will not map to the same room witht he character 'a'
	- All walk ways must have a captial W
Flow of the program 

As of now there is NO main functon. Only unit tests. 
When a Board is declared it takes both config files as parameters.
If no prameters are given, then the default ones will be used.
The board is responcible for initialzing it self.
The board calls a class called InitializeBoard that produces all the attributes of the board.
Any errors caused by the InitializeBoard class is handled in the Board class.