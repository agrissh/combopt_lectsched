package lv.lu.df.combopt.lectsched.domain;

import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

public class RoomAverageStudentNumberVariableListener implements VariableListener<LectureSchedule, Lecture> {
    @Override
    public void beforeVariableChanged(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {
        if (lecture.getRoom() != null) {
            scoreDirector.beforeVariableChanged(lecture.getRoom(), "averageStudentNumber");
            lecture.getRoom().setAverageStudentNumber(lecture.getRoom().getLectureList().stream()
                    .filter(lect -> !lect.equals(lecture))
                    .mapToInt(lect -> lect.getRegistered().size())
                    .average().orElse(0.0));
            scoreDirector.afterVariableChanged(lecture.getRoom(), "averageStudentNumber");
        }
    }

    @Override
    public void afterVariableChanged(ScoreDirector<LectureSchedule> scoreDirector, Lecture lecture) {
        if (lecture.getRoom() != null) {
            scoreDirector.beforeVariableChanged(lecture.getRoom(), "averageStudentNumber");
            lecture.getRoom().setAverageStudentNumber(lecture.getRoom().getLectureList().stream()
                    .mapToInt(lect -> lect.getRegistered().size())
                    .average().orElse(0.0));
            scoreDirector.afterVariableChanged(lecture.getRoom(), "averageStudentNumber");
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
