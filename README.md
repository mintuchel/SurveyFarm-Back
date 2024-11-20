<h1 align="center">
<a href="https://github.com/rong5026/animal_user" title="AwesomeCV Documentation">
    <img alt="AwesomeCV" src="https://github.com/user-attachments/assets/560d4d8a-a74b-4c6e-b3ce-add821b60e1c"/>
</a>
</h1>

<p align="center">
  저비용-고효율 가성비 설문조사 플랫폼 서비스입니다
</p>

<div align="center">

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit%205-25A162?style=flat&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-FF9900?style=flat&logo=mockito&logoColor=white)
![Data Faker](https://img.shields.io/badge/Data%20Faker-8A2BE2?style=flat&logoColor=white)
![Mockaroo](https://img.shields.io/badge/Mockaroo-AA5439?style=flat&logoColor=white)

</div>


# Service Introduction

---
본 서비스는 설문조사 의뢰자로부터 플랫폼 이용료를 받고, 이 중 수수료를 제외한 금액을 설문 참가자 수에 따라 분배하여 각 설문 참여자들에게 참여에 대한 보상을 포인트 형식으로 지급하는 설문조사 플랫폼 서비스입니다.

저희는 다음과 같은 현 설문조사 시장의 문제점들을 파악하고

1) 평균 100만원이 넘는 대행업체 또는 설문제작 업체 의뢰수수료 비용 
2) 의뢰자들이 참여자 모집과 설문 홍보에 더 많은 자원을 소비하는 비효율적인 상황 
3) 대중들의 설문 참여 동기를 충분히 불러일으키지 못하는 상황

아래와 같은 방식으로 문제를 해결하였습니다.

1) 고비용의 설문 제작 및 대행 업체 이용이 부담스러운 개인, 소규모 단체, 중소기업들이 편리하고 비용 부담 없이 활용할 수 있는 저비용 설문조사 플랫폼을 제공
2) 참여자들이 설문에 참여함에 따라 적립한 포인트를 기프티콘과 교환할 수 있게 하여 설문 참여 동기를 강화
3) 인기설문, 마감임박설문등의 다양한 페이지들을 통해 설문들이 유저들에게 최대한 노출될 수 있는 환경을 제공

# ERD

---
![SurveyFarm](https://github.com/user-attachments/assets/9bbd1c81-4d26-48cf-9fec-ab841153e379)

# Architecture

---
![surveyfarm-architecture](https://github.com/user-attachments/assets/6665dc9c-5ff3-43c5-aca7-492e0efb4de6)

# Service UI

---
## 1. 메인 페이지
<img width="1468" alt="홈페이지" src="https://github.com/user-attachments/assets/8a0ca770-f3c5-4e77-bbd8-7ca8ec3a96b0">

- 인기설문조사 및 마감임박설문조사를 노출시켜 각 설문조사들이 추가적으로 노출될 수 있는 환경 제공
- 설문조사 의뢰 버튼 클릭 시 아래의 설문 제작 페이지로 이동


## 2. 설문 필터링 설정
<img width="1468" alt="설문조건설정" src="https://github.com/user-attachments/assets/e1cb117b-2f1c-47a9-bc60-45dc85e47001">

- 다양한 필터링 조건 제공
- 의뢰자가 설문조사에 참여가능한 표본집단을 손쉽게 특정 가능

## 3. 설문 제작
<img width="1468" alt="설문제작" src="https://github.com/user-attachments/assets/c18e88b9-8fcf-4589-b742-77dead7da74b">

- 편리하고 직관적인 UI를 통해 누구나 손쉽게 설문 제작 가능
- 객관식, 주관식, 선형 배율, 체크 박스, 드롭다운등 다양한 종류의 선지 및 질문 유형 제공
- 의뢰자에게 자유도 높은 선지 구성 환경을 제공

## 4. 참여자 답변 분석
<img width="1468" alt="주관식 답변 분석" src="https://github.com/user-attachments/assets/8a2db162-0dc2-412d-affb-b4c3657ae676">

- 데이터를 시각화하여 직관적인 UI로 설문 결과를 파악 가능
- 형태소 분석과 단어 빈도 분석을 바탕으로 연관성을 시각화한 워드클라우드 제공