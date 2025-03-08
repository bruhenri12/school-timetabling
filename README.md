# School Timetabling

## Pre-requisites
- Java 23 JDK
- Gradle 8.13

## How to run
>$ gradle run

Is recommended to save the output in a file, so:

>$ gradle run > output.txt

## Testing with new Timetables
Follow the examples found in "src\main\java\org\acme\schooltimetabling\TimetableApp.java", and modify the "generateProblemTable()" method.

## Specification
Your service will assign Lesson instances to Timeslot and Room instances automatically by using AI to adhere to the following hard and soft scheduling constraints:

- A room can have at most one lesson at the same time. (hard)
- A teacher can teach at most one lesson at the same time. (hard)
- A student can attend at most one lesson at the same time. (hard)
- A teacher prefers to teach in a single room. (soft)
- A teacher prefers to teach sequential lessons and dislikes gaps between lessons. (soft)

Additional constraints:
- Students and teachers prefer to have lessons of the same subject in the same day part. (medium)
- A student prefers to attend sequential lessons and dislikes gaps between lessons. (soft)

Mathematically speaking, school timetabling is an NP-hard problem. That means it is difficult to scale. Simply iterating through all possible combinations with brute force would take millions of years for a non-trivial data set, even on a supercomputer. Fortunately, AI constraint solvers such as OptaPlanner or Timefold have advanced algorithms that deliver a near-optimal solution in a reasonable amount of time. What is considered to be a reasonable amount of time is subjective and depends on the goals of your problem.

## Credits
This project was made using with reference the official Timefold Solver "Hello World" Quick Start tutorial.
