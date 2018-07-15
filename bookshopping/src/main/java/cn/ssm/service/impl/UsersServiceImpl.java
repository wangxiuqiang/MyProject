package cn.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.dao.UsersMapper;
import cn.ssm.model.Users;
import cn.ssm.service.UsersService;
import cn.ssm.utils.MailUtils;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersMapper userMapper;
	

	
	@Override
	public int saveUsers(Users user) {
		// TODO Auto-generated method stub
		
		int num = userMapper.saveUsers(user);
		
	   //发送给用户激活邮件
	   //将tomcat作为web服务器
	   try {
		MailUtils.sendMail(user.getEmail(), user.getActivecode());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return num;
	}
	@Override
	public int delUsers(int id) {
		// TODO Auto-generated method stub
		return userMapper.delUsers(id);
	}
	@Override
	public int updateUsers(Users user) {
		// TODO Auto-generated method stub
		return userMapper.updateUsers(user);
	}
	@Override
	public Users findUsersById(int id) {
		// TODO Auto-generated method stub
		return userMapper.findUsersById(id);
	}
	@Override
	public List<Users> findUsersList() {
		// TODO Auto-generated method stub
		return userMapper.findUsersList();
	}
	@Override
	public Users login(Users user) {
		// TODO Auto-generated method stub
		return userMapper.login(user);
	}
	@Override
	public  Users findUserByCode(String code) {
		
		return userMapper.findUserByCode(code);
	}
	@Override
	public Users findUserByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByName(username);
	}

	

}
