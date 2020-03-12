import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class InputReader {

    Input read() throws FileNotFoundException {


        FileInputStream fis = new FileInputStream("C:\\Users\\Stefi\\Downloads\\Reply-Challenge\\.idea\\b_dream");
        Scanner sc = new Scanner(fis);    //file to be scanned
        //returns true if there is another line to read
        while(sc.hasNextLine())
        {
            System.out.println(sc.nextLine());      //returns the line that was skipped
        }
        return new Input();
    }
}
