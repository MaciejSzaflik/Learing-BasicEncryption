package simpleEncyptions;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.BitSet;

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
	
	
	@Override
	public void Encrypt() {
		
		BitSet messageBits = getBitSet("0123456789ABCDEF");
		CreateKey("133457799BBCDFF1");
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
	
	private BitSet fromBinaryStringToBinSet(String binaryString){
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
