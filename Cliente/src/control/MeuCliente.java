package control;

import java.net.*;
import java.io.*;

public class MeuCliente {

    private String nomeDNS;
    private int serverPort;
    private byte[] meuIP;

    public MeuCliente() {
        try {
            InetAddress endereco = InetAddress.getLocalHost();
            nomeDNS = endereco.getHostName();
            meuIP = endereco.getAddress();
        } catch (UnknownHostException e) {
            System.out.println("Host Desconhecido: " + e.getMessage());
        }
        serverPort = 6789;
    }

    public String enviarMsg(String msg) {
        DatagramSocket aSocket = null;
        String resposta = new String("");

        try {
            aSocket = new DatagramSocket();
            System.out.println(msg);
            byte[] m = msg.getBytes();
            InetAddress aHost = InetAddress.getByName(nomeDNS);
            
            DatagramPacket request = new DatagramPacket(m,m.length,aHost,serverPort);
            aSocket.send(request);
            
            byte[] buffer = new byte[600];
            
            DatagramPacket reply = new DatagramPacket(buffer,buffer.length);
            
            aSocket.receive(reply);
            
            resposta = new String(reply.getData());
            System.out.println(resposta);
            resposta = resposta + "\n";
            
        } catch(SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Input Output: " + e.getMessage());
        }finally{
            if(aSocket != null) aSocket.close();
        }

        return resposta;
    }

    public MeuCliente(String nomeDNS, int serverPort, byte[] meuIP) {
        this.nomeDNS = "Usuario-Pc";
        this.serverPort = serverPort;
        this.meuIP = meuIP;
    }

    public String getNomeDNS() {
        return nomeDNS;
    }

    public int getServerPort() {
        return serverPort;
    }

    public byte[] getMeuIP() {
        return meuIP;
    }
    
}
