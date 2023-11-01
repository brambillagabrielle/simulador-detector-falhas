package simuladordetectorfalhas;

public class Node {
    
    private int id;
    private int errosDetectados;
    private long tempoErroTotal;
    private long tempoInicial;
    private long tempoFinal;
    private long tempoTotalId;
    private double taxaErro;
    private double probabilidadeAcuracia;

    public Node(int idProcesso) {
        
        this.id = idProcesso; 
        this.errosDetectados = 0;
        this.tempoErroTotal = 0;
        this.tempoInicial = 0;
        this.tempoFinal = 0;
        this.tempoTotalId = 0;
        this.taxaErro = 0;
        this.probabilidadeAcuracia = 0;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getErrosDetectados() {
        return errosDetectados;
    }

    public void setErrosDetectados(int errosDetectados) {
        this.errosDetectados = errosDetectados;
    }

    public long getTempoErroTotal() {
        return tempoErroTotal;
    }

    public void setTempoErroTotal(long tempoErroTotal) {
        this.tempoErroTotal = tempoErroTotal;
    }

    public long getTempoInicial() {
        return tempoInicial;
    }

    public void setTempoInicial(long tempoInicial) {
        this.tempoInicial = tempoInicial;
    }

    public long getTempoFinal() {
        return tempoFinal;
    }

    public void setTempoFinal(long tempoFinal) {
        this.tempoFinal = tempoFinal;
    }

    public long getTempoTotalId() {
        return tempoTotalId;
    }

    public void setTempoTotalId(long tempoTotalId) {
        this.tempoTotalId = tempoTotalId;
    }

    public double getTaxaErro() {
        return taxaErro;
    }

    public void setTaxaErro(double taxaErro) {
        this.taxaErro = taxaErro;
    }

    public double getProbabilidadeAcuracia() {
        return probabilidadeAcuracia;
    }

    public void setProbabilidadeAcuracia(double probabilidadeAcuracia) {
        this.probabilidadeAcuracia = probabilidadeAcuracia;
    }
    
}
