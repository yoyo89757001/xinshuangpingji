package megvii.testfacepass.beans;

import java.util.List;

public class ZhiChiChengShi {


    /**
     * resultcode : 200
     * reason : successed
     * result : [{"id":"1","province":"北京","city":"北京","district":"北京"},{"id":"2","province":"北京","city":"北京","district":"海淀"},{"id":"3","province":"北京","city":"北京","district":"朝阳"},{"id":"4","province":"北京","city":"北京","district":"顺义"},{"id":"5","province":"北京","city":"北京","district":"怀柔"},{"id":"6","province":"北京","city":"北京","district":"通州"},{"id":"7","province":"北京","city":"北京","district":"昌平"},{"id":"8","province":"北京","city":"北京","district":"延庆"},{"id":"9","province":"北京","city":"北京","district":"丰台"},{"id":"10","province":"北京","city":"北京","district":"石景山"},{"id":"11","province":"北京","city":"北京","district":"大兴"},{"id":"12","province":"北京","city":"北京","district":"房山"},{"id":"13","province":"北京","city":"北京","district":"密云"},{"id":"14","province":"北京","city":"北京","district":"门头沟"},{"id":"15","province":"北京","city":"北京","district":"平谷"},{"id":"16","province":"上海","city":"上海","district":"上海"},{"id":"17","province":"上海","city":"上海","district":"闵行"},{"id":"18","province":"上海","city":"上海","district":"宝山"},{"id":"19","province":"上海","city":"上海","district":"嘉定"},{"id":"20","province":"上海","city":"上海","district":"南汇"},{"id":"21","province":"上海","city":"上海","district":"金山"},{"id":"22","province":"上海","city":"上海","district":"青浦"},{"id":"23","province":"上海","city":"上海","district":"松江"},{"id":"24","province":"上海","city":"上海","district":"奉贤"},{"id":"25","province":"上海","city":"上海","district":"崇明"},{"id":"26","province":"上海","city":"上海","district":"徐家汇"},{"id":"27","province":"上海","city":"上海","district":"浦东"}]
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * province : 北京
         * city : 北京
         * district : 北京
         */

        private String id;
        private String province;
        private String city;
        private String district;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }
}
