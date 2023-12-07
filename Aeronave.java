public class Aeronave {
    private static int proximo_id = 1;

    private int id;
    private int combustivel;
    private int tempoEspera;
    private int numPassageiros;
    private String companhiaAerea;
    private boolean passageiroEspecial;

    public Aeronave(int numPassageiros, int tempoEspera, int combustivel, String companhiaAerea,
            boolean passageiroEspecial) {
        this.id = proximo_id;
        proximo_id += 2;
        this.numPassageiros = numPassageiros;
        this.tempoEspera = tempoEspera;
        this.combustivel = combustivel;
        this.companhiaAerea = companhiaAerea;
        this.passageiroEspecial = passageiroEspecial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    public void setNumPassageiros(int numPassageiros) {
        this.numPassageiros = numPassageiros;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public String getCompanhiaAerea() {
        return companhiaAerea;
    }

    public void setCompanhiaAerea(String companhiaAerea) {
        this.companhiaAerea = companhiaAerea;
    }

    public boolean getPassageiroEspecial() {
        return passageiroEspecial;
    }

    public void setPassageiroEspecial(boolean passageiroEspecial) {
        this.passageiroEspecial = passageiroEspecial;
    }

    public void setIdDecolagem(int id) {
        if (id % 2 == 0) {
            this.id = id;
        } else {
            this.id = id + 1;
        }
    }

    public void setIdAterrissagem(int id) {
        if (id % 2 == 0) {
            this.id = id + 1;
        } else {
            this.id = id;
        }
    }

    public boolean verificarCombustivelCritico() {
        return this.combustivel < 6;
    }

    public void imprimirAeronave() {
        System.out.println("ID: " + this.id + " - Combustível: " + this.combustivel + " - Tempo de espera: "
                + this.tempoEspera + " - Passageiro especial: " + this.passageiroEspecial);
    }
}