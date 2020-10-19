package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ChangeInvitationRequest;
import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserCenterController {
    private UserCenterService userCenterService;

    @Autowired
    public UserCenterController(UserCenterService userCenterService) {
        this.userCenterService = userCenterService;
    }

    @GetMapping("/myApplication") //我申请的所有会议
    public ResponseEntity<List<Object>> getMyMeetingApplication(@RequestParam("username") String username) {
        return ResponseEntity.ok(userCenterService.myMeetingApplication(username));
    }

    @GetMapping("/userConf") //我参与的所有会议
    public ResponseEntity<List<Object>> getMyMeeting(@RequestParam("username") String username) {
        return ResponseEntity.ok(userCenterService.myMeeting(username));
    }

    @GetMapping("/getAuthorityList") //获取该用户在指定会议的角色列表
    public ResponseEntity<List<Authority>> getAuthorityList(@RequestParam("id") String id,
                                                            @RequestParam("username") String username) {
        return ResponseEntity.ok(userCenterService.getUser_MeetingAuthorityList(id, username));
    }

    @GetMapping("/invitationIReceived") //获得我收到的所有邀请
    public ResponseEntity<Map<String, Object>> getInvitationIReceived(@RequestParam("id") String id) {
        return ResponseEntity.ok(userCenterService.invitationIReceived(id));
    }


    @PostMapping("/changeInvitationStatus") //我通过或拒绝邀请后改变邀请状态
    public ResponseEntity<Map<String, Object>> changeInvitationStatus(@RequestBody ChangeInvitationRequest request) {
        return ResponseEntity.ok(userCenterService.changeInvitationStatus(request.getId(),
                request.getInvitationStatus(), request.getTopics()));
    }

    @GetMapping("/invitationISent") //获得我发出的邀请
    public ResponseEntity<Map<String, Object>> getInvitationISent(@RequestParam("username") String username) {
        return ResponseEntity.ok(userCenterService.invitationISent(username));
    }
}
