package com.rudra.caption.service;

import org.json.JSONObject;
import com.rudra.caption.model.Caption;
import com.rudra.caption.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.List;
import java.util.ArrayList;

@Service
public class CaptionService {

    @Autowired
    private CaptionRepository captionRepository;

    //  SEND IMAGE TO PYTHON + SAVE RESULT
    public String sendImageToPythonWithMode(MultipartFile file, String mode) {

        String url = "http://127.0.0.1:8000/generate";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        try {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            body.add("file", resource);
            body.add("mode", mode);   //  (mode sent to Python)

        } catch (Exception e) {
            return "Error reading file";
        }

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        String result = response.getBody();

        // JSON → TEXT
        JSONObject json = new JSONObject(result);
        String captionText = json.getString("caption");

        // SAVE
        Caption caption = new Caption();
        caption.setCaptionText(captionText);

        
        caption.setImageName(file.getOriginalFilename());
        caption.setMode(mode);
        caption.setCreatedAt(java.time.LocalDateTime.now());

        captionRepository.save(caption);

        return captionText;
    }

    //  NORMAL SAVE METHOD
    public String saveCaption(String text) {
        Caption caption = new Caption();
        caption.setCaptionText(text);
        captionRepository.save(caption);
        return "Saved: " + text;
    }

    //  TEST CONNECTION (NO FILE)
    public String callPythonAPI() {

        String url = "http://127.0.0.1:8000/generate";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, null, String.class);

        return response.getBody();
    }

    public List<String> processMultiple(MultipartFile[] files, String mode) {

        List<String> captions = new ArrayList<>();

        for (MultipartFile file : files) {
            String caption = sendImageToPythonWithMode(file, mode);
            captions.add(caption);
        }

        return captions;
    }

    public List<Caption> getAllCaptions() {
        return captionRepository.findAll();
    }
}