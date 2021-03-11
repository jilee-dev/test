package main.java.com.controller;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.com.service.UrlShortenerService;

@Controller
public class UrlShortenerController {

	@Resource(name="urlShortenerService")
	private UrlShortenerService urlShortenerService;
	
	@GetMapping(value="/short")
	public String UrlShortener(@RequestParam(required = false) String url, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(url != null) {
			System.out.println("request url : "+url);
			request.setAttribute("url", url);
			//´ë¼Ò¹®ÀÚ °ü°è¾øÀÌ http:// ¶Ç´Â https://·Î ½ÃÀÛµÇ´ÂÁö È®ÀÎ(ÃÖ¼Ò 7ÀÚ¸® ÀÌ»ó) ÈÄ, 
			//ÀÏÄ¡ÇÒ °æ¿ì : ¼Ò¹®ÀÚ·Î º¯Çü.
			//ÀÏÄ¡ÇÏÁö ¾ÊÀ» °æ¿ì : 'http://' Ãß°¡.
			if(url.length() > 7) {
				if(("http://".equals(url.substring(0, 7).toLowerCase()))) {
					if(!"http://".equals(url.substring(0, 7))) {
						url = "http://" + url.substring(7, url.length());
					}
				}else if(("https://".equals(url.substring(0, 8).toLowerCase()))) {
					if(!"https://".equals(url.substring(0, 8))) {
						url = "https://" + url.substring(8, url.length());
					}
				}else {
					url = "http://" + url;
				}
			}else {
				url = "http://" + url;
			}
			
			//CRC32 ¾ÏÈ£È­¸¦ ÅëÇØ 8ÀÚ¸®ÀÇ ´ÜÃà URLÀ» ¸¸µê. 
			Checksum crc = new CRC32();
			crc.update(url.getBytes(), 0, url.length());
			long convertUrl = crc.getValue();
			String shortenUrl = Long.toHexString(convertUrl);
			
			String path = request.getRequestURL().substring(0, request.getRequestURL().length()-5);
			
			//ÀÌ¹Ì ÀúÀåµÈ SHORTEN_URL Á¸ÀçÇÏ´ÂÁö 
			//Á¸Àç½Ã : INSERT ¾øÀ½.
			//Á¸ÀçÇÏÁö ¾ÊÀ»½Ã : INSERT ÁøÇà.
			if(urlShortenerService.getUrlCount(shortenUrl) > 0) {
				request.setAttribute("shortenUrl", path+shortenUrl);
			}else {
				if(urlShortenerService.insertUrl(shortenUrl, url) > 0) {
					request.setAttribute("shortenUrl", path+shortenUrl);
				}else {
					request.setAttribute("shortenUrl", "Ã³¸® ½ÇÆÐ");
				}
			}
		}
		return "main/Main";
	}
	
	@GetMapping(value="/{shortenUrl:.+}")
	public void rediectUrl(@PathVariable String shortenUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//SHORTEN_URL¿¡ ÇØ´çÇÏ´Â ORIGINAL_URL·Î REDIRECT
		if(!"".equals(shortenUrl)) {
			String originalUrl = urlShortenerService.getOriginalUrl(shortenUrl);
			if(!"".equals(originalUrl)) {
				String convertUrl = originalUrl;
				
				//ÇÑ±Û Æ÷ÇÔ½Ã encoding ÁøÇà
				Pattern pattern = Pattern.compile("[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+");
				Matcher matcher = pattern.matcher(convertUrl);
				while(matcher.find()) {
					convertUrl = convertUrl.replace(matcher.group(), URLEncoder.encode(matcher.group(), "UTF-8"));
				}

				response.sendRedirect(convertUrl);				
			}else {
				response.sendRedirect("http://www.naver.com");
			}
		}
	}
}
