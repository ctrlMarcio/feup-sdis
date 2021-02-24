package io.github.ctrlMarcio.sdis.lab1.framework.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

@AllArgsConstructor
public abstract class Command {

    @Getter
    protected final String name;

    public abstract void execute(DatagramSocket socket, InetAddress sender, int senderPort, String... args) throws IOException;
}
