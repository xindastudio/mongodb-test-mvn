package unittest;

import java.util.Arrays;
import java.util.HashMap;

import unittest.entity.EntityA;
import unittest.entity.EntityB;
import unittest.entity.EntityC;

/**
 * @author wuxufeng
 * 
 */
public class EntityBuilder {
	public static EntityA buildEntityA() {
		long t = System.nanoTime();
		EntityA a = new EntityA();
		a.setProBoolean(t % 2 == 0);
		a.setProByte((byte) (t % 2));
		a.setProChar((char) ('好'));
		a.setProShort((short) (t % 9999));
		a.setProInt((int) (t % 9999));
		a.setProFloat((float) (t * 1.0 / 9999.1));
		a.setProLong(t);
		a.setProDouble(t * 1.0d);
		a.setProString(String.valueOf(t));
		a.setProDate(new java.util.Date(t));
		a.setProSqlDate(new java.sql.Date(t));
		a.setProTime(new java.sql.Timestamp(t));

		long t1 = System.nanoTime();
		a.setArrayBoolean(new Boolean[] { t % 2 == 0, t1 % 2 == 0 });
		a.setArrayByte(new Byte[] { (byte) (t % 2), (byte) (t1 % 2) });
		a.setArrayChar(new Character[] { (char) (t % 2), (char) (t1 % 2) });
		a.setArrayShort(new Short[] { (short) (t % 9999), (short) (t1 % 9999) });
		a.setArrayInt(new Integer[] { (int) (t % 9999), (int) (t1 % 9999) });
		a.setArrayFloat(new Float[] { (float) (t * 1.0 / 9999.1),
				(float) (t1 * 1.0 / 9999.1) });
		a.setArrayLong(new Long[] { t, t1 });
		a.setArrayDouble(new Double[] { t * 1.0d, t1 * 1.0d });
		a.setArrayString(new String[] { String.valueOf(t), String.valueOf(t1) });
		a.setArrayDate(new java.util.Date[] { new java.util.Date(t),
				new java.util.Date(t1) });
		a.setArraySqlDate(new java.sql.Date[] { new java.sql.Date(t),
				new java.sql.Date(t1) });
		a.setArrayTime(new java.sql.Timestamp[] { new java.sql.Timestamp(t),
				new java.sql.Timestamp(t1) });

		a.setListBoolean(Arrays.asList(a.getArrayBoolean()));
		a.setListByte(Arrays.asList(a.getArrayByte()));
		a.setListChar(Arrays.asList(a.getArrayChar()));
		a.setListShort(Arrays.asList(a.getArrayShort()));
		a.setListInt(Arrays.asList(a.getArrayInt()));
		a.setListFloat(Arrays.asList(a.getArrayFloat()));
		a.setListLong(Arrays.asList(a.getArrayLong()));
		a.setListDouble(Arrays.asList(a.getArrayDouble()));
		a.setListString(Arrays.asList(a.getArrayString()));
		a.setListDate(Arrays.asList(a.getArrayDate()));
		a.setListSqlDate(Arrays.asList(a.getArraySqlDate()));
		a.setListTime(Arrays.asList(a.getArrayTime()));

		a.setMapBoolean(new HashMap<String, Boolean>());
		a.getMapBoolean().put("key01", a.getArrayBoolean()[0]);
		a.getMapBoolean().put("key02", a.getArrayBoolean()[1]);
		a.setMapByte(new HashMap<String, Byte>());
		a.getMapByte().put("key01", a.getArrayByte()[0]);
		a.getMapByte().put("key02", a.getArrayByte()[1]);
		a.setMapChar(new HashMap<String, Character>());
		a.getMapChar().put("key01", a.getArrayChar()[0]);
		a.getMapChar().put("key02", a.getArrayChar()[1]);
		a.setMapShort(new HashMap<String, Short>());
		a.getMapShort().put("key01", a.getArrayShort()[0]);
		a.getMapShort().put("key02", a.getArrayShort()[1]);
		a.setMapInt(new HashMap<String, Integer>());
		a.getMapInt().put("key01", a.getArrayInt()[0]);
		a.getMapInt().put("key02", a.getArrayInt()[1]);
		a.setMapFloat(new HashMap<String, Float>());
		a.getMapFloat().put("key01", a.getArrayFloat()[0]);
		a.getMapFloat().put("key02", a.getArrayFloat()[1]);
		a.setMapLong(new HashMap<String, Long>());
		a.getMapLong().put("key01", a.getArrayLong()[0]);
		a.getMapLong().put("key02", a.getArrayLong()[1]);
		a.setMapDouble(new HashMap<String, Double>());
		a.getMapDouble().put("key01", a.getArrayDouble()[0]);
		a.getMapDouble().put("key02", a.getArrayDouble()[1]);
		a.setMapString(new HashMap<String, String>());
		a.getMapString().put("key01", a.getArrayString()[0]);
		a.getMapString().put("key02", a.getArrayString()[1]);
		a.setMapDate(new HashMap<String, java.util.Date>());
		a.getMapDate().put("key01", a.getArrayDate()[0]);
		a.getMapDate().put("key02", a.getArrayDate()[1]);
		a.setMapSqlDate(new HashMap<String, java.sql.Date>());
		a.getMapSqlDate().put("key01", a.getArraySqlDate()[0]);
		a.getMapSqlDate().put("key02", a.getArraySqlDate()[1]);
		a.setMapTime(new HashMap<String, java.sql.Timestamp>());
		a.getMapTime().put("key01", a.getArrayTime()[0]);
		a.getMapTime().put("key02", a.getArrayTime()[1]);

		a.setProEntityB(buildEntityB());
		a.setArrayEntityB(new EntityB[] { buildEntityB(), buildEntityB() });
		a.setListEntityB(Arrays.asList(a.getArrayEntityB()));
		a.setMapEntityB(new HashMap<String, EntityB>());
		a.getMapEntityB().put("key01", a.getArrayEntityB()[0]);
		a.getMapEntityB().put("key02", a.getArrayEntityB()[1]);

		return a;
	}

	public static EntityB buildEntityB() {
		long t = System.nanoTime();
		EntityB a = new EntityB();
		a.setProBoolean(t % 2 == 0);
		a.setProByte((byte) (t % 2));
		a.setProChar((char) (t % 2));
		a.setProShort((short) (t % 9999));
		a.setProInt((int) (t % 9999));
		a.setProFloat((float) (t * 1.0 / 9999.1));
		a.setProLong(t);
		a.setProDouble(t * 1.0d);
		a.setProString(String.valueOf(t));
		a.setProDate(new java.util.Date(t));
		a.setProSqlDate(new java.sql.Date(t));
		a.setProTime(new java.sql.Timestamp(t));

		long t1 = System.nanoTime();
		a.setArrayBoolean(new Boolean[] { t % 2 == 0, t1 % 2 == 0 });
		a.setArrayByte(new Byte[] { (byte) (t % 2), (byte) (t1 % 2) });
		a.setArrayChar(new Character[] { (char) (t % 2), (char) (t1 % 2) });
		a.setArrayShort(new Short[] { (short) (t % 9999), (short) (t1 % 9999) });
		a.setArrayInt(new Integer[] { (int) (t % 9999), (int) (t1 % 9999) });
		a.setArrayFloat(new Float[] { (float) (t * 1.0 / 9999.1),
				(float) (t1 * 1.0 / 9999.1) });
		a.setArrayLong(new Long[] { t, t1 });
		a.setArrayDouble(new Double[] { t * 1.0d, t1 * 1.0d });
		a.setArrayString(new String[] { String.valueOf(t), String.valueOf(t1) });
		a.setArrayDate(new java.util.Date[] { new java.util.Date(t),
				new java.util.Date(t1) });
		a.setArraySqlDate(new java.sql.Date[] { new java.sql.Date(t),
				new java.sql.Date(t1) });
		a.setArrayTime(new java.sql.Timestamp[] { new java.sql.Timestamp(t),
				new java.sql.Timestamp(t1) });

		a.setListBoolean(Arrays.asList(a.getArrayBoolean()));
		a.setListByte(Arrays.asList(a.getArrayByte()));
		a.setListChar(Arrays.asList(a.getArrayChar()));
		a.setListShort(Arrays.asList(a.getArrayShort()));
		a.setListInt(Arrays.asList(a.getArrayInt()));
		a.setListFloat(Arrays.asList(a.getArrayFloat()));
		a.setListLong(Arrays.asList(a.getArrayLong()));
		a.setListDouble(Arrays.asList(a.getArrayDouble()));
		a.setListString(Arrays.asList(a.getArrayString()));
		a.setListDate(Arrays.asList(a.getArrayDate()));
		a.setListSqlDate(Arrays.asList(a.getArraySqlDate()));
		a.setListTime(Arrays.asList(a.getArrayTime()));

		a.setMapBoolean(new HashMap<String, Boolean>());
		a.getMapBoolean().put("key01", a.getArrayBoolean()[0]);
		a.getMapBoolean().put("key02", a.getArrayBoolean()[1]);
		a.setMapByte(new HashMap<String, Byte>());
		a.getMapByte().put("key01", a.getArrayByte()[0]);
		a.getMapByte().put("key02", a.getArrayByte()[1]);
		a.setMapChar(new HashMap<String, Character>());
		a.getMapChar().put("key01", a.getArrayChar()[0]);
		a.getMapChar().put("key02", a.getArrayChar()[1]);
		a.setMapShort(new HashMap<String, Short>());
		a.getMapShort().put("key01", a.getArrayShort()[0]);
		a.getMapShort().put("key02", a.getArrayShort()[1]);
		a.setMapInt(new HashMap<String, Integer>());
		a.getMapInt().put("key01", a.getArrayInt()[0]);
		a.getMapInt().put("key02", a.getArrayInt()[1]);
		a.setMapFloat(new HashMap<String, Float>());
		a.getMapFloat().put("key01", a.getArrayFloat()[0]);
		a.getMapFloat().put("key02", a.getArrayFloat()[1]);
		a.setMapLong(new HashMap<String, Long>());
		a.getMapLong().put("key01", a.getArrayLong()[0]);
		a.getMapLong().put("key02", a.getArrayLong()[1]);
		a.setMapDouble(new HashMap<String, Double>());
		a.getMapDouble().put("key01", a.getArrayDouble()[0]);
		a.getMapDouble().put("key02", a.getArrayDouble()[1]);
		a.setMapString(new HashMap<String, String>());
		a.getMapString().put("key01", a.getArrayString()[0]);
		a.getMapString().put("key02", a.getArrayString()[1]);
		a.setMapDate(new HashMap<String, java.util.Date>());
		a.getMapDate().put("key01", a.getArrayDate()[0]);
		a.getMapDate().put("key02", a.getArrayDate()[1]);
		a.setMapSqlDate(new HashMap<String, java.sql.Date>());
		a.getMapSqlDate().put("key01", a.getArraySqlDate()[0]);
		a.getMapSqlDate().put("key02", a.getArraySqlDate()[1]);
		a.setMapTime(new HashMap<String, java.sql.Timestamp>());
		a.getMapTime().put("key01", a.getArrayTime()[0]);
		a.getMapTime().put("key02", a.getArrayTime()[1]);

		a.setProEntityC(buildEntityC());
		a.setArrayEntityC(new EntityC[] { buildEntityC(), buildEntityC() });
		a.setListEntityC(Arrays.asList(a.getArrayEntityC()));
		a.setMapEntityC(new HashMap<String, EntityC>());
		a.getMapEntityC().put("key01", a.getArrayEntityC()[0]);
		a.getMapEntityC().put("key02", a.getArrayEntityC()[1]);

		return a;
	}

	public static EntityC buildEntityC() {
		long t = System.nanoTime();
		EntityC a = new EntityC();
		a.setBaseBoolean(t % 2 == 0);
		a.setBaseByte((byte) (t % 2));
		a.setBaseChar((char) (t % 2));
		a.setBaseShort((short) (t % 9999));
		a.setBaseInt((int) (t % 9999));
		a.setBaseFloat((float) (t * 1.0 / 9999.1));
		a.setBaseLong(t);
		a.setBaseDouble(t * 1.0d);

		a.setProBoolean(t % 2 == 0);
		a.setProByte((byte) (t % 2));
		a.setProChar((char) (t % 2));
		a.setProShort((short) (t % 9999));
		a.setProInt((int) (t % 9999));
		a.setProFloat((float) (t * 1.0 / 9999.1));
		a.setProLong(t);
		a.setProDouble(t * 1.0d);
		a.setProString(String.valueOf(t));
		a.setProDate(new java.util.Date(t));
		a.setProSqlDate(new java.sql.Date(t));
		a.setProTime(new java.sql.Timestamp(t));

		long t1 = System.nanoTime();
		a.setArrayBoolean(new Boolean[] { t % 2 == 0, t1 % 2 == 0 });
		a.setArrayByte(new Byte[] { (byte) (t % 2), (byte) (t1 % 2) });
		a.setArrayChar(new Character[] { (char) (t % 2), (char) (t1 % 2) });
		a.setArrayShort(new Short[] { (short) (t % 9999), (short) (t1 % 9999) });
		a.setArrayInt(new Integer[] { (int) (t % 9999), (int) (t1 % 9999) });
		a.setArrayFloat(new Float[] { (float) (t * 1.0 / 9999.1),
				(float) (t1 * 1.0 / 9999.1) });
		a.setArrayLong(new Long[] { t, t1 });
		a.setArrayDouble(new Double[] { t * 1.0d, t1 * 1.0d });
		a.setArrayString(new String[] { String.valueOf(t), String.valueOf(t1) });
		a.setArrayDate(new java.util.Date[] { new java.util.Date(t),
				new java.util.Date(t1) });
		a.setArraySqlDate(new java.sql.Date[] { new java.sql.Date(t),
				new java.sql.Date(t1) });
		a.setArrayTime(new java.sql.Timestamp[] { new java.sql.Timestamp(t),
				new java.sql.Timestamp(t1) });

		a.setListBoolean(Arrays.asList(a.getArrayBoolean()));
		a.setListByte(Arrays.asList(a.getArrayByte()));
		a.setListChar(Arrays.asList(a.getArrayChar()));
		a.setListShort(Arrays.asList(a.getArrayShort()));
		a.setListInt(Arrays.asList(a.getArrayInt()));
		a.setListFloat(Arrays.asList(a.getArrayFloat()));
		a.setListLong(Arrays.asList(a.getArrayLong()));
		a.setListDouble(Arrays.asList(a.getArrayDouble()));
		a.setListString(Arrays.asList(a.getArrayString()));
		a.setListDate(Arrays.asList(a.getArrayDate()));
		a.setListSqlDate(Arrays.asList(a.getArraySqlDate()));
		a.setListTime(Arrays.asList(a.getArrayTime()));

		a.setMapBoolean(new HashMap<String, Boolean>());
		a.getMapBoolean().put("key01", a.getArrayBoolean()[0]);
		a.getMapBoolean().put("key02", a.getArrayBoolean()[1]);
		a.setMapByte(new HashMap<String, Byte>());
		a.getMapByte().put("key01", a.getArrayByte()[0]);
		a.getMapByte().put("key02", a.getArrayByte()[1]);
		a.setMapChar(new HashMap<String, Character>());
		a.getMapChar().put("key01", a.getArrayChar()[0]);
		a.getMapChar().put("key02", a.getArrayChar()[1]);
		a.setMapShort(new HashMap<String, Short>());
		a.getMapShort().put("key01", a.getArrayShort()[0]);
		a.getMapShort().put("key02", a.getArrayShort()[1]);
		a.setMapInt(new HashMap<String, Integer>());
		a.getMapInt().put("key01", a.getArrayInt()[0]);
		a.getMapInt().put("key02", a.getArrayInt()[1]);
		a.setMapFloat(new HashMap<String, Float>());
		a.getMapFloat().put("key01", a.getArrayFloat()[0]);
		a.getMapFloat().put("key02", a.getArrayFloat()[1]);
		a.setMapLong(new HashMap<String, Long>());
		a.getMapLong().put("key01", a.getArrayLong()[0]);
		a.getMapLong().put("key02", a.getArrayLong()[1]);
		a.setMapDouble(new HashMap<String, Double>());
		a.getMapDouble().put("key01", a.getArrayDouble()[0]);
		a.getMapDouble().put("key02", a.getArrayDouble()[1]);
		a.setMapString(new HashMap<String, String>());
		a.getMapString().put("key01", a.getArrayString()[0]);
		a.getMapString().put("key02", a.getArrayString()[1]);
		a.setMapDate(new HashMap<String, java.util.Date>());
		a.getMapDate().put("key01", a.getArrayDate()[0]);
		a.getMapDate().put("key02", a.getArrayDate()[1]);
		a.setMapSqlDate(new HashMap<String, java.sql.Date>());
		a.getMapSqlDate().put("key01", a.getArraySqlDate()[0]);
		a.getMapSqlDate().put("key02", a.getArraySqlDate()[1]);
		a.setMapTime(new HashMap<String, java.sql.Timestamp>());
		a.getMapTime().put("key01", a.getArrayTime()[0]);
		a.getMapTime().put("key02", a.getArrayTime()[1]);

		return a;
	}

}
