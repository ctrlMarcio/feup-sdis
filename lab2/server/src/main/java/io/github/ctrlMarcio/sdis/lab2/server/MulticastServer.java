package io.github.ctrlMarcio.sdis.lab2.server;

import io.github.ctrlMarcio.sdis.lab2.server.task.MulticastTask;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class MulticastServer {

    private final String host;

    private final Server server;

    private final TimerTask task;

    private final int timeInterval;

    public MulticastServer(int localPort, String multicastAddress, int multicastPort, String host, int timeInterval) throws SocketException {
        this.host = host;
        this.server = new Server(localPort);
        this.task = new MulticastTask(multicastAddress, multicastPort, this.host, localPort);
        this.timeInterval = timeInterval;
    }

    public MulticastServer(int localPort, String multicastAddress, int multicastPort) throws SocketException {
        this.host = "localhost";
        this.timeInterval = 1000;

        this.server = new Server(localPort);
        this.task = new MulticastTask(multicastAddress, multicastPort, this.host, localPort);
    }

    public void run() {
        new Timer().schedule(this.task, 0, timeInterval);
        this.server.run();
    }
}
