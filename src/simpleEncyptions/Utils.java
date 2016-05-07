package simpleEncyptions;

import java.util.BitSet;

public class Utils {
	public static String bistToString(BitSet bitSet,int split)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<bitSet.length();i++)
		{
			sb.append(bitSet.get(i)?"1":"0");
			if(i!=0 && (i+1)%split == 0)
			{
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	public static void printBists(BitSet bitSet,int split)
	{
		System.out.println(bistToString(bitSet,split));
	}
	
	
}
