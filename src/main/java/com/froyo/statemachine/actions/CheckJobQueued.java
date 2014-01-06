package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;


public class CheckJobQueued implements PersistentAction {

    private int count = 0;
    private StateMachine<State, Trigger> execute;
    
    public CheckJobQueued(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {

        while (count < 5) {
            System.out.println("[JobStatus] = Queued");
            ThreadUtils.sleep(1000);
            count += 1;
        }
        
        try {
            System.out.println("[JobStatus] = Started");
            execute.Fire(Trigger.JOB_STARTED);
        } catch (Exception e) {
        }
    }

    public void before() {
        // TODO Auto-generated method stub
        
    }

    public void after() {
        // TODO Auto-generated method stub
        
    }

}
