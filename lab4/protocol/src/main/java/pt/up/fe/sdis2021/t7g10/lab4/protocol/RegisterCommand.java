package pt.up.fe.sdis2021.t7g10.lab4.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

@AllArgsConstructor
@Getter
public class RegisterCommand implements Command {

    @Serial
    private static final long serialVersionUID = -6653405756657261879L;

    private final String name;

    private final String ip;

    @Override
    public CommandType getCommandType() {
        return CommandType.REGISTER;
    }
}
