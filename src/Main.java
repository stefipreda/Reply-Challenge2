import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

  static List<Pair<Integer>> arrangeRandomly(List<Worker> developers, List<Worker> managers, char[][] grid) {
    int nD = developers.size();
    int nM = managers.size();
    List<Pair<Integer>> result = new ArrayList<>();
    for(int i = 0; i < nD + nM; i++) result.add(null);
    List<Pair<Integer>> dSpaces = new ArrayList<>();
    List<Pair<Integer>> mSpaces = new ArrayList<>();
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        if(grid[i][j] == '_') {
          dSpaces.add(new Pair<>(i, j));
        } else if(grid[i][j] == 'M') {
          mSpaces.add(new Pair<>(i, j));
        }
      }
    }
    int dSpacesCnt = 0;
    int mSpacesCnt = 0;
    for(Worker d: developers) {
      if(dSpacesCnt < dSpaces.size()) {
        System.out.println("dev: " + d.id);
        result.set(d.id, new Pair<>(dSpaces.get(dSpacesCnt)));
        dSpacesCnt++;
      } else {
        result.set(d.id, new Pair<>(-1, -1));
      }
    }
    for(Worker m: managers) {
      if(mSpacesCnt < mSpaces.size()) {
        System.out.println("man: " + m.id);
        result.set(m.id, new Pair<>(mSpaces.get(mSpacesCnt)));
        mSpacesCnt++;
      } else {
        result.set(m.id, new Pair<>(-1, -1));
      }
    }
    return result;
  }

  static List<Pair<Integer>> getDirections() {
    List<Pair<Integer>> dirs = new ArrayList<>();
    dirs.add(new Pair<>(1, 0));
    dirs.add(new Pair<>(-1, 0));
    dirs.add(new Pair<>(0, 1));
    dirs.add(new Pair<>(0, -1));
    return dirs;
  }

  static boolean isValidCell(int x, int y, int n, int m) {
    return (0 <= x && x < n && 0 <= y && y < m);
  }

  static long getScore(List<Worker> workers, char[][] grid, List<Pair<Integer>> solution) {
    long result = 0;
    int nW = workers.size();
    int n = grid.length;
    int m = grid[0].length;
    List<Pair<Integer>> directions = getDirections();
    int[][] seating_idxs = new int[n][m];
    for(int i = 0; i < n; i++) {
      for(int j = 0; j < m; j++) {
        seating_idxs[i][j] = -1;
      }
    }

    for(int i = 0; i < solution.size(); i++) {
      int x = solution.get(i).x;
      int y = solution.get(i).y;
      if(x >= 0 && y >= 0) seating_idxs[x][y] = i;
    }
    for(int i = 0; i < n; i++) {
      for(int j = 0; j < m; j++) {
        if(seating_idxs[i][j] < 0) continue;
        for(Pair<Integer> d: directions) {
          if(!isValidCell(i+d.x, j+d.y, n, m)) continue;
          int new_i = i + d.x;
          int new_j = j + d.y;
          if(seating_idxs[new_i][new_j] >= 0) {
            result += Worker.getPairScore(workers.get(seating_idxs[i][j]), workers.get(seating_idxs[new_i][new_j]));
          }
        }
      }
    }
    result /= 2; //calculated every pair 2 times
    return result;
  }

  public static class Pair<T> {
    T x;
    T y;
    Pair (T xx, T yy) {
      x = xx;
      y = yy;
    }
    Pair (Pair<T> p) {
      x = p.x;
      y = p.y;
    }
  }

  static void printSolution(List<Pair<Integer>> solution) {
    for(Pair<Integer> line: solution) {
      if(line.x >= 0) {
        System.out.println(line.y + " " + line.x);
      } else {
        System.out.println("X");
      }
    }
    return;
  }

  static void randomShuffle(List<Worker> workers) {
    Random random = new Random();
    for(int i = workers.size() - 1; i > 0; i--) {
      int j = random.nextInt(i+1);
      Worker tmp = workers.get(i);
      workers.set(i, workers.get(j));
      workers.set(j, tmp);
    }
  }



  public static void main(String[] args) {
    Input input = null;
    try {
      input = InputReader.read();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    List<Worker> developers = input.developers;
    List<Worker> managers = input.managers;
    char [][] grid = input.table;
    List<Worker> workers = new ArrayList<>();
    for(Worker w: developers) workers.add(w);
    for(Worker w: managers) workers.add(w);

    randomShuffle(developers);
    randomShuffle(managers);

    List<Pair<Integer>> solution = arrangeRandomly(developers, managers, grid);
    printSolution(solution);

    /*List<Pair<Integer>> solutionDefault = new ArrayList<>();
    solutionDefault.add(new Pair<>(1, 1));
    solutionDefault.add(new Pair<>(4, 1));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(3, 2));
    solutionDefault.add(new Pair<>(4, 2));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(1, 2));
    solutionDefault.add(new Pair<>(-1, -1));
    solutionDefault.add(new Pair<>(2, 2));

    for(Pair<Integer> p: solutionDefault) {
      int z = p.x;
      p.x = p.y;
      p.y = z;
    }*/

    long result = getScore(workers, grid, solution);
    System.out.println(result);

  }
}
