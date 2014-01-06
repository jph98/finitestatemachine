package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;

public class CheckJobStatus implements PersistentAction {

    private int count = 0;
    private StateMachine<State, Trigger> execute;

    public CheckJobStatus(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {

        while (count < 5) {
            System.out.println("[JobStatus] = Running");
            ThreadUtils.sleep(1000);
            count += 1;
        }
        
        try {
            System.out.println("[JobStatus] = Completed");
            execute.Fire(Trigger.JOB_COMPLETED);
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
