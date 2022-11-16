package lv.lu.df.combopt.lectsched.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;
import org.optaplanner.core.api.domain.variable.ShadowVariable;

import java.util.ArrayList;
import java.util.List;

@PlanningEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "code")
public class Room {
    private String code;
    private Integer maxNumberOfStudents;

    @InverseRelationShadowVariable(sourceVariableName = "room")
    private List<Lecture> lectureList;

    @ShadowVariable(sourceEntityClass = Lecture.class, sourceVariableName = "room", variableListenerClass = RoomAverageStudentNumberVariableListener.class)
    private Double averageStudentNumber;

    public Room() {
        setLectureList(new ArrayList<>());
    }

    public Room(String code, Integer maxNumberOfStudents) {
        setCode(code);
        setMaxNumberOfStudents(maxNumberOfStudents);
        setLectureList(new ArrayList<>());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public void setMaxNumberOfStudents(Integer maxNumberOfStudents) {
        this.maxNumberOfStudents = maxNumberOfStudents;
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    public Double getAverageStudentNumber() {
        return averageStudentNumber;
    }

    public void setAverageStudentNumber(Double averageStudentNumber) {
        this.averageStudentNumber = averageStudentNumber;
    }
}
