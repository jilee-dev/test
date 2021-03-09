package main.java.com.service;

public interface UrlShortenerService {
	//SHORTEN_URL의 ROW수를 가져온다.
	int getUrlCount(String shortenUrl) throws Exception;
	//SHORTEN_URL에 해당하는 ORIGINAL_URL을 가져온다.
	String getOriginalUrl(String shortenUrl) throws Exception;
	//SHORTEN_URL, ORIGINAL_URL을 저장한다.
	int insertUrl(String shortenUrl, String originalUrl) throws Exception; 
}
