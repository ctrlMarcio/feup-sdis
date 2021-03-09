package io.github.ctrlMarcio.sdis.lab3.framework.command;

import io.github.ctrlMarcio.sdis.lab3.framework.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Command {

    @Getter
    protected final String name;

    public abstract Response execute(String... args);
}
