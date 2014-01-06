package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.StateMachine;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;
import com.froyo.statemachine.utils.ThreadUtils;

public class ProvisionFiles implements PersistentAction {

    private StateMachine<State, Trigger> execute;

    public ProvisionFiles(StateMachine<State, Trigger> execute) {
        this.execute = execute;
    }

    public void doIt() {
        try {
            ThreadUtils.sleep(4000);
            System.out.println("Provisioning Files");
            execute.Fire(Trigger.COPY_OK);
        } catch (Throwable t) {
            fail();
        }
        
    }

    private void fail() {
        try {
            execute.Fire(Trigger.COPY_FAILED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void before() {
        // TODO Auto-generated method stub
        
    }

    public void after() {
        // TODO Auto-generated method stub
        
    }

}
