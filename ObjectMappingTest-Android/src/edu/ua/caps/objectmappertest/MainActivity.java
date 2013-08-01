package edu.ua.caps.objectmappertest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import edu.ua.caps.ObjectMapper.OMXmlObject;
import edu.ua.caps.ObjectMapper.ObjectMapper;

public class MainActivity extends Activity {

	private TextView mTxt;
	private SomeObject so;
	private OMXmlObject xml,xml2;
	private Note note;
	private ArrayList<String> reallys = new ArrayList<String>();
	private ArrayList<SomeOtherObject> bam = new ArrayList<SomeOtherObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Adding content to a string arrayList
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		
		//Adding Content to a complex ArrayList
		bam.add(new SomeOtherObject("hi", "bu bye"));
		bam.add(new SomeOtherObject("hola", "Asta Lavista"));
		bam.add(new SomeOtherObject("bonjour", "au revoir"));
		
		//Adding Content to a Complex object
		so = new SomeObject( 1 ,"This", "is", "something", reallys, "freaking", "Cool",bam,new SomeOtherObject("sup", "dueces"));
		
		//Creating an OMXmlObject with the SomeObject as a parameter
		xml = new OMXmlObject(so);
		//Creating an xml String from the OMXmlObject
		String text = ObjectMapper.toXML(xml);
		
	
		note = new Note("Matt", "Aaron", "XML MAPPING", "So I think this should work but we shall see");
		//Creating an OMXmlObject with the note as a parameter
		xml2 = new OMXmlObject(note);
		//Creating an xml String from the OMXmlObject
		String noteToMatt = ObjectMapper.toXML(xml2);
		//Create complex object from xml String. all you need to do is tell it what object you want to map the xml to.
		Note note2 = (Note) ObjectMapper.fromXML(noteToMatt, Note.class);
		mTxt = (TextView) findViewById(R.id.mtxt1);
		mTxt.setText(text +"\n\n\n\n" + noteToMatt  +"\n\n\n\nTo: " + note2.getTo() + "\nFrom: "+ note.getFrom()+ "\nHeading: "+ note.getHeading()+ "\n\nBody:\n"+ note.getBody());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
