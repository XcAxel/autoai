package Domian;

/**
 * Created by 赵奎 on 2019/6/4 18:46.
 */
public class PBResultBean {
    private String params;
    private byte[] data;
    
    public PBResultBean() {}
    public PBResultBean(String params, byte[] data) {
        this.params = params;
        this.data = data;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
