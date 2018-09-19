package megvii.testfacepass.beans;

/**
 * Created by Administrator on 2018/5/30.
 */

public class MOBan {


    /**
     * content : {"screenId":"10100097","sid":0,"createTime":1527647796122,"bottemImageUrl":"\"http://192.168.2.154:8080/gapp/images/welcome/business/template.jpg\"","welcomeSpeak":"发送到发大水","subType":"观众","pageSize":0,"popupImageUrl":"\"http://192.168.2.154:8080/gapp/images/welcome/business/template1_popup2.jpg\"","dtoResult":0,"companyId":0,"modifyTime":1527647796122,"pageNum":0}
     * title : 迎宾设置
     */

    private ContentBean content;
    private String title;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ContentBean {
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

        public int getPhoto_index() {
            return photo_index;
        }

        public void setPhoto_index(int photo_index) {
            this.photo_index = photo_index;
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
    }
}
