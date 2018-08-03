package cn.fm.user.service.serviceImpl;

import cn.fm.pojo.Classify;
import cn.fm.pojo.GetFile;
import cn.fm.user.dao.UserGetFileMapper;
import cn.fm.user.service.UserGetFileService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.GetFileExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserGetFileServiceImpl implements UserGetFileService{

    @Autowired
    UserGetFileMapper userGetFileMapper;



    /**
     *查找分类信息
     * @param classifyid
     * @return
     * @throws Exception
     */
    @Override
    public Classify selectClassify(int classifyid) throws Exception {
        return userGetFileMapper.selectClassify(classifyid);
    }

    /**
     * 录入收文信息
     * @param getFile
     * @return
     * @throws Exception
     */
    @Override
    public int insertGetFile(GetFile getFile) throws Exception {
        /**
         * 设置录入时间
         */
        getFile.setGfdatetime(DateToStringUtils.dataTostring());
        /**
         * 设置变量,让变量来完成字符串的拼接
         * 记录一开始的分类id
         */
        StringBuffer address = new StringBuffer();
        StringBuffer classifyname = new StringBuffer();
        int id = getFile.getGfclassifyid();
        while( id != 0) {
            Classify classify = selectClassify(id);
            /**
             * 最上层的分类的父分类id为0
             */
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
        classifyname.delete(0,address.length());
        for( int i = splits2.length - 1; i >= 0; i--)  {
            if(i != 0) {
                classifyname.append(splits2[i] + "-");
            }else {
                classifyname.append(splits2[i]);
            }
        }

        getFile.setGfaddress(address.toString());
        getFile.setGfclassifyname(classifyname.toString());


        return userGetFileMapper.insertGetFile(getFile);
    }

    /**
     * 根据单项 或多项查收文信息
     */


    /**
     * 总的调配 chaao  ,如果 传过来的值是 json'使用转化后 int的是不是默认为0 ,  String的默认是不是null
     * @param getFile
     * @return
     * @throws Exception
     */
    @Override
    public GetFileExtends findTypeFiles(GetFile getFile) throws Exception {
         GetFileExtends getFileExtends = new GetFileExtends();

        if(getFile.getGfname() != null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {

              getFileExtends.setGetFiles(selectGetFileByName(getFile.getGfname()));


        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() != 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {

            getFileExtends.setGetFiles(selectGetFileByClassifyId(getFile.getGfclassifyid()));


        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() != null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {

            getFileExtends.setGetFiles(selectGetFileByCompany(getFile.getGfcompany()));

        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() != null && getFile.getGfnumber() == 0) {

            getFileExtends.setGetFiles(selectGetFileByDateTime(getFile.getGfdatetime()));
        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() != 0){
            getFileExtends.setGetFiles(selectGetFileByNumber(getFile.getGfnumber()));



        }else {
            getFileExtends = null;
        }

        if(getFileExtends != null ) {
            getFileExtends.setState(StatusUtils.SUCCESS_FIND);
        }else {
            getFileExtends.setState(StatusUtils.FAILURE_FIND);
        }

        getFileExtends.setCount(getFileExtends.getGetFiles().size());
        return getFileExtends;



    }

    @Override
    public List<GetFile> selectGetFileByName(String gfname) throws Exception {
        return userGetFileMapper.selectGetFileByName(gfname);
    }

    @Override
    public List<GetFile> selectGetFileByCompany(String gfcompany) throws Exception {
        return userGetFileMapper.selectGetFileByCompany(gfcompany);
    }

    @Override
    public List<GetFile> selectGetFileByClassifyId(int classifyid) throws Exception {
        return userGetFileMapper.selectGetFileByClassifyId(classifyid);
    }

    @Override
    public List<GetFile> selectGetFileByDateTime(String datetime) throws Exception {
        return userGetFileMapper.selectGetFileByDateTime(datetime);
    }

    @Override
    public List<GetFile> selectGetFileByNumber(int num) throws Exception {
        return userGetFileMapper.selectGetFileByNumber(num);
    }


    /**
     * 查找全部的收文信息
     * @return
     * @throws Exception
     */
    @Override
    public List<GetFile> selectAllGetFile() throws Exception {
        return userGetFileMapper.selectAllGetFile();
    }


    /**
     * 根据id更新 文件
     * @param file
     * @return
     * @throws Exception
     */
    public int updateGetFileById(GetFile file) throws Exception{
        return userGetFileMapper.updateGetFileById(file);
    }
    /**
     * 根据id删除相关的信息,单条删除
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteGetFileById(int id) throws Exception{
        return userGetFileMapper.deleteGetFileById(id);
    }

}
