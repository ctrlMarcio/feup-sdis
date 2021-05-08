package pt.up.fe.sdis2021.t7g10.lab5.protocol;

import java.io.*;

public interface TCPMessage extends Serializable {

    static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    static Object deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(in);

        return is.readObject();
    }
}
