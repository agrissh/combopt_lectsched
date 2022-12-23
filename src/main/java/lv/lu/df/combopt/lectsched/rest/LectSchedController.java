package lv.lu.df.combopt.lectsched.rest;

import lv.lu.df.combopt.lectsched.domain.LectureSchedule;
import lv.lu.df.combopt.lectsched.domain.LectureScheduleJsonSolutionFileIO;
import lv.lu.df.combopt.lectsched.domain.Room;
import lv.lu.df.combopt.lectsched.ortools.ORToolsNQueens;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/optimizer")
public class LectSchedController {

    private LectureScheduleJsonSolutionFileIO fileIO = new LectureScheduleJsonSolutionFileIO();

    @Autowired
    private SolverManager<LectureSchedule, Long> solverManager;

    private Map<Long, LectureSchedule> scheduleList = new HashMap<Long, LectureSchedule>();

    @Autowired
    private ScoreManager<LectureSchedule, HardMediumSoftScore> scoreManager;


    @GetMapping("/home")
    @ResponseBody
    public String home() {
        LectureSchedule problem = fileIO.read(new File("data/problem.json"));
        solverManager.solve(1l, problem);
        return "Hi!";
    }

    @GetMapping("/or")
    @ResponseBody
    public String or() {
        ORToolsNQueens.run();
        return "ORTools!";
    }

    @PostMapping("/solve")
    @ResponseBody
    public LectureSchedule solve(@RequestBody LectureSchedule problem) throws ExecutionException, InterruptedException {
        return solverManager.solve(1l, problem).getFinalBestSolution();
    }

    @PostMapping("/solve2")
    @ResponseBody
    public void solve2(@RequestBody LectureSchedule problem) {
        solverManager.solveAndListen(problem.getId(), id -> problem, solution -> scheduleList.put(solution.getId(), solution));
    }

    @GetMapping("/get")
    @ResponseBody
    public LectureSchedule getSolution(@RequestParam(name="id") Long id) {
        return scheduleList.get(id);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list",
                scheduleList.values().stream()
                        .collect(Collectors.toList())
        );
        return "list";
    }

    @GetMapping("/show")
    public String show(@RequestParam(name = "scheduleId") Long scheduleId, Model model) {
        LectureSchedule grafiks = this.scheduleList.getOrDefault(scheduleId, null);
        model.addAttribute("indictmentMap", this.scoreManager.explainScore(grafiks).getIndictmentMap());
        model.addAttribute("grafiks", grafiks);
        return "show";
    }

    @PostMapping("/addroom")
    @ResponseBody
    public void addRoom(@RequestBody Room room, @RequestParam(name = "scheduleId") Long scheduleId) {
        solverManager.addProblemChange(scheduleId, (workingSolution, problemChangeDirector) -> {
            LinkedList<Room> roomList = new LinkedList<>(workingSolution.getRoomList());
            workingSolution.setRoomList(roomList);

            problemChangeDirector.addEntity(room, roomList::add);
        });
    }




}
