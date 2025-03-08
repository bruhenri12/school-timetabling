package org.acme.schooltimetabling.solver;

/*  
 * Credits: This file is built oriented with the TimeFold "Hello World" Quick Start Guide
 */

import org.acme.schooltimetabling.domain.Lesson;
import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;

import java.time.Duration;

public class TimetableConstraintProvider implements ConstraintProvider {
        @Override
        public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
                return new Constraint[] {
                        // Hard constraints
                        roomConflict(constraintFactory),
                        teacherConflict(constraintFactory),
                        studentGroupConflict(constraintFactory),
                        // Medium constraints
                        subjectDayPartPreference(constraintFactory),
                        // Soft constraints
                        teacherRoomPreference(constraintFactory),
                        teacherSequentialPreference(constraintFactory),
                        studentSequentialPreference(constraintFactory)
                };
        }

        Constraint roomConflict(ConstraintFactory constraintFactory) {
                // A room can accommodate at most one lesson at the same time.
                return constraintFactory
                        // Select each pair of 2 different lessons ...
                        .forEachUniquePair(Lesson.class,
                                // ... in the same timeslot ...
                                Joiners.equal(Lesson::getTimeslot),
                                // ... in the same room ...
                                Joiners.equal(Lesson::getRoom))
                        // ... and penalize each pair with a hard weight.
                        .penalize(HardMediumSoftScore.ONE_HARD)
                        .asConstraint("Room conflict");
        }

        Constraint teacherConflict(ConstraintFactory constraintFactory) {
                // A teacher can teach at most one lesson at the same time.
                return constraintFactory
                        .forEachUniquePair(Lesson.class,
                                Joiners.equal(Lesson::getTimeslot),
                                Joiners.equal(Lesson::getTeacher))
                        .penalize(HardMediumSoftScore.ONE_HARD)
                        .asConstraint("Teacher conflict");
        }

        Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
                // A student can attend at most one lesson at the same time.
                return constraintFactory
                        .forEachUniquePair(Lesson.class,
                                Joiners.equal(Lesson::getTimeslot),
                                Joiners.equal(Lesson::getStudentGroup))
                        .penalize(HardMediumSoftScore.ONE_HARD)
                        .asConstraint("Student group conflict");
        }

        Constraint subjectDayPartPreference(ConstraintFactory constraintFactory) {
                // Students and teachers prefer to have lessons of the same subject in the same day part.
                return constraintFactory
                        .forEachUniquePair(Lesson.class,
                                Joiners.equal(Lesson::getSubject))
                        .filter((lesson1, lesson2) -> lesson1.getTimeslot().getDayPart() != lesson2.getTimeslot().getDayPart())
                        .penalize(HardMediumSoftScore.ONE_MEDIUM)
                        .asConstraint("Subject day part preference");
        }

        Constraint teacherRoomPreference(ConstraintFactory constraintFactory) {
                // A teacher prefers to teach in a single room.
                return constraintFactory
                        .forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getTeacher))
                        .filter((lesson1, lesson2) -> lesson1.getRoom() != lesson2.getRoom())
                        .penalize(HardMediumSoftScore.ONE_SOFT)
                        .asConstraint("Teacher room preference");
        }

        Constraint teacherSequentialPreference(ConstraintFactory constraintFactory) {
                // A teacher prefers to teach sequential lessons and dislikes gaps between lessons.
                return constraintFactory
                        .forEach(Lesson.class)
                        .join(Lesson.class, 
                                Joiners.equal(Lesson::getTeacher),
                                Joiners.equal((lesson) -> lesson.getTimeslot().getDayOfWeek()))
                        .filter((lesson1, lesson2) -> {
                        Duration between = Duration.between(lesson1.getTimeslot().getEndTime(),
                                lesson2.getTimeslot().getStartTime());
                        return !between.isNegative() && between.compareTo(Duration.ofMinutes(10)) <= 0;
                        })
                        .reward(HardMediumSoftScore.ONE_SOFT)
                        .asConstraint("Teacher sequential preference");
        }

        Constraint studentSequentialPreference(ConstraintFactory constraintFactory) {
                // A student prefers to attend sequential lessons and dislikes gaps between lessons.
                return constraintFactory
                        .forEach(Lesson.class)
                        .join(Lesson.class, 
                                Joiners.equal(Lesson::getStudentGroup),
                                Joiners.equal((lesson) -> lesson.getTimeslot().getDayOfWeek()))
                        .filter((lesson1, lesson2) -> {
                        Duration between = Duration.between(lesson1.getTimeslot().getEndTime(),
                                lesson2.getTimeslot().getStartTime());
                        return !between.isNegative() && between.compareTo(Duration.ofMinutes(10)) <= 0;
                        })
                        .penalize(HardMediumSoftScore.ONE_SOFT)
                        .asConstraint("Student sequential preference");
        }
}