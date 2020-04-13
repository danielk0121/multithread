# 개요
- java 멀티 쓰레드 프로그래밍 스터디
- ***배치성 작업*** 에 대해, 멀티 쓰레드 프로그래밍시 자주 접하는 케이스를 기능으로 구현해본다
- 익숙한 java 7로 구현하는것을 1차 목표, 이때 POJO로 구현한다
- 추가하는 라이브러리는 최소한으로 한다
	- lombok, common-lang, common-collection, logback, unit-test 라이브러리 등
- reactive stream 등의 event-driven 형태로 구현하는것을 최종목표로 삼는다

# TODO - done
- maven 세팅
- git 세팅

# TODO
- task thread pool 기능
	- worker 수 고정 기능
	- worker 대기 최대 수 고정 기능
	- active, idle 제한 조건 기능
- task 특성의 일정 조건 만족 시 실행 기능
	- task 생성 후 시간 초과 조건
	- task 개수 초과 조건
	- task 용량 초과 조건
