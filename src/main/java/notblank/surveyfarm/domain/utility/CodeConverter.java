package notblank.surveyfarm.domain.utility;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CodeConverter {

    private final int ALL_CODE = 0;
    private final String ALL_STRING = "전체";

    private final List<String> regionList = new ArrayList<>(List.of(
            "서울", "경기", "인천", "대전", "세종",
            "충남", "충북", "광주", "전남", "전북",
            "대구", "경북", "부산", "울산", "경남",
            "강원", "제주"
    ));

    private final List<String> jobList = new ArrayList<>(List.of(
            "기획·전략", "법무·사무·총무", "인사·HR",
            "회계·세무", "마케팅·광고·MD", "개발·데이터",
            "물류·무역", "운전·운송·배송", "영업",
            "고객상담·TM", "금융·보험", "식·음료",
            "고객서비스·리테일", "엔지니어링·설계", "제조·생산",
            "교육", "건축·시설", "의료·바이오",
            "미디어·문화·스포츠", "공공·복지", "중학생",
            "고등학생", "대학생"
    ));

    private final List<String> ageList = new ArrayList<>(List.of(
            "10대", "20대", "30대", "40대", "50대 이상"
    ));

    private final List<String> genderList = new ArrayList<>(List.of(
            "남자", "여자"
    ));

    Map<Integer,String> regionCodeToListTable = new HashMap<>();
    Map<Integer, String> jobCodeToListTable = new HashMap<>();
    Map<Integer, String> ageCodeToListTable = new HashMap<>();
    Map<Integer, String> genderCodeToListTable = new HashMap<>();

    Map<String,Integer> regionListToCodeTable = new HashMap<>();
    Map<String, Integer> jobListToCodeTable = new HashMap<>();
    Map<String, Integer> ageListToCodeTable = new HashMap<>();
    Map<String, Integer> genderListToCodeTable = new HashMap<>();

    @PostConstruct
    public void initCodeConverter(){
        initializeTables(regionList, regionCodeToListTable, regionListToCodeTable);
        initializeTables(jobList, jobCodeToListTable, jobListToCodeTable);
        initializeTables(ageList, ageCodeToListTable, ageListToCodeTable);
        initializeTables(genderList, genderCodeToListTable, genderListToCodeTable);
    }

    //==================== INITIALIZE TABLES ====================//

    private void initializeTables(List<String> list, Map<Integer,String> codeToListTable, Map<String, Integer> listToCodeTable){
        int val = 1;
        for (String curString : list) {
            codeToListTable.put(val, curString);
            listToCodeTable.put(curString, val);
            val *= 2;
        }
        //codeToListTable.put(ALL_CODE, ALL_STRING);
        listToCodeTable.put(ALL_STRING, ALL_CODE);
    }

    //==================== STRING LIST TO CODE ====================//

    private int convertListToCode(List<String> selectedList, Map<String, Integer> listToCodeTable) {
        int code = 0;
        for(String curString : selectedList){
            code += listToCodeTable.get(curString);
        }
        return code;
    }

    public int convertRegionListToRegionCode(List<String> selectedRegion){
        return convertListToCode(selectedRegion, regionListToCodeTable);
    }
    public int convertJobListToJobCode(List<String> selectedJob) {
        return convertListToCode(selectedJob, jobListToCodeTable);
    }

    public int convertAgeListToAgeCode(List<String> selectedAge) {
        return convertListToCode(selectedAge, ageListToCodeTable);
    }

    public int convertGenderListToGenderCode(List<String> selectedGender) {
        return convertListToCode(selectedGender, genderListToCodeTable);
    }

    //==================== CODE TO STRING LIST ====================//

    // DB에서 code 값을 가지고 클라이언트로 보내줄때 비트연산통해서 해당 코드가 의미하는 항목들만 List에 넣어서 보내줌
    private List<String> convertCodeToList(int code, Map<Integer, String> codeTable) {
        if (code == ALL_CODE) return List.of(ALL_STRING);

        List<String> selectedList = new ArrayList<>();
        for (int curCode : codeTable.keySet()) {
            if ((code & curCode) == curCode) {
                selectedList.add(codeTable.get(curCode));
            }
        }
        return selectedList;
    }

    public List<String> convertRegionCodeToList(int regionCode){
        return  convertCodeToList(regionCode, regionCodeToListTable);
    }

    public List<String> convertJobCodeToList(int jobCode) {
        return convertCodeToList(jobCode, jobCodeToListTable);
    }

    public List<String> convertAgeCodeToList(int ageCode) {
        return convertCodeToList(ageCode, ageCodeToListTable);
    }

    public List<String> convertGenderCodeToList(int genderCode) {
        return convertCodeToList(genderCode, genderCodeToListTable);
    }
}
