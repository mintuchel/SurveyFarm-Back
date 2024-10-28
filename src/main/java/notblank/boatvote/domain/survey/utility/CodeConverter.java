package notblank.boatvote.domain.survey.utility;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CodeConverter {

    Map<Integer,String> regionCodeTable = new HashMap<>();
    Map<Integer, String> jobCodeTable = new HashMap<>();
    Map<Integer, String> ageCodeTable = new HashMap<>();
    Map<Integer, String> genderCodeTable = new HashMap<>();

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
        regionCodeTable.put(131071, "전체");     // 2^0 ~ 2^17 합계
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
        jobCodeTable.put(8388607, "전체");              // 2^0 ~ 2^22 합계
    }


    private void initializeAgeCodeTable() {
        ageCodeTable.put(1, "10대");   // 1
        ageCodeTable.put(2, "20대");     // 2
        ageCodeTable.put(4, "30대");   // 4
        ageCodeTable.put(8, "40대");     // 8
        ageCodeTable.put(16, "50대 이상");      // 16
        ageCodeTable.put(31, "전체");      // 32
    }

    private void initializeGenderCodeTable() {
        genderCodeTable.put(1, "남자"); // 1
        genderCodeTable.put(2, "여자"); // 2
        genderCodeTable.put(3, "전체"); // 3
    }

    //==================== STRING LIST TO CODE ====================//

    public int convertRegionListToRegionCode(List<String> selectedRegion){
        int regionCode = 0;
        for(Map.Entry<Integer,String> entry : regionCodeTable.entrySet()){
            String region = entry.getValue();
            if(selectedRegion.contains(region)){
                regionCode += entry.getKey();
            }
        }
        return regionCode;
    }

    public int convertJobListToJobCode(List<String> selectedJob){
        int jobCode = 0;
        for(Map.Entry<Integer, String> entry : jobCodeTable.entrySet()){
            String job = entry.getValue();
            if(selectedJob.contains(job)){
                jobCode += entry.getKey();
            }
        }
        return jobCode;
    }

    public int convertAgeListToAgeCode(List<String> selectedAge){
        int ageCode = 0;
        for(Map.Entry<Integer, String> entry : ageCodeTable.entrySet()){
            String age = entry.getValue();
            if(selectedAge.contains(age)){
                ageCode += entry.getKey();
            }
        }
        return ageCode;
    }

    public int convertGenderListToGenderCode(List<String> selectedGender){
        int genderCode = 0;
        for(Map.Entry<Integer, String> entry : genderCodeTable.entrySet()){
            String age = entry.getValue();
            if(selectedGender.contains(age)){
                genderCode += entry.getKey();
            }
        }
        return genderCode;
    }

    //==================== CODE TO STRING LIST ====================//

    public List<String> convertRegionCodeToList(int regionCode){
        List<String> selectedRegion = new ArrayList<>();
        for (int curRegionCode : regionCodeTable.keySet()) {
            if ((regionCode & curRegionCode) != 0) {
                selectedRegion.add(regionCodeTable.get(curRegionCode));
            }
        }
        return selectedRegion;
    }

    public List<String> convertJobCodeToList(int jobCode){
        List<String> selectedJob = new ArrayList<>();
        for(int curJobCode : jobCodeTable.keySet()){
            if((jobCode & curJobCode)!=0){
                selectedJob.add(jobCodeTable.get(curJobCode));
            }
        }
        return selectedJob;
    }

    public List<String> convertGenderCodeToList(int genderCode){
        List<String> selectedGender = new ArrayList<>();
        for(int curGenderCode : genderCodeTable.keySet()){
            if((genderCode & curGenderCode)!=0){
                selectedGender.add(genderCodeTable.get(curGenderCode));
            }
        }
        return selectedGender;
    }

    public List<String> convertAgeCodeToList(int ageCode){
        List<String> selectedAge = new ArrayList<>();
        for(int curAgeCode : ageCodeTable.keySet()){
            if((ageCode & curAgeCode)!=0){
                selectedAge.add(ageCodeTable.get(curAgeCode));
            }
        }
        return selectedAge;
    }
}
