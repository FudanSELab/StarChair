package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.RebuttalRequest;
import fudan.se.lab2.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorController {
    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author") //返回用户在当前会议的投稿
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<Map<String, Object>> getAuthorContribution(@RequestParam("username") String username,
                                                   @RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(authorService.getContribution(username, meetingId));
    }

    @PostMapping("/modifyAuthor") //修改投稿信息
    public ResponseEntity<Map<String, String>> modifyArticleContribution(@RequestParam("templateFile") MultipartFile file,
                                                       @RequestParam("articleName") String articleName,
                                                       @RequestParam("author") String author,
                                                       @RequestParam("summary") String summary,
                                                       @RequestParam("topics") String[] topics,
                                                       @RequestParam("username") String username,
                                                       @RequestParam("meetingId") String meetingId,
                                                       @RequestParam("articleId") String articleId,
                                                       HttpServletRequest request) {
        String parentDir = request.getServletContext().getRealPath("src");
        Map<String, String> response = new HashMap<>();
        response.put("message", authorService.modifyArticleContribution(file, articleName, author,
                summary, topics, username, meetingId, articleId, parentDir));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authorRebuttal") //作者rebuttal
    public ResponseEntity<String> authorRebuttal(@RequestBody RebuttalRequest rebuttalRequest) {
        return ResponseEntity.ok(authorService.authorRebuttal(rebuttalRequest));
    }
}
