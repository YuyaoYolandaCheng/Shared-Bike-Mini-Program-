package yuyao.bike.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import yuyao.bike.pojo.Bike;

@Service
public class BikeServiceImpl implements BikeService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(Bike bike) {
		mongoTemplate.insert(bike); //save the info "bikes"
	}

	@Override
	public List<Bike> findNear(double longitude, double latitude) {
		return mongoTemplate.findAll(Bike.class);
	}

}
