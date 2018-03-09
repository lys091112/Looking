package com.crescent.alert.runner.controller;

import com.crescent.alert.common.dto.Rule;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/v1")
public class RuleController {


    @GetMapping("/region/{region}/rules")
    public String registryRules(@RequestBody List<Rule> rules) {

        return "success";
    }

    @DeleteMapping("/region/{region}/rules")
    public String unRegistryRules(@RequestBody List<String> ruleIds) {

        return "success";
    }

}
