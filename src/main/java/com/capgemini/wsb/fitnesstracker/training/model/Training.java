package com.capgemini.wsb.fitnesstracker.training.model;

import com.capgemini.wsb.fitnesstracker.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch= FetchType.LAZY)   // CascadeType.ALL - poprawne CascadeType.MERGE or .PERSIST
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Column(name = "distance")
    private double distance;

    @Column(name = "average_speed")
    private double averageSpeed;


    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public void setUser(User user) { this.user = user; }

    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public void setDistance(Double distance) { this.distance = distance; }

    public void setAverageSpeed(Double averageSpeed) { this.averageSpeed = averageSpeed; }
}