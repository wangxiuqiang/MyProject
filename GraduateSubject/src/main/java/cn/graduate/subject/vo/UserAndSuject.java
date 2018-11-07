package cn.graduate.subject.vo;

import cn.graduate.subject.pojo.Subject;
import cn.graduate.subject.pojo.User;

public class UserAndSuject {
    private User user;
    private Subject subject;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
