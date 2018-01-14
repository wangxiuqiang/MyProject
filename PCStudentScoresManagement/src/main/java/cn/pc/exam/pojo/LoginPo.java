package cn.pc.exam.pojo;

/**
 * 用来查询登录的账号和密码   包含两个字段,账号和密码 可以适用于admin
 * teacher student ,
 *
 * 精确查询  老师和学生信息,  仅使用id属性
 */
public class LoginPo {
    private String id;
    private String password;
    private String selectWhoIn;

    public String getSelectWhoIn() {
        return selectWhoIn;
    }

    public void setSelectWhoIn(String selectWhoIn) {
        this.selectWhoIn = selectWhoIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
