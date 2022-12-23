package lv.lu.df.combopt.lectsched.ortools;

import com.google.ortools.Loader;
import com.google.ortools.sat.*;

public class ORToolsNQueens {
    static class SolutionPrinter extends CpSolverSolutionCallback {
        public SolutionPrinter(IntVar[] queensIn) {
            solutionCount = 0;
            queens = queensIn;
        }

        @Override
        public void onSolutionCallback() {
            System.out.println("Solution " + solutionCount);
            for (int i = 0; i < queens.length; ++i) {
                for (int j = 0; j < queens.length; ++j) {
                    if (value(queens[j]) == i) {
                        System.out.print("Q");
                    } else {
                        System.out.print("_");
                    }
                    if (j != queens.length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            solutionCount++;
        }

        public int getSolutionCount() {
            return solutionCount;
        }

        private int solutionCount;
        private final IntVar[] queens;
    }

    public static void run() {
        Loader.loadNativeLibraries();
        CpModel model = new CpModel();

        int N = 8;
        IntVar[] queens = new IntVar[N];
        for (int i = 0; i < N; i++) {
            queens[i] = model.newIntVar(0, N - 1, "c" + i);
        }

        model.addAllDifferent(queens);

        LinearExpr[] diag1 = new LinearExpr[N];
        LinearExpr[] diag2 = new LinearExpr[N];

        for (int i = 0; i < N; ++i) {
            diag1[i] = LinearExpr.newBuilder().add(queens[i]).add(i).build();
            diag2[i] = LinearExpr.newBuilder().add(queens[i]).add(-i).build();
        }

        model.addAllDifferent(diag1);
        model.addAllDifferent(diag2);

        CpSolver solver = new CpSolver();
        SolutionPrinter cb = new SolutionPrinter(queens);
        solver.getParameters().setEnumerateAllSolutions(true);
        solver.solve(model, cb);

    }
}
