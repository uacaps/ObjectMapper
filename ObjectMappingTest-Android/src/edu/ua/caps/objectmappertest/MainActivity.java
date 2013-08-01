package edu.ua.caps.objectmappertest;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import edu.ua.caps.ObjectMapper.OMXmlObject;
import edu.ua.caps.ObjectMapper.ObjectMapper;

public class MainActivity extends Activity {

	public TextView mTxt;
	SomeObject so;
	OMXmlObject xml,xml2;
	
	Note note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayList<String> reallys = new ArrayList<String>();
		
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		reallys.add("really");
		
		ArrayList<SomeOtherObject> bam = new ArrayList<SomeOtherObject>();
		bam.add(new SomeOtherObject("hi", "bu bye"));
		bam.add(new SomeOtherObject("hola", "Asta Lavista"));
		bam.add(new SomeOtherObject("bonjour", "au revoir"));
		so = new SomeObject( 1 ,"This", "is", "something", reallys, "freaking", "Cool",bam,new SomeOtherObject("sup", "dueces"));
		xml = new OMXmlObject(so);
		String text = ObjectMapper.toXML(xml);
		note = new Note("Matt", "Aaron", "XML MAPPING", "So I think this should work but we shall see");
		xml2 = new OMXmlObject(note);
		String noteToMatt = ObjectMapper.toXML(xml2);
		
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
