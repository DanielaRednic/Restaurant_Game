package utils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.*;
public class File {
    private final String path;
    File(String _path) {
        this.path = _path;
    }
    public String getFilePath() {
        return this.path;
    }



}
