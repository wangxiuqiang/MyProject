package cn.pc.exam.service;

import cn.pc.exam.pojo.Admin;

public interface LoginSelectService {

    public Admin queryAdminIDAndPassWd(String name ) throws Exception;
}
