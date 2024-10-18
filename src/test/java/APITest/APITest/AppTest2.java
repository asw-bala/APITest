package APITest.APITest;

import java.util.List;
import java.util.Map;

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
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Unit test for simple App.
 */

public class AppTest2 {
    //private static Logger logger = LogManager.getLogger(AppTest.class);

    /**
     * Rigorous Test :-)
     * @throws Exception 
     */
    @Test
    
    public void requestBody() throws Exception {

    	CreateUser_Pojo createUser = new CreateUser_Pojo();
    	Address_Pojo userAddress = new Address_Pojo();
    	
    	ConfigReader configObj = new ConfigReader();
    	
    	Response response;

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

    			
    			createUser.setUser_first_name(userFirstName);
    			createUser.setUser_last_name(userLastName);
    			createUser.setUser_contact_number(userContactNumber);
    			createUser.setUser_email_id (userEmailId);
    			
    			userAddress.setPlotNumber(plotNumber);
    			userAddress.setStreet(street);
    			userAddress.setState(state);
    			userAddress.setCountry(country);
    			userAddress.setZipCode(zipCode);
    			createUser.setUserAddress(userAddress);
    			
    			
     			ObjectMapper objMapper = new ObjectMapper();
     			
     			String RequestBody = objMapper.writeValueAsString(createUser);
     			
     			LoggerLoad.info("--------------");


     			LoggerLoad.info("RequestBody\n");
     			
     			LoggerLoad.info("--------------");
     			
     			LoggerLoad.info(RequestBody.toString());
     			//------------------Request Specification------------------
				
				  RequestSpecBuilder RequestBuilder = new RequestSpecBuilder();
				  
				  //RequestBuilder.setContentType("application/json"); //
				  RequestBuilder.setAccept("application/json");
				  
				  if (BaseURL == "Valid") BaseUri =
				  configObj.init_prop().getProperty("baseURL"); else BaseUri =
				  configObj.init_prop().getProperty("invalidbaseURL");
				  RequestBuilder.setAuth(RestAssured.basic(userName, password));
				  
				  RequestBuilder.setBaseUri(BaseUri); RequestBuilder.setBody(RequestBody);
				  
				  RequestSpecification ReqSpecification = RequestBuilder.build();
				  System.out.println(ReqSpecification.log());
				  
				  
				  //----------------Response Specification------------------
				  ResponseSpecBuilder ResponseBuilder = new ResponseSpecBuilder();
				  
				  ResponseBuilder.expectStatusLine(statusMessage);
				  ResponseBuilder.expectStatusCode(Integer.parseInt(statusCode));
				  
				  ResponseSpecification ResSpecification;
				  ResSpecification=ResponseBuilder.build();
				  
				  LoggerLoad.info(ResSpecification.toString());
				  
				  LoggerLoad.info("--------------");
				  
				  
				  
				  if(operation.equals("POST")) {
				  
//				  response =
//				  RestAssured.given().contentType("application/json").spec(ReqSpecification) //
//				  .when().post(endPoint) 
//				  .then().spec(ResSpecification).extract().response(); 
//				  LoggerLoad.info(response.toString());

				  
//				  ValidatableResponse response1 =
//				  RestAssured.given().contentType("application/json").auth().basic(userName,
//				  password).
//				  baseUri("https://userserviceapp-f5a54828541b.herokuapp.com/uap").body(
//				  RequestBody) .when().post("/createusers") .then().log().all(); //
//				  LoggerLoad.info(response1.toString());
//				  
				   RestAssured.given().contentType("application/json").auth().basic(userName,
				  password). //
				  baseUri("https://userserviceapp-f5a54828541b.herokuapp.com/uap").
				  body(RequestBody)
				  .when().post("/createusers")  .then().log().all(); //
				  
				  
				  }
				 //     			
   		}
     }
    	

    }

