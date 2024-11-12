package notblank.surveyfarm.domain.survey.constant;

public enum RegionCode {
    서울("SEOUL",1),
    경기("KYEONGKI", 2);

    private String regionName;
    private int regionCode;

    RegionCode(String regionName, int regionCode){
        this.regionName = regionName;
        this.regionCode = regionCode;
    }
}
