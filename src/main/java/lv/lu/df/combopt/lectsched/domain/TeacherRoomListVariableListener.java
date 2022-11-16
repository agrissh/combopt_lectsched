package lv.lu.df.combopt.lectsched.domain;

import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

public class TeacherRoomListVariableListener implements VariableListener<LectureSchedule, Lecture> {
    @Override
    public void beforeVariableChanged(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {
        if (lecture.getRoom() != null) {

            if (scoreDirector.getWorkingSolution().getLectureList().stream()
                    .filter(lect -> lect.getRoom() != null &&
                            lect.getTeacher().equals(lecture.getTeacher())
                            && lecture.getRoom().equals(lect.getRoom()))
            .count() == 1) {
                scoreDirector.beforeVariableChanged(lecture.getTeacher(), "roomList");
                lecture.getTeacher().getRoomList().remove(lecture.getRoom());
                scoreDirector.afterVariableChanged(lecture.getTeacher(), "roomList");
            }
        }
    }

    @Override
    public void afterVariableChanged(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {
        if (lecture.getRoom() != null) {
            scoreDirector.beforeVariableChanged(lecture.getTeacher(), "roomList");
            lecture.getTeacher().getRoomList().add(lecture.getRoom());
            scoreDirector.afterVariableChanged(lecture.getTeacher(), "roomList");
        }

    }

    @Override
    public void beforeEntityAdded(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {

    }

    @Override
    public void afterEntityAdded(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {

    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {

    }

    @Override
    public void afterEntityRemoved(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {

    }
}
