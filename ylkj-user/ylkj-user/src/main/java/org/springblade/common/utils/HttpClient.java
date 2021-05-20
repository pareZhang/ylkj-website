package org.springblade.common.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

@Component
public class HttpClient {

	@Resource
	private OkHttpClient okHttpClient;

	/**
	 * http post请求
	 * @param url
	 * @param json
	 * @return
	 */
	public  String post(String url, Map<String, String> headers, String json) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request.Builder builder = new Request.Builder();
		if(headers != null){
			for (String key: headers.keySet()){
				builder.addHeader(key,headers.get(key));
			}
		}
		Request request = builder.url(url).post(requestBody).build();
		return execNewCall(request);
	}

	/**
	 * post
	 *
	 * @param url    请求的url
	 * @param params post form 提交的参数
	 * @return
	 */
	public  String post(String url, Map<String, String> headers, Map<String, String> params) {
		FormBody.Builder formBody = new FormBody.Builder();
		//添加参数
		if (params != null && params.keySet().size() > 0) {
			for (String key : params.keySet()) {
				formBody.add(key, params.get(key));
			}
		}
		Request.Builder builder = new Request.Builder();
		if(headers!=null){
			for (String key: headers.keySet()){
				builder.addHeader(key,headers.get(key));
			}
		}
		Request request = builder.url(url).post(formBody.build()).build();
		return execNewCall(request);
	}

	public String getQueryString(String url,Map<String, String> params){
		StringBuffer sb = new StringBuffer(url);
		if (params != null && params.keySet().size() > 0) {
			boolean firstFlag = true;
			Iterator iterator = params.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry entry = (Map.Entry<String,String>) iterator.next();
				if(firstFlag) {
					sb.append("?" + entry.getKey() + "=" + entry.getValue());
					firstFlag = false;
				}else {
					sb.append("&" + entry.getKey() + "=" + entry.getValue());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * get 请求
	 * @param url
	 * @return
	 */
	public String get(String url,Map<String, String> params){
		String str = getQueryString(url,params);
		Request request = new Request.Builder().url(str).build();
		return execNewCall(request);
	}

	/**
	 * 调用okhttp的newCall方法
	 * @param request
	 * @return
	 */
	private  String execNewCall(Request request){
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return "";
	}

}
