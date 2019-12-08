package uk.org.merg.jfcu.events;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Test_ByteOps {

	@ParameterizedTest
	@ValueSource(ints = {Integer.MIN_VALUE, -1, 8, Integer.MAX_VALUE})
	void test_getBitException(int number) {
		ByteOp byteOp = new ByteOp();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			  byteOp.getBit(number);
			  });
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,7})
	void test_Clear(int number) {
		ByteOp byteOp = new ByteOp();
		byteOp.setBit(number, BitOp.CLEAR);
		assertEquals(BitOp.CLEAR, byteOp.getBit(number));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {Integer.MIN_VALUE, -1, 8, Integer.MAX_VALUE})
	void test_ClearException(int number) {
		ByteOp byteOp = new ByteOp();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			  byteOp.setBit(number, BitOp.CLEAR);
			  });
	}


	@ParameterizedTest
	@ValueSource(ints = {0,7})
	void test_Set(int number) {
		ByteOp byteOp = new ByteOp();
		byteOp.setBit(number, BitOp.SET);
		assertEquals(BitOp.SET, byteOp.getBit(number));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {Integer.MIN_VALUE, -1, 8, Integer.MAX_VALUE})
	void test_SetException(int number) {
		ByteOp byteOp = new ByteOp();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			  byteOp.setBit(number, BitOp.SET);
			  });
	}

	@ParameterizedTest
	@ValueSource(ints = {0,7})
	void test_Unchanged(int number) {
		ByteOp byteOp = new ByteOp();
		byteOp.setBit(number, BitOp.UNCHANGED);
		assertEquals(BitOp.UNCHANGED, byteOp.getBit(number));
	}

	@ParameterizedTest
	@ValueSource(ints = {Integer.MIN_VALUE, -1, 8, Integer.MAX_VALUE})
	void test_UnchangedException(int number) {
		ByteOp byteOp = new ByteOp();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			  byteOp.setBit(number, BitOp.UNCHANGED);
			  });
	}

	@ParameterizedTest
	@ValueSource(ints = {0,7})
	void test_asIntSet(int number) {
		ByteOp byteOp = new ByteOp();
		byteOp.setBit(number, BitOp.SET);
		assertEquals(1<<number, byteOp.asInt());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,7})
	void test_asIntClear(int number) {
		ByteOp byteOp = new ByteOp();
		byteOp.setBit(number, BitOp.SET);
		assertEquals(1<<number, byteOp.asInt());
		byteOp.setBit(number, BitOp.UNCHANGED);
		assertEquals(0, byteOp.asInt());
		byteOp.setBit(number, BitOp.CLEAR);
		assertEquals(0, byteOp.asInt());
	}

}
