ObjectMapper
============

A java library for the automatic mapping of custom classes to and from XML/SOAP. With ObjectMapper, you can build XML and SOAP packets automatically based on your complex objects, potentially saving hundreds of lines of serialization code.

![ScreenShot](https://raw.github.com/uacaps/ObjectMapper/master/ScreenShots/xmlScreen-01.png)

##Download
Here you can Download the latest jar. to add into your project.
[ObjectMapper 1.0.1](https://github.com/uacaps/ObjectMapper/raw/master/ObjectMapper.jar)

##OMSerailizer##

The OMSerailizer is an annotation interface that allows you to map the correct variable name to serialize to for soap and xml.

Ex. 

```java
@OMSerializer("name")
private String _name;
```
Now instead of the ObjectMapper mapping "_name" to the variable name it will map it to "name" and visa versa on the way back to being an object.

##Object Setup##

* This is a simple custom object.
* To name variables you may name them exactly how you want them to be mapped in the xml\soap or you may use the @OMSerializer to override the name as shown above.
* Each object must have a blank constructor so that me the ObjectMapper is able to map to it and do its magic.
* Otherwise you may set the variable how you like public, protected, private or default it works no matter how those are set. 


Ex.
```java
public SomeObject{

	private String String1;
	@OMSerializer("String2")
	private String _str2;

	public SomeObject(){
		//NOTE: You must have a blank contructor. For ObjectMapper to work correctly.
	}
		 
	public SomeObject(String str1,String str2){
		this.String1 = str1;
		this._str2 = str2;
	}
		
		
	public String getString1(){
		return this.String1;
	}
	public String get_str2(){
		return this._str2;
	}
	public void set_str1(String string){
		this.String1 = string;
	}
	public void set_str2(String string){
		this._str2 = string;
	}

}
```

##Serailizing##

**Object to Xml**

To serialize an object to xml

1. Make the object and set variables.

2. Call ObjectMapper.toXML(YOUROBJECT);

Ex.

```java
SomeObject so = new SomeObject("Freaking", "Magic");
String Text = ObjectMapper.toXml(so);
System.out.println(text);
```
Output :
```
<SomeObject>
	<String1>Freaking</String1>
	<String2>Magic</String2>
</SomeObject>
```
##Deserailizing##

**Xml to Object**

To deserialize back to object.

1. Valid XML

2. Object to map back to.

3. Call ObjectMapper.fromXml(YOURXML, YOUROBJECT.class);

```java
SomeObject so = new SomeObject("Freaking", "Magic");
String Text = ObjectMapper.toXml(so);
SomeObject so2 = ObjectMapper.fromXml(text,SomeObject.class);

System.out.println(so2.String1);

```

Output: Freaking

**Object to soap**

// Next TODO

**Soap to Object **

// Next TODO

--------------------
## License ##

Copyright (c) 2012 The Board of Trustees of The University of Alabama
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. Neither the name of the University nor the names of the contributors
    may be used to endorse or promote products derived from this software
    without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
