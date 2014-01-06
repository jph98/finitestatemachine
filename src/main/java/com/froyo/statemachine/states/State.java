package com.froyo.statemachine.states;

/**
 * Our grid state class
 */
public enum State {

    INITIALISE,
    PROVISION_FILES,
    SUBMIT,
    CHECK_STATUS,
    JOB_FAILED, 
    JOB_QUEUED, 
    COLLECT_ACCOUNTING, 
    RETRIEVE_FILES, 
    JOB_SUCCESS
}