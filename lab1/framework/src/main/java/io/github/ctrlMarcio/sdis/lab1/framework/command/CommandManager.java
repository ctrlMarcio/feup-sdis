package io.github.ctrlMarcio.sdis.lab1.framework.command;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
    }

    public boolean add(Command command) {
        if (commands.get(command.getName()) != null) return false;

        return commands.put(command.getName(), command) == null;
    }

    public boolean remove(Command command) {
        return commands.remove(command.getName()) != null;
    }

    public boolean execute(String commandName, DatagramSocket socket, InetAddress sender, int senderPort,
                           String... args) throws IOException {
        Command command = commands.get(commandName);

        if (command == null) return false;

        command.execute(socket, sender, senderPort, args);

        return true;
    }
}
