/*
 * ============================================================
 * PROJECT TITLE: Text Flight Mission (Console) - full version
 * STUDENT NAME: Joel
 * DATE: 04/04/2026
 * DESCRIPTION: Terminal flight game. Preflight checklist, fly toward the airport with
 * menu choices, random turbulence can drain fuel, then land or go around. All text.
 * ============================================================
 *
 * ASSIGNMENT REQUIREMENTS CHECKLIST:
 * - Input/Output: Scanner for input; System.out (println/printf) for console output.
 * - Control structures: if / else if / else (landingPart). Loops: do-while (main menu),
 *   for-each in doPreflight (for loop over collection), while in flyLoop, while in landingPart.
 * - Methods: 7 custom methods besides main (need 3+): playOneMission, resetState, doPreflight,
 *   flyLoop, landingPart, showHelp, readIntSafe.
 * - ArrayList: checklist strings stored in ArrayList<String> in doPreflight.
 * - Error handling: try-catch in readIntSafe for invalid number input.
 *
 * Comments in code: each method purpose; control structure logic; how ArrayList is used.
 * ============================================================
 *
 * NOTE: this is the older longer version. the small rubric-only build is App.java
 * run: java v2   (from folder that has v2.class on classpath, e.g. java -cp src v2)
 * ============================================================
 */

// these are imports so java already has the code i dont gotta write it myself
import java.util.ArrayList; // list that grows when u add stuff (my checklist) = assignment collection
import java.util.List; // i put List in the variable type bc thats what mr said
import java.util.Random; // random numbers for turbulence
import java.util.Scanner; // reads keyboard = assignment Scanner input

// has to be called v2.java same as the class name or it gets mad
// static = theres only one copy shared around i dont fully get objects yet lol
public class v2 {

    /*
     * these variables are up here so all my methods see them
     * private = only this file uses them i think
     * static = same fuel everywhere not like seperate copies
     * scope = where the name works (i looked that up)
     */
    private static int fuel; // fuel left goes down when you fly
    private static int altitude; // how high fake numbers not realistic
    private static int speed; // how fast also fake (real life uses knots for speed)
    private static int distanceKm; // km till airport when its 0 your there
    private static boolean crashed; // true = game over for this run
    private static int turnCount; // counts turns goes up each time thru the loop

    // Scanner reads keyboard System.in is the input stream
    // final means dont reassign it later (i just copied that pattern)
    private static final Scanner input = new Scanner(System.in);
    // rng is for nextInt when i want random turbulence
    private static final Random rng = new Random();

    // java always starts at main, args is command line stuff were not using it
    public static void main(String[] args) {
        // println prints text then goes to next line (console output)
        System.out.println("=== text flight (terminal) - full game ===");

        int pick; // what they typed for the menu 1 2 3 or -1 if broken

        /*
         * do while = runs the block first THEN checks the condition at the bottom
         * so menu shows atleast once even the first time
         * keeps going till pick is 3
         */
        do {
            System.out.println("\n1 start mission  2 help  3 quit"); // \n is newline
            System.out.print("pick: "); // print stays on same line so you type next to it
            pick = readIntSafe(); // Scanner input wrapped in readIntSafe (try-catch inside)

            /*
             * switch is like a bunch of ifs but cleaner for menu numbers
             * break = get out of switch so you dont fall thru (i messed that up once)
             * default = anything else like wrong number or -1
             */
            switch (pick) {
                case 1:
                    playOneMission(); // whole mission
                    break;
                case 2:
                    showHelp(); // just help text
                    break;
                case 3:
                    System.out.println("bye");
                    break;
                default:
                    System.out.println("type 1 2 or 3");
            }
        } while (pick != 3); // != means not equal keep looping unless quit

        input.close(); // supposed to close scanner when your done idk if always needed
    }

    /*
     * playOneMission just calls stuff in order
     * if crashed after preflight we bail out early
     * if crashed after flying we skip landing
     */
    private static void playOneMission() {
        resetState(); // fresh start numbers
        doPreflight(); // might set crashed
        if (crashed) {
            return; // stops this method goes back to menu
        }
        flyLoop(); // main flying
        if (!crashed) {
            // ! means not so if NOT crashed then land
            landingPart();
        }
    }

    // resetState sets everything back how i want it at the begining of a mission
    private static void resetState() {
        fuel = 100; // start full
        altitude = 0; // on ground till after checklist
        speed = 0; // not moving yet
        distanceKm = 150; // far away
        crashed = false; // not dead yet
        turnCount = 0; // no turns yet
    }

    /*
     * doPreflight makes a list of strings for the checklist
     * ArrayList = assignment requirement, it holds the checklist lines and i add() them
     * for-each loop is easier than counting i for me (step is just 1 2 3 for display)
     */
    private static void doPreflight() {
        List<String> checklist = new ArrayList<>(); // collection stores all the step text
        checklist.add("check fuel gauge"); // add puts it on the end of the list
        checklist.add("check engines");
        checklist.add("release brake");

        System.out.println("\n-- preflight --");

        /*
         * stackoverflow i used this for array index / loop bound stuff
         * i kept getting confused if i should use <= or < with length
         * for-each skips indexes but same idea if i used a normal for with i
         * https://stackoverflow.com/questions/5554734/what-causes-a-java-lang-arrayindexoutofboundsexception-and-how-do-i-prevent-it
         */
        int step = 1;
        for (String item : checklist) {
            System.out.println(step + ") " + item); // + glues text together
            System.out.print("type ok: ");
            String line = input.nextLine().trim().toLowerCase();
            // nextLine reads line trim spaces lowercase so OK works

            if (!line.equals("ok")) { // if its not ok then fail
                System.out.println("checklist failed. abort.");
                crashed = true;
                return; // leave method skip takeoff below
            }
            step++;
        }

        // if we get here checklist was all ok
        altitude = 500; // pretend takeoff
        speed = 420; // some speed
        System.out.println("wheels up\n");
    }

    /*
     * flyLoop is the main game while loop
     * while keeps repeating while the condition is true
     * && means AND everything has to be true still
     * !crashed means crashed is false
     */
    private static void flyLoop() {
        System.out.println("-- flying --");

        while (distanceKm > 0 && fuel > 0 && !crashed) {
            turnCount++; // ++ means add 1 shorthand

            /*
             * printf is formated printing
             * %d is int %.0f is double with no decimals %n newline
             * (double) fuel is casting int to double for the assignment / printf
             */
            System.out.printf(
                    "turn %d | fuel ~%.0f | alt %d | speed %d | dist %d km%n",
                    turnCount, (double) fuel, altitude, speed, distanceKm);

            System.out.println("1 cruise  2 climb  3 descend");
            System.out.print("action: ");
            int action = readIntSafe();

            // switch = control structure: match action 1, 2, 3 or default
            switch (action) {
                case 1: // cruise cheap on fuel moves distance
                    fuel -= 5;
                    distanceKm -= 25;
                    speed = Math.max(250, speed - 5); // dont go below 250 here
                    // && both gotta be true for warning
                    if (fuel < 30 && distanceKm > 50) {
                        System.out.println("warning: fuel getting low");
                    }
                    break;
                case 2: // climb uses more fuel go up
                    fuel -= 12;
                    altitude += 200;
                    distanceKm -= 20;
                    speed += 20;
                    break;
                case 3: // descend
                    fuel -= 8;
                    altitude = Math.max(200, altitude - 150);
                    distanceKm -= 22;
                    speed -= 15;
                    break;
                default: // typo number or whatever small punishment
                    System.out.println("bad number, you lose a little fuel anyway");
                    fuel -= 3;
            }

            // nextInt(10) is 0 thru 9
            int roll = rng.nextInt(10);
            if (roll < 3) { // kinda like 30% chance
                int bump = 1 + rng.nextInt(4); // 1 to 4 i think
                fuel -= bump;
                System.out.println("turbulence... -" + bump + " fuel");
            }

            // no gas = you loose (i know its lose but i always spell it wrong)
            if (fuel <= 0) {
                System.out.println("no fuel. you lose.");
                crashed = true;
            }
        }

        // if we stopped bc we got to the airport and didnt crash, say so
        // (if fuel died we already set crashed inside the loop above)
        if (!crashed && distanceKm <= 0) {
            System.out.println("final approach time\n");
        }
    }

    /*
     * landing you can go around a couple times then actually land
     * landed flag is easier for me to read than while true break
     * if / else if / else at bottom = smooth vs rough vs ok (assignment if-else chain)
     */
    private static void landingPart() {
        int goArounds = 2;
        boolean landed = false;

        while (!landed) {
            System.out.println("-- landing --");
            // printf 3 ints
            System.out.printf("fuel %d | alt %d | speed %d%n", fuel, altitude, speed);
            System.out.print("1 land  2 go around: ");
            int c = readIntSafe();

            // go around only if 2 and still have go arounds left both true
            if (c == 2 && goArounds > 0) {
                goArounds--; // one less
                fuel -= 10;
                distanceKm = 25; // your not at the airport yet again
                altitude = 450;
                speed += 40;
                System.out.println("going around, fly a little more\n");
                flyLoop(); // fly more
                if (crashed) {
                    return; // dead stop everything
                }
                continue; // go back to top of landing menu
            }

            // didnt do go around block
            if (c != 1) {
                if (c == 2) {
                    // wanted go around but non left
                    System.out.println("no go arounds left. press 1");
                } else {
                    System.out.println("1 or 2 only");
                }
                continue; // ask again
            }

            // if - else if - else chain for landing result
            if (speed <= 480 && altitude <= 900 && fuel > 0) {
                System.out.println("smooth landing");
            } else if (speed > 580 || altitude > 1100) {
                // || is or either one can trigger rough
                System.out.println("rough landing but alive");
            } else {
                System.out.println("ok landing");
            }
            landed = true;
        }
    }

    // help is one line not complicated
    private static void showHelp() {
        System.out.println("\nchecklist, then fly, then land. watch fuel.\n");
    }

    /*
     * readIntSafe trys to read a number from a line
     * try catch so if they type letters it doesnt explode (assignment error handling)
     * parseInt turns string "5" into int 5
     * bad input throws exception we catch and return -1 so menu shows default
     */
    private static int readIntSafe() {
        try {
            String line = input.nextLine().trim(); // Scanner
            return Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
