package unittest;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import unittest.entity.EntityA;
import unittest.entity.EntityB;
import unittest.entity.EntityC;
import cn.com.common.convert.DefaultConverter;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

public class TestMongoDb {
	private static Logger logger = Logger.getLogger(TestMongoDb.class);

	private static MongoClient mongoClient;
	private static MongoDatabase db;
	private static MongoCollection<Document> trs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String url = "mongodb://10.0.0.101:27017/?maxpoolsize=100;minpoolsize=0;maxidletimems=0;maxlifetimems=0;waitqueuemultiple=5;waitqueuetimeoutms=120000;connecttimeoutms=10000;sockettimeoutms=0;ssl=false";
		mongoClient = new MongoClient(new MongoClientURI(url));
		db = mongoClient.getDatabase("test");
		trs = db.getCollection("t_test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (null != mongoClient) {
			mongoClient.close();
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testCreateCollection() {
		trs = db.getCollection("t_test");
		logger.info("null == trs ? " + (null == trs));
		if (null == trs) {
			db.createCollection("t_test");
		} else {
		}
	}

	@Test
	@Ignore
	public void testGetCollection() {
		Assert.assertTrue(null != trs);
		logger.info("trs.count() is " + trs.count());
	}

	@Test
	@Ignore
	public void testAdd() {
		EntityA a = EntityBuilder.buildEntityA();
		DefaultConverter dc = new DefaultConverter()
				.registClassMap(java.sql.Date.class, java.util.Date.class)
				.registClassMap(java.sql.Timestamp.class, java.util.Date.class)
				.registClassMap(java.lang.Character.class,
						java.lang.String.class)
				.registClassMap(java.lang.Character.TYPE,
						java.lang.String.class)
				.registArrayClassMap(Collection.class)
				.registClassMap(EntityB.class, Map.class)
				.registClassMap(EntityC.class, Map.class);
		long s = System.currentTimeMillis();
		Document obj = (Document) dc.convert(a, Document.class);
		long e = System.currentTimeMillis();
		System.out.println("trans used time " + (e - s));
		s = System.currentTimeMillis();
		trs.insertOne(obj);
		e = System.currentTimeMillis();
		System.out.println("save used time " + (e - s));
		logger.info("add : " + obj.get("_id"));
	}

	@Test
	@Ignore
	public void testFindAll() {
		long os = System.currentTimeMillis();
		int oc = 0, min = 0;
		logger.info(trs.count());
		FindIterable<Document> objs = trs.find();
		// objs.batchSize(1);
		// logger.info("find all documents : ");
		MongoCursor<Document> docs = objs.iterator();
		Document obj = null;
		DefaultConverter dc = new DefaultConverter();
		while (docs.hasNext()) {
			long s = System.currentTimeMillis();
			obj = docs.next();
			oc++;
			long e = System.currentTimeMillis();
			if (e - s < 5) {
				min++;
			}
			logger.info(obj.get("_id"));
			s = System.currentTimeMillis();
			Object o = dc.convert(obj, EntityA.class);
			e = System.currentTimeMillis();
			System.out.println(((EntityA) o).getProChar() + ", "
					+ ((int) ((EntityA) o).getProChar()));
			System.out.println("trans use time " + (e - s));
		}
		long oe = System.currentTimeMillis();
		logger.info("count " + oc + ", " + min);
		logger.info("use time " + (oe - os));
	}

	@Test
	@Ignore
	public void testFindWithEq() {
		FindIterable<Document> objs = trs
				.find(eq("proString", "9054634356536"));
		for (Document o : objs) {
			logger.info(o.get("_id"));
		}

		objs = trs.find(eq("mapEntityB.key01.arrayString", "6919795404114"));
		for (Document o : objs) {
			logger.info(o.get("_id"));
		}
	}

	@Test
	@Ignore
	public void testFindWithGt() {
		FindIterable<Document> objs = trs
				.find(gt("proString", "6919701267473"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithLt() {
		FindIterable<Document> objs = trs
				.find(lt("proString", "9054634356536"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithLike() {
		FindIterable<Document> objs = trs.find(regex("proString",
				"^9054634356536"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}

		objs = trs.find(regex("proString", Pattern.compile("6919701267473$")));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithIn() {
		FindIterable<Document> objs = trs.find(in("proString", "9054634356536",
				"6919701267473"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithOr() {
		FindIterable<Document> objs = trs.find(or(
				eq("proString", "9054634356536"),
				eq("proString", "6919701267473")));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithAnd() {
		FindIterable<Document> objs = trs.find(and(
				eq("proString", "9054634356536"), eq("proInt", 527)));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString") + ", "
					+ o.get("proInt"));
		}
	}

	@Test
	@Ignore
	public void testFindWithLimit() {
		FindIterable<Document> objs = trs.find(
				or(eq("proString", "9054634356536"),
						eq("proString", "6919701267473"))).limit(1);
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

	@Test
	@Ignore
	public void testFindWithSort() {
		FindIterable<Document> objs = trs.find(or(
				eq("proString", "9054634356536"),
				eq("proString", "6919701267473")));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}

		objs = trs.find(
				or(eq("proString", "9054634356536"),
						eq("proString", "6919701267473"))).sort(
				Sorts.descending("proString"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}

		objs = trs.find(
				or(eq("proString", "9054634356536"),
						eq("proString", "6919701267473"))).sort(
				Sorts.ascending("proString"));
		for (Document o : objs) {
			logger.info(o.get("_id") + ", " + o.get("proString"));
		}
	}

}
