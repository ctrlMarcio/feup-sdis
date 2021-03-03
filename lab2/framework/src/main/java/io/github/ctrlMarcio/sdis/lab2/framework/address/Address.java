package io.github.ctrlMarcio.sdis.lab2.framework.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Address {

    @Getter
    private final String ip;

    @Getter
    private final int port;

    public Address(String string) {
        String[] parts = string.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException(string + " is not a valid address");
        }

        this.ip = parts[0];
        this.port = Integer.parseInt(parts[1]);
    }
}
