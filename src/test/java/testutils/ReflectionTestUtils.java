package testutils;

import java.lang.reflect.Field;

public class ReflectionTestUtils {

    public static void setField(Object o, String fieldName, Object field) throws Exception {
        Field declaredField = o.getClass().getDeclaredField(fieldName);
        if (declaredField != null) {
            declaredField.setAccessible(true);
            declaredField.set(o, field);
        }
    }
}
