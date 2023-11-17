public class Aeronave {
    private static int proximo_id = 1;

    private int id;
    private int combustivel;
    private int tempoEspera;
    private int numPassageiros;
    private String companhiaAerea;
    private boolean passageiroEspecial;

    public Aeronave(int numPassageiros, int tempoEspera, int combustivel, String companhiaAerea, boolean passageiroEspecial) {
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

    public int gettempoEspera() {
        return tempoEspera;
    }

    public void settempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public void setNumPassageiros(int numPassageiros) {
        this.numPassageiros = numPassageiros;
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
}
