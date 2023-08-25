# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_0d4d58de-4e34-486b-b1a1-325338ca3c47]([http://students.pages.st.archi-lab.io/st2/ss21/m4/tests/st2-praktikum_tests_group_0d4d58de-4e34-486b-b1a1-325338ca3c47])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone M4 - Implementing a REST-API

Last time you defined a REST-API for your project. Now it is time to implement it.

### Exercise 1 - Implementing a REST-controller

Implement a REST-controller for your cleaning device aggregate.
You have to fulfill the following eight requirements:

|Requirement # | URI | VERB |
|---|---|---|
| 1. Get all cleaning devices                                                                      | /cleaningDevices | GET |
| 2. Create a new cleaning device                                                                  | /cleaningDevices | POST |
| 3. Get a specific cleaning device by ID                                                          | /cleaningDevices/{cleaningDevice-id} | GET |
| 4. Delete a specific cleaning device                                                             | /cleaningDevices/{cleaningDevice-id} | DELETE |
| 5. Change the name of a specific cleaning device                                                 | /cleaningDevices/{cleaningDevice-id} | PATCH |
| 6. Give a specific cleaning device an order                                         | /cleaningDevices/{cleaningDevice-id}/orders | POST |
| 7. List all the orders a specific cleaning device has received so far                        | /cleaningDevices/{cleaningDevice-id}/orders | GET |
| 8. Delete the order history of a specific cleaning device                                    | /cleaningDevices/{cleaningDevice-id}/orders | DELETE | 

Use the provided class Cleaning deviceController to implement your REST-API. 

**Important:** 
1. Your cleaning device class needs to have an attribute called `name`.
1. The `CleaningDeviceService` from M3 has been split into three service classes, each dedicated to one aggregate. 
    Please migrate your M3 service class implementation to these three locations, accordingly.
    * `CleaningDeviceService`
    * `SpaceService`
    * `TransportCategoryService`
1. Please do not change the given package structure in your repo. (You can add own packages, but don't move or
    rename those that came with the repo.)
