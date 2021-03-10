# tomcat9.0
 apache-tomcat-9.0.43.zip을 받아 압축풀고, tomcat 서버 세팅.

# tomcat server.xml

<Connector connectionTimeout="20000" port="8089" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>
<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" URIEncoding="UTF-8" />

