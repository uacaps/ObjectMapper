package edu.ua.caps.objectmappertest;

public class Note {

	String to;
	String from;
	String heading;
	String body;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public Note() {
		// TODO Auto-generated constructor stub
	}
	public Note(String to,String from, String heading, String body) {
		this.to = to;
		this.from = from;
		this.heading = heading;
		this.body = body;
	}
	
	public Note(toFrom toFrom, String heading, String body) {
		this.to = toFrom.to;
		this.from = toFrom.from;
		this.heading = heading;
		this.body = body;
	}
	
	public class toFrom {
		String to;
		String from;
		
		public toFrom() {
			// TODO Auto-generated constructor stub
		}
		public toFrom(String to, String from) {
			this.to = to;
			this.from = from;
		}
	}
	
}
