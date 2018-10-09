public class Item
{
	private String name, description;
	private int hp, mp;
	private boolean hasPointEffect;

	public Item(String n, String d)
	{
		name = n;
		description = d;
		hasPointEffect = false;
	}
	public Item(String n, String d, int h, int m)
	{
		name = n;
		description = d;
		hp = h;
		mp = m;
		hasPointEffect = true;
	}

	public String getName()
	{return name;}
	public String getDes()
	{return description;}
	public int effect(int n)
	{
		if(n == 0)
			return hp;
		else return mp;
	}
	public boolean hasEffect()
	{return hasPointEffect;}

}