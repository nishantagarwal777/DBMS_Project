package com.example.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.PropertyDAO;
import com.example.model.Property;

public class PropertyBean {
	
	@Autowired
	private PropertyDAO propertyDAO;	
	
	public PropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}
	
	public ArrayList<Property> getProperty(){
		return propertyDAO.getProperty();
	}
	
	public boolean insertImage(byte[] arr){
		return propertyDAO.insertImage(arr);
	}
	
	public List<byte[]> getImages(String imageId){
		return propertyDAO.getImages(imageId);
	}
	public ArrayList<String> getCities(){
		return propertyDAO.getCities();
	}
}
