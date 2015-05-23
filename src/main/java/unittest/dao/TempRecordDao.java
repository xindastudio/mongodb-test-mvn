package unittest.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import unittest.entity.TempRecord;

public interface TempRecordDao extends MongoRepository<TempRecord, String> {

}
