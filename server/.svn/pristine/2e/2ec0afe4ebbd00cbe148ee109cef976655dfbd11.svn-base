package com.stockholdergame.server.dto.validation;

import com.stockholdergame.server.dto.validation.constraints.EnumName;
import com.stockholdergame.server.model.account.AccountStatus;
import java.lang.reflect.Field;
import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 30.12.11 22.53
 */
@Test
public class EnumValidatorTest {

    @EnumName(enumClass = AccountStatus.class)
    private String test;

    @Test(dataProvider = "getData")
    public void testIsValid(String value, boolean expected) throws NoSuchFieldException {
        EnumName enumNameAnnotation = getEnumNameAnnotation();
        EnumValidator enumValidator = new EnumValidator();
        enumValidator.initialize(enumNameAnnotation);
        Assert.assertEquals(enumValidator.isValid(value, null), expected);
    }

    @DataProvider
    private Object[][] getData() {
        return new Object[][] {
                {AccountStatus.ACTIVE.name(), true},
                {"qwerty", false},
                {null, true},
                {"", false}
        };
    }

    private EnumName getEnumNameAnnotation() throws NoSuchFieldException {
        Class thisClass = this.getClass();
        Field testField = thisClass.getDeclaredField("test");
        return testField.getAnnotation(EnumName.class);
    }
}
