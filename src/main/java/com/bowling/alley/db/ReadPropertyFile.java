package com.bowling.alley.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    String CONFIG_FILE = "src/main/java/com/bowling/alley/db/dataconfig.properties";
    Properties prop=new Properties();
    ReadPropertyFile() throws IOException {
        FileInputStream ip= new FileInputStream(CONFIG_FILE);
        prop.load(ip);
    }
    Properties getProperty()
    {
        return prop;
    }
}
