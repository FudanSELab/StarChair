package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.SendInvitationRequest;
import fudan.se.lab2.domain.Article;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.ChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChairController {
    private ChairService chairService;

    @Autowired
    public ChairController(ChairService chairService) {
        this.chairService = chairService;
    }

    @GetMapping("/searchUserInfo") //通过查找用户的真实姓名返回user list
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<List<User>> getSearchForUser(@RequestParam("fullName") String fullName) {
        return ResponseEntity.ok(chairService.searchForUser(fullName));
    }

    @PostMapping("/sendInvitation") //发送邀请到所有被邀请用户
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<Map<String, String>> sendInvitation(@RequestBody SendInvitationRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", chairService.sendInvitation(request.getMeetingID(),
                request.getInviter(), request.getInvitee()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/changeSubmitStatus") //开启投稿
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<Map<String, String>> changeSubmitStatus(@RequestParam("meetingID") String meetingID,
                                                @RequestParam("submitStatus") String submitStatus) {
        Map<String, String> response = new HashMap<>();
        response.put("message", chairService.changeSubmitStatus(meetingID, submitStatus));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/getPcList") //查找指定会议的pcMember
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<Map<String, Object>> getPcList(@RequestParam("meetingID") String meetingID) {
        return ResponseEntity.ok(chairService.getPcList(meetingID));
    }

    @GetMapping("/startReview") //开启审稿
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<Map<String, String>> startReview(@RequestParam("meetingId") String meetingId,
                                         @RequestParam("strategy") String strategy) {
        Map<String, String> response = new HashMap<>();
        response.put("message", chairService.allotArticle(meetingId, strategy));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/confirmFinish") //无投稿但确认结束投稿
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<Map<String, String>> confirmFinish(@RequestParam("meetingId") String meetingId) {
        Map<String, String> response = new HashMap<>();
        response.put("message", chairService.finishContribution(meetingId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/viewAllArticles") //查看所有稿件信息
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<List<Article>> viewAllArticles(@RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(chairService.getAllArticles(meetingId));
    }

    @GetMapping("/finishReview") //结束评审，创建讨论贴
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<String> finishReview(@RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(chairService.finishReview(meetingId));
    }

    @GetMapping("/firstPublishScores") //发布评审结果
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<String> firstPublishScores(@RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(chairService.firstPublishScores(meetingId));
    }

    @GetMapping("/finalPublishScores") //发布评审结果
    @PreAuthorize("hasRole('ROLE_CHAIR')")
    public ResponseEntity<String> finalPublishScores(@RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(chairService.finalPublishScores(meetingId));
    }
}
