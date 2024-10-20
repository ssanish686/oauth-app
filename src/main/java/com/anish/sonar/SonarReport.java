package com.anish.sonar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SonarReport {

    public String testSonar(){
        String response = "test sonar..";
        log.info(response);
        return response;
    }
}
