package com.stockholdergame.server.services;

import com.stockholdergame.server.services.localization.LocalizationService;
import com.stockholdergame.server.services.localization.impl.LocalizationServiceImpl;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test
public class BaseServiceTest {

    private LocalizationService localizationService = new LocalizationServiceImpl();

    @BeforeSuite
    public void setUpBeforeClass() {
        localizationService.initializeMessageHolder();
    }
}
