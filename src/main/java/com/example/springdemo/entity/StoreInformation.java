package com.example.springdemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="store_information")
public class StoreInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String detail;
    private String phone;
    
	public StoreInformation(int id, String name, String detail, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.detail = detail;
		this.phone = phone;
	}
	
	public StoreInformation(String name, String detail, String phone) {
		super();
		this.name = name;
		this.detail = detail;
		this.phone = phone;
	}

	public StoreInformation() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	@Override
	public String toString() {
		return "StoreInformation [id=" + id + ", name=" + name + ", detail=" + detail + ", phone=" + phone + "]";
	}

    
    
}