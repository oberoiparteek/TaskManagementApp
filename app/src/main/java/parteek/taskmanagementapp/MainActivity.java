package parteek.taskmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import parteek.taskmanagementapp.adapter.EventItem;
import parteek.taskmanagementapp.adapter.EventsAdapter;
import parteek.taskmanagementapp.adapter.HeaderItem;
import parteek.taskmanagementapp.adapter.ListItem;
import parteek.taskmanagementapp.model.Event;
import parteek.taskmanagementapp.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

	@NonNull
	private List<ListItem> items = new ArrayList<>();

	@NonNull
	private EventsAdapter  eventsAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Map<Date, List<Event>> events = toMap(loadEvents());

		for (Date date : events.keySet()) {
			HeaderItem header = new HeaderItem(date);
			items.add(header);
			for (Event event : events.get(date)) {
				EventItem item = new EventItem(event);
				items.add(item);
			}
		}


		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		eventsAdapter=new EventsAdapter(items);
		recyclerView.setAdapter(eventsAdapter);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addEvent);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Click action
				Intent intent = new Intent(MainActivity.this, AddNewEventActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {

		super.onResume();
		items.clear();
		Map<Date, List<Event>> events = toMap(loadEvents());

		for (Date date : events.keySet()) {
			HeaderItem header = new HeaderItem(date);
			items.add(header);
			for (Event event : events.get(date)) {
				EventItem item = new EventItem(event);
				items.add(item);
			}
		}

		eventsAdapter.notifyDataSetChanged();

	}

	@NonNull
	private List<Event> loadEvents() {
		List<Event> events ;
		DBHelper dbHelper=new DBHelper(this);
		events=dbHelper.getAllEvents();

		return events;
	}

	private Date buildRandomDateInCurrentMonth() {
		Random random = new Random();
		return DateUtils.buildDate(random.nextInt(31) + 1);
	}

	@NonNull
	private Map<Date, List<Event>> toMap(@NonNull List<Event> events) {
		Map<Date, List<Event>> map = new TreeMap<>();
		for (Event event : events) {
			List<Event> value = map.get(event.getDate());
			if (value == null) {
				value = new ArrayList<>();
				map.put(event.getDate(), value);
			}
			value.add(event);
		}
		return map;
	}

}
