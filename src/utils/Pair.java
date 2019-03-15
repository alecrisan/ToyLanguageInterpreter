package utils;

import java.io.BufferedReader;

public class Pair {

	String filename;
	BufferedReader bf;
	
	public Pair(String f, BufferedReader b)
	{
		filename = f;
		bf = b;
	}
	
	public String getFilename()
	{
		return this.filename;
	}
	
	public BufferedReader getBF()
	{
		return this.bf;
	}
	
	@Override
	public String toString()
	{
		return "(" + filename + ", " + bf.toString() + ")" + '\n';
	}
}
