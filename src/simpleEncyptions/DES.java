package simpleEncyptions;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;

import javax.xml.bind.DatatypeConverter;

public class DES implements Encryption{

	public static final int[] pc_1 = {
		57,49,41,33,25,17, 9,
		 1,58,50,42,34,26,18,
		10, 2,59,51,43,35,27,
		19,11, 3,60,52,44,36,
		63,55,47,39,31,23,15,
		 7,62,54,46,38,30,22,
		14, 6,61,53,45,37,29,
		21,13, 5,28,20,12, 4};
	
	public static final int[] pc_2 = {
		  14,17,11,24, 1, 5,
	       3,28,15, 6,21,10,
	      23,19,12, 4,26, 8,
	      16, 7,27,20,13, 2,
	      41,52,31,37,47,55,
	      30,40,51,45,33,48,
	      44,49,39,56,34,53,
	      46,42,50,36,29,32};
	
	public static final int [] ip = {
		58,50,42,34,26,18,10, 2,
	    60,52,44,36,28,20,12, 4,
	    62,54,46,38,30,22,14, 6,
	    64,56,48,40,32,24,16, 8,
	    57,49,41,33,25,17, 9, 1,
	    59,51,43,35,27,19,11, 3,
	    61,53,45,37,29,21,13, 5,
	    63,55,47,39,31,23,15, 7};
	
	public static final int [] ipMinus1 = {
		40,8,48,16,56,24,64,32,
		39,7,47,15,55,23,63,31,
		38,6,46,14,54,22,62,30,
		37,5,45,13,53,21,61,29,
		36,4,44,12,52,20,60,28,
		35,3,43,11,51,19,59,27,
		34,2,42,10,50,18,58,26,
		33,1,41,9,49,17,57,25};
	
	public static final int [] e_bit_selection = {
			32, 1, 2, 3, 4, 5,
             4, 5, 6, 7, 8, 9,
             8, 9,10,11,12,13,
            12,13,14,15,16,17,
            16,17,18,19,20,21,
            20,21,22,23,24,25,
            24,25,26,27,28,29,
            28,29,30,31,32, 1};
	
	public static final int [] s1 = {
			14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
			0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
			4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
			15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13};
	
	public static final int [] s2 = {
			15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
			3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
			0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
			13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9};
	
	public static final int [] s3 = {
			10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
			13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
			13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
			1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12};
	
	public static final int [] s4 = {
			7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
			13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
			10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
			3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14};
	
	public static final int [] s5 = {
			2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
			14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
			4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
			11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3};
	
	public static final int [] s6 = {
			12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
			10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
			9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
			4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13};
	
	public static final int [] s7 = {
			4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
			13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
			1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
			6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12};
	
	public static final int [] s8 = {
			13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
			1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
			7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
			2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11};
	
	public static final int [] p = {
			16,7,20,21,
			29,12,28,17,
			1,15,23,26,
			5,18,31,10,
			2,8,24,14,
			32,27,3,9,
			19,13,30,6,
			22,11,4,25};
	
	
	@Override
	public void Encrypt() {
		
		BitSet[] keys = CreateKey("133457799BBCDFF1");
		encryptBlockMessage("0123456789ABCDEF",keys);
	}
	
	public BitSet ipMessageBlock(String hexString)
	{
		BitSet messageBits = getFromHexString(hexString);
		return makePermutaion(ip,messageBits);
	}
	
	public BitSet encryptBlockMessage(String block,BitSet[] keys){
		BitSet messageBits = ipMessageBlock(block);
		BitSet[] l = new BitSet[17];
		BitSet[] r = new BitSet[17];
		
		l[0] = messageBits.get(0, 32);
		r[0] = messageBits.get(32, 64);
		
		
		for(int i = 1;i<17;i++)
		{
			l[i] = r[i-1];
			r[i] = l[i-1];
			r[i].xor(fFunctionKey(r[i-1],keys[i]));
		}
		
		BitSet fullBlock = makePermutaion(ipMinus1,concatenateBitSets(r[16],l[16],32));
		return fullBlock;
	}
	
	public BitSet fFunctionKey(BitSet r,BitSet k)
	{
		BitSet k_er0 = extendBlock(r,e_bit_selection);
		k_er0.xor(k);
		return this.makePermutaion(p, sBoxKey(k_er0));
	}
	
	
	public BitSet sBoxKey(BitSet key)
	{
		BitSet[] boxedKey = new BitSet[8];
		BitSet fullkey = new BitSet();
		int[][] sBoxes = getSBoxesArray();
		for(int i =0;i<sBoxes.length;i++)
		{
			boxedKey[i] = applySBox(key.get(i*6, 6 + i*6),sBoxes[i]);
			for(int j = 0;j<4;j++)
				fullkey.set(i*4 + j,boxedKey[i].get(j));
		}
		return fullkey;
	}
	
	private int[][] getSBoxesArray()
	{
		int [][] array = {s1,s2,s3,s4,s5,s6,s7,s8};
		return array;
	}
	
	public BitSet applySBox(BitSet bitsToEncode,int[] sBox)
	{
		BitSet rowNumber = new BitSet();
		rowNumber.set(1,bitsToEncode.get(0));
		rowNumber.set(0,bitsToEncode.get(5));
		BitSet columnNumber = new BitSet();
		for(int i = 0;i<4;i++)
			columnNumber.set(i,bitsToEncode.get(4-i));
		
		long[] lARow = rowNumber.toLongArray();
		long[] lACol = columnNumber.toLongArray();
		
		int rowNum = lARow.length>0?(int)lARow[0]:0;
		int colNum = lACol.length>0?(int)lACol[0]:0;
		BitSet result = reverseBitSet(Utils.bitSetFromLong(sBox[rowNum*16 + colNum]),4);
		return result;
	}
	
	private BitSet reverseBitSet(BitSet toReverse,int len)
	{
		BitSet result = new BitSet();
		for(int i = 0;i<len/2;i++)
		{
			boolean swapBit = toReverse.get(i);
			result.set(i,toReverse.get(len - 1 -i));
			result.set(len -1 -i,swapBit);
		}
		return result;
	}
	
	
	private BitSet extendBlock(BitSet toExtend,int[] extender)
	{
		return makePermutaion(extender, toExtend);
	}
	
	public BitSet[] CreateKey(String hexString)
	{
		BitSet key = getFromHexString(hexString);
		
		BitSet permutedKey = makePermutaion(pc_1,key);
		
		BitSet[] cnKeys = new BitSet[17];
		BitSet[] dnKeys = new BitSet[17];
		
		BitSet[] fullKeys = new BitSet[17];
		
		cnKeys[0] = permutedKey.get(0, 28);
		dnKeys[0] = permutedKey.get(28, 56);
		fullKeys[0] = permutedKey;
		
		for(int i = 1;i<cnKeys.length;i++)
		{
			cnKeys[i] = leftShiftBitSet(cnKeys[i-1],getNumberOfShifts(i),28);
			dnKeys[i] = leftShiftBitSet(dnKeys[i-1],getNumberOfShifts(i),28);
			fullKeys[i] = makePermutaion(pc_2,concatenateBitSets(cnKeys[i],dnKeys[i],28));
		}
		return fullKeys;
	}
	
	public BitSet concatenateBitSets(BitSet a, BitSet b,int size)
	{
		BitSet toReturn = new BitSet();
		for(int i =0;i<size*2;i++)
		{
			toReturn.set(i,i<size?a.get(i):b.get(i-size));
		}
		return toReturn;
	}
	
	
	public BitSet getFromHexString(String toConvert)
	{
		return fromBinaryStringToBinSet(hexToBin(toConvert));
	}
	
	private String hexToBin(String s) {
		BigInteger big = new BigInteger(s, 16);
		return padBinaryString(new BigInteger(s, 16).toString(2), (int)Math.ceil((big.bitLength()/8.0))*8);
	}
	
	public BitSet fromBinaryStringToBinSet(String binaryString){
		BitSet toReturn = new BitSet();
		for(int i = 0; i < binaryString.length(); i++)
			toReturn.set(i,binaryString.charAt(i) == '1');
		return toReturn;
	}
	
	private String padBinaryString(String s, int numDigits)
    {
        StringBuffer sb = new StringBuffer(s);
        int numZeros = numDigits - s.length();
        while(numZeros-- > 0) { 
            sb.insert(0, "0");
        }

        return sb.toString();
    }

	private BitSet makePermutaion(int[] permutationVector, BitSet toPermutate)
	{
		BitSet toReturn = new BitSet();
		for(int i = 0;i<permutationVector.length;i++)
		{
			toReturn.set(i, toPermutate.get(permutationVector[i]-1));
		}
		return toReturn;
	}
	
	
	private BitSet getBitSet(String hexbinary)
	{
		return BitSet.valueOf(DatatypeConverter.parseHexBinary(hexbinary));
	}
	
	private BitSet leftShiftBitSet(BitSet toShift, int shiftNum,int desiredSize)
	{
		boolean[] bitsToPreserve = new boolean[shiftNum];
		for(int i = 0;i<shiftNum;i++)
			bitsToPreserve[i] = toShift.get(i);
		
		
		BitSet toReturn = toShift.get(shiftNum, toShift.length());
		for(int i = 0;i<shiftNum;i++)
			toReturn.set(desiredSize - shiftNum + i,bitsToPreserve[i]);
		
		return toReturn;
	}
	
	private int getNumberOfShifts(int iterationNumber)
	{
		if(iterationNumber == 1 || 
		   iterationNumber == 2 ||
		   iterationNumber == 9 ||
		   iterationNumber == 16){
			return 1;
		}
		return 2;
	}


}
