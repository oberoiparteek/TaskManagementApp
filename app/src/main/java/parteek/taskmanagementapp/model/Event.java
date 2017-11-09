package parteek.taskmanagementapp.model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Event {

	private String title;
	private  int id=0;



	private String description;
	private Date date;
	//private Calendar c;


	public Event(@NonNull int id,@NonNull String title, @NonNull Date date ,@NonNull String description) {
		this.id=id;

		this.title = title;

		this.description=description;
		this.date = date;
	//	c.set(date.getYear(),date.getMonth(),date.getDay(),date.getHours(),date.getMinutes());
	}
	public Event(@NonNull String title, @NonNull Date date ,@NonNull String description) {


		this.title = title;

		this.description=description;
		this.date = date;
		//c.set(date.getYear(),date.getMonth(),date.getDay(),date.getHours(),date.getMinutes());
	}


	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

}
