package com.an.ajson.sqlite;

import java.util.List;

import com.aiitec.openapi.db.annotation.Column;
import com.aiitec.openapi.db.annotation.NotNull;
import com.aiitec.openapi.db.annotation.Unique;
import com.aiitec.openapi.json.JSON;

public class User {
	public User() {
	}

	@NotNull
	@Unique
	private int id;
	private String name;
	private int age;
	private int age2;
	private int age3;
	private int isExit;
	public int getIsExit() {
		return isExit;
	}

	public void setIsExit(int isExit) {
		this.isExit = isExit;
	}
	@Column(column="enName")
	private String englishName;
	private String job;
	private String time;
	private String sex;
	private long year;
	private double money;
	private float height;
	private List<String> mobiles;
	private Person person;

	// private String home = "43234432";

	public Person getPerson() {
		return person;
	}
	
	public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }

    public int getAge3() {
        return age3;
    }

    public void setAge3(int age3) {
        this.age3 = age3;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setPerson(Person person) {
		this.person = person;
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

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public List<String> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
