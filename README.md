# Project Idea - UBC Degree Builder

## Application Purpose
This application is designed for UBC students who are unsure of what to choose as their specialisation going into second year.
The application will record all the courses students have taken or plan to take, and will produce some relevant degrees from their input.
The application will not only include preset degrees, but will also allow a user to input more degrees and courses to *'future-proof'*
the software. I am interested in this software because of my daunting experience navigating through the various degrees offered by UBC,
and thus I would want to make the process easier and simpler for future students.

## User Stories
As a **user**, I want to be able to:
- *create* a new degree and save it to file <span style="color:green">(DONE)</span>
- *see* a list of existing degrees in file <span style="color:green">(DONE)</span>
- *load* a list of degrees from file <span style="color:green">(DONE)</span>
- *select* a degree and see the required courses <span style="color:green">(DONE)</span>
- *edit* an existing degree in file <span style="color:green">(DONE)</span>
- *create* a new course and add it as a requirement for one or more degrees <span style="color:green">(DONE)</span>
- *add* a new course and save it to file <span style="color:green">(DONE)</span>
- *see* a list of existing courses in file <span style="color:green">(DONE)</span>
- *select* a course and see its number of credits and its prerequisites
- *select* a degree and see similar degrees based on shared courses


## Phase 4 - Task 2
Printing EventLog:


Wed Aug 09 12:04:06 PDT 2023
Computer Science (UNSET) - UNSET added


Wed Aug 09 12:04:06 PDT 2023
Degree faculty changed from UNSET to Science


Wed Aug 09 12:04:06 PDT 2023
Degree type changed from UNSET to Undergraduate


Wed Aug 09 12:04:29 PDT 2023
CPSC 110 (4) added


Wed Aug 09 12:04:47 PDT 2023
CPSC 310 (4) added


Wed Aug 09 12:04:59 PDT 2023
BIOL 111 (3) added


Wed Aug 09 12:05:10 PDT 2023
CPSC 110 (4) added to Computer Science (Undergraduate) - Science


Wed Aug 09 12:05:10 PDT 2023
CPSC 310 (4) added to Computer Science (Undergraduate) - Science


Wed Aug 09 12:05:11 PDT 2023
BIOL 111 (3) added to Computer Science (Undergraduate) - Science


Wed Aug 09 12:05:14 PDT 2023
BIOL 111 (3) removed from Computer Science (Undergraduate) - Science

## Phase 4 - Task 3
One area of my project which needs refactoring is the frame folder in the UI package. Each of these frames (which act as
pop-up windows allowing the user to add/edit degrees and courses) have an association with the main controller
(MainAppGUI) and this creates a high degree of coupling between these classes. To improve, each of the frame classes
should instead have an association with its parent Tab, rather than a dependency, which would allow the frames to
get the main controller from its parent class which would call getController(). This would improve cohesion.

Another area of improvement would be that MainAppGUI is performing more methods than required. On top of UI methods,
this class also performs persistence functionality as well as saving degrees and courses of the current instance. To
improve, I would create new classes which are associated to the main controller (e.g. PersistenceLoader) such that
MainAppGUI does not perform functionality which does not involve UI methods.