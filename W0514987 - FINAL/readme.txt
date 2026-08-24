Text Flight Mission
A simple console-based flight game where you complete a pre-flight checklist, manage your fuel, and navigate your altitude and distance to reach the airport safely. Watch out for random system failures!

Prerequisites
To compile and run this game, you will need to have the Java Development Kit (JDK) installed on your computer. You can verify this by opening your terminal or command prompt and typing:


java -version


How to Run

Save the file: Ensure the Java code is saved in a file named App.java.

Open your terminal: Navigate to the directory where you saved App.java.

Compile the code: Run the following command to compile the Java file into bytecode:


javac App.java


Start the game: Once compiled, run the game using this command:


java App


How to Play

Main Menu: When the game starts, choose 1 to play, 2 for help, or 3 to quit.

Pre-Flight Checklist: Before taking off, you must type ok for each step of the checklist. If you type anything else or make a typo, the mission fails immediately.

In-Flight: On each turn, you will see your current fuel, distance remaining, and altitude. Choose an action:

1 Cruise: Moves you forward efficiently but burns standard fuel.

2 Ascend: Increases altitude but burns more fuel and covers less forward distance.

3 Descend: Decreases altitude and glides forward, saving a bit of fuel.

Keep a close eye on your fuel and distance. Reaching 0 distance with fuel remaining means you've landed safely. Running out of fuel, failing the checklist, or getting hit by bad luck will result in a crash!