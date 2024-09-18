package Controller;


import Dto.EmailRequest;
import jakarta.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class EmailController {
    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    @CrossOrigin
    @GetMapping("/api/email/download-csv")
    public String downloadCsv(@RequestParam String gmail, @RequestParam String password) {
        String host = "imap.gmail.com";
        StringBuilder response = new StringBuilder();

        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.ssl.trust", host);

        Session session = Session.getInstance(properties);
        Store store = null;

        try{
            store = session.getStore("imap");
            store.connect(host, gmail, password); //ket noi den server imap

            Folder folder = store.getFolder("INBOX"); //mo hop thu nguoi dung
            folder.open(Folder.READ_ONLY);

            for(Message msg : folder.getMessages()) {
                log.info(msg.getSubject());
                log.info(msg.getContent().toString());
            }

            return folder.getMessages()[0].getContent().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping("/test")
    public String test() {
        return "Test OK";
    }
}
