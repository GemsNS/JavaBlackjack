/*
 * PROJECT TITLE: Text Flight Mission (Console)
 * STUDENT NAME: Joel
 * DATE: 04/04/2026
 * DESCRIPTION: terminal game - checklist then fly. 1 cruise 2 up 3 down, sometimes rng crashes u
 */

// pull in types java already wrote so i dont have to
/*lists slide 4*/import java.util.ArrayList; // growable list for checklist strings
/*lists slide 4*/import java.util.List; // i declare as List but build ArrayList
/*misc slide 12*/import java.util.Random; // rolls dice for random crash
/*input slide 5*/import java.util.Scanner; // reads what u type

/*intro to java 02 slide 23*/public class App {

    // these sit up here = every method below can see same numbers (scope)
    private static int fuel; // gas left
    private static int distanceKm; // km until airport hits 0 = u arrived
    private static int altitude; // fake height, only changes on ascend/descend

    private static final Scanner input = new Scanner(System.in); // keyboard
    private static final Random rng = new Random(); // one rng object reused

    public static void main(String[] args) {
        System.out.println("=== text flight ==="); // title

      /*Programming_Output_Variable 3/6 */  int pick; // menu number

        // do-while: menu always shows once, then repeats till u pick 3 quit
        do {
            System.out.println("\n1 play  2 help  3 quit");
            /*intro to java 02 slide 18*/System.out.print("pick: ");
           /*input slide 10 */ pick = readIntSafe(); // safe int read (try catch inside)

            // chain of ifs = pick what to run
          /*complex decisions slide 5 */  if (pick == 1) {
                playMission(); // whole game
            } else if (pick == 2) {
                System.out.println("\nchecklist then 1=cruise 2=ascend 3=descend. random stuff can crash you. watch fuel.\n");
            } else if (pick == 3) {
                System.out.println("bye");
            } else {
                System.out.println("type 1 2 or 3"); // -1 or typo
            }
        } while (pick != 3); // keep going unless quit

     /*input slide 15 */   input.close(); // done with scanner
    }

    // playMission = one full run: reset stats, checklist, fly loop, end text
    /*intro to functions slide 3*/private static void playMission() {
        fuel = 50;
        distanceKm = 100;
        altitude = 3000; // starting pretend feet
        boolean randomCrash = false; // flips true if bad rng

        doChecklist(); // if this fails it zeros fuel etc

        // while = keep taking turns while u still have distance + fuel + no crash flag
        while (distanceKm > 0 && fuel > 0 && !randomCrash) {
            // print all 3 stats so player sees state
           /*Programming_Output_Variable slides*/System.out.println(
                    "fuel=" + fuel + "  dist=" + distanceKm + "km  alt=" + altitude);
            System.out.println("1 cruise (forward)  2 ascend  3 descend");
            System.out.print("action: ");
           /*Programming_Output_Variable variable slide */ int a = readIntSafe();

            // each branch = different move, changes numbers different amounts
            if (a == 1) {
                /*misc slide 8 "-="*/fuel -= 5; // cheap
                distanceKm -= 15; // good progress
            } else if (a == 2) {
               /*misc slide 8 "-="*/ fuel -= 8; // climbing burns more
                altitude += 500; // go up
                distanceKm -= 8; // not as much forward
            } else if (a == 3) {
                fuel -= 6;
                altitude = Math.max(0, altitude - 400); // down but not below 0
                distanceKm -= 18; // gliding forward more
            } else {
                System.out.println("pick 1 2 or 3, you burn fuel waiting");
             /*misc slide 8 "-="*/   fuel -= 2; // penalty for dumb input
            }

            if (fuel <= 0) {
                System.out.println("out of fuel.");
            }

            // nextInt(10) is 0-9, ==0 means 1 in 10 chance instant crash story
            if (fuel > 0 && distanceKm > 0 && rng.nextInt(10) == 0) {
                System.out.println("something went wrong - you crashed.");
                randomCrash = true; // stops while bc condition uses !randomCrash
            }
        }

        // pick which ending message matches how u exited the loop
      /*complex decisions slide 5 */   if (randomCrash) {
            System.out.println("mission failed (crash).");
        } else if (distanceKm <= 0 && fuel > 0) {
            System.out.println("you made it to the airport.");
        } else {
            System.out.println("mission didnt work out.");
        }
    }

    // doChecklist builds ArrayList of steps, loops them, user must type ok each time
    private static void doChecklist() {
        List<String> steps = new ArrayList<>(); // empty list then fill it
      /*lists slide 5*/  steps.add("check fuel"); // add = stick string on end
        steps.add("check engines");

        System.out.println("\n-- checklist --");
        /*loops partial slides*/for (String line : steps) { // for-each = visit every item in list order
            System.out.println("- " + line);
            System.out.print("type ok: ");
            String typed = input.nextLine().trim().toLowerCase(); // clean input
            if (!typed.equals("ok")) { //if the player types anything other than "ok" (like "yes", "done", or makes a typo), this condition becomes true. That immediately triggers the code block inside, which prints "checklist failed", zeros out the fuel, and ends the mission.
                System.out.println("checklist failed, mission over.");
                fuel = 0; // kills mission
                distanceKm = 9999; // so u cant "win" after fail
               /*IntroToFunctions slide 19 */ return; // bail out of this method
            }
        }
        System.out.println("checklist done. flying.\n");
    }

    // readIntSafe: parse line to int, if letters etc catch and return -1 instead of exploding
    private static int readIntSafe() {
     /*exceptions slide 5*/   try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1; // signals bad input, caller handles like wrong menu #
        }
    }
}
