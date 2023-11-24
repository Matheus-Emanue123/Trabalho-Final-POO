import java.util.Queue;
import java.util.LinkedList;

public class Pista {

    FilaDeEspera filaAterrissagem1;
    FilaDeEspera filaAterrissagem2;
    FilaDeEspera filaDecolagem;

    public Pista() {
        filaAterrissagem1 = new FilaDeEspera();
        filaAterrissagem1.setFila(new LinkedList<>());

        filaAterrissagem2 = new FilaDeEspera();
        filaAterrissagem2.setFila(new LinkedList<>());

        filaDecolagem = new FilaDeEspera();
        filaDecolagem.setFila(new LinkedList<>());
    }

    public Queue<Aeronave> escolherFilaAterrissagem(){
        if(filaAterrissagem1.tamanho() > filaAterrissagem2.tamanho()){
            return filaAterrissagem1.getFila();

        } else if(filaAterrissagem1.tamanho() < filaAterrissagem2.tamanho()){
            return filaAterrissagem2.getFila();

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
                return filaAterrissagem2.getFila();
            } else if (count1 < count2) {
                return filaAterrissagem1.getFila();
            } else {

                return filaAterrissagem1.getFila();
            }
        }
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
}