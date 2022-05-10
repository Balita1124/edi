package com.balita.edi.applicative.planning;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/planning")
public class PlanningApi {

    private PlanningRepository planningRepository;

    public PlanningApi(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }

    @GetMapping
    public ResponseEntity planningList() {
        return ResponseEntity.ok(planningRepository.findAll());
    }
}
