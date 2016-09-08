package cache.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    public static <V> byte[] toBytes(V value) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(value);
            return baos.toByteArray();
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V toObject(byte[] bytes) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            Object o = ois.readObject();
            return (V) o;
        }
    }
}