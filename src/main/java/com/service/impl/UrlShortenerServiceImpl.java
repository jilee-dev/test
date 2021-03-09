package main.java.com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import main.java.com.dao.UrlShortenerDao;
import main.java.com.service.UrlShortenerService;

@Service("urlShortenerService")
public class UrlShortenerServiceImpl implements UrlShortenerService {

	@Resource(name="urlShortenerDao")
	private UrlShortenerDao urlShortenerDao;
	
	@Override
	public int getUrlCount(String shortenUrl) throws Exception {
		return urlShortenerDao.getUrlCount(shortenUrl);
	}

	@Override
	public String getOriginalUrl(String shortenUrl) throws Exception {
		return urlShortenerDao.getOriginalUrl(shortenUrl);
	}

	@Override
	public int insertUrl(String shortenUrl, String originalUrl) throws Exception {
		return urlShortenerDao.insertUrl(shortenUrl, originalUrl);
	}

}
