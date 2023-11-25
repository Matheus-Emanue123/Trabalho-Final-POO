import java.util.Queue;
import java.util.LinkedList;

public class Pista {

    private FilaDeEspera filaAterrissagem1;
    private FilaDeEspera filaAterrissagem2;
    private FilaDeEspera filaDecolagem;
    private String nome;

    public Pista() {
        this.filaAterrissagem1 = new FilaDeEspera("Fila de Aterrissagem 1");
        this.filaAterrissagem1.setFila(new LinkedList<>());

        this.filaAterrissagem2 = new FilaDeEspera("Fila de Aterrissagem 2");
        this.filaAterrissagem2.setFila(new LinkedList<>());

        this.filaDecolagem = new FilaDeEspera("Fila de Decolagem");
        this.filaDecolagem.setFila(new LinkedList<>());
    }

    public Pista(String nome, boolean aterrissagem) {
        this.nome = nome;
        this.filaAterrissagem1 = new FilaDeEspera(aterrissagem ? "Fila de Aterrissagem 1" : "Fila de Aterrissagem de Emergência");

        this.filaAterrissagem2 = aterrissagem ? new FilaDeEspera("Fila de Aterrissagem 2") : null;

        this.filaDecolagem = new FilaDeEspera("Fila de Decolagem");
    }

    public FilaDeEspera escolherFilaAterrissagem(){
        if(filaAterrissagem1.tamanho() > filaAterrissagem2.tamanho()){
            return filaAterrissagem1;

        } else if(filaAterrissagem1.tamanho() < filaAterrissagem2.tamanho()){
            return filaAterrissagem2;

        } else {
            int count1 = 0;
            int count2 = 0;

            for (Aeronave a : filaAterrissagem1.getFila()) {
                if (a.getPassageiroEspecial()) {
                    count1++;
                }
            }
            
            for (Aeronave a : filaAterrissagem2.getFila()) {
                if (a.getPassageiroEspecial()) {
                    count2++;
                }
            }
            
            if (count1 > count2) {
                return filaAterrissagem2;
            } else if (count1 < count2) {
                return filaAterrissagem1;
            } else {

                return filaAterrissagem1;
            }
        }
    }

    public FilaDeEspera escolherFilaDecolagem(){
        return filaDecolagem;
    }

    public int quantidadeAeronaves(){
        return filaAterrissagem1.tamanho() + filaAterrissagem2.tamanho() + filaDecolagem.tamanho();
    }

    public int quantidadeAeronavesAterrissagem(){
        return filaAterrissagem1.tamanho() + filaAterrissagem2.tamanho();
    }

    public int quantidadeAeronavesDecolagem(){
        return filaDecolagem.tamanho();
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}