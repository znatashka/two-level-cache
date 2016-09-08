package cache.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InitDataUtilsTest {

    @Test
    public void checkBooleanAnswer() throws Exception {
        // ACT
        boolean result = InitDataUtils.checkBooleanAnswer("y");

        // ASSERT
        assertTrue(result);
    }

    @Test
    public void checkIntegerAnswer() throws Exception {
        // ACT
        int result = InitDataUtils.checkIntegerAnswer("1");

        // ASSERT
        assertEquals(1, result);
    }
}