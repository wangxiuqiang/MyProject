package cn.query.service.serviceImpl;

import cn.query.dao.AdminDao;
import cn.query.pojo.Admin;
import cn.query.pojo.FileInSystem;
import cn.query.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDao adminDao;
    @Override
    public Admin selectAdmin(String name) throws Exception {
        return adminDao.selectAdmin(name);
    }

    @Override
    public int insertFile(FileInSystem fileInSystem) throws Exception {

        return adminDao.insertFile(fileInSystem);
    }

    @Override
    public int delFile(int id) throws Exception {
        return adminDao.delFile(id);
    }

    @Override
    public int updateFile(FileInSystem fileInSystem) throws Exception {
        return adminDao.updateFile( fileInSystem );
    }
}
