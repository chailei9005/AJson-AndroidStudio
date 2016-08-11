package com.an.ajson.sqlite;

import com.aiitec.openapi.db.annotation.Unique;
import com.aiitec.openapi.json.JSON;

public class Person {

	private int id = -1;
	@Unique
	private String name;
	private String englishName;
	private long year = -1;
	private double money = -1;
	private float goods = -1;
//	private long old = 90;
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
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public float getGoods() {
		return goods;
	}
	public void setGoods(float goods) {
		this.goods = goods;
	}
	@Override
	public String toString() {
		try {
			return JSON.toJsonString(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}

