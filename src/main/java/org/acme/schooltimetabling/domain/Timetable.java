package org.acme.schooltimetabling.domain;

/*  
 * Credits: This file is built oriented with the TimeFold "Hello World" Quick Start Guide
 */

import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

@PlanningSolution
public class Timetable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslots;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Room> rooms;
    @PlanningEntityCollectionProperty
    private List<Lesson> lessons;

    @PlanningScore
    private HardMediumSoftScore score;

    public Timetable() {
    }

    public Timetable(List<Timeslot> timeslots, List<Room> rooms, List<Lesson> lessons) {
        this.timeslots = timeslots;
        this.rooms = rooms;
        this.lessons = lessons;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

}