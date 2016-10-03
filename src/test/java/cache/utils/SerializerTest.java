package cache.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    @Test
    public void serialize() throws Exception {
        // PREPARE
        String source = "test string";

        // ACT
        String result = Serializer.toObject(Serializer.toBytes(source));

        // ASSERT
        assertEquals(source, result);
    }
}