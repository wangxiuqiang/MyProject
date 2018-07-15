package cn.ssm.model;
/**
 * 实体类
 * 1.属性私有化，提供公共的get,set方法
 * 2.必须要有无参构造函数
 * 3.属性类型应该为包装类型
 * 4.类不能使用final修饰
 * 
 *               
 * final finally finalize区别
 * 
 */

public class Users {
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String email;
	private String role;
	private Integer state;
	private String activecode;
	private String updatetime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getActivecode() {
		return activecode;
	}
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", role=" + role + ", state=" + state + ", activecode=" + activecode
				+ ", updatetime=" + updatetime + "]";
	}
	

	
	

}
