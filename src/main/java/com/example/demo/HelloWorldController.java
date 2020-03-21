package com.example.demo;

import com.example.demo.model.QuestionnaireResponseResource;
import com.example.demo.model.Response;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
public class HelloWorldController {

    private MongoTemplate mongoTemplate;

    public HelloWorldController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<?> hello(@RequestBody PersonResource personResource) {
        mongoTemplate.insert(personResource);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/mood")
    public ResponseEntity<?> mood(String id) {
        List<QuestionnaireResponseResource> result = mongoTemplate.find(query(where("userId").is(id)), QuestionnaireResponseResource.class);

        Map<String, Integer> values = new HashMap<>();
        for (QuestionnaireResponseResource questionnaireResponseResource : result) {
            List<Response<Integer>> moodResponses = questionnaireResponseResource.getMoodResponses();
            for (Response<Integer> moodResponse : moodResponses) {
                values.put(questionnaireResponseResource.getResponseDate(), moodResponse.getResponse());
            }
        }

        return ResponseEntity.ok(values);
    }
}
