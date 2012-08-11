package com.cdm;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.cdm.view.Renderer;

public class Highscore {
	private static final int BUFSIZE = 1024 * 100;
	private String url = "http://0.0.0.0:9292";
	private Renderer renderer = new Renderer();

	public static class HighscoreAccessException extends Exception {

		public HighscoreAccessException(Exception e) {
			super(e);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -4486188937322255607L;

	}

	public static class Entry {

		Entry(String pName, Integer pValue) {
			name = pName;
			value = pValue;
		}

		public String name;
		public Integer value;
		
		public String toString() {
			String s=name;
			while(s.length()<20)
				s+=" ";
			s+=value;
			while(s.length()<24)
				s+=" ";
			return s;
		}
	}

	public static List<Entry> main(String[] args) throws HighscoreAccessException {
		Highscore s = new Highscore();
		Entry n = new Entry("HI", 999);
		List<Entry> vals = s.post(n);
		System.out.println(vals);
		return vals;
		
	}

	public void setUrl(String pUrl) {
		url = pUrl;
	}

	public List<Entry> post(Entry entry) throws HighscoreAccessException {
		try {
			String curl = url + "/save?score=" + entry.value + "&name="
					+ URLEncoder.encode(entry.name, "utf8");
			InputStream response = new URL(curl).openStream();

			return readFromInputStream(response);

		} catch (Exception e) {
			throw new HighscoreAccessException(e);
		}

	}

	public List<Entry> read() throws HighscoreAccessException {
		try {
			InputStream response = new URL(url).openStream();

			return readFromInputStream(response);

		} catch (Exception e) {
			throw new HighscoreAccessException(e);
		}

	}

	private List<Entry> readFromInputStream(InputStream response)
			throws IOException, JSONException {
		List<Entry> scores = new ArrayList<Entry>();
		InputStreamReader isr = new InputStreamReader(response);

		char[] buf = new char[BUFSIZE];
		int len = isr.read(buf, 0, BUFSIZE);

		StringBuilder builder = new StringBuilder();
		builder.append(buf, 0, len);
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONArray finalResult = new JSONArray(tokener);
		for (int i = 0; i < finalResult.length(); i++) {
			Object o = finalResult.get(i);
			if (o instanceof JSONObject) {
				JSONObject jo = (JSONObject) o;
				String name = jo.getString("name");
				Integer value = jo.getInt("value");
				scores.add(new Entry(name, value));
			}
		}
		isr.close();
		response.close();
		return scores;
	}
}
