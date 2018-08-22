package cn.fm.user.service.serviceImpl;

import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.user.dao.UserCompanyFileMapper;
import cn.fm.user.service.UserCompanyFileService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.CompanyFileExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserCompanyFileServiceImpl implements UserCompanyFileService {

    @Autowired
    UserCompanyFileMapper userCompanyFileMapper;


    /**
     * 录入信息
     * @param companyFile
     * @return
     * @throws Exception
     */
    @Override
    public int insertCompanyFile(CompanyFile companyFile) throws Exception {

        /**
         * 设置录入时间
         */
        if(companyFile.getCfdate() == null){
            companyFile.setCfdate(DateToStringUtils.dataTostring());
        }

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
            id = classify.getCyfather();
            if(classify.getCyfather() != 0) {
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

        companyFile.setCfaccept(address.toString());
        companyFile.setCfclassifyname(classifyname.toString());



        return userCompanyFileMapper.insertCompanyFile(companyFile);
    }

    /**
     * 查找相应的分类  ,给录入使用
     * @param classifyid
     * @return
     * @throws Exception
     */
    @Override
    public Classify selectClassify(int classifyid) throws Exception {
        return userCompanyFileMapper.selectClassify(classifyid);
    }


    /**
     * 总的查找  ,这个是将查出来的list  放到一个类里,,后面用分页的时候改掉
     * @param companyFile
     * @return
     * @throws Exception
     */
    @Override
    public List<CompanyFile> findTypeFiles(CompanyFile companyFile) throws Exception {

        CompanyFileExtends cfe = new CompanyFileExtends();
        if(companyFile.getCfname() != null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

            return selectCompanyFileByName(companyFile.getCfname());


        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() != 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

            return selectCompanyFileByClassifyId(companyFile.getCfclassifyid());

        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() != null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null) {

          return   selectCompanyFileByLanguage(companyFile.getCflanguage());


        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() != null && companyFile.getCffontid() == null) {

            return selectCompanyFileByDateTime(companyFile.getCfdate());


        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null){

           return selectCompanyFileByFontid(companyFile.getCffontid());

        }else if(companyFile.getCfname() == null && companyFile.getCfclassifyid() == 0 && companyFile.getCflanguage() == null
                && companyFile.getCfdate() == null && companyFile.getCffontid() == null){
           return  null;
        }else {
            return selectCompanyFileByTwoOrMore(companyFile);
        }

    }

    /**
     * 多项组合查询
     */
    public List<CompanyFile> selectCompanyFileByTwoOrMore(CompanyFile companyFile) throws Exception{
        return userCompanyFileMapper.selectCompanyFileByTwoOrMore(companyFile);
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

    @Override
    public List<CompanyFile> selectCompanyFileByName(String cfname) throws Exception {
        return userCompanyFileMapper.selectCompanyFileByName(cfname);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByLanguage(String language) throws Exception {
        return userCompanyFileMapper.selectCompanyFileByLanguage(language);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByClassifyId(int classifyid) throws Exception {
        return userCompanyFileMapper.selectCompanyFileByClassifyId(classifyid);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByDateTime(String date) throws Exception {
        return userCompanyFileMapper.selectCompanyFileByDateTime(date);
    }

    @Override
    public List<CompanyFile> selectCompanyFileByFontid(String cffontid) throws Exception {
        return userCompanyFileMapper.selectCompanyFileByFontid(cffontid);
    }

    /**
     * 查找全部的文件
     * @return
     * @throws Exception
     */
    @Override
    public List<CompanyFile> selectAllCompanyFile() throws Exception {
        return userCompanyFileMapper.selectAllCompanyFile();
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
              id = classify.getCyfather();
              if (classify.getCyfather() != 0) {
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
     *根据id删除单行数据
     */
    public int deleteCompanyFileById(int[] cfid) throws Exception{
        return userCompanyFileMapper.deleteCompanyFileById(cfid);
    }
}
