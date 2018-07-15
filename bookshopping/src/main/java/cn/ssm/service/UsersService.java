package cn.ssm.service;

import java.util.List;

import cn.ssm.model.Users;

public interface UsersService {
	/**
	 * 保存方法
	 * 
	 * @param user
	 *            保存的用户对象
	 * @return 如果返回1，保存成功，否则返回0
	 */
	public int saveUsers(Users user);

	/**
	 * 删除用户方法
	 * 
	 * @param id
	 *            要删除的用户id
	 * @return 如果返回1，删除成功，否则返回0
	 */
	public int delUsers(int id);

	/**
	 * 修改方法
	 * 
	 * @param user
	 *            要修改的对象
	 * @return 如果返回1，修改成功，否则返回0
	 */
	public int updateUsers(Users user);

	/**
	 * 根据id查询用户
	 * 
	 * @param id
	 *            要查询的用户id
	 * @return 查询的用户对象
	 */
	public Users findUsersById(int id);

	/**
	 * 查询所有的用户
	 * 
	 * @return 所有用户组成的list集合
	 */
	public List<Users> findUsersList();

	/**
	 * 用户登录方法
	 * 
	 * @param user 对象当中存放用户输入的用户名和密码
	 * @return  登录成功，返回Users对象；否则返回NULL
	 */
	public Users login(Users user);

	public Users findUserByCode(String code);
     /**
      * 
      * @param username
      * @return
      */
	public Users findUserByName(String username);
	
	

}
