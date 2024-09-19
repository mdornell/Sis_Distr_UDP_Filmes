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
            return s[idCli];  // Retorna o nome correspondente ao índice
        } else {
            return "Cliente não encontrado";  // Caso o índice seja inválido
        }
    }

    public String recomendarFilme(int idCli) {
        // Lista de distâncias euclidianas para cada cliente
        double menorDistancia = Double.MAX_VALUE;
        int idMaisSemelhante = -1;

        // Calcular a distância entre idCli e todos os outros clientes
        for (int i = 0; i < 10; i++) {
            if (i != idCli) {
                double distancia = calculoEuclidiano(idCli, i);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    idMaisSemelhante = i;
                }
            }
        }

        // Se nenhum cliente semelhante foi encontrado
        if (idMaisSemelhante == -1) {
            return "Nenhuma recomendação disponível para o cliente " + nomeCliente(idCli);
        }

        // Encontrar um filme que o cliente não tenha avaliado, mas o cliente semelhante tenha dado uma boa avaliação
        int filmeRecomendado = -1;
        int notaMaisAlta = 0;

        for (int i = 0; i < 20; i++) {
            if (mtz[idCli][i] == 0 && mtz[idMaisSemelhante][i] > 0) { // Cliente não avaliou, mas o cliente semelhante sim
                if (mtz[idMaisSemelhante][i] > notaMaisAlta) { // Verifica se é a maior nota já encontrada
                    notaMaisAlta = mtz[idMaisSemelhante][i];
                    filmeRecomendado = i;
                }
            }
        }

        // Se encontrou um filme para recomendar
        if (filmeRecomendado != -1) {
            return "O filme recomendado para o cliente " + nomeCliente(idCli) + " é: " + nomeFilme(filmeRecomendado);
        }

        return "Nenhuma recomendação disponível para o cliente " + nomeCliente(idCli);
    }

    public double calculoEuclidiano(int idCli1, int idCli2) {
        double soma = 0;
        boolean temAvaliacoes = false;

        // Calcular a distância euclidiana apenas com base nas avaliações feitas (onde a nota não é 0)
        for (int i = 0; i < 20; i++) {
            if (mtz[idCli1][i] != 0 && mtz[idCli2][i] != 0) { // Verifica se ambos avaliaram o filme
                temAvaliacoes = true;
                soma += Math.pow(mtz[idCli1][i] - mtz[idCli2][i], 2);
            }
        }

        // Se não há avaliações suficientes, retorna um valor alto para ignorar essa pessoa
        if (!temAvaliacoes) {
            return Double.MAX_VALUE;
        }

        return Math.sqrt(soma); // Retorna a raiz quadrada da soma das diferenças quadradas
    }

}
