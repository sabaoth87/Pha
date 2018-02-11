package com.tnk.db;

/**
 * Created by Tom on 2017-12-29.
 */

public class Item_Issue {

    // fields
    private int issueId;
    private String issueTitle;
    private String issueBody;
    private String issueDatetime;
    private String issueStatus;
    private String issueTags;
    private String issueAssignee;
    private String issueProject;
    private String issueMilestone;
    private String issueProgress;
    private String issueTicket;
    private String issueOwner;

    // constructors
    public Item_Issue() {
    }

    public Item_Issue(int id,
                      String issueTitle,
                      String issueBody,
                      String issueDatetime,
                      String issueStatus,
                      String issueTags,
                      String issueAssignee,
                      String issueProject,
                      String issueMilestone,
                      String issueProgress,
                      String issueTicket,
                      String issueOwner
                    ) {
        this.issueId = id;
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
        this.issueDatetime = issueDatetime;
        this.issueStatus = issueStatus;
        this.issueTags = issueTags;
        this.issueAssignee = issueAssignee;
        this.issueProject = issueProject;
        this.issueMilestone = issueMilestone;
        this.issueProgress = issueProgress;
        this.issueTicket = issueTicket;
        this.issueOwner = issueOwner;
    }

    // #SET/#GET      Properties of the Class        HERE
    public void setID(int id) {this.issueId = id;}
    public int getID() {return this.issueId;}
    public void setIssueTitle(String title) {this.issueTitle = title;}
    public String getIssueTitle() {return this.issueTitle;}
    public void setIssueBody(String body) {this.issueBody = body;}
    public String getIssueBody() {return this.issueBody;}
    public void setIssueDatetime(String datetime) {this.issueDatetime = datetime;}
    public String getIssueDatetime() {return this.issueDatetime;}
    public void setIssueStatus(String status) {this.issueStatus = status;}
    public String getIssueStatus() {return this.issueStatus;}
    public void setIssueTags(String tags) {this.issueTags = tags;}
    public String getIssueTags() {return this.issueTags;}
    public void setIssueAssignee(String assignee) {this.issueAssignee = assignee;}
    public String getIssueAssignee() {return this.issueAssignee;}
    public void setIssueProject(String project) {this.issueProject = project;}
    public String getIssueProject() {return this.issueProject;}
    public void setIssueMilestone(String milestone) {this.issueMilestone = milestone;}
    public String getIssueMilestone() {return this.issueMilestone;}
    public void setIssueProgress(String progress) {this.issueProgress = progress;}
    public String getIssueProgress() {return this.issueProgress;}
    public void setIssueTicket(String ticket) {this.issueTicket = ticket;}
    public String getIssueTicket() {return this.issueTicket;}
    public void setIssueOwner(String owner) {this.issueOwner = owner;}
    public String getIssueOwner() {return this.issueOwner;}
}