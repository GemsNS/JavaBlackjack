# Project proposal - Joel

**What I want to make**

A small Java program that runs in the terminal. You do a short pre-flight checklist, then fly toward the airport in turns. Each turn you pick **cruise** (forward), **ascend**, or **descend**. Fuel, distance, and a simple fake **altitude** are printed. There is also a small **random chance** each turn that something goes wrong and you crash. All feedback is plain text to the console.

**Why I like this idea**

I love aviation (planes, airports, all of it), so doing a project where you work through a checklist and “fly” toward the airport keeps me interested. Even as a text program it still feels connected to something I care about instead of feeling like boring code.

**What the user will actually do**

- Open the program and see a main menu (play, help, quit).
- If they choose play: run a two-step checklist and type `ok` after each line.
- Each flying turn: see fuel, distance, and altitude, then type **1** (cruise), **2** (ascend), or **3** (descend). Bad input still costs a little fuel.
- After each move, the program may randomly declare a **crash** (about a 1-in-10 chance) and end the mission.
- **Win:** distance reaches 0 with fuel left and no crash. **Lose:** out of fuel, checklist failed, random crash, or distance never finished before fuel dies.

**Concepts used in the main program (`App.java`)**

These match what I actually coded:

- **Input / output:** `Scanner` reads lines from the keyboard; `System.out.println` and `print` show the menu, status, and messages.
- **Variables:** `fuel`, `distanceKm`, and `altitude` as shared `static` fields; a local `boolean` in `playMission` tracks a random crash.
- **Control structures:** `if` / `else if` / `else` for the main menu, for picking cruise vs ascend vs descend vs bad input, and for the ending (crash vs win vs other lose). **do-while** for the main menu. **while** for flying until distance, fuel, or crash stops the loop. **for-each** over an `ArrayList` for the checklist.
- **Methods:** Besides `main`, three methods: `playMission`, `doChecklist`, and `readIntSafe`.
- **Collections:** `ArrayList` and `List` store checklist strings; `add()` and a loop walk through them.
- **Random:** `java.util.Random` rolls once per turn for the possible crash after a move.
- **Other:** `Math.max` keeps altitude from going below zero when descending.
- **Error handling:** `try` / `catch` in `readIntSafe` when parsing an `int`, so bad input does not crash the program.


**What I might still tweak**

Fuel, distance, and the random crash chance so the game feels fair after more testing.

Thanks for reading.
