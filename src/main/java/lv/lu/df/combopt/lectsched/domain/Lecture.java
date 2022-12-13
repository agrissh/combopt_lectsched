package lv.lu.df.combopt.lectsched.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.LinkedList;
import java.util.List;

@PlanningEntity(difficultyComparatorClass = LectureComparator.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "subject")
public class Lecture {
    @PlanningId
    private String subject;
    @PlanningVariable(valueRangeProviderRefs = "rooms", nullable = true)
    private Room room;
    @PlanningVariable(valueRangeProviderRefs = "timeslots")
    private TimeSlot timeSlot;
    private List<Student> registered;
    private Teacher teacher;

    private Boolean pinned;

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

    @PlanningPin
    public Boolean getPinned() {
        return pinned != null && pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }
}
