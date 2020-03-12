import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class InputReader {

    static Input read() throws FileNotFoundException {

        Input input = new Input();
        FileInputStream fis = new FileInputStream("C:\\Users\\Stefi\\Downloads\\Reply-Challenge\\.idea\\b_dream");
        Scanner sc = new Scanner(fis);    //file to be scanned
        //returns true if there is another line to read
        input.m = sc.nextInt();
        input.n = sc.nextInt();
        input.table = new char[input.n][input.m];
        int i = 0;
        String line = sc.nextLine();
        while(sc.hasNextLine() && i < input.n)
        {
            line = sc.nextLine();
            for(int j = 0; j  < input.m; j++) {
                input.table[i][j] = line.charAt(j);
            }
            i++;
        }

        int N = 0;

        Map<String, Integer> skills_ids = new HashMap<>();
        int skillsN = 0;
        Map<String, Integer> companies_ids = new HashMap<>();
        int companiesN = 0;

        input.developers = new LinkedList<>();
        int developers_size = sc.nextInt();

        line = sc.nextLine();

        for(int ii = 0; ii < developers_size; ii++){
            Worker worker = new Worker();
            worker.id = N;
            N++;
            worker.isManager = false;
            line = sc.nextLine();
            String[] info = line.split(" ");

            if(companies_ids.containsKey(info[0])){
                worker.companyId = companies_ids.get(info[0]);
            }
            else{
                worker.companyId = companiesN;
                companies_ids.put(info[0], worker.companyId);
                companiesN++;
            }
            worker.bonus = Integer.parseInt(info[1]);

            int skillId;
            worker.skills = new HashSet<>();
            for(int j = 3; j < info.length; j++){
                if(skills_ids.containsKey(info[j])){
                    skillId = skills_ids.get(info[j]);
                }
                else{
                    skillId = skillsN;
                    skills_ids.put(info[j], skillId);
                    skillsN++;
                }
                worker.skills.add(skillId);
            }


            input.developers.add(worker);

        }

        input.managers = new LinkedList<>();
        int managers_size = sc.nextInt();
        line = sc.nextLine();

        for(int ii = 0; ii < managers_size; ii++){
            Worker worker = new Worker();
            worker.id = N;
            N++;
            worker.isManager = true;
            line = sc.nextLine();
            String[] info = line.split(" ");
            if(companies_ids.containsKey(info[0])){
                worker.companyId = companies_ids.get(info[0]);
            }
            else{
                worker.companyId = companiesN;
                companies_ids.put(info[0], worker.companyId);
                companiesN++;
            }
            worker.bonus = Integer.parseInt(info[1]);

            input.managers.add(worker);

        }

        return input;
    }
}
