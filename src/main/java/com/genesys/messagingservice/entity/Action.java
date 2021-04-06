package com.genesys.messagingservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Action extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String username;

    private String actionType;

    @Column(nullable = false)
    private String actionStatus;

    private String failReason;

    public Action() {
    }

    public static Action newAction(User user, ActionType actionType, ActionStatus status, String failReason){
        Action action = new Action();
        action.setUserId(user.getId());
        action.setUsername(user.getUsername());
        action.setActionType(actionType);
        action.setActionStatus(status);
        action.setFailReason(failReason);
        return action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActionType getActionType() {
        return ActionType.valueOf(this.actionType);
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType.name();
    }

    public ActionStatus getActionStatus() {
        return ActionStatus.valueOf(this.actionStatus);
    }

    public void setActionStatus(ActionStatus status) {
        this.actionStatus = status.name();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public enum ActionStatus {
        SUCCESSFUL,
        FAILED
    }

    public enum ActionType {
        LOGIN,
        SEND_FRIEND_REQUEST,
        ADD_USER,
        SEND_MESSAGE
    }
}
