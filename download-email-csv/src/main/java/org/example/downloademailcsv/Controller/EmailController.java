package org.example.downloademailcsv.Controller;


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

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;



    @CrossOrigin
    @GetMapping("/download-csv")
    public ApiResonseDto<String> downloadCsv(@RequestParam String gmail, @RequestParam String password) throws Exception {
        emailService.downloadCsv(gmail, password);
        return ApiResonseDto.<String>builder().build();
    }
}

