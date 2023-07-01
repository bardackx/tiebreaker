package com.bardackx.tiebreaker.tests.structures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Person {

  private int id;
  private Gender gender;
  private int age;
  private boolean citizen;
  private double score;
  private BigDecimal funds;
  private String name;

  private boolean firstTime;
  private ZonedDateTime requestDate;
  private LocalDate birthday;

  public Person() {
  }

  public Person(Object e) {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public ZonedDateTime getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(ZonedDateTime requestDate) {
    this.requestDate = requestDate;
  }

  public boolean isFirstTime() {
    return firstTime;
  }

  public void setFirstTime(boolean firstTime) {
    this.firstTime = firstTime;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + age;
    result = prime * result + (citizen ? 1231 : 1237);
    long temp;
    temp = Double.doubleToLongBits(score);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((funds == null) ? 0 : funds.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + (firstTime ? 1231 : 1237);
    result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
    result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
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
    if (id != other.id)
      return false;
    if (gender != other.gender)
      return false;
    if (age != other.age)
      return false;
    if (citizen != other.citizen)
      return false;
    if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
      return false;
    if (funds == null) {
      if (other.funds != null)
        return false;
    } else if (!funds.equals(other.funds))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (firstTime != other.firstTime)
      return false;
    if (requestDate == null) {
      if (other.requestDate != null)
        return false;
    } else if (!requestDate.equals(other.requestDate))
      return false;
    if (birthday == null) {
      if (other.birthday != null)
        return false;
    } else if (!birthday.equals(other.birthday))
      return false;
    return true;
  }

}
