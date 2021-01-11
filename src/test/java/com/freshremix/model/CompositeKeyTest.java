package com.freshremix.model;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CompositeKeyTest {

	@Test
	public void testCreateCompositeKey() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, "K1");
		compositeKey.putKey(1, "K2");
		compositeKey.putKey(2, "K3");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>("K1",
				"K2", "K3");
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void invalidCreateCompositeKey() {

		CompositeKey<String> compositeKey = new CompositeKey<String>(2);
		compositeKey.putKey(0, "K1");
		compositeKey.putKey(1, "K2");
		compositeKey.putKey(2, "K3");
	}

	@DataProvider(name = "indexDP")
	public Object[][] indexDP() {
		return new Object[][] { { Integer.valueOf(0) }, { Integer.valueOf(-1) } };
	}

	@Test(dataProvider = "indexDP", expectedExceptions = { IllegalArgumentException.class })
	public void invalidCreateCompositeKey2(int size) {

		new CompositeKey<String>(size);
	}

	@DataProvider(name = "varArgsDP")
	public Object[][] varArgsDP() {
		return new Object[][] { { null }, { new String[] {} } };
	}

	@Test(dataProvider = "varArgsDP", expectedExceptions = { IllegalArgumentException.class })
	public void invalidCreateCompositeKey3(String... fields) {

		new CompositeKey<String>(fields);
	}

	@DataProvider(name = "invalidPositionDP")
	public Object[][] invalidPositionDP() {
		return new Object[][] { { Integer.valueOf(4) }, { Integer.valueOf(2) },
				{ Integer.valueOf(-1) } };
	}

	@Test(dataProvider = "invalidPositionDP", expectedExceptions = { IllegalArgumentException.class })
	public void invalidPutKey(int position) {

		CompositeKey<String> compositeKey = new CompositeKey<String>(2);
		compositeKey.putKey(position, "K3");

	}

	@Test(dataProvider = "invalidPositionDP", expectedExceptions = { IllegalArgumentException.class })
	public void invalidGetKey(int position) {

		CompositeKey<String> compositeKey = new CompositeKey<String>(2);
		compositeKey.getKey(position);

	}

	@Test
	public void testEqualsCompositeKey() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, "K1");
		compositeKey.putKey(1, "K2");
		compositeKey.putKey(2, "K3");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>("K1",
				"K2", "K3");
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertEquals(compositeKey, compositeKey2);
		Assert.assertTrue(compositeKey.equals(compositeKey2));
		Assert.assertTrue(compositeKey2.equals(compositeKey));
	}

	@Test
	public void testEqualsCompositeKey2() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, "K1");
		compositeKey.putKey(1, null);
		compositeKey.putKey(2, "K3");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>("K1",
				null, "K3");
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertEquals(compositeKey, compositeKey2);
		Assert.assertTrue(compositeKey.equals(compositeKey2));
		Assert.assertTrue(compositeKey2.equals(compositeKey));
	}

	@Test
	public void testEqualsCompositeKey3() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, null);
		compositeKey.putKey(1, null);
		compositeKey.putKey(2, null);
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>(null,
				null, null);
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertEquals(compositeKey, compositeKey2);
		Assert.assertTrue(compositeKey.equals(compositeKey2));
		Assert.assertTrue(compositeKey2.equals(compositeKey));
	}

	@Test
	public void testNotEqualsCompositeKey() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(2);
		compositeKey.putKey(0, "K1");
		compositeKey.putKey(1, "K2");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 2);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>("K1",
				"K2", "K3");
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertNotEquals(compositeKey, compositeKey2);
		Assert.assertFalse(compositeKey.equals(compositeKey2));
		Assert.assertFalse(compositeKey2.equals(compositeKey));
		Assert.assertFalse(compositeKey.equals(null));
	}

	@Test
	public void testNotEqualsCompositeKey2() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, "K2");
		compositeKey.putKey(1, "K2");
		compositeKey.putKey(2, "K3");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<String> compositeKey2 = new CompositeKey<String>("K1",
				"K2", "K3");
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertNotEquals(compositeKey, compositeKey2);
		Assert.assertFalse(compositeKey.equals(compositeKey2));
		Assert.assertFalse(compositeKey2.equals(compositeKey));
		Assert.assertFalse(compositeKey.equals(null));
	}

	@Test
	public void testNotEqualsCompositeKey3() {
		CompositeKey<String> compositeKey = new CompositeKey<String>(3);
		compositeKey.putKey(0, "K2");
		compositeKey.putKey(1, "K2");
		compositeKey.putKey(2, "K3");
		Assert.assertEquals(compositeKey.getNumberOfFields(), 3);

		CompositeKey<Integer> compositeKey2 = new CompositeKey<Integer>(1, 2, 3);
		Assert.assertEquals(compositeKey2.getNumberOfFields(), 3);

		Assert.assertEquals(compositeKey, compositeKey);
		Assert.assertNotEquals(compositeKey, compositeKey2);
		Assert.assertFalse(compositeKey.equals(compositeKey2));
		Assert.assertFalse(compositeKey2.equals(compositeKey));
		Assert.assertFalse(compositeKey.equals(null));
		Assert.assertFalse(compositeKey.equals(Integer.valueOf(1)));
	}

}
