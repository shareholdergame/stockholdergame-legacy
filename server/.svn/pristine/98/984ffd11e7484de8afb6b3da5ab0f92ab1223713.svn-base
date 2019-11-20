package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.ProfileDao;
import com.stockholdergame.server.services.account.impl.ProfileServiceImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.createMock;

/**
 * @author Alexander Savin
 *         Date: 16.5.2010 23.09.27
 */
@Test
public class ProfileServiceTest {

    private ProfileServiceImpl profileService;
    private ProfileDao profileDao;

    @BeforeMethod
    public void setUp() {
        profileService = new ProfileServiceImpl();
        profileDao = createMock(ProfileDao.class);
        profileService.setProfileDao(profileDao);
    }
}
