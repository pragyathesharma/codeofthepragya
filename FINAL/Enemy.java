import java.util.*;
public class Enemy
{
	private String name, des;
	private ArrayList<Skill> skills;
	private double hp, mp, defHP, defMP, perHP, perMP;
	private int level, xp, x, y, w, h;

	public Enemy(){}

	public void setEnemy(String n, int lv)
	{
		name = n;
		level = lv;
		skills = new ArrayList<Skill>();
		perHP = 100;
		perMP = 100;
		switch(name)
		{
			case "Ghost": createGhost();
				break;
			case "Book": createBook();
				break;
			case "Lunch": createLunch();
				break;
			case "Heart": createHeart();
				break;
			case "Orb": createOrb();
				break;
		}
	}

	public void createGhost()
	{

		name = "Ghost of Grades";
		des = "Taking an L, all day, every day.";
		hp = 50;
		mp = 50;
		xp = 50;
		setBound(563, 0, 237, 290);
		if(level == 2)
		{
			hp+=50;
			hp+=50;
			xp +=50;
			skills.add(new Skill("Hit", "", 0, 6));
			skills.add(new Skill("Bad Excuse", "", 15, 14));
			skills.add(new Skill("Sunday Night Panic", "", 17, 16));
			skills.add(new Skill("Where Is My Project Partner", "", 20, 18));
		    skills.add(new Skill("8 Hours of Sleep", "", 15, -17));
		}
		else{
			skills.add(new Skill("Hit", "", 0, 3));
			skills.add(new Skill("Bad Excuse", "", 8, 8));
			skills.add(new Skill("Sunday Night Panic", "", 9, 10));
			skills.add(new Skill("Where Is My Project Partner", "", 10, 12));
			skills.add(new Skill("8 Hours of Sleep", "", 10, -12));}
		defHP = hp;
		defMP = mp;
	}

	public void createBook()
	{
		name = "Overdue Library Book";
		des = "It was due 3 years ago...";
		hp = 25;
		mp = 25;
		xp = 20;
		defHP = hp;
		defMP = mp;
		setBound(319, 22, 217, 243);
		skills.add(new Skill("Hit", "", 0, 1));
		skills.add(new Skill("Ugly Cover", "", 5, 5));
		skills.add(new Skill("Papercut", "", 6, 8));
		skills.add(new Skill("Terrible Plot", "", 2, 5));
	}

	public void createLunch()
	{
		name = "Forgotten Lunch Bag";
		des = "Time has made it stronger.";
		hp = 75;
		mp = 75;
		xp = 80;
		defHP = hp;
		defMP = mp;
		setBound(349, 310, 165, 165);
		skills.add(new Skill("Hit", "", 0, 5));
		skills.add(new Skill("Stale Oreos", "", 12, 10));
		skills.add(new Skill("Mushy Apple", "", 13, 15));
		skills.add(new Skill("Tasteless Sandwich", "", 15, 20));
	}
	public void createHeart()
	{
		name = "Icy Heart";
		des = "It's just a heart made out of ice.";
		hp = 100;
		mp = 100;
		xp = 90;
		defHP = hp;
		defMP = mp;
		setBound(550, 299, 245, 231);
		skills.add(new Skill("Hit", "", 0, 7));
		skills.add(new Skill("Ice Shower", "", 20, 25));
		skills.add(new Skill("Ice Rain", "", 25, 30));
		skills.add(new Skill("Ice Storm", "", 30, 35));
		skills.add(new Skill("Ice Rainbow", "", 30, -35));
	}
	public void createOrb()
	{
		name = "Spirit of the School Year";
		des = "A sentient orb that's really big.";
		hp = 200;
		mp = 200;
		xp = 150;
		defHP = hp;
		defMP = mp;
		setBound(28, 25, 252, 247);
		skills.add(new Skill("Hit", "", 0, 10));
		skills.add(new Skill("Continuity and Change Over Time", "", 10, 15));
		skills.add(new Skill("Cry", "", 12, 25));
		skills.add(new Skill("Analyze Character Development", "", 15, 35));
		skills.add(new Skill("Wait The Test Is Tomorrow", "", 20, 45));
		skills.add(new Skill("Avoid Responsibility", "", 20, -50));
	}
	public Skill getSkill(int num)
	{return skills.get(num);}
	public ArrayList<Skill> getSkills()
	{return skills;}
	public int getHP()
	{
		return (int)hp;}
	public int getMP()
	{return (int)mp;}
	public int getXP()
	{return (int)xp;}
	public int getLV()
	{return level;}
	public String getName()
	{return name;}
	public String getDes()
	{return des;}

	public void setHP(int n)
	{hp+=n;
	 checkForOver();
	 perHP = (hp/defHP)*100;}
	public void setMP(int n)
	{mp+=n;
	checkForOver();
	 perMP = (mp/defMP)*100;}
	 	public void checkForOver()
	 	{if(hp > defHP)
	 		hp = defHP;
	 	 if(mp > defMP)
	 	 	mp = defMP;
	 	}

	public int getPerHP()
	{return (int)perHP;}
	public int getPerMP()
	{return (int)perMP;}
	public int getDefHP()
	{return (int)defHP;}
	public int getDefMP()
	{return (int)defMP;}
	public void setBound(int a, int b, int c, int d)
	{x = a;
	 y = b;
	 w = c;
	 h = d;}
	public int getX()
	{return x;}
	public int getY()
	{return y;}
	public int getW()
	{return w;}
	public int getH()
	{return h;}
}