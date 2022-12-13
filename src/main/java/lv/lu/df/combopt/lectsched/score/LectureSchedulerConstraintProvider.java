package lv.lu.df.combopt.lectsched.score;

import lv.lu.df.combopt.lectsched.domain.Lecture;
import lv.lu.df.combopt.lectsched.domain.Room;
import lv.lu.df.combopt.lectsched.domain.Teacher;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class LectureSchedulerConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {

        return new Constraint[] {
               //everyLecture(constraintFactory),
               //undersizedRoom(constraintFactory),
               undersizedRoom2(constraintFactory),
                sameRoom(constraintFactory),
                sameTeacher(constraintFactory),
                sameStudents(constraintFactory),
                sameRoomTeacher(constraintFactory),
                sameDayTeacher(constraintFactory),
                unassignedRoom(constraintFactory)
        };
    }

    private Constraint sameRoomTeacher2(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Lecture.class, equal(Lecture::getTeacher))
                .filter((l1,l2)->!l1.getRoom().equals(l2.getRoom()))
                .penalize(HardMediumSoftScore.ONE_SOFT)
                .asConstraint("same Room for Teacher");
    }

    private Constraint sameRoomTeacher(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Teacher.class)
                .filter(teacher -> teacher.getRoomList().size() > 1)
                .penalize(HardMediumSoftScore.ONE_SOFT)
                .asConstraint("same Room for Teacher");
    }

    private Constraint sameDayTeacher(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Lecture.class)
                .filter((l1,l2)->!l1.getTimeSlot().getDayOfWeek().equals(l2.getTimeSlot().getDayOfWeek()))
                .penalize(HardMediumSoftScore.ONE_SOFT)
                .asConstraint("same Day for Teacher");
    }

    private Constraint sameStudents(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEachUniquePair(Lecture.class, equal(Lecture::getTimeSlot))
                .filter((l1,l2)->l1.getRegistered().stream().anyMatch(l2.getRegistered()::contains))
                .penalize(HardMediumSoftScore.ONE_HARD)
                .indictWith((l1,l2)->l1.getRegistered().stream().filter(l2.getRegistered()::contains).collect(Collectors.toList()))
                .asConstraint("same Students");
    }

    private Constraint sameRoom(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEachUniquePair(Lecture.class, equal(Lecture::getRoom), equal(Lecture::getTimeSlot))
                .penalize(HardMediumSoftScore.ONE_HARD)
                .asConstraint("same Room");
    }

    private Constraint sameTeacher(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEachUniquePair(Lecture.class, equal(Lecture::getTeacher), equal(Lecture::getTimeSlot))
                .penalize(HardMediumSoftScore.ONE_HARD)
                .asConstraint("same Teacher");
    }

    private Constraint everyLecture(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEach(Lecture.class)
                .penalize(HardMediumSoftScore.ONE_SOFT)
                .asConstraint("every Lecture");
    }

    private Constraint undersizedRoom(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEach(Lecture.class)
                .filter(lecture -> lecture.getRoom().getMaxNumberOfStudents() < lecture.getRegistered().size())
                .penalize(HardMediumSoftScore.ONE_HARD, lecture -> lecture.getRegistered().size() - lecture.getRoom().getMaxNumberOfStudents())
                .asConstraint("undersized Room");
    }

    private Constraint undersizedRoom2(ConstraintFactory constraintFacory) {
        return constraintFacory
                .forEach(Lecture.class)
                .join(Room.class, equal(Lecture::getRoom, Function.identity()))
                .filter((lecture, room) -> room.getMaxNumberOfStudents() < lecture.getRegistered().size())
                .penalize(HardMediumSoftScore.ONE_HARD)
                .asConstraint("undersized Room 2");
    }

    private Constraint unassignedRoom(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachIncludingNullVars(Lecture.class)
                .filter(lecture -> lecture.getRoom() == null)
                .penalize(HardMediumSoftScore.ONE_MEDIUM)
                .asConstraint("Lecture with unassigned room");
    }
}
