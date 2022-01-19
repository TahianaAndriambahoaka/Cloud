package com.example.webservice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileRestController 
{
    private String uploadLocation = "Java Webservice/upload";

    public FileRestController() throws IOException
    {
        var uploadPath = Paths.get(uploadLocation);
        if(!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }
    }

    @RequestMapping(value="upload",method = RequestMethod.POST)
    public String upload(@RequestPart MultipartFile file)
    {
        return saveFile(file);
    }

    private String saveFile(MultipartFile file)
    {
        var filename = file.getOriginalFilename();
        var dest = Paths.get(uploadLocation + "/" + filename);
        try 
        {
            Files.copy(file.getInputStream(),dest);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return "Error";
        }
        return filename;
    }
}
