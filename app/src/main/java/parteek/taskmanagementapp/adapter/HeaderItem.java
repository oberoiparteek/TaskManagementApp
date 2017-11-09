package parteek.taskmanagementapp.adapter;

import android.support.annotation.NonNull;

import java.util.Date;

public class HeaderItem extends ListItem {

	@NonNull
	private Date date;

	public HeaderItem(@NonNull Date date) {
		this.date = date;
	}

	@NonNull
	public Date getDate() {
		return date;
	}


	@Override
	public int getType() {
		return TYPE_HEADER;
	}

}