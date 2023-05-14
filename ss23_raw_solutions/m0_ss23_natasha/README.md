# Praktikum Softwaretechnik 2 (ST2) im SoSe 2023

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer 
[individuellen Testseite](http://students.pages.st.archi-lab.io/st2/ss23/test/ST2M0_tests_group_55c143b7-9f71-499e-8984-243091289ce3).


## Milestone 0 (M0) - A simple shop system

_Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten._

Your software development company has been contracted to delevop an eCommerce system for a small online shop. The shop sells
a number of things (Güter, Produkte), which are not perishable and not fragile. A thing has a name, description, and size. In addition, it
has a purchase price (Einkaufspreis), and a sales price. 

The shop sells things to customers. A customer has a name, a mail address, and an address (with street, city, 
and postal code). There is no username, i.e. a customer can only be identified by his mail address. 
A customer can place an customerOrder for one or more things. Therefore, an customerOrder consists of one or several customerOrder positions. 
An customerOrder position references a thing, and contains the quantity of the thing that is ordered. 

The shop has only one warehouse, which stores all things. For each thing, the stock quantity (Warenbestand) is stored. For some
things, the stock quantity is zero. In that case, the thing is out of stock, and not available for sale at moment.

Customers can customerOrder only things that are in stock, i.e. have stock quantity > 0. (For simplicity reasons, customer returns
will not be handled by the system for now, but outside the system via mail address. So this is not your concern.) 
Shop admins can manually change the stock quantity for a thing, add new things to the catalog, or remove 
things from the catalog.

When a customer orders things, he/she puts them in the shopping basket first. By that action, the things are reserved for
the customer, and cannot be ordered by other customers. There is (as of now) no time limit for the reservation.
The customer can remove things from the shopping basket, or check out ("zur Kasse gehen"). 

_(This is what you need to model in this first milestone. Additions come later.)_


## Die Aufgaben in M0

### E1: Aufstellen eines Logischen Datenmodells (LDM)

![Bloom-Level](./images/4-filled-32.png)
[Level 4 (Analyse) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level4)


Erstellen Sie ein logisches Datenmodell (LDM) für das Shop-System. Das LDM soll die oben beschriebenen Entities, Value Objects und
Beziehungen abbilden.

Legen Sie Ihre Lösung im Verzeichnis [`exercises`](src/main/resources/exercises) ab.
Dort liegt bereits ein Template namens [`E01solution.uxf`](src/main/resources/exercises/E02solution.uxf).

**WICHTIG**: Damit der Test Ihr Diagramm überprüfen kann, müssen Sie sich an einige Regeln halten:

* Behalten Sie den Dateinamen bei, sonst findet der Test Ihr Diagramm nicht. Legen Sie bitte auch keine
  Varianten an, so wie `E02_korrigiert.uxf` o.ä.
* Eine Grafik müssen Sie diesmal nicht hochladen - außer Sie haben Fragen zu Ihrem Diagramm.


### E2: Implementieren Sie eine Minimalversion (MVP) für das Shop-System

![Bloom-Level](./images/4-filled-32.png) 
[Level 4 (Analyse) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level4)


Implementieren Sie eine Minimalversion (MVP) für das Shop-System. Die Funktionalitäten des MVP sind vorgegeben. 
Dafür finden Sie unter [`thkoeln.archilab.ecommerce.usecases`](src/main/java/thkoeln/archilab/ecommerce/usecases) vier
Interfaces, die Sie implementieren sollen.

Am geschicktesten ist es, wenn Sie dafür einen oder mehrere Spring Boot Services implementieren, z.B. so:

```java
@Service
public class MyFantasticShopSystem implements CustomerRegistrationUseCases {
    //...
}
```

Wie Sie die Use Cases implementieren, ist in M0 erstmal Ihnen überlassen. Sie sollen aber die Domain-Entities und Value Objects
aus dem LDM verwenden. Außerdem verwenden Sie bitte Spring JPA zur Persistierung der Daten. 

**WICHTIG**: Damit der Test Ihre Implementierung überprüfen kann, gibt es auch an Hinweise: 
1. Die Implementierung muss unterhalb des Packages `thkoeln.archilab.ecommerce.solution` liegen. Sie können hier beliebig
   viele Sub-Packages und Klassen anlegen.
2. Die Implementierung muss alle Interfaces aus `thkoeln.archilab.ecommerce.usecases` implementieren. 
3. Sie haben eine Anzahl von Tests im Repo, die für Sie sichtbar sind. Sie können also lokal testen.
