package com.froyo.statemachine;

import com.googlecode.stateless4j.StateMachine;

/**
 * Wrapped instance of {@link StateMachine}.
 */
public interface WrappedStateMachine {

    void configure() throws Exception;
}
