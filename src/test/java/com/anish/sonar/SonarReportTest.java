package com.anish.sonar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SonarReportTest {

    @Test
    public void testSonar(){
       SonarReport sonarReport = new SonarReport();
       assertEquals("Passed", sonarReport.getSonarStatus());
    }
}
