package simuladordetectorfalhas;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetectorFalhas extends Thread {

    private final String arquivoSaida = "src/simuladordetectorfalhas/statistic.log";
    private int sizeList = 0;
    private final long margin;
    private final String trace;
    private final Queue<Long>[] A;
    private final long delta = 100000000;
    private long EA = 0;
    private final Node nodes[] = new Node[10];

    public DetectorFalhas(int sizeList, long margin, String trace) {

        this.sizeList = sizeList;
        this.margin = margin;
        this.trace = trace;
        this.A = new Queue[10];
        
        for (int j = 0; j < 10; j++) {
            this.A[j] = new LinkedList<>();
            nodes[j] = new Node(1);
        }

    }

    public void execute() {

        FileInputStream inputStream;
        Scanner sc;
        String[] stringArray;
        long[] timeout;
        int sizeList, id = 0, lin = 1;
        long ts = 0;
        long[] tPrevious;

        String line;
        timeout = new long[10];
        tPrevious = new long[10];
        
        try {

            inputStream = new FileInputStream(trace);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {

                line = sc.nextLine();

                stringArray = line.split(" ");
                id = Integer.parseInt(stringArray[0]);
                ts = Long.parseLong(stringArray[3]);
                sizeList = A[id].size();

                if (nodes[id].getId() == 1) {
                    nodes[id].setId(id);
                    nodes[id].setTempoInicial(ts);
                }

                EA = (long) computeEA(sizeList, id);
                timeout[id] = EA + margin;

                if ((ts > timeout[id]) && (!A[id].isEmpty())) {
                    nodes[id].setErrosDetectados(nodes[id].getErrosDetectados() + 1);
                    long tempoErroAtual = ts - timeout[id];
                    nodes[id].setTempoErroTotal(nodes[id].getTempoErroTotal() + tempoErroAtual);
                }

                if (A[id].size() == this.sizeList) {
                    A[id].poll();
                }

                nodes[id].setTempoFinal(ts);

                A[id].add(ts);
                tPrevious[id] = ts;
                lin++;
            }

            for (int i = 0; i < 10; i++) {

                if (nodes[i].getId() != 1) {

                    nodes[i].setTempoTotalId(nodes[i].getTempoFinal() - nodes[i].getTempoInicial());
                    nodes[i].setTaxaErro(
                            Double.valueOf(nodes[i].getErrosDetectados())
                            / (Double.valueOf(nodes[i].getTempoTotalId()) / 1000000000));
                    nodes[i].setProbabilidadeAcuracia(1 - (Double.valueOf(nodes[i].getTempoErroTotal()) / Double.valueOf(nodes[i].getTempoTotalId())));

                    String saida = "ID: " + nodes[i].getId()
                            + " | Margem: " + margin
                            + " | Erros detectados: " + nodes[i].getErrosDetectados()
                            + " | Tempo erro: " + nodes[i].getTempoErroTotal()
                            + " | Tempo total: " + nodes[i].getTempoTotalId()
                            + " | Tempo total do ID: " + nodes[i].getTempoTotalId()
                            + " | Taxa erro: " + nodes[i].getTaxaErro()
                            + " | Probabilidade acuracia: " + nodes[i].getProbabilidadeAcuracia();

                    System.out.println(saida);
                    escreverArquivo(saida);

                }

            }

            sc.close();
            inputStream.close();

        } catch (IOException ex) {
            Logger.getLogger(DetectorFalhas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public double computeEA(long heartbeat, int id) {

        double tot = 0, avg = 0;
        int i = 0;
        long ts;

        try {

            NumberFormat f = new DecimalFormat("0.0");
            Queue<Long> q = new LinkedList();
            q.addAll(A[id]);
            while (!q.isEmpty()) {
                ts = q.poll();
                i++;
                tot += ts - (delta * i);
            }
            if (heartbeat > 0) {
                avg = ((1 / (double) heartbeat)
                        * ((double) tot)) + (((double) heartbeat + 1) * delta);
            }
            return avg;

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return 0;
        }

    }

    private void escreverArquivo(String saida) throws FileNotFoundException, IOException {

        OutputStream os;
        BufferedWriter bw;
        OutputStreamWriter osw;

        os = new FileOutputStream(arquivoSaida, true);
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);

        bw.newLine();
        bw.append(saida);
        bw.close();

    }

}
