package cn.graduate.subject.vo;

import com.alibaba.fastjson.JSON;

public class test {
    public static void main(String args[]) throws Exception {
        String a = "uaccount=123&upwd=123";
        Test t = new Test();
        t = (Test) JSON.toJSON(a);
        String b = (String)JSON.toJSON(a);
        System.out.println(b);
        System.out.println( t.getUaccount() );
    }
    static class Test {
        public String uaccount;
        public String upwd;

        public String getUaccount() {
            return uaccount;
        }

        public void setUaccount(String uaccount) {
            this.uaccount = uaccount;
        }

        public String getUpwd() {
            return upwd;
        }

        public void setUpwd(String upwd) {
            this.upwd = upwd;
        }
    }
}
