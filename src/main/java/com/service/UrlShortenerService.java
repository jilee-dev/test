package main.java.com.service;

public interface UrlShortenerService {
	//SHORTEN_URL�� ROW���� �����´�.
	int getUrlCount(String shortenUrl) throws Exception;
	//SHORTEN_URL�� �ش��ϴ� ORIGINAL_URL�� �����´�.
	String getOriginalUrl(String shortenUrl) throws Exception;
	//SHORTEN_URL, ORIGINAL_URL�� �����Ѵ�.
	int insertUrl(String shortenUrl, String originalUrl) throws Exception; 
}
