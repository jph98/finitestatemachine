package com.froyo.statemachine;

import com.google.common.base.Preconditions;
import com.googlecode.stateless4j.StateMachine;
import com.googlecode.stateless4j.delegates.Action;
import com.froyo.processor.Job;
import com.froyo.statemachine.actions.CollectJobAccounting;
import com.froyo.statemachine.actions.CheckJobQueued;
import com.froyo.statemachine.actions.CheckJobStatus;
import com.froyo.statemachine.actions.ProvisionFiles;
import com.froyo.statemachine.actions.RetrieveFiles;
import com.froyo.statemachine.actions.SubmitJob;
import com.froyo.statemachine.states.State;
import com.froyo.statemachine.triggers.Trigger;

public class GridJobStateMachine implements Runnable, WrappedStateMachine {

    private Job job;
    private StateMachine<State, Trigger> execute;
    
    public GridJobStateMachine(Job job) {
        
        this.job = job;
        configure();
    }

    /**
     * TODO: Work out how to encode this in a dynamic language DSL.
     * 
     * UI Actions (Graphically):
     *  Add Machine - Specify Flow (Graphical, XML, Dynamic Language)
     *  Define Global Context and Variables
     *  Add State - Specify Script (Groovy, Python, Perl, Shell, XML)
     *  Specify Transitions
     *  Reuse previous script (copy files)
     *  Test state and Debug Global Context @ various states
     *  Test Machine and Debug Global Context @ various states
     *  
     * Level of dynamic control, specify at:
     *  - runtime with test - would need flow + scripts
     *  - deployment time - would need flow + scripts
     *  
     * init: Initialise.groovy
     * provision: Provision.groovy
     * submit: Submit.groovy
     * queued: CheckQueued.groovy
     * status: CheckStatus.groovy
     * accounting: CollectAccounting.groovy
     * retrieve: Retrieve.groovy
     * 
     * Wrapped action for PollingItems?
     * Wrapped action for RunAsUser? Or just SSH?
     * 
     * Look at other languages:
     *  - Boo
     *  - Lua
     *  - Groovy
     *  - Ruby
     *  
     *  Repercussions for getting this wrong?
     * 
     */
    public void configure() {

        try {
                    
            // XML File, Database Table, whatever.  State machine would need to be able to be read from either place (JVM)
            // Cross JVM Purposes - we would need to look at serializing to file, storing in a table, storing on a JMS queue (message)
            execute = new StateMachine<State, Trigger>(State.INITIALISE);
                       
            execute.Configure(State.INITIALISE).Permit(Trigger.CREATE_JOB, State.PROVISION_FILES);

            execute.Configure(State.PROVISION_FILES).
                    Permit(Trigger.COPY_FAILED, State.JOB_FAILED).
                    Permit(Trigger.COPY_OK, State.SUBMIT).
                    OnEntry(new ProvisionFiles(execute));

            execute.Configure(State.SUBMIT).
                    Permit(Trigger.SUBMIT_FAILED, State.JOB_FAILED).
                    Permit(Trigger.SUBMIT_OK, State.JOB_QUEUED).
                    OnEntry(new SubmitJob(execute));

            execute.Configure(State.JOB_QUEUED).
                    Permit(Trigger.JOB_TIMEOUT, State.JOB_FAILED).
                    Permit(Trigger.JOB_STARTED, State.CHECK_STATUS).
                    OnEntry(new CheckJobQueued(execute));

            execute.Configure(State.CHECK_STATUS).
                    Permit(Trigger.JOB_COMPLETED, State.COLLECT_ACCOUNTING).
                    OnEntry(new CheckJobStatus(execute));

            execute.Configure(State.COLLECT_ACCOUNTING).
                    Permit(Trigger.JOB_CHECKED, State.RETRIEVE_FILES).
                    OnEntry(new CollectJobAccounting(execute));

            execute.Configure(State.RETRIEVE_FILES).
                    Permit(Trigger.COPY_FAILED, State.JOB_FAILED).
                    Permit(Trigger.COPY_OK, State.JOB_SUCCESS).
                    OnEntry(new RetrieveFiles(execute));
            
            // TODO: How do we complete the state machine?
            execute.Configure(State.JOB_SUCCESS).            
                OnEntry(new Action() {
                
                public void doIt() {
                    System.out.println("State Machine Completed");
                    System.exit(0);
                }
            });                        

        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create state machine");
        }
    }

    public void run() {

        Preconditions.checkNotNull(execute, "State Machine not configured");
        
        // Run
        System.out.println("Run " + job.getId());
        
        try {
            execute.Fire(Trigger.CREATE_JOB);
        } catch (Exception e) {
           throw new IllegalArgumentException("Could not create job " + job.getId(), e);
        }
    }

}
