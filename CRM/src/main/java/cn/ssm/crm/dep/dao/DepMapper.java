package cn.ssm.crm.dep.dao;

import java.util.List;

import cn.ssm.crm.dep.pojo.Department;

public interface DepMapper {
    public void insertDep(Department department) ;
    public List<Department> selectAllDepartment();
    
}
