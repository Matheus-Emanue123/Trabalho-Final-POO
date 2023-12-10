public class Aeronave {
    private int id;
    private int combustivel;
    private int tempoEspera;
    private int numPassageiros;
    private String companhiaAerea;
    private boolean passageiroEspecial;
    private Pista pista;
    private FilaDeEspera fila;

    public Aeronave(int numPassageiros, int tempoEspera, int combustivel, String companhiaAerea,
            boolean passageiroEspecial) {
        this.numPassageiros = numPassageiros;
        this.tempoEspera = tempoEspera;
        this.combustivel = combustivel;
        this.companhiaAerea = companhiaAerea;
        this.passageiroEspecial = passageiroEspecial;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public String getCompanhiaAerea() {
        return companhiaAerea;
    }

    public FilaDeEspera getFila() {
        return this.fila;
    }

    public int getId() {
        return id;
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    public Boolean getPassageiroEspecial() {
        return passageiroEspecial;
    }

    public Pista getPista() {
        return this.pista;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void imprimir() {
        System.out.println("ID: " + this.id + " - Combustível: " + this.combustivel + " - Tempo de espera: "
                + this.tempoEspera + " - Passageiro especial: " + this.passageiroEspecial + " - Companhia aérea: "
                + this.companhiaAerea + " - Número de passageiros: " + this.numPassageiros);
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public void setCompanhiaAerea(String companhiaAerea) {
        this.companhiaAerea = companhiaAerea;
    }

    public void setFila(FilaDeEspera fila) {
        this.fila = fila;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAterrissagem(int id) {
        if (id % 2 == 0) {
            this.id = id + 1;
        } else {
            this.id = id;
        }
    }

    public void setIdDecolagem(int id) {
        if (id % 2 == 0) {
            this.id = id;
        } else {
            this.id = id + 1;
        }
    }

    public void setNumPassageiros(int numPassageiros) {
        this.numPassageiros = numPassageiros;
    }

    public void setPassageiroEspecial(boolean passageiroEspecial) {
        this.passageiroEspecial = passageiroEspecial;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public boolean verificarCombustivelCritico() {
        return this.combustivel < 6;
    }
}