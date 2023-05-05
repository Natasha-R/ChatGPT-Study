# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_9fdff34d-cbe8-4c50-9d72-7727a0da2513]([http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_9fdff34d-cbe8-4c50-9d72-7727a0da2513])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone M4 - Implementing a REST-API

Last time you defined a REST-API for your project. Now it is time to implement it.

### Exercise 1 - Implementing a REST-controller

Implement a REST-controller for your mining machine aggregate.
You have to fulfill the following eight requirements:

|Requirement # | URI | VERB |
|---|---|---|
| 1. Get all mining machines                                                                      | /miningMachines | GET |
| 2. Create a new mining machine                                                                  | /miningMachines | POST |
| 3. Get a specific mining machine by ID                                                          | /miningMachines/{miningMachine-id} | GET |
| 4. Delete a specific mining machine                                                             | /miningMachines/{miningMachine-id} | DELETE |
| 5. Change the name of a specific mining machine                                                 | /miningMachines/{miningMachine-id} | PATCH |
| 6. Give a specific mining machine a command                                         | /miningMachines/{miningMachine-id}/commands | POST |
| 7. List all the commands a specific mining machine has received so far                        | /miningMachines/{miningMachine-id}/commands | GET |
| 8. Delete the command history of a specific mining machine                                    | /miningMachines/{miningMachine-id}/commands | DELETE | 

Use the provided class Mining machineController to implement your REST-API. 

**Important:** 
1. Your mining machine class needs to have an attribute called `name`.
1. The `MiningMachineService` from M3 has been split into three service classes, each dedicated to one aggregate. 
    Please migrate your M3 service class implementation to these three locations, accordingly.
    * `MiningMachineService`
    * `FieldService`
    * `TransportTechnologyService`
1. Please do not change the given package structure in your repo. (You can add own packages, but don't move or
    rename those that came with the repo.)

