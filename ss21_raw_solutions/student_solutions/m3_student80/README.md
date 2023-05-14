# Praktikum Softwaretechnik 2 (ST2) im SoSe 2021

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer individuellen Testseite:
[http://students.pages.st.archi-lab.io/st2/ss21/m3/tests/st2-praktikum_tests_group_9641d3f3-a4c1-497a-a508-fbd09b9c9f01]([http://students.pages.st.archi-lab.io/st2/ss21/m3/tests/st2-praktikum_tests_group_9641d3f3-a4c1-497a-a508-fbd09b9c9f01])

Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. 

## Milestone M3 - Persistence and REST API

In this milestone, you need to make the tidy-up robot world persistent. In addition, you need to define a
REST-API. (The implementation follow in the next milestone M4). For the REST API, you also have to define
aggregates.

### Exercise 0 - Adding a transport category

Every connection now belongs to a specific transport category, e.g. a helicopter or a transport belt.
A transport category can have one or several connections. You now need to specify a transport category 
if you want to create a new connection.


### Exercise 1 - Making the tidy-up robot world persistent

Use the Java Persistence API to make your Project persistent. Use your knowledge from ST1 to create the required 
classes:
* Entities
* Value Objects (Embeddables) 
* Repositories



### Exercise 2 - Identifying aggregates

You need to identify the **aggregates** in your domain model in order to specify the REST API. Please use the table in 
**`main/resources/E2.md`** to specify each aggregate root, and other entities or value objects contained
in the aggregate. 

These are the entities / VOs that you **must** list in the table. If you have additional entities or VOs, you can
add them as well. 

1. TidyUpRobot 
1. Room
1. Task
1. Coordinate
1. Barrier
1. TransportCategory
1. Connection


### Exercise 3 - Designing a REST API

Design a REST API for tidy-up robot steering with the following functionality:

1. Get all tidy-up robots
1. Create a new tidy-up robot
1. Get a specific tidy-up robot by ID
1. Delete a specific tidy-up robot
1. Change the name of a specific tidy-up robot
1. Give a specific tidy-up robot a task
1. List all the tasks a specific tidy-up robot has received so far
1. Delete the task history of a specific tidy-up robot
1. Get all rooms
1. Create a new room
1. Get a specific room by ID
1. Delete a specific room
1. Make a specific room larger, by specifying a new (larger) width and/or height
1. Get all barriers contained in a specific room
1. Delete all barriers contained in a specific room
1. Add a new barrier to a room
1. Delete a barrier on a room
1. Get all transport categories
1. Create a new transport category
1. Create a new connection for a transport category
1. Get all connections of a specific transport category
1. Delete a specific connection in a transport category

Please use the table in **`main/resources/E3.md`** to specify your REST-API.
 
**Important (1)**: 

As stated in the corresponding videos, creation of a value object can be done in two ways. 
1. By using `PUT /things/{thing-id}/myValueObjects/the-string-represention-of-that-value-object`, 
    and an empty request body (i.e. you use the fact that a value object is fully characterized by its
    attributes, and that their `toString()` output serves as an ID), or
2. By using `POST /things/{thing-id}/myValueObjects`, and the value object is located in the request
    body. 

Both ways are valid, but we assume the option (2) in this exercise. For GET and DELETE, option (1) is fine.

**Important (2)**: 

When you specify the REST URIs above, you **must** write IDs in the following way: 
`/things/{thing-id}`
`/otherThings/{otherThing-id}`
This applies to **both entities and value objects**.  


**Important (3)**: 

We use and expect the "lower CamelCase" way of writing a REST endpoints, both for URIs and for IDs. 
I.e. a class called `ArchiLabDocument` will have a REST endpoint like `GET /archiLabDocuments/{archiLabDocument-id}`.




