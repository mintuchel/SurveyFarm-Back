<h1 align="center">
<a href="https://github.com/rong5026/animal_user" title="AwesomeCV Documentation">
    <img alt="AwesomeCV" src="https://github.com/user-attachments/assets/560d4d8a-a74b-4c6e-b3ce-add821b60e1c"/>
</a>
</h1>
<p align="center">
  "저비용-고효율" 가성비 설문조사 플랫폼
</p>

<div align="center">

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit%205-25A162?style=flat&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-4CAF50?style=flat&logo=mockito&logoColor=white)
![Mockaroo](https://img.shields.io/badge/Mockaroo-795548?style=flat&logoColor=white)
![Data-Faker](https://img.shields.io/badge/Data--Faker-6A5ACD?style=flat&logoColor=white)

</div>

# Service Introduction

본 서비스는 설문조사 의뢰자로부터 플랫폼 이용료를 받고, 수수료를 제외한 금액을 설문 참여자 수에 따라 분배하여 참여자들에게 포인트 형태로 보상을 지급하는 설문조사 플랫폼입니다.

의뢰자는 저렴한 비용으로 효과적인 설문을 진행할 수 있으며, 참여자는 설문에 응답함으로써 보상을 받을 수 있는 구조로 설계하였습니다.

현재 설문조사 시장에서는 다음과 같은 문제들이 존재합니다.

1. **과도한 설문 제작/대행 비용**: 평균 100만 원 이상에 달하는 설문 대행 수수료 부담
2. **참여자 모집의 어려움**: 의뢰자가 직접 홍보에 자원을 소모해야 하는 비효율성
3. **낮은 참여율**: 참여자에게 동기를 부여하지 못하는 구조

이러한 문제를 해결하기 위해 본 플랫폼은 다음과 같은 기능을 제공합니다.

1. **합리적인 가격의 설문 플랫폼**

   - 개인, 소규모 단체, 중소기업도 부담 없이 이용 가능한  
     저비용 설문 제작/배포 서비스 제공

2. **보상 기반 참여 유도 시스템**

   - 참여자는 설문 응답 시 포인트를 적립하고, 이를 기프티콘 등으로 교환 가능
   - 명확한 보상 체계를 통해 설문 참여 동기를 강화하고 높은 응답률을 유도

3. **효율적인 설문 노출 구조**

   - 인기 설문, 마감임박 설문 등 다양한 설문 테마 페이지를 통한 노출 기회 최대화
   - 설문조사의 시기와 특성에 따라 사용자에게 효과적으로 노출시켜 참여율 극대화

# ERD

![SurveyFarm](https://github.com/user-attachments/assets/9bbd1c81-4d26-48cf-9fec-ab841153e379)

# Architecture

![surveyfarm-architecture](https://github.com/user-attachments/assets/6665dc9c-5ff3-43c5-aca7-492e0efb4de6)

# Service UI

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
