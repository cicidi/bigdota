package com.cicidi.bigdota.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pipeline implements Serializable {

    private String pipeLineName;
    private JobContext jobContext;
    private List<Step> stepList = new ArrayList<>();

    public Pipeline(String pipeLineName, JobContext jobContext) {
        this.pipeLineName = pipeLineName;
        this.jobContext = jobContext;
    }

    public String getPipeLineName() {
        return pipeLineName;
    }

    public void setPipeLineName(String pipeLineName) {
        this.pipeLineName = pipeLineName;
    }

    public JobContext getJobContext() {
        return jobContext;
    }

    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    public List<Step> getStep() {
        return stepList;
    }

    public void setStep(List<Step> stepList) {
        this.stepList = stepList;
    }

    public Pipeline addStep(Step step) {
        this.stepList.add(step);
        return this;
    }
}
