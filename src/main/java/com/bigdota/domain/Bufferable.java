package com.bigdota.domain;

import com.datastax.driver.core.utils.Bytes;

import java.io.*;
import java.nio.ByteBuffer;

public interface Bufferable extends Serializable {


    default ByteBuffer serialize() {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bytes);) {
            oos.writeObject(this);
            String hexString = Bytes.toHexString(bytes.toByteArray());
            return Bytes.fromHexString(hexString);
        } catch (IOException e) {
//            LOGGER.error("Serializing bufferable object error", e);
            return null;
        }
    }

    public static Bufferable deserialize(ByteBuffer bytes) {
        String hx = Bytes.toHexString(bytes);
        ByteBuffer ex = Bytes.fromHexString(hx);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ex.array()));) {
            return (Bufferable) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }
}