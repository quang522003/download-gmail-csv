package org.example.downloademailcsv.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.example.downloademailcsv.Dto.ApiResonseDto;

import org.example.downloademailcsv.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;



    @CrossOrigin
    @GetMapping("/download-csv")
    public ApiResonseDto<String> downloadCsv(@RequestParam String gmail, @RequestParam String password) throws Exception {
        int fileNumber = emailService.downloadCsv(gmail, password);
        return ApiResonseDto.<String>builder()
                .data("Download " + fileNumber + " files complete")
                .build();
    }

    @CrossOrigin
    @GetMapping("/50/{id}")
    public ApiResonseDto<String> testcallMultipleRequests(@PathVariable String id) throws JsonProcessingException, ExecutionException, InterruptedException {
        return ApiResonseDto.<String>builder()
                .data(emailService.callMultipleRequests(id))
                .build();
    }
}

