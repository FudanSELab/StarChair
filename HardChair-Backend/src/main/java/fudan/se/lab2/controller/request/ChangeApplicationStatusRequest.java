package fudan.se.lab2.controller.request;

//管理员审核会议是否通过后发送的请求，包括会议ID和是否通过
public class ChangeApplicationStatusRequest {

    private String applicationId;
    private String applyStatus;

    public ChangeApplicationStatusRequest(){}

    public ChangeApplicationStatusRequest(String applicationId, String applyStatus){
        this.applicationId = applicationId;
        this.applyStatus = applyStatus;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
}
