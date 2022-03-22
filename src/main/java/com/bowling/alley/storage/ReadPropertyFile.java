package com.bowling.alley.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

    String CONFIG_FILE = "src/main/resources/dataconfig.properties";
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
