package APITest.APITest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import Pojo_Class.CreateUser_Pojo;
import Pojo_Class.Address_Pojo;
import Utilities.ConfigReader;
import Utilities.LoggerLoad;
import Utilities.UserExcelReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Unit test for simple App.
 */

public class AppTest {
    //private static Logger logger = LogManager.getLogger(AppTest.class);
	private final static String propertyWriteFilePath = "./src/test/resources/config/writeconfig.properties";


    /**
     * Rigorous Test :-)
     * @throws Throwable 
     */
    @Test
    
    public void requestBody() throws Throwable {

    	CreateUser_Pojo create_User = new CreateUser_Pojo();
    	Address_Pojo user_Address = new Address_Pojo();
    	
    	ConfigReader configObj = new ConfigReader();
    	
    	
    	
    	RequestSpecBuilder RequestBuilder;
    	RequestSpecification ReqSpecification;
    	Response response;
    	
    	int validRun=0;
    	String USERId;
		  String userFirstNme ;

    	String BaseUri;
    	String excelPath = ".\\src\\test\\resources\\testdata\\testdata.xlsx";
    	String sheetName = "data";	
    	
    	 List<Map<String, String>> getUserData=
    			  (UserExcelReader.getData(excelPath,sheetName));
    	 //System.out.println(getUserData.toString());
    	 // Iterate over each row of data 
    	 for (Map<String, String> row : getUserData)
    	 {
    		    //Header
    		 	String operation = row.get("Operation");
    			String Scenario = row.get("Scenario");
    			String BaseURL = row.get("BaseURL");
    			String userName = row.get("userName");
    			String password = row.get("password");
    			String endPoint = row.get("endPoint");
    			
    			//Address
    			String plotNumber = row.get("plotNumber");
    			String street = row.get("street");
    			String state = row.get("state");
    			String country = row.get("country");
    			String zipCode = row.get("zipCode");
    			
    			//User Details
    			String userFirstName = row.get("userFirstName");
    			String userLastName = row.get("userLastName");
    			String userContactNumber = row.get("userContactNumber");
    			String userEmailId = row.get("userEmailId");
    			
    			//Response
    			String statusCode = row.get("statusCode");
    			String statusMessage = row.get("statusMessage");

    			
    			create_User.setUser_first_name(userFirstName);
    			create_User.setUser_last_name(userLastName);
    			create_User.setUser_contact_number(userContactNumber);
    			create_User.setUser_email_id (userEmailId);
    			
    			user_Address.setPlotNumber(plotNumber);
    			user_Address.setStreet(street);
    			user_Address.setState(state);
    			user_Address.setCountry(country);
    			user_Address.setZipCode(zipCode);
    			
    			create_User.setUserAddress(user_Address);
    			
    			
     			ObjectMapper objMapper = new ObjectMapper();
     			
     			String RequestBody = objMapper.writeValueAsString(create_User);
     			
     			LoggerLoad.info("RequestBody:\n");     			
     			LoggerLoad.info(RequestBody.toString());
     			//------------------Request Specification------------------
				
				   RequestBuilder = new RequestSpecBuilder();
				  
				  RequestBuilder.setContentType("application/json"); //
				  RequestBuilder.setAuth(RestAssured.basic(userName, password));
				  
				 // RequestBuilder.setAccept("application/json");
				  
				  if (BaseURL.equals("Valid") )
				  
					 BaseUri =configObj.init_prop().getProperty("baseURL"); 
				 else 
				 
					  BaseUri =configObj.init_prop().getProperty("invalidbaseURL");
				 
				  
				  RequestBuilder.setBaseUri(BaseUri);
				  
				  
				  //RequestBuilder.setBody(RequestBody);
				  
				   ReqSpecification = RequestBuilder.build();
				  
				  
				  //----------------Response Specification------------------
				  ResponseSpecBuilder ResponseBuilder = new ResponseSpecBuilder();
				  
				  ResponseBuilder.expectContentType("application/json");
				  //ResponseBuilder.expectStatusLine(statusMessage);
				  ResponseBuilder.expectStatusCode(Integer.parseInt(statusCode));
				  
				  ResponseSpecification ResSpecification;
				  ResSpecification=ResponseBuilder.build();
				  
				  //------ POST OPERATION-------------				  
				  
				  
				  if(operation.equals("POST")) {
				  
				  
					  //ValidatableResponse Response =
						//	  RestAssured.given().spec(ReqSpecification).body(RequestBody) 
							//  .when().post(endPoint) 
							 // .then().spec(ResSpecification).log().all(); 
//					  LoggerLoad.info(Response.extract().asPrettyString());
//					  System.out.println(Response.extract().response().getContentType());
//					  JsonPath respJson = new JsonPath(Response.extract().asString());
//					  System.out.println(respJson.getInt("user_id"));

					  
					  response = RestAssured .given() .spec(ReqSpecification).body(RequestBody) 
							  .when() .post(endPoint)		   
							  .then() .spec(ResSpecification)
							  .extract().response();
					  
					  System.out.println(response.asString());
					  System.out.println(response.getStatusCode());	
					  int StatusCode = response.getStatusCode();
					  JsonPath respJson = new JsonPath(response.asString());
					  if (StatusCode==201)
					  {	
						  validRun++;
						  int userID = respJson.getInt("user_id");
						   USERId = Integer.toString(userID);
						   userFirstNme = respJson.getString("user_first_name");
						  loadWriteConfig("UserIdCreated"+validRun,USERId);
						  loadWriteConfig("UserNameCreated"+validRun,userFirstNme);

					  }	  


				  }
				     			
				  if(operation.equals("DELETEUSERBYID")) {
					  
					  String UserIdCreated1 = configObj.write_prop().getProperty("UserIdCreated1");
					  response = RestAssured .given() .spec(ReqSpecification) 
							  .when() .delete(endPoint+"/"+UserIdCreated1)		   
							  .then() .spec(ResSpecification)
							  .extract().response();
					  System.out.println(response.asString());
					  System.out.println("Deleted user with id :"+UserIdCreated1);

				  }
				  if(operation.equals("DELETEUSERBYNAME")) {
					  
					  String UserNameCreated2 = configObj.write_prop().getProperty("UserNameCreated2");
					  response = RestAssured .given() .spec(ReqSpecification) 
							  .when() .delete(endPoint+"/"+UserNameCreated2)		   
							  .then() .spec(ResSpecification)
							  .extract().response();
					  System.out.println(response.asString());
					  System.out.println("Deleted user with name :"+UserNameCreated2);


				  }

   		}
    	 
    	 
     }
    public static void loadWriteConfig(String key, String value) throws Throwable {

		try {
			FileOutputStream fos = new FileOutputStream(propertyWriteFilePath,true);
			
			Properties prop = new Properties();
			//prop.clear();
		
			
			prop.setProperty(key, value);
			prop.store(fos, null);
			fos.close();

			try {
				prop.store(fos,null);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("config.properties not found at config file path " + propertyWriteFilePath);
		}
	}

    	

    }

