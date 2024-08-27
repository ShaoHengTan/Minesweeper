package propertiesloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import renderer.Renderer;

public class PropertiesLoader {

	final String propertiesPath = new File("").getAbsolutePath() +"\\properties\\"; //this gets the diectory that the program runs in
	Properties prop = null;
	//filename = 'game.properties'
	public PropertiesLoader(String fileName) {
		if(prop ==null)
			prop = new Properties();
		
		String appConfigPath = propertiesPath + fileName;
		System.out.println("Loading.."+appConfigPath);
		
		try {
			prop.load(new FileInputStream(appConfigPath));

		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	
	
}
