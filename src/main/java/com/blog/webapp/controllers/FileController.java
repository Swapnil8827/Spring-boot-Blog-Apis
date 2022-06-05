package com.blog.webapp.controllers;

import com.blog.webapp.payloads.FileResponse;
import com.blog.webapp.services.FileService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam("image") MultipartFile image
    ) {
        String fileName = null;
        try {
            fileName = this.fileService.uploadImage(path, image);
        return new ResponseEntity(new FileResponse(fileName,"Uploaded!!"), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        return new ResponseEntity(new FileResponse(null,"Not Uploaded!!"), HttpStatus.OK);
        }
    }

    @GetMapping(value="/post/image/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName")String imageName, HttpServletResponse response)throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
