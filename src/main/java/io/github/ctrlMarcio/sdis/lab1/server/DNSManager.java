package io.github.ctrlMarcio.sdis.lab1.server;

import java.util.HashMap;
import java.util.Map;

public class DNSManager {

    private final Map<String, String> map = new HashMap<>();

    public boolean register(String dns, String ip) {
        if (this.map.containsKey(dns))
            return false;

        this.map.put(dns, ip);
        return true;
    }

    public String lookup(String dns) {
        System.out.println(dns);
        return this.map.get(dns);
    }

    public int getEntriesAmount() {
        return this.map.size();
    }
}
