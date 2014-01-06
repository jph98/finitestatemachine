package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;

public class RetrieveFiles implements PersistentAction {

    private StateMachine<State, Trigger> execute;

    public RetrieveFiles(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {
        try {
            ThreadUtils.sleep(4000);
            System.out.println("Retrieving files");        
            execute.Fire(Trigger.COPY_OK);
        } catch (Exception e) {
            fail();
        }
    }

    private void fail() {
        try {
            execute.Fire(Trigger.COPY_FAILED);
        } catch (Exception e1) {
        }
    }

    public void before() {
        // TODO Auto-generated method stub
        
    }

    public void after() {
        // TODO Auto-generated method stub
        
    }
}
