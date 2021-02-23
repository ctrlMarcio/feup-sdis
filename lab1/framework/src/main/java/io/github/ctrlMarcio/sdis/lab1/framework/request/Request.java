package io.github.ctrlMarcio.sdis.lab1.framework.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.net.DatagramPacket;

@Builder
public class Request {

    public static final int MAX_LENGTH = 256;

    @NonNull
    @Getter
    private final RequestType requestType;

    @Getter
    private final String dns;

    @Getter
    private final String ip;

    public static Request fromPacket(DatagramPacket packet) {
        String requestString = new String(packet.getData(), 0, packet.getLength());
        return Request.fromString(requestString);
    }

    public static Request fromString(String string) {
        String[] parameters = string.split(" ");

        if (parameters[0].equals(RequestType.REGISTER.toString())) {
            return Request.builder()
                    .requestType(RequestType.REGISTER)
                    .ip(parameters[2])
                    .dns(parameters[1])
                    .build();
        } else if (parameters[0].equals(RequestType.LOOKUP.toString())) {
            return Request.builder()
                    .requestType(RequestType.LOOKUP)
                    .dns(parameters[1])
                    .build();
        }
        return null; // won't
    }

    @Override
    public String toString() {
        if (requestType == RequestType.REGISTER)
            return String.format("%s %s %s", requestType.toString(), dns, ip);

        if (requestType == RequestType.LOOKUP)
            return String.format("%s %s", requestType.toString(), dns);

        // won't happen tho
        return null;
    }
}
