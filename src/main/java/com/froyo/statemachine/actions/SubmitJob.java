package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;

public class SubmitJob implements PersistentAction {

    private StateMachine<State, Trigger> execute;

    public SubmitJob(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {
        ThreadUtils.sleep(2000);
        System.out.println("Qsub the job");
        try {
            execute.Fire(Trigger.SUBMIT_OK);
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
