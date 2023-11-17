import java.util.Random;
import java.util.Scanner;

public class Main {
    static int qtdAeronavesAterrissagem = 1;
    static int qtdAeronavesDecolagem = 0;

    enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Aeroporto aeroporto = new Aeroporto();
        aeroporto.simularMinuto();
        
        int escolha = 0;

        while(escolha != 3){
            
            menuInicial();
            escolha = scanner.nextInt();

            switch(escolha){
                case 1:
                    gerarAeronaves();
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
    }

    public static void menuInicial(){
        System.out.println("Escolha uma opcao:");
        System.out.println("1 - Geração aleatória de aeronaves");
        System.out.println("2 - Leitura de arquivo de aeronaves");
        System.out.println("3 - Sair");
    }

    public static void gerarAeronaves(){
        Random random = new Random();
        
        System.out.println("Gerando aeronaves...");


        int aeronavesAterrissagem = random.nextInt(3) + 1;

        for(int i = 0; i < aeronavesAterrissagem; i++){
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15) + 1;        

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 7 ? true : false);

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setId(qtdAeronavesAterrissagem);
            qtdAeronavesAterrissagem += 2;

            aeroporto.adicionarAeronaveFilaAterrisagem(aeronave);
        }

        int aeronavesDecolagem = random.nextInt(3) + 1;

        for(int i = 0; i < aeronavesDecolagem; i++){
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15) + 1;            

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 7 ? true : false);
            
            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setId(qtdAeronavesDecolagem++);

            aeroporto.adicionarAeronaveFilaDecolagem(aeronave);
        }
    }    
}