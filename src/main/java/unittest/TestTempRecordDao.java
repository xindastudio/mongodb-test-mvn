package unittest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import unittest.dao.TempRecordDao;
import unittest.entity.TempRecord;

@SpringBootApplication
@Component
public class TestTempRecordDao implements CommandLineRunner {
	@Autowired
	private TempRecordDao tempRecordDao;

	public void run(String... args) throws Exception {
		// add();
		find();
		// delete();
	}

	public void add() {
		TempRecord r = new TempRecord();
		r.setId(1L);
		r.setName("name-001001");
		r.setSize(1);
		r.setDate(new Date());
		r.setSqlDate(new Date(System.currentTimeMillis()));
		r.setTimestamp(new Date(System.currentTimeMillis()));
		r.setFavs(Arrays.asList("fav001", "fav002"));
		r.setChanges(Arrays.asList(new Date(), new Date(), new Date()));
		tempRecordDao.save(r);
		System.out.println(r.getId());
	}

	public void find() {
		TempRecord r = tempRecordDao.findOne("55549d731bc35606ccca6182");
		System.out.println(r.getId());
	}

	public void delete() {
		tempRecordDao.delete("55549d731bc35606ccca6182");
	}

	public static void main(String[] args) {
		appBoot(args);
	}

	public static void springBoot(String[] args) {
		SpringApplication app = new SpringApplication(TestTempRecordDao.class);
		app.setWebEnvironment(false);
		app.setShowBanner(false);
		Set<Object> paths = new HashSet<Object>();
		paths.add("ApplicationContext-mongodb-datasource.xml");
		app.setSources(paths);
		app.run(args);
	}

	public static void appBoot(String[] args) {
		String[] paths = new String[] { "ApplicationContext-mongodb-datasource.xml" };
		ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext(
				paths);
		TestTempRecordDao dao = (TestTempRecordDao) cpac
				.getBean("testTempRecordDao");
		try {
			dao.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cpac.close();
	}

}
