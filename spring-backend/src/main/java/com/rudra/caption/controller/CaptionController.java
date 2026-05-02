package com.rudra.caption.controller;

import com.rudra.caption.service.CaptionService;
import com.rudra.caption.model.Caption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class CaptionController {

    @Autowired
    private CaptionService service;

    @GetMapping("/")
    public String home() {
        return "Backend is running 🚀";
    }

    @GetMapping("/save")
    public String save() {
        return service.saveCaption("Test Caption");
    }

    @GetMapping("/generate")
    public String generate() {
        return service.callPythonAPI();
    }

    @GetMapping("/captions")
    public List<Caption> getAllCaptions() {
        return service.getAllCaptions();
    }

    @PostMapping("/upload-multiple")
    public List<String> uploadMultiple(@RequestParam("files") MultipartFile[] files,
                                       @RequestParam("mode") String mode) {
        return service.processMultiple(files, mode);
    }
}