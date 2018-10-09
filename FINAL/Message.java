import java.util.*;
public class Message
{
	private String text;
	private int faceNum;
	private ArrayList<String> formatted;

	public Message(String t)
	{
		text = t;
		formatted = new ArrayList<String>();
	}

	public Message(String t, int n)
	{
		text = t;
		faceNum = n;
		formatted = new ArrayList<String>();
	}

	public ArrayList<String> format(int length)
	{
		int index = 0;
		formatted.add(" "+text.charAt(0));
		for(int i = 1; i < text.length(); i++)
		{
			if(i%length!=0)
				formatted.set(index, formatted.get(index)+text.charAt(i));

			else
			{
				if(i+1<text.length() && !(""+text.charAt(i-1)).equals(" ") && !(""+text.charAt(i+1)).equals(" ") && !(""+text.charAt(i)).equals(" "))
				{
					formatted.set(index, formatted.get(index)+text.charAt(i)+text.charAt(i+1));
					i++;
					formatted.add("");
				}
				else if(!(""+text.charAt(i-1)).equals(" ") && !(""+text.charAt(i)).equals(" "))
				{
					formatted.set(index, formatted.get(index)+text.charAt(i)+"");
					formatted.add("");
				}
				else if(!(""+text.charAt(i-1)).equals(" ") && (""+text.charAt(i)).equals(" "))
				{
					formatted.set(index, formatted.get(index)+"-");
					formatted.add("");
				}
				else formatted.add(""+text.charAt(i));
				index++;


			}
		}
		return formatted;
	}

	public ArrayList<String> getFormattedMessage()
	{return formatted;}

	public String getMessage()
	{return text;}

	public void setMessage(String t)
	{text = t;}

	public int getNum()
	{return faceNum;}



}