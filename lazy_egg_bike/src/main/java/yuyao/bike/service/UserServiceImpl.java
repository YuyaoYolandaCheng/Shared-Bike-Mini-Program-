package yuyao.bike.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import yuyao.bike.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public boolean sendMsg(String countryCode, String phoneNum) {
		boolean flag = true;
		int appId = Integer.parseInt(stringRedisTemplate.opsForValue().get("appId"));
		String appKey = stringRedisTemplate.opsForValue().get("appKey");
		
		//use Tencent message API to text users
		String code = (int)((Math.random() * 9 + 1) * 1000) + "";
		String[] params = {code};
		
		int templateId = Integer.parseInt(stringRedisTemplate.opsForValue().get("templateId"));
		String sign = stringRedisTemplate.opsForValue().get("sign");
		
        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        
        try {
        SmsSingleSenderResult result = ssender.sendWithParam(countryCode, phoneNum, templateId, params, sign, "", "");
        stringRedisTemplate.opsForValue().set(phoneNum, code, 300, TimeUnit.SECONDS);
        }catch(Exception e){
        	flag = false;
        	e.printStackTrace();
        }
		
		return flag;
	}

	@Override
	public boolean verify(String phoneNum, String verifyCode) {
		boolean flag = false;
		//find the verify code in redis
		String code = stringRedisTemplate.opsForValue().get(phoneNum);
		
		if(code != null && code.equals(verifyCode)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void register(User user) {
		//use mongodb's dao to save user info
		mongoTemplate.insert(user);
	}

	@Override
	public void update(User user) {
		Update up = new Update();
		
		if(user.getDeposite() != null){		
		up.set("deposite", user.getDeposite());
	}
		if(user.getStatus() != null) {
			up.set("status", user.getStatus());
		}
		if(user.getName() != null) {
			up.set("name", user.getName());
		}
		if(user.getIdNum() != null) {
			up.set("idNum", user.getIdNum());
		}
		mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())), up, User.class);
}
}
