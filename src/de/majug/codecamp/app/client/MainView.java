package de.majug.codecamp.app.client;

import java.util.ArrayList;
import java.util.Random;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	final Random r = new Random();

	final static String[] colors = { "#086A87", "#088A85", "#088A68",
			"#088A4B", "#088A29" };
	final static String[] sizes = { "20px", "44px", "25px", "30px", "18px" };

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button add;

	@UiField
	Button addMega;

	@UiField
	Button draw;

	@UiField
	AbsolutePanel participantPanel;

	@UiField
	TextBox person;

	ArrayList<HTML> participants = new ArrayList<HTML>();

	String wrappWithRandomStyle(String p) {
		StringBuilder b = new StringBuilder();
		return b.append("<span style=\"color: ").append(colors[r.nextInt(4)])
				.append("; font-size: ").append(sizes[r.nextInt(4)])
				.append("\">").append(p).append("</span>").toString();
	}

	@UiHandler("addMega")
	void onMegaAdd(ClickEvent e) {
		for (int i1 = 0; i1 < 200; i1++) {
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < r.nextInt(50); i++)
				b.append((char) ('a' + r.nextInt(26)));
			person.setText(b.toString());
			onAdd(null);
		}
	}

	@UiHandler("add")
	void onAdd(ClickEvent e) {
		HTML p = new HTML(wrappWithRandomStyle(person.getText()));
		CrazyAnimation a = new CrazyAnimation(p.getElement());
		participants.add(p);
		participantPanel.add(p, calculateLeft(), calculateTop());
		System.out.println(p.getOffsetWidth());
		System.out.println(p.getOffsetHeight());
		a.scrollTo(
				r.nextInt(participantPanel.getOffsetWidth()
						- p.getOffsetWidth()),
				r.nextInt(participantPanel.getOffsetHeight()
						- p.getOffsetHeight()), r.nextInt(10000));
	}

	@UiHandler("draw")
	void onDraw(ClickEvent e) {
		add.setEnabled(false);
		Timer t = new Timer() {
			@Override
			public void run() {
				int unluckyNr = r.nextInt(participants.size());
				participantPanel.remove(participants.get(unluckyNr));
				participants.remove(unluckyNr);
				if (participants.size() == 1) {
					this.cancel();
					add.setEnabled(true);
				}
			}
		};
		t.scheduleRepeating(150);
	}

	int calculateTop() {
		return participantPanel.getOffsetHeight()
				- r.nextInt(participantPanel.getOffsetHeight() - 100);
	}

	int calculateLeft() {
		return participantPanel.getOffsetWidth()
				- r.nextInt(participantPanel.getOffsetWidth() - 200);
	}

}
