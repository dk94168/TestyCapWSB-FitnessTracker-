package com.capgemini.wsb.fitnesstracker.training.model;

import lombok.Value;

import java.util.Date;

@Value
public class CreateTrainingRequest {
    private long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
