import java.util.ArrayList;

public class PlayerStats
{
	private double hp, mp, xp, money, perHP, perMP, lvHP, lvMP, lvNext, toNext, perXP;
	private int lv;
	private String name;
	private ArrayList<Item> items;
	private ArrayList<Skill> skills;
	//private boolean;

	public PlayerStats(double hp, double mp, double xp, int lv, double money, String name)
	{
		this.hp = hp;
		this.mp = mp;
		this.money = money;
		this.name = name;
		this.xp = xp;
		this.lv = lv;
		toNext = 100;
		lvNext = toNext;
		lvHP = hp;
		lvMP = mp;
		perHP = 100;
		perMP = 100;
		perXP = 0;
		items = new ArrayList<Item>();
		skills = new ArrayList<Skill>();
	}

	public void levelUp()
	{lv++;
	lvNext *= 1.5;
	toNext = lvNext;
	perXP = xp/(toNext+xp);
	lvHP*=1.5;
	perHP=(hp/lvHP)*100;
	lvMP*=1.2;
	perMP=(mp/lvMP)*100;
	checkForOver();}

	public void setXP(int n)
	{xp+=n;
	 toNext-=n;
	 if(toNext==0)
	 	levelUp();}
	public void setHP(int n)
	{hp+=n;
	 checkForOver();
	 perHP = (hp/lvHP)*100;}
	public void setMP(int n)
	{mp+=n;
	 checkForOver();
	 perMP = (mp/lvMP)*100;}
	public void setMoney(double n)
	{money+=n;}
	public void addItem(String item, String des)
	{if(items.size()<12)
		items.add(new Item(item, des));}
	public void addItem(String item, String des, int hpChange, int mpChange)
		{if(items.size()<12)
		items.add(new Item(item, des, hpChange, mpChange));}
	public void useItem(int n)
	{if(items.get(n).hasEffect()){
	 setHP(items.get(n).effect(0));
	 setMP(items.get(n).effect(1));
	 items.remove(n);}}
	public void removeItem(int n)
	{if(n<items.size())
		items.remove(n);}
	public void removeItemByName(String name)
	{
		boolean alreadyFound = false;
		for(int i = 0; i < items.size(); i++)
		{if(items.get(i).getName().equals(name) && !alreadyFound)
			{
				items.remove(i);
				alreadyFound = true;
			}
		}
	}

	public void addSkill(String name, String des, int mpU, int da)
	{if(skills.size()<6)
		skills.add(new Skill(name, des, mpU, da));}

	public void checkForOver()
	{if(hp > lvHP)
		hp = lvHP;
	 if(mp > lvMP)
	 	mp = lvMP;
	 if(hp < 0)
	 	hp = 0;
	 if(mp < 0)
	 	mp = 0;
	}
	public void resetAll(double a, double b, double c, double d, int e)
	{
		hp = a;
		mp = b;
		lvHP = hp;
		lvMP = mp;
		xp = c;
		toNext = 100;
		lvNext = toNext;
		perHP = 100;
		perMP = 100;
		money = d;
		lv = e;
		for(int i = items.size()-1; i >=0 ; i--)
			  items.remove(i);
		for(int i = skills.size()-1; i >=0 ; i--)
			  skills.remove(i);
	}
	public int getPerHP()
	{return (int)perHP;}
	public int getPerMP()
	{return (int)perMP;}
	public int getPerXP()
	{return (int)perXP;}
	public int getHP()
	{return (int)hp;}
	public int getLVHP()
	{return (int)lvHP;}
	public int getLVMP()
	{return (int)lvMP;}
	public int getMP()
	{return (int)mp;}
	public int getXP()
	{return (int)xp;}
	public int getToNext()
	{return (int)toNext;}
	public int getLV()
	{return lv;}
	public double getMoney()
	{return money;}
	public String getName()
	{return name;}
	public Item getItem(int n)
	{return items.get(n);}
	public ArrayList<Item> getItems()
	{return items;}
	public Skill getSkill(int n)
	{return skills.get(n);}
	public ArrayList<Skill> getSkills()
	{return skills;};


}