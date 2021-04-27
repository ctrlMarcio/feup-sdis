package pt.up.fe.sdis2021.t7g10.lab4.server;

import lombok.Getter;
import lombok.Setter;
import pt.up.fe.sdis2021.t7g10.lab4.server.dns.DNSRecordManager;
import pt.up.fe.sdis2021.t7g10.lab4.server.thread.ClientWorkerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

@Getter
public class Server {

    private final int port;

    private final ServerSocket socket;

    private final DNSRecordManager dnsManager;

    @Setter
    private boolean running = true;

    public Server(int port) throws IOException {
        this.port = port;
        this.dnsManager = new DNSRecordManager();
        this.socket = new ServerSocket(this.port);

        this.run();
    }

    public void run() {
        while (running) {
            try {
                Socket socket = this.socket.accept();

                new ClientWorkerThread(socket, this.dnsManager).start();
            } catch (SocketException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
