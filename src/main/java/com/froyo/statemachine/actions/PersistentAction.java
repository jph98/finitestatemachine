package com.froyo.statemachine.actions;

import com.googlecode.stateless4j.delegates.Action;

public interface PersistentAction extends Action {

    void before();
    
    void after();

}
