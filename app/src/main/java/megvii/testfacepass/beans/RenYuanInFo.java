package megvii.testfacepass.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Administrator on 2018/4/9.
 */

@Entity
public class RenYuanInFo {
    /**
     * accountId : 10000065
     * assemblyId : Wo10001
     * channel : 0
     * come_from : 气管炎
     * companyName : 10000025
     * country : 中国
     * createTime : 1527496888000
     * department : 领导
     * dtoResult : 0
     * gender : 0
     * id : 10403782
     * identity : 身份
     * jobStatus : 1
     * location : 11
     * meetingId : 0
     * modifyTime : 1527560211000
     * name : 张三
     * namePinyin : ZS
     * num : 0
     * pageNum : 0
     * pageSize : 0
     * phone : 1234
     * photo_ids : 20180528/1527497456044_657.jpg
     * province : 广东
     * remark :
     * sid : 0
     * sourceMeeting : Wo10001a
     * sourceQuestionJson :
     * status : 1
     * subject_type : 0
     * title : 普通员工
     */

    @Id(assignable = true)
    private Long id;
    private int accountId;
    private String assemblyId;
    private int channel;
    private String come_from;
    private String companyName;
    private String country;
    private long createTime;
    private String department;
    private int dtoResult;
    private int gender;
    private String identity;
    private int jobStatus;
    private String location;
    private int meetingId;
    private long modifyTime;
    private String name;
    private String namePinyin;
    private int num;
    private int pageNum;
    private int pageSize;
    private String phone;
    private String photo_ids;
    private String province;
    private String remark;
    private int sid;
    private String sourceMeeting;
    private String sourceQuestionJson;
    private int status;
    private int subject_type;
    private String title;

    public RenYuanInFo() {
    }
    public int getAccountId() {
        return this.accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getAssemblyId() {
        return this.assemblyId;
    }
    public void setAssemblyId(String assemblyId) {
        this.assemblyId = assemblyId;
    }
    public int getChannel() {
        return this.channel;
    }
    public void setChannel(int channel) {
        this.channel = channel;
    }
    public String getCome_from() {
        return this.come_from;
    }
    public void setCome_from(String come_from) {
        this.come_from = come_from;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public int getDtoResult() {
        return this.dtoResult;
    }
    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIdentity() {
        return this.identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public int getJobStatus() {
        return this.jobStatus;
    }
    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getMeetingId() {
        return this.meetingId;
    }
    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }
    public long getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNamePinyin() {
        return this.namePinyin;
    }
    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getPageNum() {
        return this.pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhoto_ids() {
        return this.photo_ids;
    }
    public void setPhoto_ids(String photo_ids) {
        this.photo_ids = photo_ids;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getSid() {
        return this.sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public String getSourceMeeting() {
        return this.sourceMeeting;
    }
    public void setSourceMeeting(String sourceMeeting) {
        this.sourceMeeting = sourceMeeting;
    }
    public String getSourceQuestionJson() {
        return this.sourceQuestionJson;
    }
    public void setSourceQuestionJson(String sourceQuestionJson) {
        this.sourceQuestionJson = sourceQuestionJson;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getSubject_type() {
        return this.subject_type;
    }
    public void setSubject_type(int subject_type) {
        this.subject_type = subject_type;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RenYuanInFo{" +
                "accountId=" + accountId +
                ", assemblyId='" + assemblyId + '\'' +
                ", channel=" + channel +
                ", come_from='" + come_from + '\'' +
                ", companyName='" + companyName + '\'' +
                ", country='" + country + '\'' +
                ", createTime=" + createTime +
                ", department='" + department + '\'' +
                ", dtoResult=" + dtoResult +
                ", gender=" + gender +
                ", id=" + id +
                ", identity='" + identity + '\'' +
                ", jobStatus=" + jobStatus +
                ", location='" + location + '\'' +
                ", meetingId=" + meetingId +
                ", modifyTime=" + modifyTime +
                ", name='" + name + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", num=" + num +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", phone='" + phone + '\'' +
                ", photo_ids='" + photo_ids + '\'' +
                ", province='" + province + '\'' +
                ", remark='" + remark + '\'' +
                ", sid=" + sid +
                ", sourceMeeting='" + sourceMeeting + '\'' +
                ", sourceQuestionJson='" + sourceQuestionJson + '\'' +
                ", status=" + status +
                ", subject_type=" + subject_type +
                ", title='" + title + '\'' +
                '}';
    }
}
