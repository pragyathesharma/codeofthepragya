public class Skill
{
	private String name, des;
	private int mpUse, damage;


	public Skill(String n, String d, int m, int da)
	{
		name = n;
		des = d;
		mpUse = m;
		damage = da;

	}

	public String getName()
	{return name;}
	public String getDes()
	{return des;}
	public int getMPUse()
	{return mpUse;}
	public int getDamage()
	{return damage;}

}