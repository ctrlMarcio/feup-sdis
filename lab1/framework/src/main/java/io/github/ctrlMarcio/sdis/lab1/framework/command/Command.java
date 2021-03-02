package io.github.ctrlMarcio.sdis.lab1.framework.command;

import io.github.ctrlMarcio.sdis.lab1.framework.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@AllArgsConstructor
public abstract class Command {

    @Getter
    protected final String name;

    public abstract Response execute(String... args);
}
