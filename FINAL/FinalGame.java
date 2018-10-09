import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
import java.awt.geom.Ellipse2D;
import javax.sound.sampled.*;

public class FinalGame extends JPanel implements KeyListener,Runnable
{
	private int x, y, selX, selY, time, menuState, messageState, mapID, speed, currentFace, currentSkill, enemySkill;
	private boolean gameOn, up, down, left, right, jUp, jDown, jLeft, jRight, stopTime, showMessage, asking, battle;
	private boolean map3Complete, map4Complete, gameComplete, waitingFor, gameOver, usedItem, introducing, storyWait;
	private JFrame frame;
	private Thread t;
	private Font f, meF;
	private String currentMessage, answer;
	private Color hpC, mpC;
	private Image menu, map1, map2, map3, map4, map5, battleBG;
	private Message m;
	private Enemy cEne;
	private BufferedImage event, ene;
	private ArrayList<Polygon> noMove, events;
	private ArrayList<Image> playerUp, playerDown, playerLeft, playerRight, playerFaces;
	private ArrayList<Boolean> eSwitches;
	private PlayerStats nessa;
	private Clip clip, battleClip, sfx;
	private AudioInputStream bgm, battleBGM, hit;

	public FinalGame()
	{
		waitingFor = true;
		cEne = new Enemy();
		answer = "";
		currentFace = -1;
		currentMessage = "";
		map3Complete = false;
		x = 400;
		y = 400;
		nessa = new PlayerStats(100, 100, 500, 1, 0, "Nessa");
		stuffThatThrowsNullPointerExceptionsForNoReasonExceptToAnnoyMeIGuess();
		setSystem();
		setSprites();
		frame.setIconImage(playerDown.get(4));
		setFaces();
		mainMap();

		addStuff();



	}

	public void stuffThatThrowsNullPointerExceptionsForNoReasonExceptToAnnoyMeIGuess()
	{
		try {event = ImageIO.read(new File("images/char/events.png"));
			 ene = ImageIO.read(new File("images/enemies.png"));}
		catch (IOException e) {}
		startStuff();
	}
	public void addStuff()
	{	nessa.addItem("Lool", "+50 HP", 50, 0);
		nessa.addItem("Lool", "+50 HP", 50, 0);
		nessa.addItem("Vuse", "+50 MP", 0, 50);
		nessa.addSkill("Nice Fire Thing", "Causes minimal fire damage.", 10, 5);
		nessa.addSkill("Nicer Fire Thing", "Causes moderate fire damage.", 13, 15);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D bg = (Graphics2D)g;
		Graphics2D fg = (Graphics2D)g;
		Graphics2D mg = (Graphics2D)g;

		selX = 15;
		selY = 40;

		if(introducing)
		{fg.drawImage(playerUp.get(1),400,400,this);}

		if(mapID == 1)
		{
			bg.drawImage(map1, 0, 0, this);
			if(eSwitches.get(0))
				bg.drawImage(event.getSubimage(32, 0, 32, 32), 500, 500, 32, 32, this);
			else bg.drawImage(event.getSubimage(0, 0, 32, 32), 500, 500, 32, 32, this);
		}
		if(mapID == 2)
		{
			bg.drawImage(map2, 0, 0, this);
			if(eSwitches.get(3))
				bg.drawImage(event.getSubimage(32, 0, 32, 32), 179, 434, 32, 32, this);
			else bg.drawImage(event.getSubimage(0, 0, 32, 32), 179, 434, 32, 32, this);
			if(eSwitches.get(4))
				bg.drawImage(event.getSubimage(32, 0, 32, 32), 597, 434, 32, 32, this);
			else bg.drawImage(event.getSubimage(0, 0, 32, 32), 597, 434, 32, 32, this);
		}
		if(mapID == 3)
		{
			bg.drawImage(map3, 0, 0, this);
		/*	bg.setColor(Color.RED); //show where events are
						for(Polygon i : events)
				bg.draw(i);*/
			if(!eSwitches.get(0))
				bg.drawImage(event.getSubimage(160, 15, 32, 81), 386, 165, 32, 81, this);
			else bg.drawImage(event.getSubimage(128, 15, 32, 81), 386, 165, 32, 81, this);
		}
		if(mapID == 4)
		{
			bg.drawImage(map4, 0, 0, this);
			/*bg.setColor(Color.CYAN);  //show where events are
			for(Polygon i : events)
				bg.draw(i);*/
			if(!eSwitches.get(0))
					bg.drawImage(event.getSubimage(192, 15, 32, 81), 660, 420, 32, 86, this);
			else bg.drawImage(event.getSubimage(128, 15, 32, 81), 660, 420, 32, 86, this);
		}
		if(mapID == 5)
		{
			bg.drawImage(map5, 0, 0, this);
			if(!eSwitches.get(0))
							bg.drawImage(event.getSubimage(224, 16, 32, 112), 377, 284, 29, 108, this);
			else bg.drawImage(event.getSubimage(256, 16, 32, 112), 377, 284, 29, 108, this);
		}
		if(showMessage)
		{
			Message m = new Message(currentMessage, currentFace);
			mg.setFont(f);
			int tx = 0;
			if(currentFace < 0)
			{tx = 10;
			 m.format(75);}
			else
			{tx = 215;
			 m.format(50);}
			int ty = 700-(30* m.getFormattedMessage().size());
			mg.setColor(new Color(0, 0, 0, 100));
			mg.fillRect(0, ty-30, 800, 600 );
			mg.setColor(Color.WHITE);
			for(String i : m.getFormattedMessage())
			{mg.drawString(i, tx, ty);
			 ty+=30;}
			if(currentFace>=0)
				mg.drawImage(playerFaces.get(currentFace), -30, 400, this);

		}
		fg.setColor(Color.WHITE);
		fg.setFont(f);
		/*fg.drawString("x: "+x, 20, 20);
		fg.drawString("y: "+y, 20, 40);
		fg.drawString("time: "+time, 100, 20);
		fg.drawString("mapID: "+mapID, 100, 40);*/
		if(gameOn){
		if((up || jUp) && (left || jLeft))
			fg.drawImage(playerUp.get((time%3)+3),x,y,this);
		else if((up || jUp) && (right || jRight))
			fg.drawImage(playerUp.get((time%3)+6),x,y,this);
		else if((down || jDown) && (left || jLeft))
			fg.drawImage(playerDown.get((time%3)+3),x,y,this);
		else if((down || jDown) && (right || jRight))
			fg.drawImage(playerDown.get((time%3)+6),x,y,this);
		else if(up || jUp)
			fg.drawImage(playerUp.get(time%3),x,y,this);
		else if(down || jDown)
			fg.drawImage(playerDown.get(time%3),x,y,this);
		else if(left || jLeft)
			fg.drawImage(playerLeft.get(time%3),x,y,this);
		else if(right || jRight)
			fg.drawImage(playerRight.get(time%3),x,y,this);}

		if(menuState == 1 || menuState == 2)
		{
			mg.drawImage(menu, 0, 0, this);
			mg.setColor(Color.WHITE);
			mg.setFont(f.deriveFont(50f));
			mg.drawString(nessa.getName(), 300, 550);
			mg.setFont(f);
			mg.drawString("HP: "+nessa.getHP(), 300, 600);
			mg.drawRect(300, 610, 100, 10);
			mg.drawString("MP: "+nessa.getMP(), 300, 650);
			mg.drawRect(300, 660, 100, 10);
			mg.drawString("$"+nessa.getMoney(), 300, 700);
			mg.drawString("EXP: "+nessa.getXP(), 500, 600);
			mg.drawString("Level: "+nessa.getLV(), 500, 650);
			mg.drawString("To Next Level: "+nessa.getToNext(), 500, 700);
			mg.setColor(hpC);
			mg.fillRect(300, 610, nessa.getPerHP()+1, 11);
			mg.setColor(mpC);
			mg.fillRect(300, 660, nessa.getPerMP()+1, 11);
			mg.setColor(Color.WHITE);
			mg.drawString("Items:", 10, 30);
			mg.drawString("Skills:", 390, 30);
			int sY = 60;
			int nu = 1;
			for(Item i : nessa.getItems())
			{if(i.hasEffect())
				mg.drawString(nu+" - "+i.getName()+"      "+i.getDes(), 20, sY);
			 else mg.drawString("x - "+i.getName()+"      "+i.getDes(), 20, sY);
			 sY+=30;
			 nu++;}
			sY = 60;
			for(Skill s : nessa.getSkills())
			{mg.drawString(s.getName()+"       "+s.getMPUse()+" MP", 400, sY);
			 sY+=20;
			 mg.drawString("     "+s.getDes(), 400, sY);
			 sY+=50;}
			 if(asking)
			 {	 mg.setColor(new Color(0, 0, 0, 200));
				 mg.fillRect(250, 200, 300, 100);
				 mg.setColor(Color.WHITE);
				 mg.setStroke(new BasicStroke(2));
				 mg.drawRect(250, 200, 300, 100);
				 mg.drawString("* USE ITEM *", 335, 180);
				 mg.drawString("Enter Item Number: ", 285, 255);
				 mg.drawString(answer, 480, 255);
			 }

		}

		if(battle)
		{ try{
		  clip.stop();
		  battleClip.open(battleBGM);
		  battleClip.start();
		  battleClip.loop(Clip.LOOP_CONTINUOUSLY);}
		  catch(Exception e){}
		  bg.drawImage(battleBG, 0, -20, this);
		  bg.drawImage(ene.getSubimage(cEne.getX(), cEne.getY(), cEne.getW(), cEne.getH()), 300, 200, cEne.getW(), cEne.getH(), this);
		  mg.setColor(Color.WHITE);

		  mg.drawString("NESSA", 20, 620);
		  mg.drawString(cEne.getName(), 190, 620);

		  mg.drawString("Skill: " +nessa.getSkill(currentSkill).getName(), 10, 30 );
		  mg.drawString(nessa.getSkill(currentSkill).getDes()+"   "+nessa.getSkill(currentSkill).getMPUse()+"MP", 10, 70);
		  mg.drawString(cEne.getName(), 4002, 30 );
		  mg.drawString(cEne.getDes(), 400, 70 );
		  mg.drawString("Enemy Skill: " +cEne.getSkill(enemySkill).getName(), 400, 110 );
		  mg.drawString(nessa.getHP()+"/"+nessa.getLVHP(), 20, 657);
		  mg.drawString(cEne.getHP()+"/"+cEne.getDefHP(), 190, 657);
		  mg.drawRect(20, 710, 100, 10);
		  mg.drawRect(190, 710, 100, 10);
		  mg.drawString(nessa.getMP()+"/"+nessa.getLVMP(), 20, 708);
		  mg.drawString(cEne.getMP()+"/"+cEne.getDefMP(), 190, 708);
		  mg.setColor(hpC);
		  mg.fillRect(20, 660, nessa.getPerHP()+1, 11);
		  mg.fillRect(190, 660, cEne.getPerHP()+1, 11);
		  mg.setColor(mpC);
		  mg.fillRect(20, 710, nessa.getPerMP()+1, 11);
		  mg.fillRect(190, 710, cEne.getPerMP()+1, 11);
		   mg.setColor(Color.WHITE);
		  mg.drawString("SHIFT for ITEMS", 357, 665);
		  mg.drawString("#KEYS to CHANGE SKILL", 357, 695);
		  mg.drawString("ENTER to USE", 357, 725);
		  if(asking)
		  {
			 mg.setColor(new Color(0, 0, 0, 200));
			 mg.fillRect(250, 200, 300 , 100+ (nessa.getItems().size()*30));
			 mg.setColor(Color.WHITE);
			 mg.setStroke(new BasicStroke(2));
			 mg.drawRect(250, 200, 300 , 100+ (nessa.getItems().size()*30));
			 mg.drawString("* USE ITEM *", 335, 180);
			 mg.drawString("Enter Item Number: ", 285, 255);
			 mg.drawString(answer, 480, 255);
			 int sY = 285;
			int nu = 1;
			for(Item i : nessa.getItems())
			{if(i.hasEffect())
				mg.drawString(nu+" - "+i.getName()+"      "+i.getDes(), 285, sY);
			 else mg.drawString("x - "+i.getName()+"      "+i.getDes(), 285, sY);
			 sY+=30;
			 nu++;}

		  }
			battleHandler(nessa, cEne);

		}
	}

	public void intro()
	{
		introducing = true;
		currentFace = 2;
		currentMessage = ("You might be wondering what I'm doing here in the middle of nowhere outside of a random green building. Well, the answer to that is...actually not a long story as you might expect. They say that to this very random green building, a high school student came long, long ago, and, as it goes, they were never seen again. But they also say that they here somehow, and their spirit roams the random hallways of this random green building. There's even some legendary treasure involved, although I don't know what sort of great treasure a high schooler would have. I'm not exactly planning to meet them, or get stuck here. And that's why I'm going to barge in and try to find the treasure! First step is to get inside.");
		showMessage = true;
		introducing = false;
	}
	public void mainMap()
	{
		mapID = 1;
		intro();
		noMove = new ArrayList<Polygon>();
		events = new ArrayList<Polygon>();
		eSwitches = new ArrayList<Boolean>();
		events.add(new Polygon()); //chest
			events.get(0).addPoint(487, 490);
			events.get(0).addPoint(545, 490);
			events.get(0).addPoint(545, 546);
			events.get(0).addPoint(487, 546);
		eSwitches.add(false);
		events.add(new Polygon()); //door
			events.get(1).addPoint(385, 300);
			events.get(1).addPoint(415, 300);
			events.get(1).addPoint(415, 351);
			events.get(1).addPoint(385, 351);
		noMove.add(new Polygon());
			noMove.get(0).addPoint(64, 32);
			noMove.get(0).addPoint(735, 32);
			noMove.get(0).addPoint(735, 607);
			noMove.get(0).addPoint(672, 607);
			noMove.get(0).addPoint(672, 191);
			noMove.get(0).addPoint(607, 191);
			noMove.get(0).addPoint(607, 351);
			noMove.get(0).addPoint(416, 351);
			noMove.get(0).addPoint(416, 319);
			noMove.get(0).addPoint(384, 319);
			noMove.get(0).addPoint(384, 351);
			noMove.get(0).addPoint(192, 351);
			noMove.get(0).addPoint(192, 191);
			noMove.get(0).addPoint(127, 191);
			noMove.get(0).addPoint(127, 607);
			noMove.get(0).addPoint(64, 607);
			noMove.get(0).addPoint(64, 32);
		noMove.add(new Polygon());
			noMove.get(1).addPoint(500, 503);
			noMove.get(1).addPoint(531, 503);
			noMove.get(1).addPoint(531, 531);
			noMove.get(1).addPoint(500, 531);


	}
	public void mainMapEvents()
	{
		if(intersection(0) && !eSwitches.get(0))
		{
			eSwitches.set(0, true);
			nessa.addItem("Key", "An ornate key.");
			currentMessage = "Got a key.";
			showMessage = true;

		}
		if(intersection(1) && eSwitches.get(0)==true)
		{
			nessa.removeItemByName("Key");
			map2();
			x= 372;
			y = 600;


		}
		else if(intersection(1) && eSwitches.get(0)==false)
		{currentFace = 0;
			currentMessage = "It's locked.";
			showMessage = true;
		}
	}
	public void map2()
	{
		mapID = 2;
		noMove = new ArrayList<Polygon>();
		events = new ArrayList<Polygon>();
		eSwitches = new ArrayList<Boolean>();
		noMove.add(new Polygon());
			noMove.get(0).addPoint(88, 81);
			noMove.get(0).addPoint(735, 81);
			noMove.get(0).addPoint(735, 189);
			noMove.get(0).addPoint(88, 189);
		noMove.add(new Polygon());
			noMove.get(1).addPoint(705, 192);
			noMove.get(1).addPoint(724, 192);
			noMove.get(1).addPoint(724, 584);
			noMove.get(1).addPoint(705, 584);
		noMove.add(new Polygon());
			noMove.get(2).addPoint(88, 576);
			noMove.get(2).addPoint(351, 576);
			noMove.get(2).addPoint(351, 639);
			noMove.get(2).addPoint(515, 639);
			noMove.get(2).addPoint(416, 576);
			noMove.get(2).addPoint(703, 576);
			noMove.get(2).addPoint(713, 715);
			noMove.get(2).addPoint(88, 715);
		noMove.add(new Polygon());
			noMove.get(3).addPoint(75, 70);
			noMove.get(3).addPoint(95, 70);
			noMove.get(3).addPoint(95, 605);
			noMove.get(3).addPoint(75, 605);
		noMove.add(new Polygon());
			noMove.get(4).addPoint(256, 224);
			noMove.get(4).addPoint(544, 224);
			noMove.get(4).addPoint(544, 378);
			noMove.get(4).addPoint(256, 378);
		eSwitches.add(false); //0left text
		eSwitches.add(false); //1mid text
		eSwitches.add(false); //2right text
		eSwitches.add(false); //3 CHEST1
		eSwitches.add(false); //4 CHEST2
		events.add(new Polygon()); // 0left text
			events.get(0).addPoint(352, 378);
			events.get(0).addPoint(384, 378);
			events.get(0).addPoint(384, 424);
			events.get(0).addPoint(352, 424);
		events.add(new Polygon()); // 1mid text
			events.get(1).addPoint(384, 378);
			events.get(1).addPoint(416, 378);
			events.get(1).addPoint(416, 424);
			events.get(1).addPoint(384, 424);
		events.add(new Polygon()); // 2right text
			events.get(2).addPoint(416, 378);
			events.get(2).addPoint(448, 378);
			events.get(2).addPoint(448, 424);
			events.get(2).addPoint(416, 424);
		events.add(new Polygon()); // 3left door
			events.get(3).addPoint(94, 320);
			events.get(3).addPoint(133, 320);
			events.get(3).addPoint(133, 442);
			events.get(3).addPoint(94, 442);
		events.add(new Polygon()); // 4right door
			events.get(4).addPoint(665, 288);
			events.get(4).addPoint(704, 288);
			events.get(4).addPoint(704, 410);
			events.get(4).addPoint(665, 410);
		events.add(new Polygon()); // 5crack
			events.get(5).addPoint(338, 192);
			events.get(5).addPoint(403, 192);
			events.get(5).addPoint(403, 224);
			events.get(5).addPoint(338, 224);
		events.add(new Polygon()); // 6 chest1
			events.get(6).addPoint(179, 434);
			events.get(6).addPoint(211, 434);
			events.get(6).addPoint(211, 466);
			events.get(6).addPoint(179, 466);
		events.add(new Polygon());  //7 chest2
			events.get(7).addPoint(597, 434);
			events.get(7).addPoint(620, 434);
			events.get(7).addPoint(620, 466);
			events.get(7).addPoint(597, 466);
	}

	public void map2Events()
	{
		if(intersection(0))
		{
			eSwitches.set(0, true);
			currentMessage = "\"To the left is the room of invisible threats. Watch out for the floor...\"";
			showMessage = true;

		}
		if(intersection(1))
		{
			eSwitches.set(1, true);
			currentMessage = "\"To get to the final destination, collect the two orbs from the side rooms and proceed through the crack in the wall...\" It goes on, but it's been ripped off.";
			showMessage = true;
		}
		if(intersection(2))
		{
			eSwitches.set(2, true);
			currentMessage = "\"To the right is...\" That's all that's visible on the tiny scrap of paper.";
			showMessage = true;
		}
		if((intersection(3) || intersection(4)) && (!eSwitches.get(0) && !eSwitches.get(1) && !eSwitches.get(2)))
		{
			currentFace = 0;
			currentMessage = "I don't think I should go here yet...";
			showMessage = true;
		}
		if(intersection(3) && eSwitches.get(0) && eSwitches.get(1) && eSwitches.get(2) && !map3Complete)
		{
			map3();
			x = 768;
			y = 491;
		}
		else if(intersection(3) && eSwitches.get(0) && eSwitches.get(1) && eSwitches.get(2) && map3Complete)
		{
			currentFace = 1;
			currentMessage = "I'm done with that place.";
			showMessage = true;
		}
		if(intersection(4) && eSwitches.get(0) && eSwitches.get(1) && eSwitches.get(2) && !map4Complete)
		{
			map4();
			x = 7;
			y = 352;
		}
		else if(intersection(4) && eSwitches.get(0) && eSwitches.get(1) && eSwitches.get(2) && map4Complete)
		{
			currentFace = 1;
			currentMessage = "I've already finished up there.";
			showMessage = true;
		}
		if(intersection(5) && map4Complete && map3Complete)
		{
			map5();
			x = 373;
			y = 608;
			currentFace = 2;
			currentMessage = "Guess I've reached the final stage!";
			showMessage = true;
		}
		else if (intersection(5) && !map4Complete && !map3Complete)
		{
			currentFace = 6;
			currentMessage = "There's some sort of invisible barrier here. Guess I really can't go here without both of those orbs...";
			showMessage = true;
		}
		if(intersection(6) && !eSwitches.get(0))
		{
			eSwitches.set(3, true);
			nessa.addItem("Lool", "+50 HP");
			nessa.addItem("Lool", "+50 HP");
			nessa.addItem("Mega Lool", "+100 HP");
			currentMessage = "Got lool.";
			showMessage = true;

		}
		if(intersection(7) && !eSwitches.get(0))
		{
			eSwitches.set(4, true);
			nessa.addItem("Vuse", "+50 MP");
			nessa.addItem("Vuse", "+50 MP");
			nessa.addItem("Mega Vuse", "+50 MP");
			currentMessage = "Got vuse.";
			showMessage = true;

		}
	}

	public void map3()
	{
		mapID = 3;
		noMove = new ArrayList<Polygon>();
		events = new ArrayList<Polygon>();
		eSwitches = new ArrayList<Boolean>();
		noMove.add(new Polygon());
			noMove.get(0).addPoint(146, 197);
			noMove.get(0).addPoint(658, 197);
			noMove.get(0).addPoint(658, 452);
			noMove.get(0).addPoint(799, 452);
			noMove.get(0).addPoint(799, 475);
			noMove.get(0).addPoint(641, 475);
			noMove.get(0).addPoint(641, 218);
			noMove.get(0).addPoint(158, 218);
			noMove.get(0).addPoint(158, 576);
			noMove.get(0).addPoint(640, 576);
			noMove.get(0).addPoint(640, 544);
			noMove.get(0).addPoint(799, 544);
			noMove.get(0).addPoint(799, 557);
			noMove.get(0).addPoint(652, 557);
			noMove.get(0).addPoint(652, 590);
			noMove.get(0).addPoint(146, 590);
		noMove.add(new Polygon());
			noMove.get(1).addPoint(796, 480);
			noMove.get(1).addPoint(799, 480);
			noMove.get(1).addPoint(799, 525);
			noMove.get(1).addPoint(796, 525);
		noMove.add(new Polygon());
			noMove.get(2).addPoint(390, 196);
			noMove.get(2).addPoint(412, 196);
			noMove.get(2).addPoint(412, 245);
			noMove.get(2).addPoint(390, 245);
		eSwitches.add(false); //0pillar reached?
		events.add(new Polygon()); //0pillar
			events.get(0).addPoint(374, 219);
			events.get(0).addPoint(427, 219);
			events.get(0).addPoint(427, 263);
			events.get(0).addPoint(374, 263);
		events.add(new Polygon()); //1door
			events.get(1).addPoint(772, 480);
			events.get(1).addPoint(800, 480);
			events.get(1).addPoint(800, 544);
			events.get(1).addPoint(772, 544);
		for(int i = 0; i < 10; i++)
		{
				int tempx = (int)(Math.random()*320)+(int)(Math.random()*160)+160;
				int tempy = (int)(Math.random()*100)+(int)(Math.random()*250)+200;
				int[] xpoints = {tempx, tempx+40, tempx+40, tempx};
				int[] ypoints = {tempy, tempy, tempy+40, tempy+40};
				events.add(new Polygon(xpoints, ypoints, 4));

		}


	}

	public void map3Events()
	{
		if(intersection(0) && !eSwitches.get(0))
		{
			currentMessage = "Got a red orb.";
			showMessage = true;
			nessa.addItem("Red Orb", "A shiny red orb.");
			map3Complete = true;
			eSwitches.set(0, true);
		}
		if(eSwitches.get(0) && intersection(1) )
		{
			map2();
			x = 104;
			y = 363;
			eSwitches.set(0, true);
			eSwitches.set(1, true);
			eSwitches.set(2, true);
		}
	}

	public void map4()
	{
		mapID = 4;
		noMove = new ArrayList<Polygon>();
		events = new ArrayList<Polygon>();
		eSwitches = new ArrayList<Boolean>();
		noMove.add(new Polygon());
			noMove.get(0).addPoint(0, 339);
			noMove.get(0).addPoint(151, 339);
			noMove.get(0).addPoint(151, 193);
			noMove.get(0).addPoint(702, 193);
			noMove.get(0).addPoint(702, 378);
			noMove.get(0).addPoint(731, 378);
			noMove.get(0).addPoint(731, 561);
			noMove.get(0).addPoint(715, 561);
			noMove.get(0).addPoint(715, 589);
			noMove.get(0).addPoint(654, 589);
			noMove.get(0).addPoint(654, 611);
			noMove.get(0).addPoint(149, 611);
			noMove.get(0).addPoint(149, 398);
			noMove.get(0).addPoint(0, 398);
			noMove.get(0).addPoint(0, 384);
			noMove.get(0).addPoint(158, 384);
			noMove.get(0).addPoint(158, 576);
			noMove.get(0).addPoint(640, 576);
			noMove.get(0).addPoint(640, 544);
			noMove.get(0).addPoint(704, 544);
			noMove.get(0).addPoint(704, 474);
			noMove.get(0).addPoint(640, 474);
			noMove.get(0).addPoint(640, 216);
			noMove.get(0).addPoint(158, 216);
			noMove.get(0).addPoint(158, 345);
			noMove.get(0).addPoint(0, 345);
		noMove.add(new Polygon());
			noMove.get(1).addPoint(0, 345);
			noMove.get(1).addPoint(2, 397);
			noMove.get(1).addPoint(2, 397);
			noMove.get(1).addPoint(0, 345);


		eSwitches.add(false); //0pillar reached?
		events.add(new Polygon()); //0pillar
			events.get(0).addPoint(650, 480);
			events.get(0).addPoint(700, 480);
			events.get(0).addPoint(700, 512);
			events.get(0).addPoint(650, 512);
		events.add(new Polygon()); //1door
			events.get(1).addPoint(0, 352);
			events.get(1).addPoint(45, 352);
			events.get(1).addPoint(45, 384);
			events.get(1).addPoint(0, 384);
		for(int i = 0; i < 10; i++)
		{

			int tempx = (int)(Math.random()*320)+(int)(Math.random()*160)+160;
			int tempy = (int)(Math.random()*100)+(int)(Math.random()*250)+200;
			int[] xpoints = {tempx, tempx+40, tempx+40, tempx};
			int[] ypoints = {tempy, tempy, tempy+40, tempy+40};
			events.add(new Polygon(xpoints, ypoints, 4));

		}




	}

	public void map4Events()
	{
		if(intersection(0) && !eSwitches.get(0))
		{
			currentMessage = "Got a blue orb.";
			showMessage = true;
			nessa.addItem("Blue Orb", "A shiny blue orb.");
			map4Complete = true;
			eSwitches.set(0, true);
		}
		if(eSwitches.get(0) && intersection(1))
		{
			map2();
			x = 673;
			y = 323;
			eSwitches.set(0, true);
			eSwitches.set(1, true);
			eSwitches.set(2, true);
		}
	}

	public void map5()
	{
		mapID = 5;
		noMove = new ArrayList<Polygon>();
		events = new ArrayList<Polygon>();
		eSwitches = new ArrayList<Boolean>();
		noMove.add(new Polygon());
			noMove.get(0).addPoint(96, 218);
			noMove.get(0).addPoint(714, 218);
			noMove.get(0).addPoint(714, 677);
			noMove.get(0).addPoint(84, 677);
			noMove.get(0).addPoint(84, 576);
			noMove.get(0).addPoint(319, 576);
			noMove.get(0).addPoint(319, 607);
			noMove.get(0).addPoint(350, 607);
			noMove.get(0).addPoint(350, 639);
			noMove.get(0).addPoint(416, 639);
			noMove.get(0).addPoint(416, 607);
			noMove.get(0).addPoint(447, 607);
			noMove.get(0).addPoint(447, 576);
			noMove.get(0).addPoint(703, 576);
			noMove.get(0).addPoint(703, 250);
			noMove.get(0).addPoint(96, 250);
		noMove.add(new Polygon());
			noMove.get(1).addPoint(74, 78);
			noMove.get(1).addPoint(95, 78);
			noMove.get(1).addPoint(95, 587);
			noMove.get(1).addPoint(74, 587);
		noMove.add(new Polygon());
			noMove.get(2).addPoint(373, 288);
			noMove.get(2).addPoint(400, 288);
			noMove.get(2).addPoint(400, 394);
			noMove.get(2).addPoint(373, 394);
		eSwitches.add(false); //0put STUFF in PILLAR??
		events.add(new Polygon()); //0pillar
			events.get(0).addPoint(352, 346);
			events.get(0).addPoint(430, 346);
			events.get(0).addPoint(430, 419);
			events.get(0).addPoint(352, 419);

	}

	public void map5Events()
	{
		if(intersection(0) && !eSwitches.get(0))
		{
			eSwitches.set(0, true);
			nessa.removeItemByName("Red Orb");
			nessa.removeItemByName("Blue Orb");
			currentFace = 2;
			currentMessage = "Finally!";
			showMessage = true;
			cEne.setEnemy("Orb", 1);
			battle = true;

		}
		else if(gameComplete)
		{
			currentFace = 2;
						currentMessage = "Finally!";
			showMessage = true;
		}
	}

	public void run()
	{
		while(true)
		{
			if(gameOn)
			{

				if(up && canMove(-1, 0, -3)&& menuState == 0 && !showMessage && !battle)
					y-=2;
				if(down && canMove(-1, 0, 3)&& menuState == 0 && !showMessage && !battle)
					y+=2;
				if(left && canMove(-1, -3, 0)&& menuState == 0 && !showMessage && !battle)
					x-=2;
				if(right && canMove(-1, 3, 0) && menuState == 0 && !showMessage && !battle)
					x+=2;
				timeDelay();
				if(time == 30)
					time=0;
				if(mapID == 3){
					for(int i = 2; i < 11; i ++)
					{
						if(intersection(i) && !eSwitches.get(0))
						{
							nessa.setHP(-10);
							x = 750;
							y = 491;
						}
					}}
				if(mapID == 4){
					for(int i = 2; i < 11; i ++)
					{
						if(intersection(i) && !eSwitches.get(0))
						{
							x = 26;
							y = 352;

							int which = (int)(Math.random()*3);
							switch(which)
							{
								case 0: cEne.setEnemy("Ghost", 1);
									break;
								case 1: cEne.setEnemy("Book", 1);
									break;
								case 2: cEne.setEnemy("Lunch", 1);
									break;
								case 3: cEne.setEnemy("Heart", 1);
									break;
							}
						battle = true;
					}}}

				if(nessa.getHP()==0)
					gameOver = true;

			}
			if(gameOver)
			{

			        x = 400;
			        y = 400;
			        nessa.resetAll(100, 100, 500, 23.45,  1);
					addStuff();
			        mainMap();
			        gameOver = false;
			        gameOn = true;

			}
			try{t.sleep(5);
				repaint();}
			catch(InterruptedException e){}
			}

	}
	public void battleHandler(PlayerStats a, Enemy b)
    {

       if(a.getHP()>0 && b.getHP()>0)
        {
			//System.out.println(a.getHP()+" "+b.getHP());
          int damage = 0;
            //print out skills as 1, 2, 3

			if(!waitingFor)
			{
				if(!usedItem)
				{
				damage = a.getSkill(currentSkill).getDamage();
                if(damage > 0)
                    b.setHP(-(damage));
                else a.setHP(-(damage));
				a.setMP(-(a.getSkill(currentSkill).getMPUse()));}

				enemySkill = (int)(Math.random()*b.getSkills().size()-1);
                damage = b.getSkill(enemySkill).getDamage();
                if(damage > 0)
                    a.setHP(-(damage));
                else b.setHP(-(damage));
			}
			waitingFor = true;
			usedItem = false;
        }

        else {

		if(a.getHP() >0)
		{
			a.setXP(b.getXP());
			if(mapID ==5)
				gameComplete = true;
		}
		else gameOver = true;
        battle = false;

        }
    }


	public boolean canMove(int which, int a, int b)
	{
		if (which >=0)
		{	if(noMove.get(which).intersects(new Rectangle(x+a, y+b, 22, 32)))
				return false;}
	 	else{for(Polygon p : noMove)
			{if(p.intersects(new Rectangle(x+a, y+b, 22, 32)))
				return false;}}
		return true;
	}

	public void keyPressed(KeyEvent ke)
	{
		System.out.println(ke.getKeyCode());
		stopTime = false;

		if(ke.getKeyCode()==32)
		{
			if(mapID == 1){mainMapEvents();}
			if(mapID == 2){map2Events();}
			if(mapID == 3){map3Events();}
			if(mapID == 4){map4Events();}
			if(mapID == 5){map5Events();}
		}
		if(ke.getKeyCode()==37 && menuState ==0)
		{left = true;
		 noJust();}
		if(ke.getKeyCode()==38 && menuState ==0)
		{up = true;
		 noJust();}
		if(ke.getKeyCode()==39 && menuState ==0)
		{right = true;
		 noJust();}
		if(ke.getKeyCode()==40 && menuState ==0)
		{down = true;
		 noJust();}
		if(ke.getKeyCode()==88 && menuState == 0 &&!showMessage)
			menuState = 1;
		if(ke.getKeyCode()==88 && menuState == 2 && !showMessage)
			menuState = 0;

		if(ke.getKeyCode()==16 && (menuState > 0 || battle))
			asking = true;
		if(asking)
		{
			if(ke.getKeyCode() >= 48 && ke.getKeyCode() <= 67 && answer.length()<=1)
				answer+=""+(char)ke.getKeyCode();
			if(ke.getKeyCode() == 8 && answer.length()>0)
				answer = answer.substring(0, answer.length()-1);
		}
		if((ke.getKeyCode() >= 48 && ke.getKeyCode() <= 67)  && waitingFor && battle && !asking)
		{
			if(ke.getKeyCode()-48 <= nessa.getSkills().size())
			{
				if(nessa.getSkill(ke.getKeyCode()-49).getMPUse() <= nessa.getMP())
					currentSkill = ke.getKeyCode()-49;
		}
		}

	}
	public void keyReleased(KeyEvent ke)
	{
		stopTime = true;
		if(ke.getKeyCode()==37)
		{left = false;
		 jLeft = true;}
		if(ke.getKeyCode()==38)
		{up = false;
	     jUp = true;}
		if(ke.getKeyCode()==39)
		{right = false;
		 jRight = true;}
		if(ke.getKeyCode()==40 && menuState == 0)
		{down = false;
         jDown = true;}
		if(ke.getKeyCode()==88 && menuState == 1)
			menuState++;
		if(ke.getKeyCode()==10 && showMessage)
		{showMessage = false;
		 currentMessage = "";
		 currentFace = -1;}
		if(ke.getKeyCode()==10 && asking && (menuState > 0 || battle))
		{
			asking = false;
			 if(answer.length()>0)
			{
				if(Integer.parseInt(answer) <= nessa.getItems().size())
				nessa.useItem(Integer.parseInt(answer)-1);
				answer ="";
			}
			if(battle)
				usedItem = true;
		}
		if(ke.getKeyCode() == 10 && waitingFor && battle)
		{ waitingFor = false;}
		if(ke.getKeyCode() == 65 && battle)
		{nessa.setHP(-2000);}
	}
	public void keyTyped(KeyEvent ke){}

	public void startStuff()
	{
		hpC = new Color(165, 255, 74);
		mpC = new Color(128, 185, 255);
		frame = new JFrame();
		time = 0;
		gameOn=true;
		showMessage = false;
		speed = 0;
		frame.setTitle("NESSA'S SUPER DUPER AWESOME ADVENTURE");
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(800,800);
		frame.setVisible(true);
		frame.setResizable(false);
		soundAndFont();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		t = new Thread(this);
		t.start();
	}

	public void setSystem()
	{
		MediaTracker sysTracker = new MediaTracker(this);
		menu = new ImageIcon(this.getClass().getResource("images/menu.png")).getImage();
		map1 = new ImageIcon(this.getClass().getResource("images/map1.png")).getImage();
		map2 = new ImageIcon(this.getClass().getResource("images/map2.png")).getImage();
		map3 = new ImageIcon(this.getClass().getResource("images/map3.png")).getImage();
		map4 = new ImageIcon(this.getClass().getResource("images/map4.png")).getImage();
		map5 = new ImageIcon(this.getClass().getResource("images/map5.png")).getImage();
		battleBG = new ImageIcon(this.getClass().getResource("images/battle.png")).getImage();
		sysTracker.addImage(menu,0);
		sysTracker.addImage(map1,1);
		sysTracker.addImage(map2,2);
		sysTracker.addImage(map3,3);
		sysTracker.addImage(map4,4);
		sysTracker.addImage(map5,5);
		try {for(int i = 0; i<5; i++)
			sysTracker.waitForID(i);}
		 catch (InterruptedException e){}
	}

	public void setFaces()
	{
		playerFaces = new ArrayList<Image>();
		MediaTracker faceTracker = new MediaTracker(this);
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n0.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n1.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n2.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n3.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n4.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n5.png")).getImage());
		playerFaces.add(new ImageIcon(this.getClass().getResource("images/face/n6.png")).getImage());
		int n = 0;
		for(Image i : playerFaces){faceTracker.addImage(i, n);
									n++;}
		try{for(int i = 0; i<7; i++)
			faceTracker.waitForID(i);}
		catch (InterruptedException e){}
	}


	public void setSprites()
	{
		playerUp = new ArrayList<Image>();
		playerDown = new ArrayList<Image>();
		playerLeft = new ArrayList<Image>();
		playerRight = new ArrayList<Image>();
		MediaTracker spriteTracker = new MediaTracker(this);

		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/up1.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/up2.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/up3.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ul1.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ul2.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ul3.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ur1.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ur2.png")).getImage());
		playerUp.add(new ImageIcon(this.getClass().getResource("images/char/ur3.png")).getImage());

		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/down1.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/down2.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/down3.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dl1.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dl2.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dl3.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dr1.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dr2.png")).getImage());
		playerDown.add(new ImageIcon(this.getClass().getResource("images/char/dr3.png")).getImage());

		playerLeft.add(new ImageIcon(this.getClass().getResource("images/char/left1.png")).getImage());
		playerLeft.add(new ImageIcon(this.getClass().getResource("images/char/left2.png")).getImage());
		playerLeft.add(new ImageIcon(this.getClass().getResource("images/char/left3.png")).getImage());
		playerRight.add(new ImageIcon(this.getClass().getResource("images/char/right1.png")).getImage());
		playerRight.add(new ImageIcon(this.getClass().getResource("images/char/right2.png")).getImage());
		playerRight.add(new ImageIcon(this.getClass().getResource("images/char/right3.png")).getImage());

		int n = 0;
		for(Image i : playerUp)
		{
			spriteTracker.addImage(i, n);
			n++;
		}
		for(Image i : playerDown)
		{
			spriteTracker.addImage(i, n);
			n++;
		}
		for(Image i : playerLeft)
		{
			spriteTracker.addImage(i, n);
			n++;
		}
		for(Image i : playerRight)
		{
			spriteTracker.addImage(i, n);
			n++;
		}
		try{for(int i = 0; i < 24; i++)
			spriteTracker.waitForID(i);}
		catch (InterruptedException e){}

	}
	public void timeDelay()
	{
		if(!stopTime)
		{try
			{if(!(time%7==0))
				t.sleep(10);
			time++;}
			catch(InterruptedException e){}}
	}
	public void noJust()
	{jLeft = false;
	 jUp = false;
	 jRight = false;
	 jDown = false;}
	public void soundAndFont()
	{
	 try
	 {bgm = AudioSystem.getAudioInputStream(new File("/Comp Sci/FINAL/audio/cycle.wav"));
	  clip = AudioSystem.getClip();
	  battleBGM = AudioSystem.getAudioInputStream(new File("/Comp Sci/FINAL/audio/synth.wav"));
	  battleClip = AudioSystem.getClip();
	  hit = AudioSystem.getAudioInputStream(new File("/Comp Sci/FINAL/audio/hit.wav"));
	  sfx = AudioSystem.getClip();
	  clip.open(bgm);
	  clip.start();
	  clip.loop(Clip.LOOP_CONTINUOUSLY);}
	 catch(Exception e){}
     try
     {f = Font.createFont(Font.TRUETYPE_FONT, new File("rainy.ttf")).deriveFont(24f);
	  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	  ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("rainy.ttf")));}
	  catch (IOException e) {}
	  catch(FontFormatException e){}
	}
	public boolean intersection(int n)
	{if(events.get(n).intersects(new Rectangle(x, y, 22, 32)))
		return true;
	return false;
	}

	public static void main(String args[])
	{FinalGame app=new FinalGame();}
}