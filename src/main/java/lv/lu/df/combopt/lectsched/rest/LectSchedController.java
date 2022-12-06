package lv.lu.df.combopt.lectsched.rest;

import lv.lu.df.combopt.lectsched.domain.LectureSchedule;
import lv.lu.df.combopt.lectsched.domain.LectureScheduleJsonSolutionFileIO;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
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
    private ScoreManager<LectureSchedule, HardSoftScore> scoreManager;


    @GetMapping("/home")
    @ResponseBody
    public String home() {
        LectureSchedule problem = fileIO.read(new File("data/problem.json"));
        solverManager.solve(1l, problem);
        return "Hi!";
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




}
