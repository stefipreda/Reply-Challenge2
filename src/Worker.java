import java.util.Set;

public class Worker {
  int id;
  int companyId;
  int bonus;
  boolean isManager;
  Set<Integer> skills;
  static long getPairScore(Worker x, Worker y) {
    long res = 0;
    if(x.companyId == y.companyId) {
      long prod = ((long)(x.bonus)*(long)(y.bonus));
      res += prod;
    }
    if(x.isManager || y.isManager) {
      return res;
    }
    long common = 0;
    long distinct = 0;
    for(int skill: x.skills) {
      if(y.skills.contains(skill)) common++;
    }
    distinct = x.skills.size() + y.skills.size() - 2 * common;
    res += (common * distinct);
    return res;
  }
}