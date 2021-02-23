package io.github.ctrlMarcio.sdis.lab1.framework.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Command {

    @Getter
    protected final String name;

    public abstract void execute();
}
