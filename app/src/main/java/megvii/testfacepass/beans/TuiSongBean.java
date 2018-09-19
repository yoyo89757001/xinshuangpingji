package megvii.testfacepass.beans;

/**
 * Created by Administrator on 2018/4/8.
 */

public class TuiSongBean {


    /**
     * id : 1019047083040571392
     * title : 绑定激活
     * url : /app/getMachine
     */

    private String id;
    private String title;
    private String url;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
