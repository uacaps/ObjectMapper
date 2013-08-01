package edu.ua.caps.ObjectMapper;

import java.util.ArrayList;

public class TestMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Declare local variables
		SomeObject so;
		OMXmlObject xml,xml2;
		Note note;
		ArrayList<String> reallys = new ArrayList<String>();
		
		//Add content to reallys
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		
		//Build SomeOtherObject
		ArrayList<SomeOtherObject> bam = new ArrayList<SomeOtherObject>();
		bam.add(new SomeOtherObject("hi", "bu bye"));
		bam.add(new SomeOtherObject("hola", "Asta Lavista"));
		bam.add(new SomeOtherObject("bonjour", "au revoir"));
		so = new SomeObject( 1 ,"This", "is", "something", reallys, "freaking", "Cool",bam,new SomeOtherObject("sup", "dueces"));
		
		//To xml
		xml = new OMXmlObject(so);
		String text = ObjectMapper.toXML(xml);
		
		//Build note and make xml from it
		note = new Note("Matt", "Aaron", "XML MAPPING", "So I think this should work but we shall see");
		xml2 = new OMXmlObject(note);
		String noteToMatt = ObjectMapper.toXML(xml2);
		
		//Bring back to note object from xml
		Note note2 = (Note) ObjectMapper.fromXML(noteToMatt, Note.class);
		
		//Print everything out!
		System.out.println(text +"\n\n\n\n" + noteToMatt  +"\n\n\n\nTo: " + note2.getTo() + "\nFrom: "+ note.getFrom()+ "\nHeading: "+ note.getHeading()+ "\n\nBody:\n"+ note.getBody());
	}

}
