package lv.lu.df.combopt.lectsched.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.LinkedList;
import java.util.List;

@PlanningSolution
public class LectureSchedule {
    @PlanningEntityCollectionProperty
    private List<Lecture> lectureList;
    @ValueRangeProvider(id = "rooms")
    @PlanningEntityCollectionProperty
    private List<Room> roomList;
    @ValueRangeProvider(id = "timeslots")
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlotList;
    @ProblemFactCollectionProperty
    private List<Student> studentList;
    @PlanningEntityCollectionProperty
    private List<Teacher> teacherList;

    @PlanningScore
    private HardSoftScore score;

    public LectureSchedule() {
        setLectureList(new LinkedList<>());
        setRoomList(new LinkedList<>());
        setTimeSlotList(new LinkedList<>());
        setStudentList(new LinkedList<>());
        setTeacherList(new LinkedList<>());
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
