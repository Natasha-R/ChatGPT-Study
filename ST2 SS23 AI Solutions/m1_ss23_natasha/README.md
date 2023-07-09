# Praktikum Softwaretechnik 2 (ST2) im SoSe 2023

Sofortiges Feedback zu Ihres Lösung finden Sie wie immer auf Ihrer 
[individuellen Testseite](http://students.pages.st.archi-lab.io/st2/ss23-m1/test/ST2M1_tests_group_55c143b7-9f71-499e-8984-243091289ce3).


# Milestone 1 (M1) - Payment & Delivery, und Refactoring nach Clean Code und SOLID-Prinzipien

_Da wir aus der Aufgabenbeschreibung direkt Coding-Aufgaben ableiten, ist die komplette Beschreibung in Englisch
gehalten. Wir brauchen immer mal wieder Englisch, weil fachliche Beschreibungen dazukommen. Damit nicht
ein deutsch-englisches Sprach-Mischmasch entsteht, ist ab hier alles auf Englisch._



## E1: Payment & Delivery in our Shop System - Mock Service Implementations

![Bloom-Level](./images/4-filled-32.png)
[Level 4 (Analyse) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level4)

Your company is now adding new features to the shop system. In its final stage, the shop system will have B2B
("business to business") links to a payment provider, and a logistics company. Both the payment provider and 
the logistics company will be external services. They offer REST APIs. Your shop system calls these REST APIs, 
when customers want to check out their shopping baskets.

The payment provider will handle the customer's payment for the shop. I.e. it will charge the customer's 
account, and reimburse the shop afterwards, minus a small fee. The payment provider will also check
the customer's payment details, and reject the payment if something is wrong or invalid. There
is a maximum limit for a single payment.

The logistics company will fetch the ordered things from the shop's warehouse, and deliver them to 
the customer. They have certain limitations for the delivery: a single delivery can only contain a maximum 
number of things.

This is what the shop will have in its final stage. For now, neither the payment provider nor the logistics
company have been selected. Therefore, the shop owner has decided that you should implement a mock for 
both of them. The mock will be a simple implementation of the two new interfaces given in 
`thkoeln.archilab.ecommerce.usecases`:

* `PaymentUseCases`
* `DeliveryUseCases`


### Your Task in E1

The mock will be used for now in the shop system, instead of the real payment provider and logistics company.
It is your task in E1 to implement this mock. Implement the two interfaces `PaymentUseCases` 
and `DeliveryUseCases` in the package (just two methods each) as services (tagged `@Service`). You need 
to make sure that the following additional tests are green: 

* `PaymentProviderMockTest`
* `DeliveryUseMockTest`

In addition, all the tests from M0 (apart from the hidden tests) stay in place and act as regression 
tests. They must all be green, too.

As in M0, the implementation must be located below the package `thkoeln.archilab.ecommerce.solution`. 
You can create sub-packages below. Your implementation must implement all interfaces from `thkoeln.archilab.ecommerce.usecases`.
You have a number of tests in the repository, which are visible to you, so you can test locally.

### Additional Hint

You will find the that `DeliveryUseCases` uses another interface, `DeliveryRecipient`, to describe what information
the logistics company needs. Think about which of your existing classes could implement this interface.



## E2: Integrate Payment & Delivery into your Checkout Process

![Bloom-Level](./images/4-filled-32.png)
[Level 4 (Analyse) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level4)

Until now (in M0), the checkout process was very much simplified: The only effect was that the things
were removed from the shopping basket. Now, with (mocked) payment and delivery in place, you need to enhance your
checkout process. The way you should implement this is to think about **state transitions** 
(Zustandsübergänge) - remember ST1 and the state diagrams!

For the improved checkout process, you need to implement a ShoppingBasket entity, which goes through the following
state transitions:
1. Initially, the shopping basket is either undefined, or `empty`.
2. When the customer adds a thing to the shopping basket, the shopping basket changes to `filled`. Upon removal, it
   changes back to `empty`, or is undefined (deleted) again.
3. When the customer checks out, the payment is processed (by a call to 
   `PaymentUseCases.authorizePayment(...)`). If successful, the shopping basket state changes to `paymentAuthorized`. 
   Otherwise, it remains in the former state. In that case, things can be added or removed in order to
   fulfill the payment limits.
4. From a successful payment, the delivery is triggered (by a call to 
   `DeliveryUseCases.triggerDelivery(...)`). If successful, the shopping basket state changes to `deliveryTriggered`. 
   Otherwise, it remains in the `paymentAuthorized` state. In that case, things can be added or removed in order to
   fulfill the delivery limits. This would move the shopping basket back to state `filled`.
5. In the `deliveryTriggered` state, the order can be created, and the shopping basket goes back to `empty` or undefined.


### Your Tasks in E2

Your task in E2 is to implement the state transitions described above. First, you need to create a state 
diagram as in ST1. Put your state diagram in the folder [`exercises`](src/main/resources/exercises).
There is already a Template called [`E02solution.uxf`](src/main/resources/exercises/E02solution.uxf).
**Please make sure your solution has the same name!**

There are the following tests checking this state flow: 
- An automated `automatedPretestStateDiagram` test, which checks the state diagram for essential elements.
- When this automated pretest is green (but not before that), you can ask for a manual check. The 
  `e2ManualCheckStateDiagram` test reflects the manual check, and needs to be green.
- Two tests in `E2CheckoutTest` check the proper implementation of the state transitions. They 
  are green, if your code reflects the logic described above.


## E3: Keep DDD Conventions, and Avoid Anti-Patterns

![Bloom-Level](./images/5-filled-32.png)
[Level 5 (Evaluate) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level5)

In this milestone, you need to keep Domain-Driven Design (DDD) conventions, and avoid anti-patterns. 
We will especially check the following aspects:

* Value Objects are immutable
* Entities have relationships via object reference, not via ID
* References between entities are NEVER bidirectional, ALWAYS unidirectional
* Your code follows the **naming conventions** usually used in DDD
  * Entities and Value Objects are named as the corresponding domain model objects, in camel case (like `ShoppingBasket`)
  * Repositories are named like the corresponding entity, with the suffix `Repository` (like `ShoppingBasketRepository`)
  * Application services are named like the corresponding entity, with the suffix `Service` (like `ShoppingBasketService`)
* Your code follows the **package conventions** we have defined in this course (see also
  the [ST2 workshop on Mon 24.04.](https://www.archi-lab.io/regularModules/ss23/st2_ss23.html#workshopDay3))
  * Your code is structured in packages according to the main objects in the domain model. In this domain model, we 
    certainly need to have packages for the following domain objects:
    * customer
    * shopping basket
    * thing
    * order
    * (You will probably have _more_ than these, but these packages are mandatory.)
  * Package naming is the domain object name in lower case, and singular (like `shoppingbasket`) 
  * Packages have each two sub-packages: 
    * `application` for application services, controllers, DTOs, ... (all that is needed for communication with "outside" of the package) 
    * `domain` for entities, value objects, and repositories


### Your Tasks in E3

Refactor your code from M0, so that it follows the conventions and rules described above. This will be tested 
by automated tests.



## E4: Refactoring according to Clean Code and SOLID Principles

![Bloom-Level](./images/5-filled-32.png)
[Level 5 (Evaluate) in Bloom's Taxonomy](https://www.archi-lab.io/infopages/material/blooms_taxonomy.html#level5)

When you refactor and enhance your code from M0, you need to apply the Clean Code rules and the SOLID
principles. We will check the following aspects:

* Clean Code
    * Meaningful names for variables, classes, packages, ...
    * Methods should be small, not longer than max. 30 lines of code, and if possible should be much smaller.
    * Code lines should not extend 120 characters - add a linebreak otherwise, or try re-writing the code.
* SOLID
    * Single Responsibility Principle
        * Classes should serve one purpose only
        * No domain business logic in application services or controllers
    * Open-Closed Principle
        * No public access to member variables
        * States (see above for ShoppingBasket) must be encapsulated - no possibility of directly changing the status from outside
    * Dependency Inversion Principle
        * No cyclic dependencies - avoid them by applying the DIP


### Your Tasks in E4

Refactor your code from M0, so that it follows the principles described above. Some of these aspects we will cover by 
automatic tests, others we will check manually.

Manual tests will be performed by looking at samples (Stichproben) of the students' solutions. Therefore, the manual
tests for E4 will all be initially green. If we pick your solution for a manual check, and we find violations, then
it will turn red. You will see the feedback in error message. We will not perform a thorough code review, but
check certain critical aspects of your code.

* This manual check will **happen not later than Monday** before solution submission.
* However, we will perform such checks through the coming milestones M1 ... M4, so you still need to get your
  code in shape - at one point or another, we will pick your solution for a manual check.
* This also means that a **a green manual test doesn't mean "approval"** - it just means that we didn't pick
  your solution for a manual check in this milestone, or that we didn't spot the violation in the sample we picked.

For E4, **we guarantee only one (1) intermediate feedback** if you had a red manual check - since this is a lot of 
manual work for the supervisors. This means:
* Use the workshop(s) in this milestone to talk to supervisors directly.
* Start early - **this is probably the biggest milestone in ST2!!**

### Additional Help for Clean Code and SOLID

- Some of the automated tests are based on the PMD Source Code Analyzer, which runs in our pipeline (so you
  don't have this locally). If you want to learn more about each test, feel free to check the
  documentation at [pmd.github.io](https://pmd.github.io/latest/pmd_rules_java.html).
- Although PMD is not available as a local unit test, there is an IntelliJ plugin that you can use to 
  run the PMD ruleset locally. More infos how to install and use it on our ArchiLab infopage:
  [https://www.archi-lab.io/pmd](https://www.archi-lab.io/pmd).
- For our subjective selection of Clean Code and SOLID principles, see our ArchiLab infopage
  [https://www.archi-lab.io/cc-solid](https://www.archi-lab.io/cc-solid).


