package com.chengyan.webapp.ServiceController;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.stereotype.Service;

@Service
public class StatsD {
    private final StatsDClient statsd;

    public StatsD() {
        this.statsd = new NonBlockingStatsDClient(null, "localhost", 8125);
    }

    public StatsDClient getStatsd() {
        return statsd;
    }
}
