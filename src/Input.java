import java.util.List;

public class Input {
    List<Worker> developers;
    List<Worker> managers;
    int m, n;
    char[][] table;

    public void print(){
        for(Worker w : developers){
            System.out.println(w.id);
        }
    }
}
