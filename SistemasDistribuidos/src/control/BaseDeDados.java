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
                return idCli + "; " + i;
            }
        }
        return idCli + "; 0";
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

    private String nomeFilme(int idFilme) {
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
}
