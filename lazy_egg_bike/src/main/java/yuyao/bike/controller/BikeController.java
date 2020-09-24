package yuyao.bike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yuyao.bike.pojo.Bike;
import yuyao.bike.service.BikeService;

@Controller 
public class BikeController {
	
	@Autowired
	private BikeService bikeService;
	
	@RequestMapping("/bike/add")
	@ResponseBody
	
	public String add(@RequestBody Bike bike) {
		//go to service level
		bikeService.save(bike);
		return "success";
	}
	
	@RequestMapping("/bike/findNear")
	@ResponseBody
	public List<Bike> findNear(double longitude, double latitude) {
		//go to service level
		List<Bike> bikes = bikeService.findNear(longitude, latitude);
		return bikes;
	}
}

