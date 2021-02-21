package io.github.ctrlMarcio.sdis.lab1.request;

import lombok.Builder;

@Builder
public class Reply {

    public static final int ERROR_VALUE = -1;

    private final int returnValue;

    private final String dns;

    private final String ip;

    public static Reply fromString(String string) {
        String[] lines = string.split("\n");
        int returnValue = Integer.parseInt(lines[0]);

        String[] parameters = lines[1].split(" ");
        String dns = parameters[0];
        String ip = parameters[1];

        return Reply.builder().returnValue(returnValue).dns(dns).ip(ip).build();
    }

    @Override
    public String toString() {
        return String.format("%d\n%s %s", this.returnValue, this.dns, this.ip);
    }

    public String toDisplayString() {
        if (returnValue == Reply.ERROR_VALUE)
            return "ERROR";
        else return String.format("%d %s %s", this.returnValue, this.dns, this.ip);
    }
}
