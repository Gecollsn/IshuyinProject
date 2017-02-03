package com.ishuyin.gecollsn.http;

import com.google.gson.Gson;
import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.domain.BannerInfo;
import com.ishuyin.gecollsn.db.BookInfo;
import com.ishuyin.gecollsn.base.IDataWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gecollsn on 10/23/2016.
 */

public class Api_accueil extends Api {
    /**
     * 获取精选页的内容
     *
     * @param writer
     */
    public static void retrieveHandpickDetail(final IDataWriter writer) {
        Map<String, String> params = getParams();
        get(params, HAND_PICK_DETAIL, new JsonCallback() {
            @Override
            public void onMainSuccess(JSONObject jsonObject) {
                try {
                    List<BannerInfo> banners = new ArrayList<>();
                    List<BookInfo> recommends = new ArrayList<>();
                    List<BookInfo> hots = new ArrayList<>();
                    List<BookInfo> news = new ArrayList<>();
                    JSONArray jaBanner = jsonObject.getJSONArray(AC.type.TAG_BANNER);
                    for (int i = 0; i < jaBanner.length(); i++) {
                        String jsonString = jaBanner.getJSONObject(i).toString();
                        Gson gson = new Gson();
                        BannerInfo bannerInfo = gson.fromJson(jsonString, BannerInfo.class);
                        banners.add(bannerInfo);
                    }
                    JSONArray jaRecommend = jsonObject.getJSONArray(AC.type.TAG_RECOMMEND);
                    for (int i = 0; i < jaRecommend.length(); i++) {
                        String jsonString = jaRecommend.getJSONObject(i).toString();
                        Gson gson = new Gson();
                        BookInfo bookInfo = gson.fromJson(jsonString, BookInfo.class);
                        recommends.add(bookInfo);
                    }
                    JSONArray jaHot = jsonObject.getJSONArray(AC.type.TAG_HOT);
                    for (int i = 0; i < jaHot.length(); i++) {
                        String jsonString = jaHot.getJSONObject(i).toString();
                        Gson gson = new Gson();
                        BookInfo bookInfo = gson.fromJson(jsonString, BookInfo.class);
                        hots.add(bookInfo);
                    }
                    JSONArray jaNew = jsonObject.getJSONArray(AC.type.TAG_NEW);
                    for (int i = 0; i < jaNew.length(); i++) {
                        String jsonString = jaNew.getJSONObject(i).toString();
                        Gson gson = new Gson();
                        BookInfo bookInfo = gson.fromJson(jsonString, BookInfo.class);
                        news.add(bookInfo);
                    }

                    writer.writeData(banners, recommends, hots, news);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
