# 개요
- java 멀티 쓰레드 프로그래밍 스터디
- ***배치성 작업*** 에 대해, 멀티 쓰레드 프로그래밍시 자주 접하는 케이스를 기능으로 구현해본다
- 익숙한 java 7로 구현하는것을 1차 목표, 이때 POJO로 구현한다
- 추가하는 라이브러리는 최소한으로 한다
	- lombok, common-lang, common-collection, logback, unit-test 라이브러리 등
- reactive stream 등의 event-driven 형태로 구현하는것을 최종목표로 삼는다

# 참고 코드
- 엘라스틱 서치 bulk 라이브러리
```
// bulk listener 생성
class BulkProcessorListener implements BulkProcessor.Listener {
    @Override
    public void beforeBulk(long executionId, BulkRequest bulkRequest) {
        log.debug("beforeBulk, executionId: {}", executionId);
    }
    @Override
    public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
    		log.debug("afterBulk , executionId: {}", executionId);
    }
    @Override
    public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable e) {
    	log.error("afterBulk, error, executionId: " + executionId, e);
    }
}

// bulk 생성
BulkProcessorListener bulkProcessorListener = new BulkProcessorListener();
bulkProcessor = BulkProcessor.builder(clientFactory.getClient(), bulkProcessorListener)
        .setBulkActions(bulkActionSize)
        .setBulkSize(new ByteSizeValue(50, ByteSizeUnit.MB))
        .setConcurrentRequests(1)
        .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 10))                
        .build()
        ;

// bulk 사용
bulkProcessor.add(task);
bulkProcessor.flush();
```

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
