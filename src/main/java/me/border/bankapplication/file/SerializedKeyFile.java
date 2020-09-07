package me.border.bankapplication.file;

import me.border.utilities.file.AbstractSerializedFile;

import javax.crypto.SecretKey;
import java.io.File;

public class SerializedKeyFile extends AbstractSerializedFile<SecretKey> {

    public SerializedKeyFile(String file, File path, SecretKey item) {
        super(file, path, item);
    }
}
