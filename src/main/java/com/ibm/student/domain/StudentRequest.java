package com.ibm.student.domain;


import java.util.List;

/**
 * Created by MengR on 15/12/28.
 */
public class StudentRequest {
    private List<Long> ids;

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }
    public String idstoString(List<Long> ids){
        String str ="";
        for(int i=0;i<ids.size();i++){
            str = str+String.valueOf(ids.get(i))+",";
        }
        return str.substring(0,str.length()-1);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
