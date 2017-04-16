package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.PredictBean;
import com.example.business.PropertyBean;
import com.example.business.SellBean;
import com.example.business.UserBean;
import com.example.model.PredictProperty;
import com.example.model.Property;
import com.example.model.PropertyFeature;
import com.example.model.Sell;
import com.example.model.User;

@RestController
class DemoController2{
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
	UserBean userBean = (UserBean) applicationContext.getBean("userBean");
	PropertyBean propertyBean = (PropertyBean)applicationContext.getBean("propertyBean");
	PredictBean predictBean = (PredictBean)applicationContext.getBean("predictBean");//Anitha changes
	SellBean sellBean = (SellBean)applicationContext.getBean("sellBean");//Anitha changes
	
	@RequestMapping(value = "/login",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public User secondPage(@RequestBody User user){
		User userOutput = userBean.getUser(user);
		return userOutput;
		
		
		/*anitha ranganathan*/
	}
	
	@RequestMapping(value = "/signUp",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public User signUp(@RequestBody User user){
		User userOutput = userBean.insertUser(user);
		return userOutput;
	}
	
	@RequestMapping(value = "/getPopularProperties",method=RequestMethod.GET,produces="application/json")
	public ArrayList<Property> getPopularProperties(){
		ArrayList<Property> popularProperty = propertyBean.getProperty();
		return popularProperty;
	}

	/*Anitha changes begin*/
	@RequestMapping(value = "/getCities",method=RequestMethod.GET,produces="application/json")
	public ArrayList<String> getCities(){
		ArrayList<String> popularProperty = propertyBean.getCities();
		return popularProperty;
	}
	@RequestMapping(value = "/Sell",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public Sell insertDetails(@RequestBody Sell sell){
//		Sell sell = new Sell();
//		sell.setPropId(0);
//		sell.setSquareFeet(1200);
//		sell.setNumberOfBedrooms(2);;
//		sell.setNumberOfBathrooms(2);;
//		sell.setNumberOfFloors(1);;
//		sell.setYearRenovated("2010");
//		sell.setYearBuilt("1991");
//	//image
//		sell.setImage(null);
//		sell.setStatus("UNSOLD");;
//		sell.setDescription("anitha inserted data");
//		sell.setSaleDate(null);;
//		sell.setPrice(12300000);
//		sell.setLatitude(200);
//		sell.setLongitude(20);
//		sell.setStreet("dfhsf");
//		sell.setZipcode(32608);
//		sell.setCity("Mumbai");
//		sell.setSellerId(4646);
//		sell.setNegotiable(0);
		Sell sellOutput = sellBean.insertDetails(sell);
		return sellOutput;
	}
	/*Anitha changes end*/
	@RequestMapping(value = "/estimateValue",method=RequestMethod.POST,produces="application/json")
	public double getEstimate(@RequestBody PredictProperty p){
		double estimated_value = predictBean.predictValue(p).getEstimatedValue();
		return estimated_value;
	}
	

	@RequestMapping(value="/getTotalRecords",method=RequestMethod.GET)
	public int showAllRecords(){
		System.out.println("Show all records called");
		int total = userBean.getTotalRecords();
		System.out.println("Total Records = "+total);
		return total;
	}

	@RequestMapping(value = "/insertImage",method=RequestMethod.GET,produces="application/json")
	public boolean imsertImageData(){
		Boolean insertionResult = propertyBean.insertImage();
		return insertionResult;
	}
	
	/*
	 * END-Point to search properties based on selected features
	 */
	@RequestMapping(value = "/searchProperties",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public ArrayList<Property> getSearchedProperties(@RequestBody Property property){
		System.out.println("Controller hit");
		ArrayList<Property> searchedProperty = propertyBean.getSearchedProperties(property);
		return searchedProperty;
	}
	
	@RequestMapping(value = "/downloadImage", method = RequestMethod.GET, produces = "image/jpeg")
    public ResponseEntity<String> getPDF() {
        FileInputStream fileStream;
        try {
        	List<byte[]> images = propertyBean.getImages();  
        	System.out.println("Inside this ");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/jpeg"));
            String filename = "image1.jpeg";
            headers.setContentDispositionFormData(filename, filename);
            String imageEncoded = Base64.encode(images.get(0));
            ResponseEntity<String> response = new ResponseEntity<String>(imageEncoded, headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

}
