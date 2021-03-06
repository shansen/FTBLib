package com.feed_the_beast.ftbl.lib.io;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection
{
	public final RequestMethod type;
	public final String url;
	public byte[] data;

	public HttpConnection(RequestMethod t, String s)
	{
		type = t;
		url = s;
	}

	public Response connect() throws Exception
	{
		long startTime = System.currentTimeMillis();

		if (type == RequestMethod.FILE)
		{
			FileInputStream is = new FileInputStream(url);
			return new Response(RequestMethod.FILE, System.currentTimeMillis() - startTime, 200, is);
		}

		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod(type.name());
		con.setRequestProperty("User-Agent", "HTTP/1.1");
		con.setDoInput(true);

		if (data != null && data.length > 0)
		{
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(data, 0, data.length);
			os.flush();
			os.close();
		}

		int responseCode = con.getResponseCode();
		return new Response(type, System.currentTimeMillis() - startTime, responseCode, con.getInputStream());
	}
}