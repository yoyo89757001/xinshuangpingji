package megvii.testfacepass.beans;

/**
 * Created by Administrator on 2017/6/21.
 */

public class UserInfoBena {
    private String partyName;
    private String gender;
    private String nation;
    private String bornDay;
    private String certAddress;
    private String certNumber=null;
    private String certOrg;
    private String effDate;
    private String expDate;
    private String cardPhoto;
    private String scanPhoto;
    private String type;

    public UserInfoBena(String partyName, String gender, String nation, String bornDay, String certAddress, String certNumber, String certOrg, String effDate, String expDate, String cardPhoto, String scanPhoto, String type) {
        this.partyName = partyName;
        this.gender = gender;
        this.nation = nation;
        this.bornDay = bornDay;
        this.certAddress = certAddress;
        this.certNumber = certNumber;
        this.certOrg = certOrg;
        this.effDate = effDate;
        this.expDate = expDate;
        this.cardPhoto = cardPhoto;
        this.scanPhoto = scanPhoto;
        this.type = type;
    }

    public UserInfoBena() {
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBornDay() {
        return bornDay;
    }

    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }

    public String getCertAddress() {
        return certAddress;
    }

    public void setCertAddress(String certAddress) {
        this.certAddress = certAddress;
    }

    public String getCertNumber() {
        if (null==certNumber){
            return "";
        }
        else
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getCertOrg() {
        return certOrg;
    }

    public void setCertOrg(String certOrg) {
        this.certOrg = certOrg;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getScanPhoto() {
        return scanPhoto;
    }

    public void setScanPhoto(String scanPhoto) {
        this.scanPhoto = scanPhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
