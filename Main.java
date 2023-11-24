import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int qtdAeronavesAterrissagem = 1;
    static int qtdAeronavesDecolagem = 2;
    static Queue<Aeronave> filaAeronavesDecolagemArquivo = new LinkedList<Aeronave>();
    static Queue<Aeronave> filaAeronavesAterrissagemArquivo = new LinkedList<Aeronave>();

    enum CompanhiaAerea {
        GOL,
        LATAM,
        AZUL,
        AmericanAirlines
    }

    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Aeroporto aeroporto = new Aeroporto();
        aeroporto.simularMinuto();
        
        int escolha = 0;

        while(escolha != 3){
            
            menuInicial();
            escolha = scanner.nextInt();

            switch(escolha){
                case 1:
                    iniciar(false);
                    break;
                case 2:
                    iniciar(true);
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

    public static void iniciar(boolean arquivo) throws FileNotFoundException{
        if(arquivo){
            leituraArquivoAeronaves();
        } else{
            gerarAeronaves();
        }

        aeroporto.simularMinuto();
    }

    public static Aeronave linhaAeronave(Scanner arqScanner){
        String[] aviaoArrayString = arqScanner.nextLine().split(":");

        int numPassageiros = Integer.parseInt(aviaoArrayString[0]);
        int combustivel = Integer.parseInt(aviaoArrayString[1]);
        String companhiaAerea = aviaoArrayString[2];
        boolean passageiroEspecial = Boolean.parseBoolean(aviaoArrayString[3]);

        return new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
    }

    public static void leituraArquivoAeronaves() throws FileNotFoundException{
        System.out.println("Lendo arquivo de aeronaves.");
        try {
            Scanner arqScanner = new Scanner(new File("aeronavesAterrissagem.txt"));
            
            while(arqScanner.hasNextLine()){
                Aeronave aeronave = linhaAeronave(arqScanner);
                aeronave.setId(qtdAeronavesAterrissagem);
                qtdAeronavesAterrissagem += 2;

                filaAeronavesAterrissagemArquivo.add(aeronave);
            }

            arqScanner = new Scanner(new File("aeronavesDecolagem.txt"));

            while(arqScanner.hasNextLine()){
                Aeronave aeronave = linhaAeronave(arqScanner);
                aeronave.setId(qtdAeronavesDecolagem);
                qtdAeronavesDecolagem += 2;

                filaAeronavesDecolagemArquivo.add(aeronave);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void gerarAeronaves(){
        Random random = new Random();
    
        System.out.println("Gerando aeronaves...");

        int aeronavesAterrissagem = random.nextInt(3) + 1;

        for(int i = 0; i < aeronavesAterrissagem; i++){
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = random.nextInt(15) + 1;        

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 9 ? true : false);

            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setId(qtdAeronavesAterrissagem);
            qtdAeronavesAterrissagem += 2;

            aeroporto.adicionarAeronaveFilaAterrisagem(aeronave);
        }

        int aeronavesDecolagem = random.nextInt(3) + 1;

        for(int i = 0; i < aeronavesDecolagem; i++){
            int numPassageiros = random.nextInt(380) + 1;
            int combustivel = 15;            

            String companhiaAerea = CompanhiaAerea.values()[random.nextInt(CompanhiaAerea.values().length)].toString();

            boolean passageiroEspecial = (random.nextInt(10) + 1 > 9 ? true : false);
            
            Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);
            aeronave.setId(qtdAeronavesDecolagem);
            qtdAeronavesAterrissagem += 2;

            aeroporto.adicionarAeronaveFilaDecolagem(aeronave);
        }
    }    
}