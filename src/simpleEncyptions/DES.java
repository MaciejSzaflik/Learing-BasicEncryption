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
	@Override
	public void Encrypt() {
		
		BitSet messageBits = getBitSet("0123456789ABCDEF");
		CreateKey();
	}
	
	public BitSet[] CreateKey()
	{
		BitSet key = getFromHexString("133457799BBCDFF1");
		
		//printBits(key,8);
		//System.out.println("end");
		BitSet permutedKey = makePermutaion(pc_1,key);
		//printBits(permutedKey,7);
		
		BitSet[] cnKeys = new BitSet[16];
		BitSet[] dnKeys = new BitSet[16];
		
		cnKeys[0] = permutedKey.get(0, 28);
		dnKeys[0] = permutedKey.get(28, 56);
		
		for(int i = 1;i<cnKeys.length;i++)
		{
			cnKeys[i] = leftShiftBitSet(cnKeys[i-1],getNumberOfShifts(i),28);
			dnKeys[i] = leftShiftBitSet(dnKeys[i-1],getNumberOfShifts(i),28);
		}
		return cnKeys;
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
