package fudan.se.lab2.controller;

import fudan.se.lab2.service.AllMeetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AllMeetingsController {
    private AllMeetingsService allMeetingsService;

    @Autowired
    public AllMeetingsController(AllMeetingsService allMeetingsService) {
        this.allMeetingsService = allMeetingsService;
    }

    @GetMapping("/getAllPassedMeetings") //查看所有通过审核的会议
    public ResponseEntity<List<Object>> getAllPassedMeetings() {
        return ResponseEntity.ok(allMeetingsService.allPassedMeetings());
    }


    @GetMapping("/checkIfChair") //用户进入会议投稿之前要先判断用户是否为该会议的chair
    public ResponseEntity<Map<String, String>> checkIfUserIsChair(@RequestParam("username") String username,
                                                @RequestParam("meetingId") String meetingId) {
        Map<String, String> response = new HashMap<>();
        response.put("message", allMeetingsService.checkIfChair(username, meetingId));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/contribution") //论文投稿
    public ResponseEntity<Map<String, String>> articleContribution(@RequestParam("templateFile") MultipartFile file,
                                                 @RequestParam("articleName") String articleName,
                                                 @RequestParam("author") String author,
                                                 @RequestParam("summary") String summary,
                                                 @RequestParam("topics") String[] topics,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("meetingId") String meetingId,
                                                 HttpServletRequest request) {
        String parentDir = request.getServletContext().getRealPath("src");
        Map<String, String> response = new HashMap<>();
        response.put("message", allMeetingsService.storeArticleContribution(file, articleName, author,
                summary, topics, username, meetingId, parentDir));
        return ResponseEntity.ok(response);
    }
}
