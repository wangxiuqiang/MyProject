package cn.ssm.crm.dep.service;

import java.util.List;

import cn.ssm.crm.dep.pojo.Department;

public interface DepService {
	public void insertDep(Department department) throws Exception;
	// 查询所有的部门

	public List<Department> selectAllDep();
	
	public void updateDep(Department department);
	public void deleteThis(String depId);
}
