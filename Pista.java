import java.util.Queue;
import java.util.LinkedList;

public class Pista {
    private Queue<Aeronave> filaAterrissagem1;
    private Queue<Aeronave> filaAterrissagem2;
    private Queue<Aeronave> filaDecolagem;

    public Pista() {
        filaAterrissagem1 = new LinkedList<>();
        filaAterrissagem1 = new LinkedList<>();

        filaDecolagem = new LinkedList<>();

    }

    public Queue<Aeronave> escolherFilaAterrissagem(){
        if(filaAterrissagem1.size() > filaAterrissagem2.size()){
            return filaAterrissagem1;
        }else if(filaAterrissagem1.size() < filaAterrissagem2.size()){
            return filaAterrissagem2;
        } else {
            int count1 = 0;
            int count2 = 0;

            for (Aeronave a : filaAterrissagem1) {
                if (a.getPassageiroEspecial()) {
                    count1++;
                }
            }
            
            for (Aeronave a : filaAterrissagem2) {
                if (a.getPassageiroEspecial()) {
                    count2++;
                }
            }
            
            if (count1 > count2) {
                return filaAterrissagem2;
            } else if (count1 < count2) {
                return filaAterrissagem1;
            } else {
                // return any queue when the counts are equal
                return filaAterrissagem1;
            }
        }
    }

    public int quantidadeAeronaves(){
        return filaAterrissagem1.size() + filaAterrissagem2.size() + filaDecolagem.size();
    }

    public int quantidadeAeronavesAterrissagem(){
        return filaAterrissagem1.size() + filaAterrissagem2.size();
    }

    public int quantidadeAeronavesDecolagem(){
        return filaDecolagem.size();
    }
}
