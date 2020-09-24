package yuyao.bike.service;


import java.util.List;

import yuyao.bike.pojo.Bike;

public interface BikeService {

	void save(Bike bike);

	public List<Bike> findNear(double longitude, double latitude);
	
}
