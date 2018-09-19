package megvii.testfacepass.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Administrator on 2018/5/30.
 */
@Entity
public class BenDiMBbean {


    /**
     * screenId : 10100097
     * sid : 0
     * createTime : 1527647796122
     * bottemImageUrl : "http://192.168.2.154:8080/gapp/images/welcome/business/template.jpg"
     * welcomeSpeak : 发送到发大水
     * subType : 观众
     * pageSize : 0
     * popupImageUrl : "http://192.168.2.154:8080/gapp/images/welcome/business/template1_popup2.jpg"
     * dtoResult : 0
     * companyId : 0
     * modifyTime : 1527647796122
     * pageNum : 0
     */
    @Id(assignable = true)
    private Long id;
    private String screenId;
    private int sid;
    private long createTime;
    private String bottemImageUrl;
    private String welcomeSpeak;
    private String subType;
    private int pageSize;
    private String popupImageUrl;
    private int dtoResult;
    private int companyId;
    private long modifyTime;
    private int pageNum;
    private int photo_index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getBottemImageUrl() {
        return bottemImageUrl;
    }

    public void setBottemImageUrl(String bottemImageUrl) {
        this.bottemImageUrl = bottemImageUrl;
    }

    public String getWelcomeSpeak() {
        return welcomeSpeak;
    }

    public void setWelcomeSpeak(String welcomeSpeak) {
        this.welcomeSpeak = welcomeSpeak;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPopupImageUrl() {
        return popupImageUrl;
    }

    public void setPopupImageUrl(String popupImageUrl) {
        this.popupImageUrl = popupImageUrl;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPhoto_index() {
        return photo_index;
    }

    public void setPhoto_index(int photo_index) {
        this.photo_index = photo_index;
    }
}
