package io.github.ctrlMarcio.sdis.lab1.server.dns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DNSManager {

    private final Map<String, String> map = new HashMap<>();

    public boolean register(String dns, String ip) {
        if (this.map.containsKey(dns))
            return false;

        this.map.put(dns, ip);
        return true;
    }

    public Optional<String> lookup(String dns) {
        return Optional.ofNullable(this.map.get(dns));
    }

    public int getEntriesAmount() {
        return this.map.size();
    }
}
