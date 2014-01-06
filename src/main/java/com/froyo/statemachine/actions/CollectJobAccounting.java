package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;

public class CollectJobAccounting implements PersistentAction {

    private StateMachine<State, Trigger> execute;

    public CollectJobAccounting(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {
        ThreadUtils.sleep(1000);
        System.out.println("Checking job accounting information");
        
        try {
            System.out.println("Complete collection of job accounting info");
            execute.Fire(Trigger.JOB_CHECKED);
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
