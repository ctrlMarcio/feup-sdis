package pt.up.fe.sdis2021.t7g10.lab5.server;

import lombok.Getter;
import lombok.Setter;
import pt.up.fe.sdis2021.t7g10.lab5.server.dns.DNSRecordManager;
import pt.up.fe.sdis2021.t7g10.lab5.server.thread.ClientWorkerThread;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.SocketException;

@Getter
public class Server {

    private final int port;

    private final SSLServerSocket socket;

    private final DNSRecordManager dnsManager;

    @Setter
    private boolean running = true;

    public Server(int port) throws IOException {
        this.port = port;
        this.dnsManager = new DNSRecordManager();

        /*
         * javax.net.ssl.keyStore
         * javax.net.ssl.keyStorePassword
         * javax.net.ssl.trustStore
         * javax.net.ssl.trustStorePassword
         */

        System.setProperty("javax.net.ssl.keyStore", "./server-app/src/main/resources/server.keys");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", "./server-app/src/main/resources/truststore");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        var ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.socket = (SSLServerSocket) ssf.createServerSocket(port);

        this.run();
    }

    public void run() {
        while (running) {
            try {
                SSLSocket socket = (SSLSocket) this.socket.accept();

                System.out.println("Receiving to client");
                new ClientWorkerThread(socket, this.dnsManager).start();
            } catch (SocketException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
