# School Timetabling
## Specification
Your service will assign Lesson instances to Timeslot and Room instances automatically by using AI to adhere to the following hard and soft scheduling constraints:

- A room can have at most one lesson at the same time.
- A teacher can teach at most one lesson at the same time.
- A student can attend at most one lesson at the same time.
- A teacher prefers to teach in a single room.
- A teacher prefers to teach sequential lessons and dislikes gaps between lessons.

Mathematically speaking, school timetabling is an NP-hard problem. That means it is difficult to scale. Simply iterating through all possible combinations with brute force would take millions of years for a non-trivial data set, even on a supercomputer. Fortunately, AI constraint solvers such as OptaPlanner have advanced algorithms that deliver a near-optimal solution in a reasonable amount of time. What is considered to be a reasonable amount of time is subjective and depends on the goals of your problem.
