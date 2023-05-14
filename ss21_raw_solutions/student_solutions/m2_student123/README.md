# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m2/tests/st2-praktikum_tests_group_9e982474-9b1b-46ef-8711-c571fa7da5aa]([http://students.pages.st.archi-lab.io/st2/ss21/m2/tests/st2-praktikum_tests_group_9e982474-9b1b-46ef-8711-c571fa7da5aa])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone M2 - Rich Domain Model and Testing

Until now certain objects were only encoded as base Java classes, like Strings. We now want to add some business
logic to them. So we need to represent them accordingly, as dedicated "Domain Primitives". Please see the related
video(s) for the concept. 

To make it easier for you, we have already identified the Domain Primitives you need to implement.
 
1. Vector2D
    * a vector2D needs a constructor to be created from a string, e.G. "(2,1)". Invalid strings need to be 
        detected.
    * a vector2D has to be positive    
    * invalid strings need to be detected
1. Barrier
    * a barrier needs a constructor to be created from a string, e.G. "(1,1)-(3,1)". Invalid strings need to be 
        detected.
    * a barrier must be either vertical or horizontal
    * the first vector2Ds of a barrier always has to be the one closer to the bottom left corner
1. Task
    * a task needs a constructor to be created from a string e.G. "\[we,2]"
    * invalid strings need to be detected, e.g. "\[tr, 3]" or "\[blömpf]" 
    

### Exercise 1 - Adding Domain Primitives

Implement the Domain Primitives. 

* Implement these three aforementioned classes accordingly, with. 
* Please define appropriate exceptions for error cases. You can derive these exceptions from #
    `java.lang.RuntimeException`. 


### Exercise 2 - Writing Unit Tests

Your second task is to write some unit tests. Please choose **three out of six** of the following conditions to test:

1. Hitting another tidyUpRobot during a Move-Command has to interrupt the current movement
1. Trying to move out of bounds must not be possible
1. Placing a connection out of bounds must not be possible
1. Traversing a connection must not be possible in case the destination is occupied 
1. Using the TR-command must not be possible if there is no outgoing connection on the current position 
1. Placing a barrier out of bounds must not be possible

Please place your tests in **`src/test/java/thkoeln.st.st2praktikum.exercise`**.








