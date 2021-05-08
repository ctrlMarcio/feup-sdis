package pt.up.fe.sdis2021.t7g10.lab5.server.dns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DNSRecord {

    private final String name;

    private final String ip;
}
