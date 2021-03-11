# URL 단축
 
 URL을 입력받아 짧게 줄여주고(8 Character), Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortenign Service
 
- java1.8
- spring5
- mysql(aws ec2)


** tomcat 설정

1. apache-tomcat-9.0.43.zip 또는 apache-tomcat-9.0.43.tar.gz을 받아 압축풀고, tomcat 서버 세팅.

2. server.xml 추가(한글 인코딩)
<Connector connectionTimeout="20000" port="8089" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>


** URL Shortenign Service 이용 방법. 
1. /short 접근 (ex. localhost:8080/short)
2. 단축할 url 입력
3. Go 버튼 클릭
4. 단축된 url 이 생성되면 해당 값 복사하여 주소창 입력
