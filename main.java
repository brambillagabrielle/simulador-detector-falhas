package simuladordetectorfalhas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

    public static void main(String args[]) throws FileNotFoundException {
        
        FileInputStream inputStream;
        Scanner sc;
        int sizeList, p;
        long margin;
        String trace;
        String[] sArray;
        String line;

        inputStream = new FileInputStream("src/simuladordetectorfalhas/execplan.txt");
        sc = new Scanner(inputStream, "UTF-8");

        line = sc.nextLine();
        while (sc.hasNextLine()) {
            
            line = sc.nextLine();
            sArray = line.split(";");
            
            if (!sArray[0].equals("#")) {
                
                p = Integer.parseInt(sArray[0]);
                sizeList = Integer.parseInt(sArray[1]);
                margin = Long.parseLong(sArray[2]);
                trace = "src/simuladordetectorfalhas/traces/trace2.log";                
                System.out.println(p + "|" + sizeList + "|" + margin + "|" +
                        trace);
                DetectorFalhas teste = new DetectorFalhas(sizeList, margin, trace);
                teste.execute();
                
            }
            
        }
        
    }
    
}
