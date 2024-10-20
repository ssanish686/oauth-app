package com.anish.sonar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SonarReport {

    public String getSonarStatus(){
        String response = "Passed";
        log.info(response);
        return response;
    }
}
