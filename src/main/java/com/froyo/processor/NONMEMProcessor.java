package com.froyo.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.froyo.statemachine.GridJobStateMachine;

public class NONMEMProcessor implements ProcessorLifecycle, JobLifecycle {

    // Processes as many jobs as there are active threads available
    private ExecutorService executorService;

    public NONMEMProcessor(int numThreads) {
        executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void start() {
        System.out.println("Started NONMEM processor");
    }

    public void stop() {
        executorService.shutdown();
    }

    public void submitTo(String xml) {
        executorService.execute(createJob(xml));
        
        // Shutdown, we want to end after these jobs are complete
        executorService.shutdown();
    }

    private Runnable createJob(final String xml) {

        Job job = new Job("nonmem-" + System.currentTimeMillis());
        Thread t = new Thread(new GridJobStateMachine(job));
        return t;
    }

}
