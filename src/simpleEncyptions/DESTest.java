package simpleEncyptions;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.BitSet;

public class DESTest {
	
	public static final String[] testKeys = 
		{
		"000110 110000 001011 101111 111111 000111 000001 110010",
		"011110 011010 111011 011001 110110 111100 100111 100101",
		"010101 011111 110010 001010 010000 101100 111110 011001",
		"011100 101010 110111 010110 110110 110011 010100 011101",
		"011111 001110 110000 000111 111010 110101 001110 101000",
		"011000 111010 010100 111110 010100 000111 101100 101111",
		"111011 001000 010010 110111 111101 100001 100010 111100",
		"111101 111000 101000 111010 110000 010011 101111 111011",
		"111000 001101 101111 101011 111011 011110 011110 000001",
		"101100 011111 001101 000111 101110 100100 011001 001111",
		"001000 010101 111111 010011 110111 101101 001110 000110",
		"011101 010111 000111 110101 100101 000110 011111 101001",
		"100101 111100 010111 010001 111110 101011 101001 000001",
		"010111 110100 001110 110111 111100 101110 011100 111010",
		"101111 111001 000110 001101 001111 010011 111100 001010",
		"110010 110011 110110 001011 000011 100001 011111 110101",
		};
	
	public static final String ipMessage = "1100 1100 0000 0000 1100 1100 1111 1111 1111 0000 1010 1010 1111 0000 1010 1010";
	public static final String firstKey = "011000010001011110111010100001100110010100100111";
	public static final String firstKeySBoxed = "0101 1100 1000 0010 1011 0101 1001 0111";
	public static final String fFunctionResult = "0010 0011 0100 1010 1010 1001 1011 1011";
	public static final String blockFinalResult = "10000101 11101000 00010011 01010100 00001111 00001010 10110100 00000101";
	@Test
	public void testCreateKey()
	{
		DES des = new DES();
		BitSet[] toTest = des.CreateKey("133457799BBCDFF1");
		for(int i = 0;i<testKeys.length;i++)
		{
			assertEquals(testKeys[i], Utils.bistToString(toTest[i+1], 6, 48));
		}
	}
	
	@Test
	public void testIpMessage()
	{
		DES des = new DES();
		BitSet toTest = des.ipMessageBlock("0123456789ABCDEF");
		assertEquals(ipMessage, Utils.bistToString(toTest, 4, 64));
	}
	
	@Test
	public void testEncryptBlock()
	{
		DES des = new DES();
		BitSet[] keys = des.CreateKey("133457799BBCDFF1");
		BitSet result = des.encryptBlockMessage("0123456789ABCDEF",keys);
		assertEquals(blockFinalResult, Utils.bistToString(result, 8, 64));
		
	}
		
	@Test 
	public void testFullSboxKey()
	{
		DES des = new DES();
		BitSet key = des.fromBinaryStringToBinSet(firstKey);
		BitSet sBoxedKey = des.sBoxKey(key);
		assertEquals(firstKeySBoxed, Utils.bistToString(sBoxedKey, 4, 32));
	}
	
	@Test
	public void testSBoxKeyPart()
	{
		DES des = new DES();
		BitSet key = des.fromBinaryStringToBinSet("011011");
		BitSet sBoxedKey = des.applySBox(key,DES.s1);
		assertEquals("0101", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("011000");
		sBoxedKey = des.applySBox(key,DES.s1);
		assertEquals("0101", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("010001");
		sBoxedKey = des.applySBox(key,DES.s2);
		assertEquals("1100", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("011110");
		sBoxedKey = des.applySBox(key,DES.s3);
		assertEquals("1000", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("111010");
		sBoxedKey = des.applySBox(key,DES.s4);
		assertEquals("0010", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("100001");
		sBoxedKey = des.applySBox(key,DES.s5);
		assertEquals("1011", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("100110");
		sBoxedKey = des.applySBox(key,DES.s6);
		assertEquals("0101", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("010100");
		sBoxedKey = des.applySBox(key,DES.s7);
		assertEquals("1001", Utils.bistToString(sBoxedKey, 6, 4));
		
		key = des.fromBinaryStringToBinSet("100111");
		sBoxedKey = des.applySBox(key,DES.s8);
		assertEquals("0111", Utils.bistToString(sBoxedKey, 6, 4));
	}
	
	
}
