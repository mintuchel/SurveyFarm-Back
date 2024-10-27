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
        regionCodeTable.put((int) Math.pow(2, 0), "서울");   // 1
        regionCodeTable.put((int) Math.pow(2, 1), "경기");   // 2
        regionCodeTable.put((int) Math.pow(2, 2), "인천");   // 4
        regionCodeTable.put((int) Math.pow(2, 3), "대전");   // 8
        regionCodeTable.put((int) Math.pow(2, 4), "세종");   // 16
        regionCodeTable.put((int) Math.pow(2, 5), "충남");   // 32
        regionCodeTable.put((int) Math.pow(2, 6), "충북");   // 64
        regionCodeTable.put((int) Math.pow(2, 7), "광주");   // 128
        regionCodeTable.put((int) Math.pow(2, 8), "전남");   // 256
        regionCodeTable.put((int) Math.pow(2, 9), "전북");   // 512
        regionCodeTable.put((int) Math.pow(2, 10), "대구");  // 1024
        regionCodeTable.put((int) Math.pow(2, 11), "경북");  // 2048
        regionCodeTable.put((int) Math.pow(2, 12), "부산");  // 4096
        regionCodeTable.put((int) Math.pow(2, 13), "울산");  // 8192
        regionCodeTable.put((int) Math.pow(2, 14), "경남");  // 16384
        regionCodeTable.put((int) Math.pow(2, 15), "강원");  // 32768
        regionCodeTable.put((int) Math.pow(2, 16), "제주");  // 65536
        regionCodeTable.put((int) Math.pow(2, 17), "외국");  // 131072
        regionCodeTable.put(262143, "전체"); // 2^0 ~ 2^17 합계
    }


    private void initializeJobCodeTable() {
        jobCodeTable.put((int) Math.pow(2, 0), "학생");      // 1
        jobCodeTable.put((int) Math.pow(2, 1), "기획");      // 2
        jobCodeTable.put((int) Math.pow(2, 2), "법조");      // 4
        jobCodeTable.put((int) Math.pow(2, 3), "인사");      // 8
        jobCodeTable.put((int) Math.pow(2, 4), "회계");      // 16
        jobCodeTable.put((int) Math.pow(2, 5), "마케팅");    // 32
        jobCodeTable.put((int) Math.pow(2, 6), "개발");      // 64
        jobCodeTable.put((int) Math.pow(2, 7), "디자인");    // 128
        jobCodeTable.put((int) Math.pow(2, 8), "물류/무역");  // 256
        jobCodeTable.put((int) Math.pow(2, 9), "배송업");    // 512
        jobCodeTable.put((int) Math.pow(2, 10), "영업");     // 1024
        jobCodeTable.put((int) Math.pow(2, 11), "고객상담");  // 2048
        jobCodeTable.put((int) Math.pow(2, 12), "금융/보험"); // 4096
        jobCodeTable.put((int) Math.pow(2, 13), "요식업");    // 8192
        jobCodeTable.put((int) Math.pow(2, 14), "서비스업");  // 16384
        jobCodeTable.put((int) Math.pow(2, 15), "설계");      // 32768
        jobCodeTable.put((int) Math.pow(2, 16), "제조업");    // 65536
        jobCodeTable.put((int) Math.pow(2, 17), "교육");      // 131072
        jobCodeTable.put((int) Math.pow(2, 18), "건축");      // 262144
        jobCodeTable.put((int) Math.pow(2, 19), "의료");      // 524288
        jobCodeTable.put((int) Math.pow(2, 20), "스포츠");    // 1048576
        jobCodeTable.put((int) Math.pow(2, 21), "공공/복지"); // 2097152
        jobCodeTable.put(8388607, "전체"); // 2^0 ~ 2^22 합계
    }

    private void initializeAgeCodeTable() {
        ageCodeTable.put((int) Math.pow(2, 0), "초등학생");   // 1
        ageCodeTable.put((int) Math.pow(2, 1), "중학생");     // 2
        ageCodeTable.put((int) Math.pow(2, 2), "고등학생");   // 4
        ageCodeTable.put((int) Math.pow(2, 3), "대학생");     // 8
        ageCodeTable.put((int) Math.pow(2, 4), "10대");      // 16
        ageCodeTable.put((int) Math.pow(2, 5), "20대");      // 32
        ageCodeTable.put((int) Math.pow(2, 6), "30대");      // 64
        ageCodeTable.put((int) Math.pow(2, 7), "40대");      // 128
        ageCodeTable.put((int) Math.pow(2, 8), "50대");      // 256
        ageCodeTable.put((int) Math.pow(2, 9), "60대");      // 512
        ageCodeTable.put((int) Math.pow(2, 10), "70대");     // 1024
    }

    private void initializeGenderCodeTable() {
        genderCodeTable.put((int)Math.pow(2,0), "남자"); // 1
        genderCodeTable.put((int)Math.pow(2,1), "여자"); // 2
        genderCodeTable.put(3, "전체"); // 3 남자 + 여자
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
