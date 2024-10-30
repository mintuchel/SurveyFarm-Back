package notblank.boatvote.domain.utility;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CodeConverter {

    Map<Integer,String> regionCodeTable = new HashMap<>();
    Map<Integer, String> jobCodeTable = new HashMap<>();
    Map<Integer, String> ageCodeTable = new HashMap<>();
    Map<Integer, String> genderCodeTable = new HashMap<>();

    int REGION_ALL = 131071;
    int JOB_ALL = 8388607;
    int AGE_ALL = 31;
    int GENDER_ALL = 3;

    @PostConstruct
    public void initCodeConverter(){
        initializeRegionCode();
        initializeJobCodeTable();
        initializeAgeCodeTable();
        initializeGenderCodeTable();
    }

    private void initializeRegionCode(){
        regionCodeTable.put(1, "서울");          // 2^0
        regionCodeTable.put(2, "경기");          // 2^1
        regionCodeTable.put(4, "인천");          // 2^2
        regionCodeTable.put(8, "대전");          // 2^3
        regionCodeTable.put(16, "세종");         // 2^4
        regionCodeTable.put(32, "충남");         // 2^5
        regionCodeTable.put(64, "충북");         // 2^6
        regionCodeTable.put(128, "광주");        // 2^7
        regionCodeTable.put(256, "전남");        // 2^8
        regionCodeTable.put(512, "전북");        // 2^9
        regionCodeTable.put(1024, "대구");       // 2^10
        regionCodeTable.put(2048, "경북");       // 2^11
        regionCodeTable.put(4096, "부산");       // 2^12
        regionCodeTable.put(8192, "울산");       // 2^13
        regionCodeTable.put(16384, "경남");      // 2^14
        regionCodeTable.put(32768, "강원");      // 2^15
        regionCodeTable.put(65536, "제주");      // 2^16
    }

    private void initializeJobCodeTable() {
        jobCodeTable.put(1, "기획·전략");                // 2^0
        jobCodeTable.put(2, "법무·사무·총무");           // 2^1
        jobCodeTable.put(4, "인사·HR");                 // 2^2
        jobCodeTable.put(8, "회계·세무");               // 2^3
        jobCodeTable.put(16, "마케팅·광고·MD");         // 2^4
        jobCodeTable.put(32, "개발·데이터");            // 2^5
        jobCodeTable.put(64, "물류·무역");              // 2^6
        jobCodeTable.put(128, "운전·운송·배송");         // 2^7
        jobCodeTable.put(256, "영업");                  // 2^8
        jobCodeTable.put(512, "고객상담·TM");           // 2^9
        jobCodeTable.put(1024, "금융·보험");            // 2^10
        jobCodeTable.put(2048, "식·음료");              // 2^11
        jobCodeTable.put(4096, "고객서비스·리테일");     // 2^12
        jobCodeTable.put(8192, "엔지니어링·설계");       // 2^13
        jobCodeTable.put(16384, "제조·생산");           // 2^14
        jobCodeTable.put(32768, "교육");                // 2^15
        jobCodeTable.put(65536, "건축·시설");           // 2^16
        jobCodeTable.put(131072, "의료·바이오");        // 2^17
        jobCodeTable.put(262144, "미디어·문화·스포츠");   // 2^18
        jobCodeTable.put(524288, "공공·복지");          // 2^19
        jobCodeTable.put(1048576, "중학생");            // 2^20
        jobCodeTable.put(2097152, "고등학생");          // 2^21
        jobCodeTable.put(4194304, "대학생");            // 2^22
    }


    private void initializeAgeCodeTable() {
        ageCodeTable.put(1, "10대");   // 1
        ageCodeTable.put(2, "20대");     // 2
        ageCodeTable.put(4, "30대");   // 4
        ageCodeTable.put(8, "40대");     // 8
        ageCodeTable.put(16, "50대 이상");      // 16
    }

    private void initializeGenderCodeTable() {
        genderCodeTable.put(1, "남자"); // 1
        genderCodeTable.put(2, "여자"); // 2
    }

    //==================== STRING LIST TO CODE ====================//

    public int convertListToCode(List<String> selectedList, Map<Integer, String> codeTable, int allCode) {
        if (selectedList.contains("전체")) return allCode;

        int code = 0;
        for (Map.Entry<Integer, String> entry : codeTable.entrySet()) {
            if (selectedList.contains(entry.getValue())) {
                code += entry.getKey();
            }
        }
        return code;
    }

    public int convertRegionListToRegionCode(List<String> selectedRegion){
        return convertListToCode(selectedRegion, regionCodeTable, REGION_ALL);
    }
    public int convertJobListToJobCode(List<String> selectedJob) {
        return convertListToCode(selectedJob, jobCodeTable, JOB_ALL);
    }

    public int convertAgeListToAgeCode(List<String> selectedAge) {
        return convertListToCode(selectedAge, ageCodeTable, AGE_ALL);
    }

    public int convertGenderListToGenderCode(List<String> selectedGender) {
        return convertListToCode(selectedGender, genderCodeTable, GENDER_ALL);
    }

    //==================== CODE TO STRING LIST ====================//

    public List<String> convertCodeToList(int code, Map<Integer, String> codeTable, int allCode) {
        if (code == allCode) return List.of("전체");

        List<String> selectedList = new ArrayList<>();
        for (int curCode : codeTable.keySet()) {
            if ((code & curCode) != 0) {
                selectedList.add(codeTable.get(curCode));
            }
        }
        return selectedList;
    }

    public List<String> convertRegionCodeToList(int regionCode){
        return  convertCodeToList(regionCode, regionCodeTable, REGION_ALL);
    }

    public List<String> convertJobCodeToList(int jobCode) {
        return convertCodeToList(jobCode, jobCodeTable, JOB_ALL);
    }

    public List<String> convertAgeCodeToList(int ageCode) {
        return convertCodeToList(ageCode, ageCodeTable, AGE_ALL);
    }

    public List<String> convertGenderCodeToList(int genderCode) {
        return convertCodeToList(genderCode, genderCodeTable, GENDER_ALL);
    }
}
