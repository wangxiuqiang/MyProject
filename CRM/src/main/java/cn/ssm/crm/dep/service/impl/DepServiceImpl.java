package cn.ssm.crm.dep.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.crm.dep.dao.DepMapper;
import cn.ssm.crm.dep.pojo.Department;
import cn.ssm.crm.dep.service.DepService;
import cn.ssm.crm.exception.MyIllException;
import cn.ssm.crm.utils.StringUtils;
@Service
public class DepServiceImpl implements DepService{
    private static final Exception MyIllException = null;
	/**
     * 插入dep对象
     */
	@Autowired
	DepMapper depMapper;
	//在service中进行异常捕捉,然后抛出,  controller在进行判断
	public void insertDep(Department department) throws Exception {
		if(StringUtils.isEmpty(department.getDepName()))
		{
			throw MyIllException;
		}else {
			depMapper.insertDep(department);

		}
	}
	public List<Department> selectAllDep() {
		// TODO Auto-generated method stub
		List<Department> listDep = depMapper.selectAllDepartment();
	   return listDep;
	}
    
}
