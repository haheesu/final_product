# myhome

### fourth  
- 파일 업로드 시스템 제작 중
- '추가'시 id와 name은 db에 업로드 되나 파일은 업로드 안됨
- Mapper의 int fileInsert 메서드의 sql구문에서 에러 

### fifth
- detail화면에서 파일 다운로드 링크 클릭 시 파일 다운로드 불가
- ERROR : [Ljava.lang.StackTraceElement;@6ec71e18
- filenotfoundexception 발생
- myhome\files에 파일이 저장되지 않는 현상


===================================
### sixth
- bootstrap 원페이지 사이트와 합침(zium_organization)
- 이미지 출력 안됨
- 캐러셀 출력 

### seventh
- fifth, sixth 문제점 미결
- list'추가' 404error 해결
- embed container 크기 80%로 줄이자 가운데 정렬 안됨
/
- 로고 이미지 출력 에러 로그
- 2020-10-30 13:09:49.867  WARN 24708 --- [io-8080-exec-10] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: "zium_bg_b.png"]
/
- fileUpdate :  첨부파일 수정 시 500 error
-  Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near '='UUelxEfFQeDj8BavmFKV31d6rEnvUYVH.jpg', fileOriginName='ponyo017.jpg', fileUrl=' at line 1
 /The error may exist in com/ziumks/organization/mapper/OrganizationMapper.java (best guess)
 /The error may involve com.ziumks.organization.mapper.OrganizationMapper.fileUpdate-Inline
 /The error occurred while setting parameters
 /SQL: update file setfilename=?, fileOriginName=?, fileUrl=? where id=?
