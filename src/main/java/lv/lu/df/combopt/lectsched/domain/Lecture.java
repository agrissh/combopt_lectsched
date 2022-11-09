package lv.lu.df.combopt.lectsched.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.LinkedList;
import java.util.List;

@PlanningEntity(difficultyComparatorClass = LectureComparator.class)
public class Lecture {
    @PlanningId
    private String subject;
    @PlanningVariable(valueRangeProviderRefs = "rooms")
    private Room room;
    @PlanningVariable(valueRangeProviderRefs = "timeslots")
    private TimeSlot timeSlot;
    private List<Student> registered;
    private Teacher teacher;

    public Lecture() {
        setRegistered(new LinkedList<>());
    }

    public Lecture(String subject, Room room, TimeSlot timeSlot, List<Student> students, Teacher teacher) {
        setRegistered(students);
        setRoom(room);
        setTeacher(teacher);
        setSubject(subject);
        setTimeSlot(timeSlot);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<Student> getRegistered() {
        return registered;
    }

    public void setRegistered(List<Student> registered) {
        this.registered = registered;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return this.getSubject();
    }
}
