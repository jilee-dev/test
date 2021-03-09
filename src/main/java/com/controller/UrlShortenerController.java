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
			//��ҹ��� ������� http:// �Ǵ� https://�� ���۵Ǵ��� Ȯ��(�ּ� 7�ڸ� �̻�) ��, 
			//��ġ�� ��� : �ҹ��ڷ� ����.
			//��ġ���� ���� ��� : 'http://' �߰�.
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
			
			//CRC32 ��ȣȭ�� ���� 8�ڸ��� ���� URL�� ����. 
			Checksum crc = new CRC32();
			crc.update(url.getBytes(), 0, url.length());
			long convertUrl = crc.getValue();
			String shortenUrl = Long.toHexString(convertUrl);
			
			//�̹� ����� SHORTEN_URL �����ϴ��� 
			//����� : INSERT ����.
			//�������� ������ : INSERT ����.
			if(urlShortenerService.getUrlCount(shortenUrl) > 0) {
				request.setAttribute("shortenUrl", "http://localhost:8080/"+shortenUrl);
			}else {
				if(urlShortenerService.insertUrl(shortenUrl, url) > 0) {
					request.setAttribute("shortenUrl", "http://localhost:8080/"+shortenUrl);
				}else {
					request.setAttribute("shortenUrl", "ó�� ����");
				}
			}
			
			
		}
		return "main/Main";
	}
	
	@GetMapping(value="/{shortenUrl:.+}")
	public void rediectUrl(@PathVariable String shortenUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//SHORTEN_URL�� �ش��ϴ� ORIGINAL_URL�� REDIRECT
		if(!"".equals(shortenUrl)) {
			response.sendRedirect(urlShortenerService.getOriginalUrl(shortenUrl));
		}
	}
}