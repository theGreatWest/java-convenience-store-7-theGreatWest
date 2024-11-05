# 프로젝트명
편의점: 결제 시스템 개발
<hr>

# 기능 개요
- 할인 혜택, 재고 상황에 대한 정보를 resources 폴더 하위의 products.md, promotions.md 파일에서 읽어와 저장
<hr>

# 예외 처리
## [ 파일 입출력 ] 
요구사항에 없는 항목이지만 확장성을 고려해 예외 처리해 주었습니다.
- [x] resource 패키지 내부에 있는 파일이 아닌 경우
- [x] 파일 이름이 잘못 입력되었을 경우
<hr>

# 기능 목록
## [ 파일 입출력 ]
### 상세 기능 및 작동 순서
- [x] 할인 혜택 정보, 재고 상황 정보를 불러오는 메서드를 나누어 작성한다.
- [x] 파일의 경로를 enum 객체를 활용해 얻는다.
- [x] 첫 번째 줄은 헤더이므로 삭제한다.
- [x] 정보를 행 단위로 읽어 List<String> 형태로 저장해 반환한다.
<hr>

# 테스트 계획
- [ ] 