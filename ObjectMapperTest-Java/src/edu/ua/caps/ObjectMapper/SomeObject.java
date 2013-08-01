package edu.ua.caps.ObjectMapper;

import java.util.ArrayList;

public class SomeObject {
	
	int int1;
	@OMSerializer("STRING1")
	String string1;
	@OMSerializer("STRING2")
	String string2;
	@OMSerializer("STRING3")
	String string3;
	ArrayList<String> string4;
	String string5;
	String string6;
	
	ArrayList<SomeOtherObject> boom = new ArrayList<SomeOtherObject>();
	
	SomeOtherObject whoosh;

	public SomeObject(int int1,String string1, String string2, String string3,
			ArrayList<String> string4, String string5, String string6,ArrayList<SomeOtherObject> boom,SomeOtherObject whoosh) {
		this.int1 = int1;
		this.string1 = string1;
		this.string2 = string2;
		this.string3 = string3;
		this.string4 = string4;
		this.string5 = string5;
		this.string6 = string6;
		this.boom = boom;
		this.whoosh = whoosh;
	}

}
