package lv.lu.df.combopt.lectsched;

import lv.lu.df.combopt.lectsched.domain.*;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.debug("Scheduler start!");
        SolverFactory<LectureSchedule> solverFactory = SolverFactory.create(SolverConfig.createFromXmlResource("lv/lu/df/combopt/lectsched/solverConfig.xml"));

        // Load the problem
        LectureSchedule problem = generateData();

        // Solve the problem
        Solver<LectureSchedule> solver = solverFactory.buildSolver();
        LectureSchedule solution = solver.solve(problem);

        ScoreManager<LectureSchedule, HardSoftScore> scoreManager = ScoreManager.create(solverFactory);
        LOGGER.debug(scoreManager.explainScore(solution).getSummary());

        // Visualize the solution
        printSchedule(solution);
    }

    public static LectureSchedule generateData() {

        List<TimeSlot> timeslotList = new LinkedList<>();
        timeslotList.add(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10,30), LocalTime.of(12, 10)));
        timeslotList.add(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(12,30), LocalTime.of(14, 10)));

        Teacher profV = new Teacher("Prof. Vīksna");
        Teacher profK = new Teacher("Prof. Kloviņs");
        Teacher profP = new Teacher("Prof. Podnieks");
        List<Teacher> teacherList = List.of(profK,profV,profP);

        Student J = new Student("Jānis");
        Student R = new Student("Ralfs");
        Student E = new Student("Elīza");
        List<Student> studentList = List.of(J,R,E);
        List<Student> first = List.of(J);
        List<Student> second = List.of(R,E);

        List<Room> roomList = new LinkedList<>();
        roomList.add(new Room("13", 2));
        roomList.add(new Room("16", 1));

        List<Lecture> lectureList = new LinkedList<>();
        lectureList.add(new Lecture("Algoritmi",null , null ,first,profV));
        lectureList.add(new Lecture("Cilvēka genoms", null , null ,second,profK));
        lectureList.add(new Lecture("Bioinformātika", null , null ,second,profV));
        lectureList.add(new Lecture("Datizrace", null , null ,first,profP));

        LectureSchedule schedule = new LectureSchedule();
        schedule.setTeacherList(teacherList);
        schedule.setTimeSlotList(timeslotList);
        schedule.setRoomList(roomList);
        schedule.setStudentList(studentList);
        schedule.setLectureList(lectureList);

        return schedule;
    }

    private static void printSchedule(LectureSchedule schedule) {
        LOGGER.info("");
        List<Room> roomList = schedule.getRoomList();
        List<Lecture> lectureList = schedule.getLectureList();
        Map<TimeSlot, Map<Room, List<Lecture>>> lectureMap = lectureList.stream()
                .filter(lecture -> lecture.getTimeSlot() != null && lecture.getRoom() != null)
                .collect(Collectors.groupingBy(Lecture::getTimeSlot, Collectors.groupingBy(Lecture::getRoom)));
        LOGGER.info("|                      | " + roomList.stream()
                .map(room -> String.format("%-20s", room.getCode())).collect(Collectors.joining(" | ")) + " |");
        LOGGER.info("|" + "----------------------|".repeat(roomList.size() + 1));
        for (TimeSlot timeslot : schedule.getTimeSlotList()) {
            List<List<Lecture>> cellList = roomList.stream()
                    .map(room -> {
                        Map<Room, List<Lecture>> byRoomMap = lectureMap.get(timeslot);
                        if (byRoomMap == null) {
                            return Collections.<Lecture>emptyList();
                        }
                        List<Lecture> cellLectureList = byRoomMap.get(room);
                        if (cellLectureList == null) {
                            return Collections.<Lecture>emptyList();
                        }
                        return cellLectureList;
                    })
                    .collect(Collectors.toList());

            LOGGER.info("| " + String.format("%-20s",
                    timeslot.getDayOfWeek().toString().substring(0, 3) + " " + timeslot.getStartTime()) + " | "
                    + cellList.stream().map(cellLessonList -> String.format("%-20s",
                    cellLessonList.stream().map(Lecture::getSubject).collect(Collectors.joining(", "))))
                    .collect(Collectors.joining(" | "))
                    + " |");
            LOGGER.info("|                      | "
                    + cellList.stream().map(cellLessonList -> String.format("%-20s",
                    cellLessonList.stream().map(Lecture::getTeacher).map(Teacher::getName).collect(Collectors.joining(", "))))
                    .collect(Collectors.joining(" | "))
                    + " |");
            LOGGER.info("|" + "----------------------|".repeat(roomList.size() + 1));
        }
        List<Lecture> unassignedLessons = lectureList.stream()
                .filter(lecture -> lecture.getTimeSlot() == null || lecture.getRoom() == null)
                .collect(Collectors.toList());
        if (!unassignedLessons.isEmpty()) {
            LOGGER.info("");
            LOGGER.info("Unassigned lessons");
            for (Lecture lecture : unassignedLessons) {
                LOGGER.info("  " + lecture.getSubject() + " - " + lecture.getTeacher());
            }
        }
    }
}
