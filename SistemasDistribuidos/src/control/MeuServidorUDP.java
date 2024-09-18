package control;

import java.net.*;
import java.io.*;

public class MeuServidorUDP {
    private static BaseDeDados bd = null;

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        bd = new BaseDeDados();

        try {
            aSocket = new DatagramSocket(6789);

            while (true) {
                byte[] buffer = new byte[600];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String mensagem = new String(request.getData(), 0, request.getLength());
                
                // [0:Mensagem; 1:IndexFilme; 2:indexCliente; 3:Nota; 4:Opção]
                String[] msgs = mensagem.split(";");

                String msg = executarOp(msgs);

                bd.insere(msg.toUpperCase()); 
                String resposta = bd.le(); 

                byte[] todasMsg = resposta.getBytes();
                DatagramPacket reply = new DatagramPacket(todasMsg, todasMsg.length, request.getAddress(), request.getPort());

                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Servidor - Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Servidor - Input Output: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }

    private static String executarOp(String[] msgs) {
        int indFilme = Integer.parseInt(msgs[1]);
        int indCli = Integer.parseInt(msgs[2]);
        int nota = Integer.parseInt(msgs[3]);
        int op = Integer.parseInt(msgs[4]);
        System.out.println("index F : " + indFilme+
                                      "index C : " + indCli +
                                       "nota : "+ nota+
                                        "op : " + op);

        String msg = "";

        switch (op) {
            case 0: // Solicita ao servidor um título de filme para a avaliação.
                msg = bd.filmeNaoAvaliado(indCli);
                break;
            case 1: // Registra a nota de avaliação do usuário para um filme específico.
                bd.avaliarFilme(indCli, indFilme, nota);
                msg = "Filme avaliado com sucesso!";
                break;
            case 2: // Solicita ao servidor uma recomendação de filme ou série (não implementado).
                // Você pode implementar a lógica de recomendação aqui.
                msg = "Recomendação de filme não implementada.";
                break;
            case 3: // Solicita lista de avaliações feitas pelo próprio usuário.
                msg = bd.listarFilmesAvaliados(indCli);
                break;
        }

        return msg;
    }
}
