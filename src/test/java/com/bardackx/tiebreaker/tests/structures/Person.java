package com.bardackx.tiebreaker.tests.structures;

import java.math.BigDecimal;

public class Person {

	private int id;
	private Gender gender;
	private int age;
	private boolean citizen;
	private double score;
	private BigDecimal funds;

	public Person() {
	}

	public Person(Object e) {
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isCitizen() {
		return citizen;
	}

	public void setCitizen(boolean citizen) {
		this.citizen = citizen;
	}

	public boolean isFemale() {
		return gender == Gender.female;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + (citizen ? 1231 : 1237);
		result = prime * result + ((funds == null) ? 0 : funds.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		if (citizen != other.citizen)
			return false;
		if (funds == null) {
			if (other.funds != null)
				return false;
		} else if (!funds.equals(other.funds))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
			return false;
		return true;
	}

}
