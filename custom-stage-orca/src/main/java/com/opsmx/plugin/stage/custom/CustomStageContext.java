package com.opsmx.plugin.stage.custom;

public class CustomStageContext {

    public String getVmDetails() {
        return vmDetails;
    }

    public void setVmDetails(String vmDetails) {
        this.vmDetails = vmDetails;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGitAccount() {
        return gitAccount;
    }

    public void setGitAccount(String gitAccount) {
        this.gitAccount = gitAccount;
    }

    private String vmDetails;

    private String filename;

    private String gitAccount;

    public CustomStageContext(String vmDetails, String filename, String gitAccount) {
        this.vmDetails = vmDetails;
        this.filename = filename;
        this.gitAccount = gitAccount;
    }
}
