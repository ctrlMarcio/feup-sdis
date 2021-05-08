package pt.up.fe.sdis2021.t7g10.lab5.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

@AllArgsConstructor
@Getter
public class LookupCommand implements Command {

    @Serial
    private static final long serialVersionUID = 8532307092885803807L;

    private final String name;

    @Override
    public CommandType getCommandType() {
        return CommandType.LOOKUP;
    }
}
