package cn.fm.user.service.serviceImpl;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.user.dao.UserGetFileMapper;
import cn.fm.user.service.UserGetFileService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.GetFileExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
         * 设置变量,让变量来完成字符串的拼接
         * 记录一开始的分类id
         */
        StringBuffer address = new StringBuffer();
        StringBuffer classifyname = new StringBuffer();
        int id = getFile.getGfclassifyid();
        System.out.println(id);
        while( id != 0) {
            Classify classify = this.selectClassify(id);
            /**
             * 最上层的分类的父分类id为0
             */
            id = classify.getCyfatherid();
            if(classify.getCyfatherid() != 0) {
                address.append(classify.getCyaddress() + "-");
                classifyname.append(classify.getCyname() + "-");

            }else {
                address.append(classify.getCyaddress());
                classifyname.append(classify.getCyname());

            }
        }

        System.out.println(address);
        System.out.println(classifyname);
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

        getFile.setGfaddress(address.toString());
        getFile.setGfclassifyname(classifyname.toString());

//        System.out.println(getFile.getGfaddress());
//        System.out.println(getFile.getGfclassifyname());

        return userGetFileMapper.insertGetFile(getFile);
    }
    /**
     * 用来返回文件的所有的父分类的id和自己的id
     * @param cgs
     * @return
     * @throws Exception
     */
    public List<GetFile> selectAllClassifyId(List<GetFile> cgs) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        cgs.forEach(n -> {
            list.clear();
            int fatherid = 1;
            Classify classify = new Classify();
            try {
                classify = selectClassify(n.getGfclassifyid());
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
        return cgs;
    }

    /**
     * 根据单项 或多项查收文信息
     */


    /**
     * 总的调配   ,如果 传过来的值是 json'使用转化后 int的是不是默认为0 ,  String的默认是不是null
     * @param getFile
     * @return
     * @throws Exception
     */
    @Override
    public List<GetFile> findTypeFiles(GetFile getFile , String endtime ) throws Exception {

        if(getFile.getGfname() != null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {
            return   selectGetFileByName(getFile.getGfname());


        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() != 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {
            return selectGetFileByClassifyId(getFile.getGfclassifyid());


        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() != null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0) {
            return selectGetFileByCompany(getFile.getGfcompany());

        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() != null && endtime !=null && getFile.getGfnumber() == 0) {

            return selectGetFileByDateTime(getFile.getGfdatetime() ,endtime);
        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() != 0){
           return  selectGetFileByNumber(getFile.getGfnumber());

        }else if(getFile.getGfname() == null && getFile.getGfclassifyid() == 0 && getFile.getGfcompany() == null
                && getFile.getGfdatetime() == null && getFile.getGfnumber() == 0){
             return  null;
        }else {
            //多项查询
          return selectGetFileByTwoAndMore(getFile ,endtime);
        }
    }

    @Override
    public List<GetFile> selectGetFileByName(String gfname) throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectGetFileByName(gfname));
    }

    @Override
    public List<GetFile> selectGetFileByCompany(String gfcompany) throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectGetFileByCompany(gfcompany));
    }

    @Override
    public List<GetFile> selectGetFileByClassifyId(int classifyid) throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectGetFileByClassifyId(classifyid));
    }

    @Override
    public List<GetFile> selectGetFileByDateTime(String datetime, String endtime ) throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectGetFileByDateTime(datetime , endtime ));
    }

    @Override
    public List<GetFile> selectGetFileByNumber(int num) throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectGetFileByNumber(num));
    }
    /**
     * 根据id找文件
     * @param gfid
     * @return
     * @throws Exception
     */
    @Override
    public GetFile selectGetFileById(int gfid) throws Exception{
        return userGetFileMapper.selectGetFileById(gfid);
    }

    /**
     * 查找全部的收文信息
     * @return
     * @throws Exception
     */
    @Override
    public List<GetFile> selectAllGetFile() throws Exception {
        return selectAllClassifyId(userGetFileMapper.selectAllGetFile());
    }
    /**
     * 通过两个或以上进行查询
     * @param getFile
     * @return
     */
    public List<GetFile> selectGetFileByTwoAndMore( GetFile getFile , String endtime) throws Exception{
        return userGetFileMapper.selectGetFileByTwoAndMore(getFile,endtime);
    }

    /**
     * 根据id更新 文件
     * @param file
     * @return
     * @throws Exception
     */
    public int updateGetFileById(GetFile file) throws Exception{
        /**
         * 设置变量,让变量来完成字符串的拼接
         * 记录一开始的分类id
         */
       if(file.getGfclassifyid() != 0) {
           StringBuffer address = new StringBuffer();
           StringBuffer classifyname = new StringBuffer();
           int id = file.getGfclassifyid();
//        System.out.println(id);
           while (id != 0) {
               Classify classify = this.selectClassify(id);
               /**
                * 最上层的分类的父分类id为0
                */
               id = classify.getCyfatherid();
               if (classify.getCyfatherid() != 0) {
                   address.append(classify.getCyaddress() + "-");
                   classifyname.append(classify.getCyname() + "-");

               } else {
                   address.append(classify.getCyaddress());
                   classifyname.append(classify.getCyname());

               }
           }

//        System.out.println(address);
//        System.out.println(classifyname);
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

           file.setGfaddress(address.toString());
           file.setGfclassifyname(classifyname.toString());
       }
        return userGetFileMapper.updateGetFileById(file);
    }
    /**
     * 根据id删除相关的信息,单条删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteGetFileById(int[] id) throws Exception{
        for(int i = 0; i < id.length; i++) {
            List<Borrow> list = userGetFileMapper.selectGFhasBorrowInfo(id[i]);
            //如果不为空 就删除 借阅信息
            if(list != null && list.size() > 0) {
                deleteGetFileBorrowInfo(id[i]);
            }
        }
        return userGetFileMapper.deleteGetFileById(id);
    }

    /**
     * 同时删除借阅信息表里的信息
     */
    @Override
    public int deleteGetFileBorrowInfo(int gfid) throws Exception{
        return userGetFileMapper.deleteGetFileBorrowInfo(gfid);
    }

}
