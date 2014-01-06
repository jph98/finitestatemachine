package com.froyo.engine;

import java.util.Map;

import com.google.common.collect.Maps;
import com.froyo.processor.JobLifecycle;
import com.froyo.processor.ProcessorLifecycle;

/**
 * Core engine for various {@link ProcessorLifecycle} instances
 */
public class CoreExecutableEngine {

    private Map<String, Object> processors = Maps.newHashMap();
    
    public CoreExecutableEngine() {
    }
    
    public void start() {
        for (Object obj: processors.values()) {
            if (obj instanceof ProcessorLifecycle) {
                ProcessorLifecycle pc = (ProcessorLifecycle) obj;
                pc.start();
            }
        }
    }
    
    public void stop() {
        for (Object obj: processors.values()) {
            if (obj instanceof ProcessorLifecycle) {
                ProcessorLifecycle pc = (ProcessorLifecycle) obj;
                pc.stop();
            }
        }
    }
    
    public void add(String name, ProcessorLifecycle proc) {
        processors.put(name, proc);
    }
    
    public void submitTo(String name, String xml) {
        
        ((JobLifecycle) processors.get(name)).
            submitTo(xml);
    }
    
}
