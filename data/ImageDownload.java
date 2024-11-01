package kr.top2blue.autumn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownload {
	@SuppressWarnings("deprecation")
	public static void download(String urlAddress, String path) {
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			URL url = new URL(urlAddress);
			is = url.openStream();
			String filename = urlAddress.substring(urlAddress.lastIndexOf("/")+1);
			System.out.println(filename);
			fos = new FileOutputStream("src/main/resources/" + path + "/" + filename);
			byte[] data = new byte[2048];
			int n = 0;
			while((n=is.read(data))>0) {
				fos.write(data, 0, n);
				fos.flush();
			}
			System.out.println(filename + "다운로드 완료!!!");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		for(int i=1;i<=6;i++) {
			String url = String.format("https://www.seoul.go.kr/res_newseoul_story/autumn/images/ico/ico-visit%1d.png", i);
			download(url,"images");
		}
		for(int i=1;i<=103;i++) {
			String url = String.format("https://www.seoul.go.kr/res_newseoul_story/autumn/roadimg/%03d.jpg", i);
			download(url,"images");
		}
	}
}
