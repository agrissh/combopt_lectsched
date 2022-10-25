package lv.lu.df.combopt.lectsched.score;

import lv.lu.df.combopt.lectsched.domain.Lecture;
import lv.lu.df.combopt.lectsched.domain.LectureSchedule;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;

public class LectureSchedulerEasyScoreCalculator implements EasyScoreCalculator<LectureSchedule, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(LectureSchedule lectureSchedule) {
        int hardScore = 0, softScore = 0;
        for (Lecture lecture1: lectureSchedule.getLectureList()) {
            for (Lecture lecture2: lectureSchedule.getLectureList()) {

                // vienlaicigas lekcijas
                if (lecture1.getTimeSlot() != null && lecture1.getTimeSlot().equals(lecture2.getTimeSlot())) {

                    // viena telpa
                    if (lecture1.getRoom().equals(lecture2.getRoom())) {
                        hardScore--;
                    }

                    // viens pasniedzejs
                    if (lecture1.getTeacher().equals(lecture2.getTeacher())) {
                        hardScore--;
                    }

                    // viens un tas pats students
                    if (lecture1.getRegistered().stream().anyMatch(lecture2.getRegistered()::contains)) {
                        hardScore--;
                    }
                }

                // tas pats pasniedzejs
                if (lecture1.getTeacher().equals(lecture2.getTeacher())) {
                    // nav taa pati telpa
                    if (lecture1.getRoom() != null && !lecture1.getRoom().equals(lecture2.getRoom())) {
                        softScore--;
                    }

                    // nav taa pati diena
                    if (lecture1.getTimeSlot() != null && lecture2.getTimeSlot() != null && !lecture1.getTimeSlot().getDayOfWeek().equals(lecture2.getTimeSlot().getDayOfWeek())) {
                        softScore--;
                    }
                }
            }
            // pietiekosi liela telpa
            if (lecture1.getRoom() != null && lecture1.getRoom().getMaxNumberOfStudents() < lecture1.getRegistered().size()) {
                hardScore--;
            }
        }

        return HardSoftScore.of(hardScore,softScore);
    }
}
