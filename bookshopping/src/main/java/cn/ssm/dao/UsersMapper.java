package cn.ssm.dao;
import java.util.List;

import cn.ssm.model.Users;

public interface UsersMapper {
	
	/**
	 * 保存方法
	 * @param user 保存用户对象
	 * @return 返回1保存成功，否则失败
	 */
	public int saveUsers(Users user);
	
	/**
	 * 删除方法
	 * @param id 要删除的用户id
	 * @return 1成功
	 */
	public int delUsers(int id);
	
	/**
	 * 修改方法
	 * @param user 修改的对象
	 * @return 1成功
	 */
	public int updateUsers(Users user);
	
	/**
	 * 根据id查询用户
	 * @param id 要查询的用户的id
	 * @return 返回要查询的用户对象
	 */
	public Users findUsersById(int id);
	
	/**
	 * 查询所有的
	 * @return
	 */
	public List<Users> findUsersList();
	
	/**
	 * 用户登录方法
	 * @param user 对象中存放用户输入的用户名和密码
	 * @return 登陆成功返回users对象，否则返回null
	 */
	public Users login(Users user);
	
	public  Users findUserByCode(String code);
	
	public Users findUserByName(String username);
	

}
