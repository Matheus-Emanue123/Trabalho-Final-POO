public class Aeronave {
    private static int proximo_id = 1;

    private int id;
    private int combustivel;
    private int tempo_maximo_espera;

    public Aeronave(int combustivel, int tempo_maximo_espera) {
        this.id = proximo_id++;
        this.combustivel = combustivel;
        this.tempo_maximo_espera = tempo_maximo_espera;
    }

    public int get_id() {
        return id;
    }

    public int get_combustivel() {
        return combustivel;
    }

    public int get_tempo_maximo_espera() {
        return tempo_maximo_espera;
    }
}
