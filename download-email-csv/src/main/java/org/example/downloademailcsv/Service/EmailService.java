package org.example.downloademailcsv.Service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Qualifier("taskExecutor")
    @Autowired
    private Executor executor;



    public void downloadCsv(String email, String password) throws Exception{
        String host = "imap.gmail.com"; //host
        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable", "true"); //accept connect in session
        Session session = Session.getInstance(properties);
        Store store = session.getStore("imap");
        store.connect(host, email, password); // Kết nối đến server IMAP
        Folder folder = store.getFolder("INBOX"); // Mở hộp thư người dùng
        folder.open(Folder.READ_ONLY);
        for(Message msg: folder.getMessages()){
            Object content = msg.getContent();
            if (content instanceof MimeMultipart) {
                MimeMultipart mimeMultipart = (MimeMultipart) content;
                for (int i = 0; i < mimeMultipart.getCount(); i++) {
                    BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                    if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                        // Xử lý tệp đính kèm
                        String fileName = bodyPart.getFileName();
                        int dotIndex = fileName.lastIndexOf('.');
                        String extension = fileName.substring(dotIndex);
                        if (extension.equals(".csv")) {
                            CompletableFuture<?> future = CompletableFuture.runAsync(()-> {
                                try {
                                    saveFile(bodyPart, fileName);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            },this.executor);
                        }
                    }
                }
            }
        }
    }



    private void saveFile(BodyPart bodyPart,String fileName) throws Exception {
        InputStream inputStream = bodyPart.getInputStream();
        String downloadDir = "D:/downloadFile/"; // Đường dẫn đến thư mục
        File file = new File(downloadDir + fileName);
        file = getUniqueFileName(file);
        file.setWritable(true);
        file.setReadable(true);
        log.info(Thread.currentThread().getName() + " saveFile: " + file.getName());
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        log.info("Tệp đính kèm đã tải xuống: " + fileName);
    }

    private File getUniqueFileName(File file) {
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        int dotIndex = fileName.lastIndexOf('.');
        String baseName;
        String extension = "";

        if (dotIndex > 0) { //tach ten file va pha mo rong
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        } else {
            baseName = fileName;
        }

        int counter = 1;
        // Kiểm tra file đã tồn tại
        while (file.exists()) {
            fileName = baseName + " (" + counter + ")" + extension;
            filePath = new File(file.getParent(), fileName).getAbsolutePath();
            file = new File(filePath);
            counter++;
        }
        return file;
    }
}
