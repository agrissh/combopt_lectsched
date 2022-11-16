package lv.lu.df.combopt.lectsched.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.ShadowVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@PlanningEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
public class Teacher {
    private String name;

    @ShadowVariable(sourceEntityClass = Lecture.class, sourceVariableName = "room", variableListenerClass = TeacherRoomListVariableListener.class)
    private Set<Room> roomList;

    public Teacher() {
        setRoomList(new HashSet<>());
    }

    public Teacher(String name) {
        setName(name);
        setRoomList(new HashSet<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(Set<Room> roomList) {
        this.roomList = roomList;
    }
}
