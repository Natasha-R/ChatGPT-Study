# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_0d4fe930-5c68-430e-9194-17bad520345d]([http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_0d4fe930-5c68-430e-9194-17bad520345d])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone M4 - Implementing a REST-API

Last time you defined a REST-API for your project. Now it is time to implement it.

### Exercise 1 - Implementing a REST-controller

Implement a REST-controller for your maintenance droid aggregate.
You have to fulfill the following eight requirements:

|Requirement # | URI | VERB |
|---|---|---|
| 1. Get all maintenance droids                                                                      | /maintenanceDroids | GET |
| 2. Create a new maintenance droid                                                                  | /maintenanceDroids | POST |
| 3. Get a specific maintenance droid by ID                                                          | /maintenanceDroids/{maintenanceDroid-id} | GET |
| 4. Delete a specific maintenance droid                                                             | /maintenanceDroids/{maintenanceDroid-id} | DELETE |
| 5. Change the name of a specific maintenance droid                                                 | /maintenanceDroids/{maintenanceDroid-id} | PATCH |
| 6. Give a specific maintenance droid an order                                         | /maintenanceDroids/{maintenanceDroid-id}/orders | POST |
| 7. List all the orders a specific maintenance droid has received so far                        | /maintenanceDroids/{maintenanceDroid-id}/orders | GET |
| 8. Delete the order history of a specific maintenance droid                                    | /maintenanceDroids/{maintenanceDroid-id}/orders | DELETE | 

Use the provided class Maintenance droidController to implement your REST-API. 

**Important:** 
1. Your maintenance droid class needs to have an attribute called `name`.
1. The `MaintenanceDroidService` from M3 has been split into three service classes, each dedicated to one aggregate. 
    Please migrate your M3 service class implementation to these three locations, accordingly.
    * `MaintenanceDroidService`
    * `SpaceShipDeckService`
    * `TransportTechnologyService`
1. Please do not change the given package structure in your repo. (You can add own packages, but don't move or
    rename those that came with the repo.)

