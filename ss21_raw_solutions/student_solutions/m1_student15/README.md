# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m1/tests/st2-praktikum_tests_group_f7a798e1-4101-4dc8-b46a-f5c8ac1e8e7c]([http://students.pages.st.archi-lab.io/st2/ss21/m1/tests/st2-praktikum_tests_group_f7a798e1-4101-4dc8-b46a-f5c8ac1e8e7c])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone 1 (M1) - Expanding the mining machine world

A mining machine is used to collect minerals from a mining field, like copper, cobalt, lithium, etc. 
In Milestone 0, you have successfully taught the mining machine to navigate a field with walls. Hooray!
But soon after that, some other companies also started releasing mining machine simulation software - so you have to 
implement some new features to stay competitive!

These new features are:
1. There can now be multiple fields.
1. These fields are connected by traversable connections between two end points on different fields.   
1. There are now multiple mining machines. A tile can contain only one mining machine, therefore mining machines 
    are like walls for other mining machines.   
1. The mining machines have to learn some new commands - in addition to moving to adjacent tiles, they can now use 
    connections to move from one field to another.
1. Existing mining machines can be placed on a field, if they are not currently located in one. 
1. Initially there are no mining machines, no fields, no walls and no connections - you have to provide an API to create them.

An example of a multi-field-mining machine-world could look like this:

![field](src/main/resources/explanationM1.png)

* mining machines (red)
* walls (blue)
* connections (green)



## Exercise 1) Refactor your M0 solution to match the above scenario

Please implement `thkoeln.st.st2praktikum.exercise.MiningMachineService`. This service class contains all the methods 
you need to implement, each with an empty implementation body. 

Please refactor your M0 code to fit to this enhanced scenario. You will also have to implement additional entities (see also Exercise 2 and 3). 

You find a number of visible unit tests in your repo, testing your service class. As in M0, just hardcoding the expected 
results won't work. 


## Exercise 2) Apply the SOLID principles to build a clean architecture

This exercise doesn't require additional code (on top of Exercise 1). Instead, in Exercise 2 & 3 we will check
the **style** of your code and architecture. Exercise 2 is about application of the SOLID principles, Exercise 3 about
the Clean Code rules. 

Exercise 2 & 3 will mainly be checked by hand. So it may happen that your solution is functionally correct (i.e.
it passes Exercise 1), but that is not compliant with the application of those principles.  

### 2a) Single Responsibility Principle: Design "right-sized" entities

Make sure that your entities have just one purpose, as stated in the Single Responsibility Principle.

### 2b) Open-Closed Principle, Interface Segregation Principle: Design suitable interfaces

Each entity must implement one or several interfaces. Be sure to keep them focused on a single purpose, and give 
them a proper name. The interface(s) must be so that they represent the main way for the outside world to interact
with the entity. 

### 2c) Dependency Inversion Principle: Avoid circular dependencies

Entities should not have circular dependencies. Use the "abstraction / concretion" concept in the Dependency Inversion 
Principle to avoid such circular dependencies.


## Exercise 3) Apply the Clean Code rules to implement clean code

Obey the following Clean Code rules as outlined in the corresponding video.

* 3a) Meaningful names
* 3b) Comments only where necessary
* 3c) Keep your methods small
* 3d) No side effect in a function
* 3e) Only one level of abstraction within a method
* 3f) Stepdown rule
* 3g) Proper error handling using exceptions






