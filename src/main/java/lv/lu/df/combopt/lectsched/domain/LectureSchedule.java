package lv.lu.df.combopt.lectsched.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.Indictment;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private HardMediumSoftScore score;

    private Long id;

    public LectureSchedule() {

        setLectureList(new LinkedList<>());
        setRoomList(new LinkedList<>());
        setTimeSlotList(new LinkedList<>());
        setStudentList(new LinkedList<>());
        setTeacherList(new LinkedList<>());
    }

    @JsonIgnore
    public List<Lecture> getLectures(Room room, TimeSlot timeSlot) {
        return this.getLectureList().stream()
                .filter(lecture -> lecture.getRoom() != null && lecture.getTimeSlot() != null &&
                        lecture.getRoom().equals(room) && lecture.getTimeSlot().equals(timeSlot))
                .collect(Collectors.toList());

    }

    @JsonIgnore
    public String getScoreIndictmentsText(Object object, Indictment<HardMediumSoftScore> indictment) {
        if (indictment == null || (indictment.getScore().getHardScore() == 0 && indictment.getScore().getMediumScore() == 0 && indictment.getScore().getSoftScore() == 0)) {
            return "no impact";
        }
        return "<b>Total score: " + indictment.getScore() + "</b><br />"
                + indictment.getConstraintMatchSet().stream()
                .map(constraintMatch -> constraintMatch.getConstraintName() + " = " + constraintMatch.getScore())
                .collect(Collectors.joining("<br />"));
    }

    @JsonIgnore
    public List<Lecture> getUnassignedLectures() {
        return this.getLectureList().stream()
                .filter(lecture -> lecture.getRoom() == null)
                .collect(Collectors.toList());
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

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
