package com.froyo.statemachine.triggers;

/**
 * Triggers for states
 */
public enum Trigger {
    
    CREATE_JOB,
    COPY_OK, COPY_FAILED,
    SUBMIT_OK, SUBMIT_FAILED,
    FILE_COPY_FAILED,
    JOB_ON_GRID, 
    JOB_COMPLETED,
    JOB_FAILED, 
    JOB_TIMEOUT, 
    JOB_STARTED, 
    JOB_CHECKED;
}