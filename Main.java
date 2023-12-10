import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Aeroporto aeroporto = new Aeroporto();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        int escolha = 0;

        while (escolha != 3) {

            menuInicial();
            escolha = scanner.nextInt();
            scanner.nextLine();
            aeroporto.clearConsole();

            switch (escolha) {
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

    public static void aeronavesAleatorias() {
        while (true) {
            System.out.println("Aperte ENTER para simular um minuto. Digite 0 para sair.");
            String enter = scanner.nextLine();

            if (enter.equals("0")) {
                System.out.println("Saindo da função aleatória...");
                return;
            } else {
                aeroporto.simularMinuto();
                imprimirInformacoes();
            }
        }
    }

    public static void imprimirInformacoes() {
        int escolha;

        do {
            menuInformacoes();
            escolha = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (escolha) {
                case 1:
                    aeroporto.clearConsole();
                    aeroporto.imprimirAcontecimentosMinuto();
                    scanner.nextLine();
                    break;

                case 2:
                    aeroporto.clearConsole();
                    aeroporto.imprimirInformacoesAeroporto();
                    scanner.nextLine();
                    break;

                case 3:
                    int escolhaPista;
                    do {
                        System.out.println("Escolha uma pista: ");
                        System.out.println("1 - Pista 1");
                        System.out.println("2 - Pista 2");
                        System.out.println("3 - Pista 3");
                        System.out.println("0 - Voltar");

                        escolhaPista = scanner.nextInt();
                        scanner.nextLine();
                        aeroporto.clearConsole();

                        switch (escolhaPista) {
                            case 1:
                                aeroporto.getPista1().imprimirInformacoesPista(1, scanner);
                                break;
                            case 2:
                                aeroporto.getPista2().imprimirInformacoesPista(2, scanner);
                                break;
                            case 3:
                                aeroporto.getPista3().imprimirInformacoesPista(3, scanner);
                                break;
                            case 0:
                                System.out.println("Saindo do menu de informações...");
                                break;
                            default:
                                System.out.println("Opcao invalida.");
                                break;
                        }
                        aeroporto.clearConsole();
                    } while (escolhaPista != 0);
                    break;

                case 4:
                    int escolhaFila;

                    do {
                        System.out.println("Escolha uma fila de espera: ");
                        System.out.println("1 - Fila de Aterrissagem 1");
                        System.out.println("2 - Fila de Aterrissagem 2");
                        System.out.println("3 - Fila de Decolagem");
                        System.out.println("0 - Voltar");
                        escolhaFila = scanner.nextInt();
                        scanner.nextLine();

                        aeroporto.clearConsole();

                        switch (escolhaFila) {
                            case 1:
                                aeroporto.getPista1().getFilaAterrissagem1().imprimirInformacoesFila(1, scanner);
                                break;
                            case 2:
                                aeroporto.getPista1().getFilaAterrissagem2().imprimirInformacoesFila(2, scanner);
                                break;
                            case 3:
                                aeroporto.getPista1().getFilaDecolagem().imprimirInformacoesFila(3, scanner);
                                break;
                            case 0:
                                System.out.println("Saindo do menu de informações...");
                                break;
                            default:
                                System.out.println(
                                        "Opcao invalida . Digite novamente qual fila voce deseja ver: 1, 2 ou 3");
                                break;
                        }
                        aeroporto.clearConsole();
                    } while (escolhaFila != 0);
                    break;

                case 5:
                    aeroporto.clearConsole();
                    aeroporto.imprimirSituacaoCombustivel();
                    scanner.nextLine();
                    break;

                case 6:
                    aeroporto.clearConsole();
                    aeroporto.imprimirTodasAeronaves();
                    scanner.nextLine();
                    break;

                case 0:
                    System.out.println("Saindo do menu de informações...");
                    break;

                default:
                    System.out.println("Opção invalida.");
                    aeroporto.clearConsole();
                    break;
            }
            aeroporto.clearConsole();
        } while (escolha != 0);
    }

    public static void iniciar(boolean arquivo) throws FileNotFoundException {
        if (arquivo) {
            leituraArquivoAeronaves();
        } else {
            aeronavesAleatorias();
        }
    }

    public static void leituraArquivoAeronaves() throws FileNotFoundException {
        System.out.println("Lendo arquivo de aeronaves...");
        try {
            Scanner arqScanner = new Scanner(new File("aeronaves.txt"));

            while (arqScanner.hasNextLine()) {
                linhaAeronave(arqScanner);
            }

            System.out.println("-------------------------");
            System.out.println("Arquivo lido com sucesso.");

            arqScanner.close();

            while (true) {
                System.out.println("-------------------------");
                System.out.println("Informações sobre o arquivo: ");
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesAterrissagemArquivo.size())
                        + " aeronaves para aterrisar no arquivo.");
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesDecolagemArquivo.size())
                        + " aeronaves para decolar no arquivo.");
                System.out.println("Tem no total " + (Aeroporto.filaAeronavesAterrissagemArquivo.size()
                        + Aeroporto.filaAeronavesDecolagemArquivo.size()) + " aeronaves no arquivo.");

                System.out.println("-------------------------\n");
                System.out.println("Aperte ENTER para simular um minuto. Digite 0 para sair.");
                String enter = scanner.nextLine();

                if (Aeroporto.filaAeronavesAterrissagemArquivo.isEmpty()
                        && Aeroporto.filaAeronavesDecolagemArquivo.isEmpty()) {
                    System.out.println("Não há mais aeronaves para simular.");
                    scanner.nextLine();
                    imprimirInformacoes();

                    System.out.println("Voltando para o menu inicial...");
                    scanner.nextLine();
                    aeroporto.clearConsole();
                    return;
                } else {

                    if (enter.equals("0")) {
                        System.out.println("Saindo da função arquivo...");
                        return;
                    } else {
                        aeroporto.simularMinutoArquivo();
                        imprimirInformacoes();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void linhaAeronave(Scanner arqScanner) {
        String[] aviaoArrayString = arqScanner.nextLine().split(":");

        int tipo = Integer.parseInt(aviaoArrayString[0]);
        int numPassageiros = Integer.parseInt(aviaoArrayString[1]);
        int combustivel = Integer.parseInt(aviaoArrayString[2]);
        String companhiaAerea = aviaoArrayString[3];
        boolean passageiroEspecial = Integer.parseInt(aviaoArrayString[4]) == 1;

        Aeronave aeronave = new Aeronave(numPassageiros, 0, combustivel, companhiaAerea, passageiroEspecial);

        if (tipo == 0) {
            aeronave.setIdAterrissagem(Aeroporto.idsAeronavesAterrissagem);
            Aeroporto.idsAeronavesAterrissagem += 2;

            Aeroporto.filaAeronavesAterrissagemArquivo.add(aeronave);
        } else {
            aeronave.setIdDecolagem(Aeroporto.idsAeronavesDecolagem);
            Aeroporto.idsAeronavesDecolagem += 2;

            Aeroporto.filaAeronavesDecolagemArquivo.add(aeronave);
        }
    }

    public static void menuInformacoes() {
        System.out.println("O que você deseja ver: ");
        System.out.println("1 - Informações sobre este minuto");
        System.out.println("2 - Informações sobre o aeroporto");
        System.out.println("3 - Informações sobre as pistas");
        System.out.println("4 - Informações sobre as filas de espera");
        System.out.println("5 - Aeronaves de combustível crítico");
        System.out.println("6 - Ver as aeronaves");
        System.out.println("0 - Voltar para simular outro minuto");
    }

    public static void menuInicial() {
        aeroporto.clearConsole();
        System.out.println("MENU INICIAL");
        System.out.println("Escolha uma opcao:");
        System.out.println("1 - Geração aleatória de aeronaves");
        System.out.println("2 - Leitura de arquivo de aeronaves");
        System.out.println("3 - Sair");
    }
}