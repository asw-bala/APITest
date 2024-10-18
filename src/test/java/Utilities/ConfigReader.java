package Utilities;

import java.io.IOException;
import java.util.Properties;

import java.io.FileInputStream;




public class ConfigReader {

	private static Properties prop;
	private static String ConfigFilePath;
	//private final static String propertyFilePath = "./src/test/resources/config/config.properties";	

	
	public Properties init_prop() throws IOException {
    	
    	 System.out.println("executing LoadProperties.....");
    	 try {
    		
    		 ConfigFilePath = System.getProperty("user.dir") + "//src//test//resources//config//config.properties";
    		
    		 prop = new Properties();
    		
    		 FileInputStream ip = new FileInputStream(ConfigFilePath);
    		 prop.load(ip);
    		
    	 }
         catch(NullPointerException nullPointer) {
  			System.out.println(nullPointer.getCause());
  			System.out.println(nullPointer.getMessage());
  			nullPointer.printStackTrace();
  		}
		
    	
    	 return prop;
    	
    }
	
	public Properties write_prop() throws IOException {
    	
   	 System.out.println("executing Write Properties.....");
   	 try {
   		
   		 ConfigFilePath = System.getProperty("user.dir") + "//src//test//resources//config//writeconfig.properties";
   		
   		 prop = new Properties();
   		
   		 FileInputStream ip = new FileInputStream(ConfigFilePath);
   		 prop.load(ip);
   		
   	 }
        catch(NullPointerException nullPointer) {
 			System.out.println(nullPointer.getCause());
 			System.out.println(nullPointer.getMessage());
 			nullPointer.printStackTrace();
 		}
		
   	
   	 return prop;
   	
   }
}
	
	