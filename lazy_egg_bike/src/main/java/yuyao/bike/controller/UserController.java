package yuyao.bike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yuyao.bike.pojo.User;
import yuyao.bike.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/user/genCode")
	@ResponseBody
public boolean genVerifyCode(String countryCode, String phoneNum){
		boolean flag = userService.sendMsg(countryCode, phoneNum);
		return flag ;
}
	
	@RequestMapping("/user/verify")
	@ResponseBody
	public boolean verify(String phoneNum, String verifyCode) {
		//use service level
		return userService.verify(phoneNum, verifyCode);
	}
	
	@RequestMapping("/user/register")
	@ResponseBody
	public boolean reg(@RequestBody User user) { // add @RequestBody to receive json typed info
		// use service level
		boolean flag = true;
		try {
		userService.register(user);
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping("/user/deposite")
	@ResponseBody
	public boolean deposite(@RequestBody User user) {
		boolean flag = true;
		try {
		userService.update(user);
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@RequestMapping("/user/identify")
	@ResponseBody
	public boolean identify(@RequestBody User user) {
		boolean flag = true;
		try {
		userService.update(user);
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
