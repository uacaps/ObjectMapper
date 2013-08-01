ObjectMapper
============

A java library for the automatic mapping of custom classes to and from XML/SOAP. With ObjectMapper, you can build XML and SOAP packets automatically based on your complex objects, potentially saving hundreds of lines of serialization code.

![ScreenShot](https://raw.github.com/uacaps/ObjectMapper/master/ScreenShots/xmlScreen-01.png)

##OMSerailizer

The OMSerailizer is an annotation interface that allows you to map the correct variable name to serialize to for soap and xml.

Ex. 

```
@OMSerailizer("name")
private String _name;
```
Now instead of the ObjectMapper mapping "_name" to the variable name it will map it to "name" and visa versa on the way back to being an object.
