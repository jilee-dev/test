package main.java.com.controller;

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
			request.setAttribute("url", url);
			//대소문자 관계없이 http:// 또는 https://로 시작되는지 확인(최소 7자리 이상) 후, 
			//일치할 경우 : 소문자로 변형.
			//일치하지 않을 경우 : 'http://' 추가.
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
			
			//CRC32 암호화를 통해 8자리의 단축 URL을 만듦. 
			Checksum crc = new CRC32();
			crc.update(url.getBytes(), 0, url.length());
			long convertUrl = crc.getValue();
			String shortenUrl = Long.toHexString(convertUrl);
			
			//이미 저장된 SHORTEN_URL 존재하는지 
			//존재시 : INSERT 없음.
			//존재하지 않을시 : INSERT 진행.
			if(urlShortenerService.getUrlCount(shortenUrl) > 0) {
				request.setAttribute("shortenUrl", "http://localhost:8080/"+shortenUrl);
			}else {
				if(urlShortenerService.insertUrl(shortenUrl, url) > 0) {
					request.setAttribute("shortenUrl", "http://localhost:8080/"+shortenUrl);
				}else {
					request.setAttribute("shortenUrl", "처리 실패");
				}
			}
			
			
		}
		return "main/Main";
	}
	
	@GetMapping(value="/{shortenUrl:.+}")
	public void rediectUrl(@PathVariable String shortenUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//SHORTEN_URL에 해당하는 ORIGINAL_URL로 REDIRECT
		if(!"".equals(shortenUrl)) {
			response.sendRedirect(urlShortenerService.getOriginalUrl(shortenUrl));
		}
	}
}
