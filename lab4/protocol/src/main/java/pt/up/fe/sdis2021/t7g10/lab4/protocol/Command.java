package pt.up.fe.sdis2021.t7g10.lab4.protocol;

import java.io.Serializable;

public interface Command extends TCPMessage {

    CommandType getCommandType();
}
