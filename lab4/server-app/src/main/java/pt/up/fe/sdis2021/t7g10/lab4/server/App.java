package pt.up.fe.sdis2021.t7g10.lab4.server;

public class App {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Server <port: number>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);

            new Server(port);
        } catch (Exception e) {
            System.err.println("Incorrect number of arguments.\nCorrect usage: java Server <port: number>");
        }
    }
}
