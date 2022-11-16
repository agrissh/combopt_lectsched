package lv.lu.df.combopt.lectsched.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.optaplanner.persistence.jackson.impl.domain.solution.JacksonSolutionFileIO;

public class LectureScheduleJsonSolutionFileIO extends JacksonSolutionFileIO<LectureSchedule> {
    public LectureScheduleJsonSolutionFileIO() {
        super(LectureSchedule.class, new ObjectMapper().findAndRegisterModules());
    }
}
