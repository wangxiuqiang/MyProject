package cn.fm.user.service.serviceImpl;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.user.dao.UserCompanyFileMapper;
import cn.fm.user.service.UserCompanyFileService;
import cn.fm.user.service.UserService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.CompanyFileExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class UserCompanyFileServiceImpl implements UserCompanyFileService {

    @Autowired
    UserCompanyFileMapper userCompanyFileMapper;

    @Autowired
    UserService userService;
    /**
     * 录入信息
     * @param companyFile
     * @return
     * @throws Exception
     */
    @Override
    public int insertCompanyFile(CompanyFile companyFile) throws Exception {



        /**
         * 设置变量,让变量来完成字符串的拼接
         * 记录一开始的分类id
         */
        StringBuffer address = new StringBuffer();
        StringBuffer classifyname = new StringBuffer();
        int id = companyFile.getCfclassifyid();
        Classify classify = new Classify();
        while( id != 0) {
            classify = selectClassify(id);
            id = classify.getCyfatherid();
            if(classify.getCyfatherid() != 0) {
                address.append(classify.getCyaddress() + "-");
                classifyname.append(classify.getCyname() + "-");

            }else {
                address.append(classify.getCyaddress());
                classifyname.append(classify.getCyname());

            }
        }

        /**
         * 因为取分类名是从后取的,,所以应该把address和classifyname ,反过来
         */
        //临时变量
        String addressTime =  address.toString();
        String[] splits = addressTime.split("-");
        address.delete(0,address.length());
        for( int i = splits.length - 1; i >= 0; i--)  {
            if(i != 0) {
                address.append(splits[i] + "-");
            }else {
                address.append(splits[i]);
            }
        }

        String classifynameTime =  classifyname.toString();  //临时变量
        String[] splits2 = classifynameTime.split("-");
        classifyname.delete(0,classifyname.length());
        for( int i = splits2.length - 1; i >= 0; i--)  {
            if(i != 0) {
                classifyname.append(splits2[i] + "-");
            }else {
                classifyname.append(splits2[i]);
            }
        }

        companyFile.setCfaddress(address.toString());
        companyFile.setCfclassifyname(classifyname.toString());
        return userCompanyFileMapper.insertCompanyFile(companyFile);
    }

    /**
     * 查找相应的分类  ,给录入使用  ,查询也使用
     * @param classifyid
     * @return
     * @throws Exception
     */
    @Override
    public Classify selectClassify(int classifyid) throws Exception {
        return userCompanyFileMapper.selectClassify(classifyid);
    }


    /**
     * 总的查找,不根据id查询  ,这个是将查出来的list  放到一个类里,,后面用分页的时候改掉
     * @param companyFile
     * @return
     * @throws Exception
     */
    @Override
    public List<CompanyFile> findTypeFiles(CompanyFile companyFile , String endtime ) throws Exception {
        //先找到文件信息,然后根据文件的id找到文件的借阅信息,并将借阅信息写入
        List<CompanyFile> companyFiles = new ArrayList<>();
        //用来做返回数据
//        List<CompanyFileExtends> companyFileExtends = new ArrayList<>();

        if(companyFile.getCfname() != null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

            companyFiles = selectCompanyFileByName(companyFile.getCfname());
        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() != 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

            companyFiles = selectCompanyFileByClassifyId(companyFile.getCfclassifyid());

        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() != null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

            companyFiles = selectCompanyFileByLanguage(companyFile.getCflanguage());


        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() != null && endtime != null && companyFile.getCffontid() == null) {

            companyFiles = selectCompanyFileByDateTime(companyFile.getCfdate() , endtime);


        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() != null){

            companyFiles = selectCompanyFileByFontid(companyFile.getCffontid());

        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null){
           return  null;
        }else {
            companyFiles = selectCompanyFileByTwoOrMore(companyFile , endtime );
        }
        /**
         * 将 指定领取人和 实际领取人的用户名和用户编号放到文件类中
         */
        companyFiles.forEach( n -> {
            try {
                //根据文件id,获取到该文件的被借阅历史.
                List<BorrowCFExtends> borrowCFExtends = userService.selectBorrowcfInfoByFileid( n.getCfid() );
                for (BorrowCFExtends borrowCFExtend : borrowCFExtends) {
                    if( borrowCFExtend.getUser() != null ) {
                        n.setUid( borrowCFExtend.getUser().getUid() );
                        n.setUname( borrowCFExtend.getUser().getUname() );
                    }
                    if( borrowCFExtend.getUserSecond() != null ) {
                        n.setSecondUid( borrowCFExtend.getUserSecond().getUid() );
                        n.setSecondName( borrowCFExtend.getUserSecond().getUname() );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return  companyFiles;

    }

    /**
     * 多项组合查询
     */
    public List<CompanyFile> selectCompanyFileByTwoOrMore(CompanyFile companyFile , String endtime ) throws Exception{
        return userCompanyFileMapper.selectCompanyFileByTwoOrMore( companyFile , endtime );
    }

    /**
     * 根据id找文件
     * @param cfid
     * @return
     * @throws Exception
     */
    @Override
    public CompanyFile selectCompanyFileById(int cfid) throws Exception{
        return userCompanyFileMapper.selectCompanyFileById(cfid);
    }

    /**
     * 用来返回文件的所有的父分类的id和自己的id
     * @param cfs
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectAllClassifyId(List<CompanyFile> cfs) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        cfs.forEach(n -> {
            list.clear();
            int fatherid = 1;
            Classify classify = new Classify();
            try {
                classify = selectClassify(n.getCfclassifyid());
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(classify.getCyid()) ;
            list.add(classify.getCyfatherid());

            fatherid = classify.getCyfatherid();
            while (fatherid != 0){
                try {
                    classify = selectClassify(fatherid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(classify != null) {
                    fatherid = classify.getCyfatherid();
                }
                if(fatherid != 0)
                {
                    list.add(fatherid);
                }
            }
            n.setClassifies(list);

        });
        return cfs;
    }

    @Override
    public List<CompanyFile> selectCompanyFileByName(String cfname) throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectCompanyFileByName(cfname);

        return selectAllClassifyId(cfs);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByLanguage(String language) throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectCompanyFileByLanguage(language);
        return selectAllClassifyId(cfs);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByClassifyId(int classifyid) throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectCompanyFileByClassifyId(classifyid);
        return selectAllClassifyId(cfs);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByDateTime(String date , String endtime ) throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectCompanyFileByDateTime(date , endtime );
        return selectAllClassifyId(cfs);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByFontid(String cffontid) throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectCompanyFileByFontid(cffontid);
        return selectAllClassifyId(cfs);
    }

    /**
     * 查找全部的文件
     * @return
     * @throws Exception
     */
    @Override
    public List<CompanyFile> selectAllCompanyFile() throws Exception {
        List<CompanyFile> cfs = userCompanyFileMapper.selectAllCompanyFile();
        return selectAllClassifyId(cfs);
    }

    /**
     * 更改数据
     */
    public int updateCompanyFileById(CompanyFile companyFile) throws Exception{
        /**
         * 设置变量,让变量来完成字符串的拼接
         * 记录一开始的分类id
         */
      if(companyFile.getCfclassifyid() != 0) {
          StringBuffer address = new StringBuffer();
          StringBuffer classifyname = new StringBuffer();
          int id = companyFile.getCfclassifyid();
          Classify classify = new Classify();
          while (id != 0) {
              classify = selectClassify(id);
              id = classify.getCyfatherid();
              if (classify.getCyfatherid() != 0) {
                  address.append(classify.getCyaddress() + "-");
                  classifyname.append(classify.getCyname() + "-");

              } else {
                  address.append(classify.getCyaddress());
                  classifyname.append(classify.getCyname());

              }
          }

          /**
           * 因为取分类名是从后取的,,所以应该把address和classifyname ,反过来
           */
          //临时变量
          String addressTime = address.toString();
          String[] splits = addressTime.split("-");
          address.delete(0, address.length());
          for (int i = splits.length - 1; i >= 0; i--) {
              if (i != 0) {
                  address.append(splits[i] + "-");
              } else {
                  address.append(splits[i]);
              }
          }

          String classifynameTime = classifyname.toString();  //临时变量
          String[] splits2 = classifynameTime.split("-");
          classifyname.delete(0, classifyname.length());
          for (int i = splits2.length - 1; i >= 0; i--) {
              if (i != 0) {
                  classifyname.append(splits2[i] + "-");
              } else {
                  classifyname.append(splits2[i]);
              }
          }

          companyFile.setCfaccept(address.toString());
          companyFile.setCfclassifyname(classifyname.toString());
      }
        return userCompanyFileMapper.updateCompanyFileById(companyFile);
    }

    /**
     *根据id删除单行数据 ,就是将标记位进行修改
     */
    public int deleteCompanyFileById(int[] cfid) throws Exception{

        for(int i = 0; i < cfid.length; i++) {
            List<Borrow> list = new ArrayList<>();
            list = userCompanyFileMapper.selectCFhasBorrowInfo(cfid[i]);
            //如果不为空 就删除 借阅信息
            if(list != null && list.size() > 0) {
                deleteCompanyFileBorrowInfo(cfid[i]);
            }
        }

        return userCompanyFileMapper.deleteCompanyFileById(cfid);
    }
    /**
     * 同时删除这个文件留下的借阅信息 , 就是将标记为进行修改
     */
    @Override
    public int deleteCompanyFileBorrowInfo(int cfid) throws Exception{
        return userCompanyFileMapper.deleteCompanyFileBorrowInfo(cfid);
    }
    /**
     * 查看被删除的发文
     */
    @Override
    public List<CompanyFile> selectTheCFileIsDel() throws Exception {
        return userCompanyFileMapper.selectTheCFileIsDel();
    }


    /**
     * 销毁一个文件
     * @param cfid
     * @return
     * @throws Exception
     */
    public int delCompanyFileDestroy(  int cfid) throws Exception {
        return userCompanyFileMapper.delCompanyFileDestroy( cfid , DateToStringUtils.dataTostring() );
    }

    /**
     * 清退一个文件 ,更新清退日期
     * @param cfid
     * @return
     * @throws Exception
     */
    @Override
    public int delCompanyFileBack(int cfid  ) throws Exception{
        return userCompanyFileMapper.delCompanyFileBack( cfid  , DateToStringUtils.dataTostring() );
    }
}
