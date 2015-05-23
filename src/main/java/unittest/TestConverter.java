package unittest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import unittest.entity.EntityA;
import cn.com.common.convert.DefaultConverter;

import com.google.gson.Gson;

/**
 * @author wuliwei
 * 
 */
public class TestConverter {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPrimitive() {
		long s = System.currentTimeMillis();
		Map<Class<?>, Object> cm = new LinkedHashMap<Class<?>, Object>();
		cm.put(Boolean.class, Boolean.valueOf(true));
		cm.put(Byte.class, Byte.valueOf((byte) 1));
		cm.put(Character.class, Character.valueOf((char) 1));
		cm.put(Short.class, Short.valueOf((short) 1));
		cm.put(Integer.class, (Integer) 1);
		cm.put(Float.class, (Float) 1.0F);
		cm.put(Long.class, (Long) 1L);
		cm.put(Double.class, (Double) 1.0D);
		cm.put(String.class, "1");
		cm.put(Boolean.TYPE, (boolean) true);
		cm.put(Byte.TYPE, (byte) 1);
		cm.put(Character.TYPE, (char) 1);
		cm.put(Short.TYPE, (short) 1);
		cm.put(Integer.TYPE, (int) 1);
		cm.put(Float.TYPE, (float) 1.0F);
		cm.put(Long.TYPE, (long) 1L);
		cm.put(Double.TYPE, (double) 1.0D);
		DefaultConverter dc = new DefaultConverter();
		for (Entry<Class<?>, Object> etr1 : cm.entrySet()) {
			for (Entry<Class<?>, Object> etr2 : cm.entrySet()) {
				convert(dc, etr1, etr2);
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("used time " + (e - s));
	}

	@Test
	public void testDate() {
		long s = System.currentTimeMillis();
		Map<Class<?>, Object> cm = new LinkedHashMap<Class<?>, Object>();
		cm.put(java.util.Date.class, new java.util.Date());
		cm.put(java.sql.Date.class,
				new java.sql.Date(System.currentTimeMillis()));
		cm.put(java.sql.Timestamp.class,
				new java.sql.Timestamp(System.currentTimeMillis()));
		DefaultConverter dc = new DefaultConverter();
		for (Entry<Class<?>, Object> etr1 : cm.entrySet()) {
			for (Entry<Class<?>, Object> etr2 : cm.entrySet()) {
				convert(dc, etr1, etr2);
			}
		}

		dc.registClassMap(java.sql.Date.class, java.util.Date.class);
		dc.registClassMap(java.sql.Timestamp.class, java.util.Date.class);
		for (Entry<Class<?>, Object> etr1 : cm.entrySet()) {
			for (Entry<Class<?>, Object> etr2 : cm.entrySet()) {
				convert(dc, etr1, etr2);
			}
		}
		long e = System.currentTimeMillis();
		System.out.println("used time " + (e - s));
	}

	@Test
	public void testComplex() {
		DefaultConverter dc = new DefaultConverter();
		String s1, s2;
		EntityA a = EntityBuilder.buildEntityA();
		s1 = new Gson().toJson(a);
		long s = System.currentTimeMillis();
		Object o = dc.convert(a, EntityA.class);
		long e = System.currentTimeMillis();
		System.out.println("used time " + (e - s));
		s2 = new Gson().toJson(o);
		System.out.println(s1.equals(s2));
		o = dc.convert(a, Map.class);
		e = System.currentTimeMillis();
		System.out.println("used time " + (e - s));
		o = dc.convert(o, EntityA.class);
		e = System.currentTimeMillis();
		System.out.println("used time " + (e - s));
		s2 = new Gson().toJson(o);
		System.out.println(s1.equals(s2));
	}

	private static void convert(DefaultConverter dc,
			Entry<Class<?>, Object> etr1, Entry<Class<?>, Object> etr2) {
		try {
			Object dest = dc.convert(etr1.getValue(), etr2.getKey());
			System.out.println(String.format("s %s = %s, d %s, %s = %s", etr1
					.getValue().getClass(), etr1.getValue(), etr2.getKey(),
					null != dest ? dest.getClass() : dest, dest));
		} catch (Exception e) {
			System.out.println(String.format("s %s = %s, d %s error ", etr1
					.getValue().getClass(), etr1.getValue(), etr2.getKey()));
			e.printStackTrace();
		}
	}

}
