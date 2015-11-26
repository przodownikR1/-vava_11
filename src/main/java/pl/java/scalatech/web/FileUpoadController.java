package pl.java.scalatech.web;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileUpoadController {

    
     @RequestMapping("/form")
     public String fileUpload(){
         return "uploadFile";
     }
     @RequestMapping("/uploadSuccess")
     @ResponseBody String success(){
         return "success";
     }
     @RequestMapping("/uploadFailure")
     public @ResponseBody String failure(){
         return "failure";
     }
     
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        try {
            log.info("name : {}  file:  {} : size : {}",name,file.getOriginalFilename(),file.getSize());      
            byte[] bytes = file.getBytes();
            Files.write(bytes, new File("new_"+file.getOriginalFilename()));
            return "redirect:uploadSuccess";
        } catch (IOException e) {
            return "redirect:uploadFailure";
        }
    }
}