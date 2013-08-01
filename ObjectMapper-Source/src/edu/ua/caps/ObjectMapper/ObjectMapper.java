//  Copyright (c) 2012 The Board of Trustees of The University of Alabama
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions
//  are met:
//
//  1. Redistributions of source code must retain the above copyright
//  notice, this list of conditions and the following disclaimer.
//  2. Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//  3. Neither the name of the University nor the names of the contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
//  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
//  THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
//  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
//   SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
//  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
//  STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
//  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.

package edu.ua.caps.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;

public class ObjectMapper {

	public static final String datePatternString = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String dateTimeZone = "UTC";

	///////////////////////////////////////////////////////////////////BEGIN SOAP
	/**
	 * This method is called to serialize an object to its SOAP representation.
	 * 
	 * @param rootHeaderClass
	 *            The class of the header object.
	 * @param rootBodyClass
	 *            The class of the body object.
	 * @return
	 */

	public static String toSoap(OMSoapObject soapObject) {
		// Open Envelope
		String soapString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://tempuri.org/\">";

		if (soapObject.header != null) {
			soapString += "<soap:Header>";

			// Create header
			// soapString += ObjectMapper.stringForObject(soap.header,
			// rootHeaderClass,true);
			soapString += ObjectMapper.stringForObject(soapObject.header,
					soapObject.header.getClass(), true);

			soapString += "</soap:Header>";
		}

		if (soapObject.body != null) {
			soapString += "<soap:Body>";

			// Create body
			soapString += ObjectMapper.stringForObject(soapObject.body,
					soapObject.body.getClass(), true);

			soapString += "</soap:Body>";
		}

		// Close Envelope
		soapString = soapString + "</soap:Envelope>";

		return soapString;
	}

	/**
	 * This method is called to deserialize a SOAP string to its designated
	 * object representation.
	 * 
	 * @param soapString
	 *            The input soap string to be deserialized.
	 * @param desiredClass
	 *            The desired class for the soap string to be deserialized to.
	 * @return Object The desired object. This will be casted to your desired
	 *         subclass of Object.
	 */

	public static Object fromSoap(String soapString, Class<?> desiredClass) {
		// Instantiate new scanner
		OMScanner scanner = new OMScanner(soapString);

		// Scan to beginning of response
		scanner.skipToString("<"
				+ ObjectMapper.unquailifiedClassNameForClass(desiredClass));
		scanner.skipToString(">");

		// Get object and start recursion
		Object newObject = ObjectMapper.getNodeValue(scanner, desiredClass,
				ObjectMapper.unquailifiedClassNameForClass(desiredClass));

		// Return object
		return newObject;
	}

	///////////////////////////////////////////////////////////////////END SOAP

	///////////////////////////////////////////////////////////////////BEGIN XML

	public static String toXML(OMXmlObject xmlObject) {
		String xmlString = "";
		xmlString += ObjectMapper.stringForObject(xmlObject.body,
				xmlObject.body.getClass(), true);
		return xmlString;
	}

	/**
	 * @author afleshner  
	 * This method is called to deserialize a XML string to its
	 *         designated object representation.
	 * 
	 * @param soapString
	 *            The input soap string to be deserialized.
	 * @param desiredClass
	 *            The desired class for the soap string to be deserialized to.
	 * @return Object The desired object. This will be casted to your desired
	 *         subclass of Object.
	 * 
	 */
	public static Object fromXML(String xmlString, Class<?> desiredClass) {
		// Instantiate new scanner
		OMScanner scanner = new OMScanner(xmlString);

		// Scan to beginning of response
		scanner.skipToString("<"
				+ ObjectMapper.unquailifiedClassNameForClass(desiredClass));
		scanner.skipToString(">");

		// Get object and start recursion
		Object newObject = ObjectMapper.getNodeValue(scanner, desiredClass,
				ObjectMapper.unquailifiedClassNameForClass(desiredClass));

		// Return object
		return newObject;
	}

	///////////////////////////////////////////////////////////////////END XML

	///////////////////////////////////////////////////////////////////BEGIN HELPERS

	@SuppressWarnings("unchecked")
	private static String stringForObject(Object object, Class<?> desiredClass,
			Boolean printTopClass) {
		String objectString = "";

		if (desiredClass != null) {
			if (printTopClass) {
				objectString += "<"
						+ ObjectMapper
								.unquailifiedClassNameForClass(desiredClass)
						+ ">";
			}

			Field[] fields = desiredClass.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);

				// Check for null object
				try {
					if (field.get(object) == null) {
						continue;
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}

				// Open tag
				objectString += "<" + checkForAnnotaions(field) + ">";

				// String or number
				if (String.class.equals(field.getType())
						|| Number.class.equals(field.getType())
						|| field.getType().isPrimitive()) {
					try {
						objectString += field.get(object).toString();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}
				// Byte array
				else if (field.getType().equals(byte[].class)) {
					try {
						objectString += Base64
								.encodeBase64String((byte[]) field.get(object));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getType().equals(Date.class)) {
					DateFormat format = new SimpleDateFormat(datePatternString);
					format.setTimeZone(TimeZone.getTimeZone(dateTimeZone));
					try {
						objectString += format.format(field.get(object));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				// Other Array (Are we seriously doing a string comparison?) --
				// Edit: YEP!
				else if (field.getType().getName().charAt(0) == '[') {
					// Get the array
					Object[] objects = null;
					try {
						objects = (Object[]) field.get(object);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					// Iterate over all objects and add their strings for each
					for (Object object1 : objects) {
						objectString += "<"
								+ ObjectMapper
										.unquailifiedClassNameForClass(object1
												.getClass()) + ">";
						objectString += ObjectMapper.stringForObject(object1,
								object1.getClass(), false);
						objectString += "</"
								+ ObjectMapper
										.unquailifiedClassNameForClass(object1
												.getClass()) + ">";
					}
				}
				// Other Array (Are we seriously doing a string comparison?)
				else if (field.getType().equals(ArrayList.class)) {
					// Get the array
					ArrayList<Object> objects = new ArrayList<Object>();
					try {
						objects = (ArrayList<Object>) field.get(object);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					// Iterate over all objects and add their strings for each
					for (Object object1 : objects) {
						if (ObjectMapper.unquailifiedClassNameForClass(
								object1.getClass()).equals("String")) {
							objectString += "<string>" + object1 + "</string>";
						} else {
							objectString += "<"
									+ ObjectMapper
											.unquailifiedClassNameForClass(object1
													.getClass()) + ">";
							objectString += ObjectMapper.stringForObject(
									object1, object1.getClass(), false);
							objectString += "</"
									+ ObjectMapper
											.unquailifiedClassNameForClass(object1
													.getClass()) + ">";
						}
					}
				}
				// Complex Type
				else {
					try {
						objectString += ObjectMapper.stringForObject(field.get(object), field.getType(), false);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}

				// Close tag
				objectString += "</" + checkForAnnotaions(field) + ">";
			}

			if (printTopClass) {
				objectString += "</"
						+ ObjectMapper
								.unquailifiedClassNameForClass(desiredClass)
						+ ">";
			}
		}

		return objectString;
	}

	/**
	 * @author afleshner :) Takes in the Field and Checks for an annotation of
	 *         type OMSerializier and Returns the value if annotation present
	 *         and returns the name of the field if not.
	 * @param f
	 * @return
	 */
	private static String checkForAnnotaions(Field f) {
		String returnValue = "";
		try {
			// Processing all the annotations on a single field
			Annotation a = f.getAnnotations()[0];
			// Checking for a NullValueValidate annotation
			if (a.annotationType() == OMSerializer.class) {
				OMSerializer annotation = (OMSerializer) a;
				f.setAccessible(true);
				returnValue = annotation.value();
			}
		} catch (Exception e) {
			returnValue = f.getName();
		}
		return returnValue;
	}

	private static String unquailifiedClassNameForClass(Class<?> inputClass) {
		return inputClass.getName().substring(
				inputClass.getName().lastIndexOf('.') + 1);
	}

	private static Object getNodeValue(OMScanner scanner,
			Class<?> desiredClass, String tagName) {

		Object object = null;
		try {
			object = desiredClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		while (!scanner.endOfTag(tagName) && scanner.hasNext()) {
			// Scan next tag
			String nextTag = scanner.nextXMLTag();

			if (nextTag.contains(" /")) {
				scanner.skipEmptyTag();
				continue;
			}

			Class<?> elementClass = null;
			Field elementField = null;
			for (Field field : object.getClass().getDeclaredFields()) {
				if (checkForAnnotaions(field).equals(nextTag)) {
					elementClass = field.getType();
					elementField = field;
					break;
				}
			}

			if (elementClass != null) {
				try {
					if (String.class.equals(elementClass)) {
						elementField.set(object, scanner.readNextTagValue());
					} else if (Date.class.equals(elementClass)) {
						// Create date formatter
						DateFormat format = new SimpleDateFormat(
								datePatternString);
						format.setTimeZone(TimeZone.getTimeZone(dateTimeZone));

						// Parse back date
						try {
							elementField.set(object,
									format.parse(scanner.readNextTagValue()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else if (Double.class.equals(elementClass)) {
						elementField.set(object,
								Double.valueOf(scanner.readNextTagValue()));
					} else if (double.class.equals(elementClass)) {
						elementField.set(object,
								Double.parseDouble(scanner.readNextTagValue()));
					} else if (Integer.class.equals(elementClass)) {
						elementField.set(object,
								Integer.valueOf(scanner.readNextTagValue()));
					} else if (int.class.equals(elementClass)) {
						elementField.set(object,
								Integer.parseInt(scanner.readNextTagValue()));
					} else if (Float.class.equals(elementClass)) {
						elementField.set(object,
								Float.valueOf(scanner.readNextTagValue()));
					} else if (float.class.equals(elementClass)) {
						elementField.set(object,
								Float.parseFloat(scanner.readNextTagValue()));
					} else if (Boolean.class.equals(elementClass)) {
						elementField.set(object,
								Boolean.valueOf(scanner.readNextTagValue()));
					} else if (boolean.class.equals(elementClass)) {
						elementField.set(object, Boolean.parseBoolean(scanner
								.readNextTagValue()));
					} else if (ArrayList.class.equals(elementClass)) {
						scanner.skipToString("<" + nextTag);
						scanner.skipToString(">");

						// Get array internal object class
						String arrayClassString = scanner.nextXMLTag();
						Class<?> desiredClass2 = null;
						try {
							// Handle primitive types in array
							if (arrayClassString.equals("string")) {
								desiredClass2 = String.class;
							} else if (arrayClassString.equals("int")) {
								desiredClass2 = Integer.class;
							} else if (arrayClassString.equals("float")) {
								desiredClass2 = Float.class;
							} else if (arrayClassString.equals("double")) {
								desiredClass2 = Double.class;
							} else if (arrayClassString.equals("bool")
									|| arrayClassString.equals("boolean")) {
								desiredClass2 = Boolean.class;
							} else if (arrayClassString.equals("base64Binary")) {
								desiredClass2 = byte[].class;
							} else {
								desiredClass2 = Class.forName(object.getClass()
										.getPackage().getName()
										+ "." + arrayClassString);
							}

						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

						// Parse Array
						if (desiredClass2 != null) {
							elementField.set(object, ObjectMapper
									.GetArrayValue(scanner, nextTag,
											desiredClass2));
						}

						scanner.skipToString("</" + nextTag + ">");
					} else if (byte[].class.equals(elementClass)) {
						elementField
								.set(object, Base64.decodeBase64(scanner
										.readNextTagValue()));
					} else {
						scanner.skipToString("<" + nextTag);
						scanner.skipToString(">");
						elementField.set(object, ObjectMapper.getNodeValue(
								scanner, elementClass, nextTag));
						scanner.skipToString("</" + nextTag + ">");
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				System.out
						.println("Property "
								+ nextTag
								+ " not found in object "
								+ desiredClass.getName()
								+ ". Please make sure the property is present and made public.");
				break;
			}
		}

		return object;
	}

	private static ArrayList<Object> GetArrayValue(OMScanner scanner,
			String arrayName, Class<?> desiredClass) {
		ArrayList<Object> objects = new ArrayList<Object>();

		// Get desired array class name
		String arrayClassString = scanner.nextXMLTag();

		if (desiredClass != null) {
			while (!scanner.endOfTag(arrayName) && scanner.hasNext()) {

				// Handle primitives
				if (desiredClass.equals(String.class)) {
					objects.add(scanner.readNextTagValue());
				} else if (desiredClass.equals(Integer.class)) {
					objects.add(Integer.valueOf(scanner.readNextTagValue()));
				} else if (desiredClass.equals(Float.class)) {
					objects.add(Float.valueOf(scanner.readNextTagValue()));
				} else if (desiredClass.equals(Double.class)) {
					objects.add(Double.valueOf(scanner.readNextTagValue()));
				} else if (desiredClass.equals(Boolean.class)) {
					objects.add(Boolean.valueOf(scanner.readNextTagValue()));
				} else if (desiredClass.equals(byte[].class)) {
					objects.add(Base64.decodeBase64(scanner.readNextTagValue()));
				} else { // Complex object
					scanner.skipToString("<" + arrayClassString);
					scanner.skipToString(">");
					objects.add(ObjectMapper.getNodeValue(scanner,
							desiredClass, arrayClassString));
					scanner.skipToString("</" + arrayClassString + ">");
				}

			}
		}

		return objects;
	}

}
