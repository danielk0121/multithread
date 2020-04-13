# 개요
- ***배치성 작업*** 에 대해, 멀티 쓰레드 프로그래밍시 자주 접하는 케이스를 기능으로 구현해본다

# 스토리
- 일반적으로 java 서버 프로그램 작성시 요청에 대해 생성되는 모든 task 를 즉시 처리 하지는 않는다
- 서비스 요청에서 생성되는 task 들을 Queue 등을 사용해서 비동기 배치성으로 처리하는 경우가 많다
- 배치성으로 처리하는 경우, 일정 시간이 경과하거나, 일정 task 건수 등이 대기열에서 초과 되거나 등의 bulk process 처리 규칙을 적용 하는 경우가 대부분이라 생각된다
- 이런 잦은 반복 작업을 위한 bulkprocess 라이브러리 작성을 목표로 해본다 

# 간략한 규칙
- 익숙한 java 7 이상 POJO로 구현하는것을 1차 목표
- 추가 사용 라이브러리 최소로 사용
	- lombok, common-lang, common-collection, logback, unit-test 라이브러리 등 만 사용
- reactive stream 등의 event-driven 형태로 구현하는것이 최종 목표

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
