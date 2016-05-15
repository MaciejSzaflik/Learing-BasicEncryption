package simpleEncyptions;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class Utils {
	public static String bistToString(BitSet bitSet,int split,int desiredLen)
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
		if(desiredLen > bitSet.length())
		{
			for(int i = 0;i<desiredLen -bitSet.length();i++)
				sb.append("0");
		}
		return sb.toString().trim();
		
	}
	public static void printBits(BitSet bitSet,int split)
	{
		System.out.println(bistToString(bitSet,split,0));
	}
	
	public static String bytesToHex(byte[] in) {
	    final StringBuilder builder = new StringBuilder();
	    for(byte b : in) {
	        builder.append(String.format("%02x", b));
	    }
	    return builder.toString();
	}
	
	public static BitSet bitSetFromLong(long value) {
	    BitSet bits = new BitSet();
	    int index = 0;
	    while (value != 0L) {
	      if (value % 2L != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	 }	
}
