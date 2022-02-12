package com.hncd.carcontrol.bean;

import com.luck.picture.lib.bean.CheckItemPhotoBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class CheckAllBean extends BaseBean{

   private DataBean data;
   public DataBean getData() {
      return data;
   }

   public void setData(DataBean data) {
      this.data = data;
   }

   public static class DataBean {
      private List<CheckLineBean> checkLine;
      private List<CheckItemPhotoBean> checkItemPhoto;
      private List<CheckItemBean> checkItem;

      public List<CheckLineBean> getCheckLine() {
         return checkLine;
      }

      public void setCheckLine(List<CheckLineBean> checkLine) {
         this.checkLine = checkLine;
      }

      public List<CheckItemPhotoBean> getCheckItemPhoto() {
         return checkItemPhoto;
      }

      public void setCheckItemPhoto(List<CheckItemPhotoBean> checkItemPhoto) {
         this.checkItemPhoto = checkItemPhoto;
      }

      public List<CheckItemBean> getCheckItem() {
         return checkItem;
      }

      public void setCheckItem(List<CheckItemBean> checkItem) {
         this.checkItem = checkItem;
      }

      public static class CheckLineBean {
         private String lineNo;

         public String getLineNo() {
            return lineNo;
         }

         public void setLineNo(String lineNo) {
            this.lineNo = lineNo;
         }
      }

      public static class CheckItemBean {
         private String checkItemCode;
         private String checkItemName;
         private String noright;
         private List<LocalMedia>  picLists = new ArrayList<>();
         private int type;
         private boolean ok,nogz;
         private int typeone;
         private int typetwo;

         public int getTypetwo() {
            return typetwo;
         }

         public CheckItemBean setTypetwo(int typetwo) {
            this.typetwo = typetwo;
            return this;
         }

         public int getTypeone() {
            return typeone;
         }

         public CheckItemBean setTypeone(int typeone) {
            this.typeone = typeone;
            return this;
         }

         public int getType() {
            return type;
         }

         public CheckItemBean setType(int type) {
            this.type = type;
            return this;
         }

         public boolean isOk() {
            return ok;
         }

         public CheckItemBean setOk(boolean ok) {
            this.ok = ok;
            return this;
         }

         public boolean isNogz() {
            return nogz;
         }

         public CheckItemBean setNogz(boolean nogz) {
            this.nogz = nogz;
            return this;
         }

         public String getCheckItemCode() {
            return checkItemCode;
         }

         public void setCheckItemCode(String checkItemCode) {
            this.checkItemCode = checkItemCode;
         }

         public String getCheckItemName() {
            return checkItemName;
         }

         public void setCheckItemName(String checkItemName) {
            this.checkItemName = checkItemName;
         }

         public String getNoright() {
            return noright;
         }

         public CheckItemBean setNoright(String noright) {
            this.noright = noright;
            return this;
         }

         public List<LocalMedia> getPicLists() {
            return picLists;
         }

         public CheckItemBean setPicLists(List<LocalMedia> picLists) {
            this.picLists = picLists;
            return this;
         }
      }
   }
}
