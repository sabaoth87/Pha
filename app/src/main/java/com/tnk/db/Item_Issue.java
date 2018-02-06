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
                      String issueProgress
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
    }

    // #SET/#GET      Properties of the Class        HERE
    public void setID(int id) {this.issueId = id;}
    public int getID() {return this.issueId;}
    public void setIssueTitle(String type) {this.issueTitle = issueTitle;}
    public String getIssueTitle() {return this.issueTitle;}
    public void setIssueBody(String name) {this.issueBody = issueBody;}
    public String getIssueBody() {return this.issueBody;}
    public void setIssueDatetime(String brand) {this.issueDatetime = issueDatetime;}
    public String getIssueDatetime() {return this.issueDatetime;}
    public void setIssueStatus(String quantity) {this.issueStatus = issueStatus;}
    public String getIssueStatus() {return this.issueStatus;}
    public void setIssueTags(String quality) {this.issueTags = issueTags;}
    public String getIssueTags() {return this.issueTags;}
    public void setIssueAssignee(String location) {this.issueAssignee = issueAssignee;}
    public String getIssueAssignee() {return this.issueAssignee;}
    public void setIssueProject(String note) {this.issueProject = issueProject;}
    public String getIssueProject() {return this.issueProject;}
    public void setIssueMilestone(String link) {this.issueMilestone = issueMilestone;}
    public String getIssueMilestone() {return this.issueProgress;}
    public void setIssueProgress(String pic) {this.issueProgress = issueProgress;}
    public String getIssueProgress() {return this.issueProgress;}

}