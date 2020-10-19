package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ChangeApplicationStatusRequest;
import fudan.se.lab2.controller.request.MeetingRequest;
import fudan.se.lab2.domain.Meeting;
import fudan.se.lab2.service.ConferenceApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConferenceApplicationController {
    private ConferenceApplicationService conferenceApplicationService;

    @Autowired
    public ConferenceApplicationController(ConferenceApplicationService conferenceApplicationService){
        this.conferenceApplicationService = conferenceApplicationService;
    }

    @PostMapping("/applyConference") //申请会议
    public ResponseEntity<Meeting> applyConference(@RequestBody MeetingRequest request) {
        return ResponseEntity.ok(conferenceApplicationService.applyConference(request));
    }

    @GetMapping("/getUncheckedConference") //管理员页面返回待审核会议
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Object>> getUncheckedConference() {
        return ResponseEntity.ok(conferenceApplicationService.getUncheckedConference());
    }

    @PostMapping("/changeApplicationStatus") //管理员审核会议后改变会议申请状态
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Meeting>> changeApplicationStatus(@RequestBody ChangeApplicationStatusRequest request) {
        return ResponseEntity.ok(conferenceApplicationService.changeApplicationStatus(request));
    }

}
