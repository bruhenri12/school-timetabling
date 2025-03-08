package org.acme.schooltimetabling;

/*  
 * Credits: This file is built oriented with the TimeFold "Hello World" Quick Start Guide
 */

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;

import org.acme.schooltimetabling.domain.Lesson;
import org.acme.schooltimetabling.domain.Room;
import org.acme.schooltimetabling.domain.Timeslot;
import org.acme.schooltimetabling.domain.Timetable;
import org.acme.schooltimetabling.solver.TimetableConstraintProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimetableApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableApp.class);

    public static void main(String[] args) {
        SolverFactory<Timetable> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(Timetable.class)
                .withEntityClasses(Lesson.class)
                .withConstraintProviderClass(TimetableConstraintProvider.class)
                // It's recommended to run for at least 5 minutes ("5m") otherwise.
                .withTerminationSpentLimit(Duration.ofSeconds(15)));

        // Load the problem
        Timetable problem = generateDemoData();

        // Solve the problem
        Solver<Timetable> solver = solverFactory.buildSolver();
        Timetable solution = solver.solve(problem);

        // Visualize the solution
        printTimetable(solution);
    }

    public static Timetable generateDemoData() {
        List<Timeslot> timeslots = new ArrayList<>(10);
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

        List<Room> rooms = new ArrayList<>(3);
        rooms.add(new Room("Room A"));
        rooms.add(new Room("Room B"));
        rooms.add(new Room("Room C"));

        List<Lesson> lessons = new ArrayList<>();
        long nextLessonId = 0L;
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Math", "A. Turing", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Math", "A. Turing", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Physics", "M. Curie", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Chemistry", "M. Curie", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Biology", "C. Darwin", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "History", "I. Jones", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "English", "I. Jones", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "English", "I. Jones", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Spanish", "P. Cruz", "9th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Spanish", "P. Cruz", "9th grade"));

        lessons.add(new Lesson(Long.toString(nextLessonId++), "Math", "A. Turing", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Math", "A. Turing", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Math", "A. Turing", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Physics", "M. Curie", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Chemistry", "M. Curie", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "French", "M. Curie", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "Geography", "C. Darwin", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "History", "I. Jones", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId++), "English", "P. Cruz", "10th grade"));
        lessons.add(new Lesson(Long.toString(nextLessonId), "Spanish", "P. Cruz", "10th grade"));

        return new Timetable(timeslots, rooms, lessons);
    }

    /**
     * Prints the timetable in a formatted manner.
     *
     * @param timetable the timetable to print
     */
    private static void printTimetable(Timetable timetable) {
        LOGGER.info("");
        List<Room> rooms = timetable.getRooms();
        List<Lesson> lessons = timetable.getLessons();
        
        // Group lessons by timeslot and room
        Map<Timeslot, Map<Room, List<Lesson>>> lessonMap = lessons.stream()
                .filter(lesson -> lesson.getTimeslot() != null && lesson.getRoom() != null)
                .collect(Collectors.groupingBy(Lesson::getTimeslot, Collectors.groupingBy(Lesson::getRoom)));
        
        // Print header
        LOGGER.info("|            | " + rooms.stream()
                .map(room -> String.format("%-10s", room.getName()))
                .collect(Collectors.joining(" | ")) + " |");
        LOGGER.info("|" + "------------|".repeat(rooms.size() + 1));

        // Print each timeslot row
        for (Timeslot timeslot : timetable.getTimeslots()) {
            List<List<Lesson>> cells = rooms.stream()
                    .map(room -> {
                        Map<Room, List<Lesson>> byRoomMap = lessonMap.get(timeslot);
                        if (byRoomMap == null) {
                            return Collections.<Lesson>emptyList();
                        }
                        List<Lesson> cellLessons = byRoomMap.get(room);
                        return Objects.requireNonNullElse(cellLessons, Collections.<Lesson>emptyList());
                    }).toList();

            // Print timeslot and subjects
            LOGGER.info("| " + String.format("%-10s",
                    timeslot.getDayOfWeek().toString().substring(0, 3) + " " + timeslot.getStartTime()) + " | "
                    + cells.stream().map(cellLessons -> String.format("%-10s",
                            cellLessons.stream().map(Lesson::getSubject).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            
            // Print teachers
            LOGGER.info("|            | "
                    + cells.stream().map(cellLessons -> String.format("%-10s",
                            cellLessons.stream().map(Lesson::getTeacher).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            
            // Print student groups
            LOGGER.info("|            | "
                    + cells.stream().map(cellLessons -> String.format("%-10s",
                            cellLessons.stream().map(Lesson::getStudentGroup).collect(Collectors.joining(", "))))
                            .collect(Collectors.joining(" | "))
                    + " |");
            
            LOGGER.info("|" + "------------|".repeat(rooms.size() + 1));
        }

        // Print unassigned lessons
        List<Lesson> unassignedLessons = lessons.stream()
                .filter(lesson -> lesson.getTimeslot() == null || lesson.getRoom() == null)
                .toList();
        if (!unassignedLessons.isEmpty()) {
            LOGGER.info("");
            LOGGER.info("Unassigned lessons");
            for (Lesson lesson : unassignedLessons) {
                LOGGER.info("  " + lesson.getSubject() + " - " + lesson.getTeacher() + " - " + lesson.getStudentGroup());
            }
        }
    }

}