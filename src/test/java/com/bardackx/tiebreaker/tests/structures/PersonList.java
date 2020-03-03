package com.bardackx.tiebreaker.tests.structures;

import java.util.List;

public class PersonList {

	private List<Person> list;

	public List<Person> getList() {
		return list;
	}

	public void setList(List<Person> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("[").append("\n");
		for (int i = 0; i < list.size(); i++) {
			Person customer = list.get(i);
			b.append("  ");
			b.append(customer.toString());
			if (i != list.size() - 1) b.append(",");
			b.append("\r\n");
		}
		b.append("]");
		return b.toString();
	}
}
