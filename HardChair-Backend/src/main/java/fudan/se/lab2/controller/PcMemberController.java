package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ModifyScoreRequest;
import fudan.se.lab2.controller.request.ScoreRequest;
import fudan.se.lab2.service.PcMemberService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
public class PcMemberController {
    private PcMemberService pcMemberService;

    @Autowired
    public PcMemberController(PcMemberService pcMemberService) {
        this.pcMemberService = pcMemberService;
    }

    @RequestMapping(value = "/showPdf", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_PCMEMBER')")
    public ResponseEntity<byte[]> pdfDownload(HttpServletRequest httpServletRequest) throws IOException {
        String path = httpServletRequest.getParameter("path");
        File file = new File(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),
                httpHeaders,
                HttpStatus.OK);
    }

    @GetMapping("/getAllotedArticle") //查看分配到的稿件信息
    @PreAuthorize("hasRole('ROLE_PCMEMBER')")
    public ResponseEntity<Map<String,Object>> getAllotedArticle(@RequestParam("username") String username,
                                                                @RequestParam("meetingId") String meetingId) {
        return ResponseEntity.ok(pcMemberService.getAllotedArticle(username, meetingId));
    }

    @PostMapping("/submitScore") //查看分配到的稿件信息
    @PreAuthorize("hasRole('ROLE_PCMEMBER')")
    public ResponseEntity<String> submitScore(@RequestBody ScoreRequest scoreRequest) {
        return ResponseEntity.ok(pcMemberService.submitScore(scoreRequest));
    }

    @PostMapping("/submitModify") //修改评分
    @PreAuthorize("hasRole('ROLE_PCMEMBER')")
    public ResponseEntity<String> submitModify(@RequestBody ModifyScoreRequest modifyScoreRequest) {
        return ResponseEntity.ok(pcMemberService.submitModify(modifyScoreRequest));
    }

}
