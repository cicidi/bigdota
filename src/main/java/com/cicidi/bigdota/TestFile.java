package com.cicidi.bigdota;

import com.cicidi.bigdota.converter.dota.DotaConverter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestFile {
    public static String read(String path) throws IOException {

        File file = new File(path);
        String data = FileUtils.readFileToString(file);
        return data;
    }

    public static void main(String[] args) throws IOException {
        String path = "/Volumes/WD/project/bigdota/src/main/resources/matchRawData/2156917527";
        read(path);
        Map map = new DotaConverter(read(path)).process();
        System.out.println(map);
    }
}
