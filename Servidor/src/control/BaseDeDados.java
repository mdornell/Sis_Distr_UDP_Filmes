package control;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDados {

    private ArrayList lista = null;
    private static int mtz[][] = null;

    public BaseDeDados() {
        lista = new ArrayList();
        mtz = new int[10][20];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                mtz[i][j] = 0;
            }
        }
    }

    public void insere(String msg) {
        lista.add(msg.trim());
    }

    public String le() {
        String s = "\n";
        int fim = lista.size();

        for (int pos = 0; pos < fim; pos++) {
            s = s + "[" + (pos + 1) + "]" + (String) lista.get(pos) + "\n";
        }
        return s;
    }

    public String filmeNaoAvaliado(int idCli) {
        for (int i = 0; i < 20; i++) {
            if (mtz[idCli][i] == 0) {
                return nomeFilme(i) + " - Ainda não foi Avaliado";
            }
        }
        return "Todos os filmes avaliados";
    }

    public void avaliarFilme(int idCli, int idFilme, int nota) {
        mtz[idCli][idFilme] = nota;
    }

    public String listarFilmesAvaliados(int idCli) {
        String msg = "";

        for (int i = 0; i < 20; ++i) {
            if (mtz[idCli][i] > 0) {
                msg = msg + nomeFilme(i) + " ;\n";
            }
        }
        msg = msg + "\n";
        return msg;
    }

    public String nomeFilme(int idFilme) {
        String[] filmes = {
                "O Poderoso Chefão",
                "Clube da Luta",
                "Pulp Fiction: Tempo de Violência",
                "A Origem",
                "O Senhor dos Anéis: A Sociedade do Anel",
                "Matrix",
                "O Silêncio dos Inocentes",
                "Forrest Gump: O Contador de Histórias",
                "A Vida é Bela",
                "O Resgate do Soldado Ryan",
                "Gladiador",
                "O Rei Leão",
                "Vingadores: Ultimato",
                "Jurassic Park: Parque dos Dinossauros",
                "Star Wars: O Império Contra-Ataca",
                "Interestelar",
                "Corra!",
                "A Forma da Água",
                "Cidadão Kane",
                "Parasita"
        };

        return filmes[idFilme];
    }

    public String nomeCliente(int idCli) {
        String[] s = {
                "Lucas Almeida",
                "Mariana Costa",
                "Rafael Pereira",
                "Ana Silva",
                "Bruno Oliveira",
                "Carolina Santos",
                "Diego Martins",
                "Fernanda Rocha",
                "Guilherme Souza",
                "Júlia Mendes"
        };

        // Verifica se o idCli está dentro dos limites do array
        if (idCli >= 0 && idCli < s.length) {
            return s[idCli]; // Retorna o nome correspondente ao índice
        } else {
            return "Cliente não encontrado"; // Caso o índice seja inválido
        }
    }

    public String recomendarFilme(int idCli) {
        List<String> recomendacoes = new ArrayList<>();
        double menorDistancia = Double.MAX_VALUE;
        int idMenorDistancia = -1; // Para garantir que temos um valor válido

        // Encontra o usuário mais próximo com a menor distância euclidiana
        for (int i = 0; i < 10; i++) {
            if (i != idCli && usuarioFezAvaliacao(i)) { // Verifica se o usuário fez alguma avaliação
                double distancia = calculoEuclidiano(idCli, i);
                System.out.println("Distância entre cliente " + idCli + " e cliente " + i + ": " + distancia); // Print
                                                                                                               // da
                                                                                                               // distância
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    idMenorDistancia = i;
                }
            }
        }

        // Verificação de usuário mais próximo
        if (idMenorDistancia == -1) {
            return "Nenhum usuário fez avaliações suficientes para recomendar ao cliente " + idCli;
        }

        // System.out.println("Usuário mais próximo de " + idCli + " é o usuário " +
        // idMenorDistancia + " com distância: " + menorDistancia);

        // Print das avaliações do cliente atual e do usuário mais próximo
        // System.out.println("Avaliações do cliente " + idCli + ":");
        for (int i = 0; i < 20; i++) {
            System.out.print(mtz[idCli][i] + " ");
        }
        // System.out.println("\nAvaliações do usuário mais próximo (cliente " +
        // idMenorDistancia + "):");
        for (int i = 0; i < 20; i++) {
            System.out.print(mtz[idMenorDistancia][i] + " ");
        }
        System.out.println();

        // Recomendação baseada nos filmes avaliados pelo usuário mais próximo
        // System.out.println("Filmes avaliados pelo usuário " + idMenorDistancia + "
        // que o cliente " + idCli + " não viu:");
        for (int i = 0; i < 20; i++) {
            if (mtz[idMenorDistancia][i] > 0 && mtz[idCli][i] == 0) {
                recomendacoes.add(nomeFilme(i));
                System.out.println("Filme recomendado: " + nomeFilme(i)); // Print do filme recomendado
            }
        }

        // Monta a mensagem de recomendação
        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhum filme para recomendar ao cliente " + idCli); // Print caso não haja recomendações
            return "Nenhum filme para recomendar ao cliente " + idCli;
        } else {
            StringBuilder msg = new StringBuilder("Filmes recomendados para o cliente " + idCli + ": ");
            for (String filme : recomendacoes) {
                msg.append(filme).append(", ");
            }
            // Remove a última vírgula e espaço
            msg.setLength(msg.length() - 2);
            System.out.println("Recomendações finais: " + msg.toString()); // Print das recomendações finais
            return msg.toString();
        }
    }

    // Verifica se o usuário fez alguma avaliação (tem algum valor maior que 0 na
    // linha da matriz)
    private boolean usuarioFezAvaliacao(int idUsuario) {
        for (int i = 0; i < 20; i++) {
            if (mtz[idUsuario][i] > 0) {
                return true; // Usuário fez pelo menos uma avaliação
            }
        }
        return false; // Usuário não fez nenhuma avaliação
    }

    public double calculoEuclidiano(int idCli1, int idCli2) {
        int soma = 0;
        for (int i = 0; i < 20; i++) {
            int diferenca = mtz[idCli1][i] - mtz[idCli2][i];
            soma += diferenca * diferenca; // Soma dos quadrados
        }
        return Math.sqrt(soma); // Retorna a raiz quadrada para obter a distância euclidiana
    }

}
