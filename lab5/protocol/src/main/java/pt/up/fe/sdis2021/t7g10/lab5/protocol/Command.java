package pt.up.fe.sdis2021.t7g10.lab5.protocol;

public interface Command extends TCPMessage {

    CommandType getCommandType();
}
