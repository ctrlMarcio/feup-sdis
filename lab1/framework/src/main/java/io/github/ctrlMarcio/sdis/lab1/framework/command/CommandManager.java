package io.github.ctrlMarcio.sdis.lab1.framework.command;

import io.github.ctrlMarcio.sdis.lab1.framework.response.Response;

import java.io.IOException;
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

    public Response execute(String commandName, String... args) throws IOException {
        Command command = commands.get(commandName);

        if (command == null)
            return Response.builder().returnCode(Response.ERROR_VALUE).content("INVALID_METHOD").build();

        return command.execute(args);
    }
}
