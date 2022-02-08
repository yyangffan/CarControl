package com.hncd.carcontrol.bean;

public class BaseBean {
   /*{"msg":"用户名或密码错误！","code":500}*/
   private String msg;
   private int code;

   public String getMsg() {
      return msg;
   }

   public BaseBean setMsg(String msg) {
      this.msg = msg;
      return this;
   }

   public int getCode() {
      return code;
   }

   public BaseBean setCode(int code) {
      this.code = code;
      return this;
   }
}
