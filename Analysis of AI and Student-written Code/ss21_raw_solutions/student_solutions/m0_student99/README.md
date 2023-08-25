# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m0/tests/st2-praktikum_tests_group_6f4601bc-c300-488b-896a-8ad3179ca589]([http://students.pages.st.archi-lab.io/st2/ss21/m0/tests/st2-praktikum_tests_group_6f4601bc-c300-488b-896a-8ad3179ca589])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone 0 (M0) - Controlling a maintenance droid

A maintenance droid is used to fix minor problems - reconnect loose plugs, switch broken light bulbs, etc. 
In milestone 0, you have the task to steer such a maintenance droid across a spaceship deck with barriers.
The spaceship deck is square-shaped. In the following image, you see a how the spaceship deck is modelled as a 
system of gridcells.

(Please note - this is **not your personal map yet**, just a general explanation. Your personal map is further down!)

![spaceship deck](src/main/resources/explanation.jpg)

1. A **spaceship deck** is divided into **gridcells** by an x- and y-coordinate system, each starting
 at 0. 
1. A **gridcell** is identified by its lower left corner. So the blue gridcell depicted here has the coordinate **(2,1)**. 
1. The spaceship deck contains **barriers**, depicted as red lines. The barriers run only horizontally or
    vertically. They may be connected to each other. A barrier is identified by its start- and 
    end coordinate. So, in the above image you see three barriers: 
    * (5,0)-(5,3)
    * (3,3)-(5,3)
    * (3,3)-(3,4)
1. In addition, spaceship deck boundaries cannot be passed - essentially they also are a kind of barrier.
1. The **maintenance droid** initially is located in the gridcell indicated by the red dot. In the above case this is 
    **(1,2)**.  
1. The maintenance droid can be moved by a simple set of commands. A command is enclosed by square brackets, and 
    has two parts: a direction and the number of steps. A maintenance droid can only be moved horizontally or 
    vertically, not diagonally. 
    * If the command is e.g. **[no,2]**, the maintenance droid moves 2 gridcells up, and is then positioned 
        on **(1,4)**.
    * The direction is either **no** (north), **ea** (east), **so** (south), or **we** (west).    
1. If the maintenance droid meets a barrier or a spaceship deck boundary, then it moves only as 
    many steps as possibly, and then stops. Let's make an example: 
    * At the beginning, the maintenance droid is at (1,2), as depicted.
    * The first command is [no,1]. The maintenance droid is at (1,3). 
    * The next command is [ea,3]. But the maintenance droid can only be moved by one step, and stops at (2,3).
    * The next command is [so,7]. The maintenance droid can be moved by 3 steps, and stops at (2,0).
    * The next command is again [ea,3]. This time, the maintenance droid can be moved by 2 steps, and stops at (4,0).

## Your task in milestone 0

This is your personal map of the spaceship deck. You see the dimensions of it. The maintenance droid is at (3,0).

![Grid](src/main/resources/grid.png "Grid")

Please implement `thkoeln.st.st2praktikum.exercise.Exercise0`. It basically means that you have to implement one 
simple interface: 

```java
public interface Walkable {
    String walk(String walkCommandString);
}
```

The string parameter `walkCommandString` has the same format as shown above (e.g. "`[no,2]`"). The expexted return
value is the coordinate where the maintenance droid is afterwards, in the format as above (e.g. "`(2,4)`").

A word of warning: You find two visible unit tests in your repo. Just hardcoding the expected results won't work, 
since we have some hidded tests as well - and you won't be told the commands of those. So, you might have to add
own tests and start debugging, should the hidden tests keep failing.