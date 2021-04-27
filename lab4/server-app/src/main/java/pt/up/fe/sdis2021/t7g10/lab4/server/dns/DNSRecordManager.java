package pt.up.fe.sdis2021.t7g10.lab4.server.dns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DNSRecordManager {

    private final Map<String, DNSRecord> map = new HashMap<>();

    public boolean register(DNSRecord record) {
        if (this.map.containsKey(record.getName()))
            return false;

        this.map.put(record.getName(), record);
        return true;
    }

    public Optional<DNSRecord> lookup(String dns) {
        return Optional.ofNullable(this.map.get(dns));
    }

    public int getEntriesAmount() {
        return this.map.size();
    }
}
