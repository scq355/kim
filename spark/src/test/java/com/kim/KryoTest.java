package com.kim;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import junit.framework.TestCase;
import lombok.Data;
import scala.Serializable;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class KryoTest extends TestCase {

    public static void main(String[] args) {
        User user = new User();
        user.setUsergae(20);
        user.setUsername("scq");
        // 比较大小差异
        javaSerial(user, "serial/user_java.dat");
        kryoSerial(user, "serial/user_kryo.dat");
        User user1 = kryoDeSerial(User.class, "serial/user_kryo.dat");
        System.out.println(user1.getUsergae());
        System.out.println(user1.getUsername());
    }

    public static void javaSerial(Serializable s, String filepath) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
            outputStream.writeObject(s);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void kryoSerial(Serializable s, String filepath) {
        try {
            Kryo kryo = new Kryo();
            kryo.register(s.getClass(), new BeanSerializer(kryo, s.getClass()));
            Output output = new Output(new BufferedOutputStream(new FileOutputStream(filepath)));
            kryo.writeObject(output, s);
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T kryoDeSerial(Class<T> c, String filepath) {
        try {
            Kryo kryo = new Kryo();
            kryo.register(c, new BeanSerializer(kryo, c));
            Input input = new Input(new BufferedInputStream(new FileInputStream(filepath)));
            T t = kryo.readObject(input, c);
            input.close();
            return t;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

@Data
class User implements Serializable{
    private transient String username;
    private int usergae;
}
